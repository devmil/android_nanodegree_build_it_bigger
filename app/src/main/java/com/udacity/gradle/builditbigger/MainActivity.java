package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.udacity.gradle.builditbigger.jokespresentation.JokeActivity;


public class MainActivity extends AppCompatActivity implements JokesClientListener {

    private JokesClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClient = new JokesClient();
        mClient.setListener(this);
    }


    public void tellJoke(View view){
        mClient.requestNewJoke();
    }

    @Override
    public void onNewJokeReceived(JokesClientResponse response) {
        Intent launchActivityIntent = JokeActivity.createLaunchIntent(this, response.getTitle(), response.getContent());
        startActivity(launchActivityIntent);
    }
}
