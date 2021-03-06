package com.udacity.gradle.builditbigger.jokespresentation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class JokeActivityFragment extends Fragment {

    private TextView txtJokeTitle;
    private TextView txtJokeContent;

    public JokeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_joke, container, false);

        txtJokeTitle = (TextView)result.findViewById(R.id.fragment_joke_txt_title);
        txtJokeContent = (TextView)result.findViewById(R.id.fragment_joke_txt_content);

        return result;
    }

    public void setJoke(String jokeTitle, String jokeContent) {
        txtJokeTitle.setText(jokeTitle);
        txtJokeContent.setText(jokeContent);
    }
}
