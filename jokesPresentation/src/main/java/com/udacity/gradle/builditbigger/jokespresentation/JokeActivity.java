package com.udacity.gradle.builditbigger.jokespresentation;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class JokeActivity extends AppCompatActivity {

    private static final String EXTRA_JOKE_TITLE = "EXTRA_JOKE_TITLE";
    private static final String EXTRA_JOKE_CONTENT = "EXTRA_JOKE_CONTENT";

    public static Intent createLaunchIntent(Context context, String jokeTitle, String jokeContent) {
        Intent result = new Intent(context, JokeActivity.class);
        result.putExtra(EXTRA_JOKE_TITLE, jokeTitle);
        result.putExtra(EXTRA_JOKE_CONTENT, jokeContent);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_joke);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        JokeActivityFragment fragment = (JokeActivityFragment)getSupportFragmentManager().findFragmentById(R.id.activity_joke_fragment_joke);
        if(getIntent() != null
                && getIntent().hasExtra(EXTRA_JOKE_TITLE)
                && getIntent().hasExtra(EXTRA_JOKE_CONTENT)) {
            fragment.setJoke(getIntent().getStringExtra(EXTRA_JOKE_TITLE), getIntent().getStringExtra(EXTRA_JOKE_CONTENT));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
