package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.udacity.gradle.builditbigger.entities.Joke;
import com.udacity.gradle.builditbigger.jokespresentation.JokeActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void tellJoke(View view){
        JokesService js = new JokesService();
        Joke joke = js.getRandomJoke();
        Intent launchActivityIntent = JokeActivity.createLaunchIntent(this, joke.getTitle(), joke.getContent());
        startActivity(launchActivityIntent);
    }
}
