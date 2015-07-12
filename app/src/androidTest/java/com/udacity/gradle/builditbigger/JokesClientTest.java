package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JokesClientTest extends AndroidTestCase {

    public void testJokeRequest() throws InterruptedException {
        JokesClient client = new JokesClient(getContext());

        final String[] responseTitle = new String[1];
        final String[] responseContent = new String[1];
        final CountDownLatch signal = new CountDownLatch(1);

        JokesClientListener listener = new JokesClientListener() {
            @Override
            public void onNewJokeReceived(JokesClientResponse response) {
                responseTitle[0] = response.getTitle();
                responseContent[0] = response.getContent();
                signal.countDown();
            }

            @Override
            public void onStateChanged(@States int oldState, @States int newState) {
            }

            @Override
            public void onErrorStateChanged(@ErrorCodes int errorState) {
            }
        };
        client.setListener(listener);
        client.requestNewJoke();

        signal.await(10, TimeUnit.SECONDS);

        Assert.assertNotNull(responseTitle[0]);
        Assert.assertNotNull(responseContent[0]);
    }
}
