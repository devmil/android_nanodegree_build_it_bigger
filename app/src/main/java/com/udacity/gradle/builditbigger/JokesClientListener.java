package com.udacity.gradle.builditbigger;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface JokesClientListener {

    /**
     * This callback gets called whenever a new Joke has been received
     * It won't be called when any error occurs
     * @param response contains the Joke
     */
    void onNewJokeReceived(JokesClientResponse response);

    @Retention(RetentionPolicy.SOURCE)
    @IntDef( { STATE_IDLE, STATE_FETCHING } )
    @interface States {}

    int STATE_IDLE = 0;
    int STATE_FETCHING = 1;

    /**
     * Gets called when the state of the client changes.
     * Whenever the client starts fetching a new joke its
     * state changes to @see STATE_FETCHING and changes
     * back to @see STATE_IDLE when the request is done
     * (due to receiving a Joke or any error state
     * @param oldState the old state
     * @param newState the new state
     */
    void onStateChanged(@States int oldState, @States int newState);

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ ERROR_NO_ERROR, ERROR_BAD_FORMAT, ERROR_CONNECTION })
    @interface ErrorCodes {}

    int ERROR_NO_ERROR = 0;
    int ERROR_BAD_FORMAT = -1;
    int ERROR_CONNECTION = -2;

    /**
     * Gets called when the error state changes.
     * It changes to any error state (negative const) for errors and resets
     * to \see ERROR_NO_ERROR when a new request is started
     * @param errorState the current error state
     */
    void onErrorStateChanged(@ErrorCodes int errorState);
}
