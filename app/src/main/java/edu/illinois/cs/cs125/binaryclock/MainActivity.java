package edu.illinois.cs.cs125.binaryclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Main class for UI.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Default logging tag for messages from the main activity.
     */
    private static final String TAG = "BinaryClock:Main";

    private static RequestQueue requestQueue;

    /**
     * Run when this activity comes to the foreground.
     *
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Opening app");

        // Set up queue for API requests
        requestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_main);

        startAPICall();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Closing app");
    }

    /**
     * Make a call to the time API.
     */
    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "http://api.timezonedb.com/v2/get-time-zone?key=" + BuildConfig.API_KEY + "&format=json&by=zone&zone=America/Chicago",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d(TAG, response.toString(2));
                                TextView textView = findViewById(R.id.time);
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                JsonParser jsonParser = new JsonParser();
                                JsonElement jsonElement = jsonParser.parse(response.toString());
                                String prettyJsonString = gson.toJson(jsonElement);
                                textView.setText(prettyJsonString);
                                textView.setVisibility(View.VISIBLE);
                            } catch (JSONException ignored) {}
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
