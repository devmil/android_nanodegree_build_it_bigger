package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements JokesClientListener {

    private JokesClient mClient;
    private LinearLayout llProgress;

    private ShowJokeBehavior mShowBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llProgress = (LinearLayout)findViewById(R.id.activity_main_llProgress);

        mClient = new JokesClient(this);
        mClient.setListener(this);

        mShowBehavior = ShowJokeBehaviorFactory.createBehavior(this);
    }

    public void tellJoke(View view){
        mClient.requestNewJoke();
    }

    @Override
    public void onNewJokeReceived(final JokesClientResponse response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShowBehavior.showJoke(response.getTitle(), response.getContent());
            }
        });
    }

    @Override
    public void onStateChanged(@States int oldState, @States final int newState) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean active = newState != JokesClientListener.STATE_IDLE;
                llProgress.setVisibility(active ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onErrorStateChanged(@ErrorCodes final int errorState) {
        if(errorState == JokesClientListener.ERROR_NO_ERROR) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, getErrorText(errorState), Toast.LENGTH_LONG).show();
            }
        });
    }

    private CharSequence getErrorText(@ErrorCodes int errorState) {
        switch(errorState) {
            case JokesClientListener.ERROR_CONNECTION:
                return getString(R.string.error_connection);
            case JokesClientListener.ERROR_BAD_FORMAT:
                return getString(R.string.error_bad_format);
            default:
                return getString(R.string.error_unknown);
        }
    }
}
