package com.udacity.gradle.builditbigger.entities;

import java.util.Arrays;
import java.util.List;

public class JokeDatabase {
    private Joke[] jokes;

    public List<Joke> getJokes() {
        return Arrays.asList(jokes);
    }

    public int getCount() {
        return jokes.length;
    }

    public Joke getJokeAt(int index) {
        return jokes[index];
    }
}
