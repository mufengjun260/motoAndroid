package com.bolo4963gmail.motoandroid.javaClass;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10733 on 2016/8/2.
 */
public class ActivityCollector {

    public static List<AppCompatActivity> activities = new ArrayList<>();

    public static void addActivity(AppCompatActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (AppCompatActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        System.exit(0);
    }
    
}
