package edu.illinois.cs.cs125.binaryclock;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import edu.illinois.cs.cs125.binaryclock.lib.BinaryClock;

/**
 * Dot Mode activity.
 */
public class DotMode extends AppCompatActivity {
    /**
     * Default logging tag for messages from Dot Mode.
     */
    private static final String TAG = "BinaryClock:DotMode";

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

        setContentView(R.layout.activity_dot_mode);

        Bundle bundle = getIntent().getExtras();
        String timezone;
        if (bundle != null) {
            timezone = bundle.getString("timeZone");
            startAPICall(timezone);
        }

        Log.i(TAG, "Our app was created");

        final Button selectTimeZone = findViewById(R.id.dotModeSelectTimeZone);

        selectTimeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked Select Time Zone");
                Intent intent = new Intent(DotMode.this, DotModeTimeZoneActivity.class);
                startActivity(intent);
            }
        });

        final Button mode = findViewById(R.id.numericalMode);
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked Switch Mode");
                Intent intent2 = new Intent(DotMode.this, MainActivity.class);
                startActivity(intent2);
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
                                    TextView timeView = findViewById(R.id.dotTime);
                                    TextView binaryTimeView = findViewById(R.id.dotBinaryTime);
                                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                    JsonParser jsonParser = new JsonParser();
                                    JsonElement jsonElement = jsonParser.parse(response.toString());
                                    String prettyJsonString = gson.toJson(jsonElement);
                                    String time = BinaryClock.getTime(prettyJsonString);
                                    String binaryTime = BinaryClock.convertToBinaryTime(time);
                                    timeView.setText(time);
                                    binaryTimeView.setText(binaryTime);
                                    showDots(binaryTime);
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
        Log.d(TAG, "Finished API Call");
        final Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Update time");
                TextView timeView = findViewById(R.id.dotTime);
                TextView binaryTimeView = findViewById(R.id.dotBinaryTime);
                String time = timeView.getText().toString();
                String newTime = BinaryClock.addTime(time);
                timeView.setText(newTime);
                String binaryTime = BinaryClock.convertToBinaryTime(newTime);
                binaryTimeView.setText(binaryTime);
                showDots(binaryTime);
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    void showDots(String binaryTime) {
        int hour16 = Integer.parseInt(binaryTime.substring(0,1));
        int hour8 = Integer.parseInt(binaryTime.substring(1,2));
        int hour4 = Integer.parseInt(binaryTime.substring(2,3));
        int hour2 = Integer.parseInt(binaryTime.substring(3,4));
        int hour1 = Integer.parseInt(binaryTime.substring(4,5));

        int minute32 = Integer.parseInt(binaryTime.substring(6,7));
        int minute16 = Integer.parseInt(binaryTime.substring(7,8));
        int minute8 = Integer.parseInt(binaryTime.substring(8,9));
        int minute4 = Integer.parseInt(binaryTime.substring(9,10));
        int minute2 = Integer.parseInt(binaryTime.substring(10,11));
        int minute1 = Integer.parseInt(binaryTime.substring(11,12));

        int second32 = Integer.parseInt(binaryTime.substring(13,14));
        int second16 = Integer.parseInt(binaryTime.substring(14,15));
        int second8 = Integer.parseInt(binaryTime.substring(15,16));
        int second4 = Integer.parseInt(binaryTime.substring(16,17));
        int second2 = Integer.parseInt(binaryTime.substring(17,18));
        int second1 = Integer.parseInt(binaryTime.substring(18));

        TextView hour_16 = findViewById(R.id.hour_16);
        TextView hour_8 = findViewById(R.id.hour_8);
        TextView hour_4 = findViewById(R.id.hour_4);
        TextView hour_2 = findViewById(R.id.hour_2);
        TextView hour_1 = findViewById(R.id.hour_1);

        TextView minute_32 = findViewById(R.id.minute_32);
        TextView minute_16 = findViewById(R.id.minute_16);
        TextView minute_8 = findViewById(R.id.minute_8);
        TextView minute_4 = findViewById(R.id.minute_4);
        TextView minute_2 = findViewById(R.id.minute_2);
        TextView minute_1 = findViewById(R.id.minute_1);

        TextView second_32 = findViewById(R.id.second_32);
        TextView second_16 = findViewById(R.id.second_16);
        TextView second_8 = findViewById(R.id.second_8);
        TextView second_4 = findViewById(R.id.second_4);
        TextView second_2 = findViewById(R.id.second_2);
        TextView second_1 = findViewById(R.id.second_1);

        if (hour16 == 1) {
            hour_16.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            hour_16.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (hour8 == 1) {
            hour_8.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            hour_8.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (hour4 == 1) {
            hour_4.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            hour_4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (hour2 == 1) {
            hour_2.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            hour_2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (hour1 == 1) {
            hour_1.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            hour_1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (minute32 == 1) {
            minute_32.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            minute_32.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (minute16 == 1) {
            minute_16.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            minute_16.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (minute8 == 1) {
            minute_8.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            minute_8.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (minute4 == 1) {
            minute_4.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            minute_4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (minute2 == 1) {
            minute_2.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            minute_2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (minute1 == 1) {
            minute_1.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            minute_1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (second32 == 1) {
            second_32.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            second_32.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (second16 == 1) {
            second_16.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            second_16.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (second8 == 1) {
            second_8.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            second_8.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (second4 == 1) {
            second_4.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            second_4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (second2 == 1) {
            second_2.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            second_2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (second1 == 1) {
            second_1.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            second_1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }
}
