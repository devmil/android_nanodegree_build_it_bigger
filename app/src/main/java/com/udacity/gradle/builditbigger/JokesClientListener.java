package com.udacity.gradle.builditbigger;

public interface JokesClientListener {
    void onNewJokeReceived(JokesClientResponse response);
}
