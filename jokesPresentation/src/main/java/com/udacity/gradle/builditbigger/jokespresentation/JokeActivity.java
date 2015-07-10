package com.udacity.gradle.builditbigger.jokespresentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class JokeActivity extends AppCompatActivity {

    private static final String EXTRA_JOKE = "EXTRA_JOKE";

    public static Intent createLaunchIntent(Context context, String joke) {
        Intent result = new Intent(context, JokeActivity.class);
        result.putExtra(EXTRA_JOKE, joke);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_joke);

        JokeActivityFragment fragment = (JokeActivityFragment)getSupportFragmentManager().findFragmentById(R.id.activity_joke_fragment_joke);
        if(getIntent() != null
                && getIntent().hasExtra(EXTRA_JOKE)) {
            fragment.setJoke(getIntent().getStringExtra(EXTRA_JOKE));
        }
    }

}
