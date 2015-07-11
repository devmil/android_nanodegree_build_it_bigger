package com.udacity.gradle.builditbigger;

import com.google.gson.Gson;
import com.udacity.gradle.builditbigger.entities.Joke;
import com.udacity.gradle.builditbigger.entities.JokeDatabase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public final class JokesRepository {

    private JokeDatabase mJokesDatabase;


    private JokesRepository() {
        loadJokes();
    }

    private static JokesRepository sInstance;

    public static JokesRepository getInstance() {
        if(sInstance == null) {
            sInstance = new JokesRepository();
        }
        return sInstance;
    }

    public int getCount() {
        return mJokesDatabase.getCount();
    }

    public Joke getJokeAt(int index) {
        return mJokesDatabase.getJokeAt(index);
    }

    private void loadJokes() {
        InputStream inStream = getClass().getResourceAsStream("/jokes.json");
        InputStreamReader inReader = new InputStreamReader(inStream);

        Gson gson = new Gson();
        mJokesDatabase = gson.fromJson(inReader, JokeDatabase.class);
    }
}
