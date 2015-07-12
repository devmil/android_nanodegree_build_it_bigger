package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private WeakReference<Context> mContextRef;
    private @JokesClientListener.States int mState = JokesClientListener.STATE_IDLE;
    private @JokesClientListener.ErrorCodes int mLastErrorCode = JokesClientListener.ERROR_NO_ERROR;

    public JokesClient(Context context) {
        mContextRef = new WeakReference<>(context);
    }

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
        if(mState != JokesClientListener.STATE_IDLE) {
            return;
        }
        if(!checkConnectionAvailable()) {
            setError(JokesClientListener.ERROR_CONNECTION);
            return;
        }
        setState(JokesClientListener.STATE_FETCHING);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                setError(JokesClientListener.ERROR_NO_ERROR);
                MyApi api = getApi();
                try {
                    JokeBean rndJoke = api.getRandomJoke().execute();

                    onNewJokeReceived(new JokesClientResponse(rndJoke.getTitle(), rndJoke.getContent()));
                }
                catch(IOException e) {
                    Log.e(TAG, "Error getting Jokes data", e);
                    setError(JokesClientListener.ERROR_BAD_FORMAT);
                }
                finally {
                    setState(JokesClientListener.STATE_IDLE);
                }
                return null;
            }
        }.execute();
    }

    private boolean checkConnectionAvailable() {
        Context context = mContextRef.get();
        if(context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return nInfo != null
                && nInfo.isAvailable()
                && nInfo.isConnected();
    }

    private void setState(@JokesClientListener.States int newState) {
        if(mState == newState) {
            return;
        }
        @JokesClientListener.States int oldState = mState;
        mState = newState;
        onStateChanged(oldState, newState);
    }

    private void setError(@JokesClientListener.ErrorCodes int errorCode) {
        if(mLastErrorCode == errorCode) {
            return;
        }
        mLastErrorCode = errorCode;
        onErrorStateChanged(errorCode);
    }

    private void onNewJokeReceived(JokesClientResponse jokesResponse) {
        JokesClientListener l = mListenerRef.get();
        if(l != null) {
            l.onNewJokeReceived(jokesResponse);
        }
    }

    private void onStateChanged(@JokesClientListener.States int oldState, @JokesClientListener.States int newState) {
        JokesClientListener l = mListenerRef.get();
        if(l != null) {
            l.onStateChanged(oldState, newState);
        }
    }

    private void onErrorStateChanged(@JokesClientListener.ErrorCodes int errorCode) {
        JokesClientListener l = mListenerRef.get();
        if(l != null) {
            l.onErrorStateChanged(errorCode);
        }
    }
}
