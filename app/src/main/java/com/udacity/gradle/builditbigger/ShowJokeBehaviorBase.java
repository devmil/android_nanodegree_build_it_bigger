package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;

import com.udacity.gradle.builditbigger.jokespresentation.JokeActivity;

import java.lang.ref.WeakReference;

public abstract class ShowJokeBehaviorBase implements ShowJokeBehavior {

    private WeakReference<Context> mContextRef;

    public ShowJokeBehaviorBase(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    protected void launchShowJokeActivity(String title, String content) {
        Context context = mContextRef.get();
        if(context == null) {
            return;
        }
        Intent launchActivityIntent = JokeActivity.createLaunchIntent(context, title, content);
        context.startActivity(launchActivityIntent);
    }
}
