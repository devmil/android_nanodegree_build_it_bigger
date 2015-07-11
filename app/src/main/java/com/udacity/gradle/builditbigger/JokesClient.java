package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.jokesbackend.myApi.MyApi;
import com.udacity.gradle.builditbigger.jokesbackend.myApi.model.JokeBean;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class JokesClient {

    private static final String TAG = JokesClient.class.getSimpleName();

    private MyApi mApi;
    private WeakReference<JokesClientListener> mListenerRef;
    private boolean mRequesting = false;

    synchronized private MyApi getApi() {
        if(mApi == null) {
            MyApi.Builder apiBuilder =
                    new MyApi.Builder(
                            AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(),
                            null)
                            //Connection out of the emulator to the dev PC
                            .setRootUrl("http://10.0.2.2:8080/_ah/api")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {

                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                    request.setDisableGZipContent(true);
                                }
                            });
            mApi = apiBuilder.build();

        }
        return mApi;
    }

    public void setListener(JokesClientListener listener) {
        mListenerRef = new WeakReference<>(listener);
    }

    public void requestNewJoke() {
        if(mRequesting) {
            return;
        }
        mRequesting = true;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                MyApi api = getApi();
                try {
                    JokeBean rndJoke = api.getRandomJoke().execute();

                    JokesClientListener l = mListenerRef.get();

                    if(l != null) {
                        l.onNewJokeReceived(new JokesClientResponse(rndJoke.getTitle(), rndJoke.getContent()));
                    }
                }
                catch(IOException e) {
                    Log.e(TAG, "Error getting Jokes data", e);
                }
                finally {
                    mRequesting = false;
                }
                return null;
            }
        }.execute();
    }
}
