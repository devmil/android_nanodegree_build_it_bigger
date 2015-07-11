package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.jokesbackend.myApi.MyApi;
import com.udacity.gradle.builditbigger.jokesbackend.myApi.model.JokeBean;
import com.udacity.gradle.builditbigger.jokespresentation.JokeActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private MyApi mApi;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void tellJoke(View view){
        new AsyncTask<Context, Void, Void>() {

            @Override
            protected Void doInBackground(Context... params) {
                Context context = params[0];
                MyApi api = getApi();
                try {
                    JokeBean rndJoke = api.getRandomJoke().execute();

                    Intent launchActivityIntent = JokeActivity.createLaunchIntent(context, rndJoke.getTitle(), rndJoke.getContent());
                    startActivity(launchActivityIntent);
                }
                catch(IOException e) {

                }
                return null;
            }
        }.execute(this);
    }
}
