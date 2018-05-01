package edu.illinois.cs.cs125.binaryclock;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Main class for UI.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Default logging tag for messages from the main activity.
     */
    private static final String TAG = "BinaryClock:Main";

    private static RequestQueue requestQueue;

    private String timeZone;

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

        Bundle bundle = getIntent().getExtras();
        String timezone = null;
        if (bundle != null) {
            timezone = bundle.getString("timeZone");
            startAPICall(timezone);
        }

        Log.i(TAG, "Our app was created");

        final Button selectTimeZone = findViewById(R.id.timezone);

        selectTimeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked Select Time Zone");
                Intent intent = new Intent(MainActivity.this, TimeZoneActivity.class);
                startActivity(intent);
            }
        });

        final Button mode = findViewById(R.id.mode);
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked Switch Mode");
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Closing app");
    }

    /**
     * Make a call to the time API.
     */
    void startAPICall(String timeZone) {
        if (timeZone != null) {
            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        "http://api.timezonedb.com/v2/get-time-zone?key=" + BuildConfig.API_KEY + "&format=json&by=zone&zone="
                                + timeZone,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d(TAG, response.toString(2));
                                    TextView timeView = findViewById(R.id.time);
                                    TextView binaryTimeView = findViewById(R.id.binaryTime);
                                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                    JsonParser jsonParser = new JsonParser();
                                    JsonElement jsonElement = jsonParser.parse(response.toString());
                                    String prettyJsonString = gson.toJson(jsonElement);
                                    String time = getTime(prettyJsonString);
                                    String binaryTime = convertToBinaryTime(time);
                                    timeView.setText(time);
                                    binaryTimeView.setText(binaryTime);
                                    binaryTimeView.setVisibility(View.VISIBLE);
                                    finishAPICall();
                                } catch (JSONException ignored) {
                                }
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

    void finishAPICall() {
        Log.d(TAG, "Finish API Call");
        final Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Update time");
                TextView timeView = findViewById(R.id.time);
                TextView binaryTimeView = findViewById(R.id.binaryTime);
                String time = timeView.getText().toString();
                String newTime = addTime(time);
                timeView.setText(newTime);
                String binaryTime = convertToBinaryTime(newTime);
                binaryTimeView.setText(binaryTime);
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public static String getTime(final String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(jsonString).getAsJsonObject();
        String caption = result.get("formatted").getAsString();
        return caption.substring(11);
    }

    public static String addTime(final String timeString) {
        String hourString = timeString.substring(0, 2);
        String minuteString = timeString.substring(3, 5);
        String secondString = timeString.substring(6, 8);
        int hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);
        int second = Integer.parseInt(secondString);
        second++;
        if (second >= 60) {
            second -= 60;
            minute++;
        }
        if (minute >= 60) {
            minute -= 60;
            hour++;
        }
        if (hour >= 24) {
            hour -= 24;
        }
        if (hour < 10) {
            hourString = "0" + Integer.toString(hour);
        } else {
            hourString = Integer.toString(hour);
        }
        if (minute < 10) {
            minuteString = "0" + Integer.toString(minute);
        } else {
            minuteString = Integer.toString(minute);
        }
        if (second < 10) {
            secondString = "0" + Integer.toString(second);
        } else {
            secondString = Integer.toString(second);
        }
        return hourString + ":" + minuteString + ":" + secondString;
    }

    public static String convertToBinaryTime(String timeString) {
        String hourString = timeString.substring(0, 2);
        String minuteString = timeString.substring(3, 5);
        String secondString = timeString.substring(6, 8);
        int hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);
        int second = Integer.parseInt(secondString);
        String binaryHour = Integer.toBinaryString(hour);
        while (binaryHour.length() < 5) {
            binaryHour = "0" + binaryHour;
        }
        String binaryMinute = Integer.toBinaryString(minute);
        while (binaryMinute.length() < 6) {
            binaryMinute = "0" + binaryMinute;
        }
        String binarySecond = Integer.toBinaryString(second);
        while (binarySecond.length() < 6) {
            binarySecond = "0" + binarySecond;
        }
        return binaryHour + ":" + binaryMinute + ":" + binarySecond;
    }
}
