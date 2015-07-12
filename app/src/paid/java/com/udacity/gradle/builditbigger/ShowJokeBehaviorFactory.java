package com.udacity.gradle.builditbigger;

import android.content.Context;

public class ShowJokeBehaviorFactory {

    public static ShowJokeBehavior createBehavior(Context context) {
        return new PaidShowJokeBehavior(context);
    }
}
