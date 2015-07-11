package com.udacity.gradle.builditbigger;

import com.udacity.gradle.builditbigger.entities.Joke;

import java.util.Random;

public class JokesService {

    private Random mRnd;

    public JokesService() {
        mRnd = new Random();
    }

    public Joke getRandomJoke() {
        JokesRepository repo = JokesRepository.getInstance();

        int max = repo.getCount() - 1;
        int index = Math.round(mRnd.nextFloat() * max);
        return repo.getJokeAt(index);
    }
}
