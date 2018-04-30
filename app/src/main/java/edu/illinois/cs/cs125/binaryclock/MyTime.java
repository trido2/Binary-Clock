package edu.illinois.cs.cs125.binaryclock;

import android.content.Intent;
import android.util.Log;

import edu.illinois.cs.cs125.binaryclock.MainActivity;
import edu.illinois.cs.cs125.binaryclock.TimeZoneActivity;

public class MyTime {

    private String name;

    private Intent intent;


    public MyTime(String setName, TimeZoneActivity timeZoneActivity) {
        this.name = setName;
        this.intent = new Intent(timeZoneActivity, TimeZoneActivity.class);
    }

    public void setIntent(Intent setIntent) {
        this.intent = setIntent;
    }

    public Intent getIntent() {
        return this.intent;
    }
}
