package com.udacity.gradle.builditbigger;

public class JokesClientResponse {
    private String mTitle;
    private String mContent;

    public JokesClientResponse(String mTitle, String mContent) {
        this.mTitle = mTitle;
        this.mContent = mContent;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }
}
