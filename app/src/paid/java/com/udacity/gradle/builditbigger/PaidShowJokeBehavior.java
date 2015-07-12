package com.udacity.gradle.builditbigger;

import android.content.Context;

public class PaidShowJokeBehavior extends ShowJokeBehaviorBase {

    public PaidShowJokeBehavior(Context context) {
        super(context);
    }

    @Override
    public void showJoke(String title, String content) {
        launchShowJokeActivity(title, content);
    }
}
