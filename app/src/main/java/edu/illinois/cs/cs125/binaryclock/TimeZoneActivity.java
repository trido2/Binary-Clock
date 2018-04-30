package edu.illinois.cs.cs125.binaryclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TimeZoneActivity extends AppCompatActivity {

    private static final String TAG = "BinaryClock:TimeZone";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MyTime hawaiiTime = new MyTime("hawaii", this);
        final MyTime alaskaTime = new MyTime("alaska", this);
        final MyTime pacificTime = new MyTime("pacific", this);
        final MyTime mountainTime = new MyTime("mountain", this);
        final MyTime centralTime = new MyTime("central", this);
        final MyTime easternTime = new MyTime("eastern", this);
        Log.d(TAG, "Inside Time Zone");
        setContentView(R.layout.activity_time_zone_activity);

        Log.i(TAG, "Select Time Zone");

        final Button hawaii = findViewById(R.id.hawaii);
        hawaii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pressed Hawaii");
                Intent intent =  new Intent(TimeZoneActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("timeZone", "Pacific/Honolulu");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        final Button alaska = findViewById(R.id.alaska);
        alaska.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pressed Alaska");
                Intent intent =  new Intent(TimeZoneActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("timeZone", "America/Anchorage");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        final Button pacific = findViewById(R.id.pacific);
        pacific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pressed Pacific");
                Intent intent =  new Intent(TimeZoneActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("timeZone", "America/Los_Angeles");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        final Button mountain = findViewById(R.id.mountain);
        mountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pressed Mountain");
                Intent intent =  new Intent(TimeZoneActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("timeZone", "America/Denver");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        final Button central = findViewById(R.id.central);
        central.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pressed Central");
                Intent intent =  new Intent(TimeZoneActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("timeZone", "America/Chicago");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        final Button eastern = findViewById(R.id.eastern);
        eastern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pressed Eastern");
                Intent intent =  new Intent(TimeZoneActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("timeZone", "America/New_York");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
