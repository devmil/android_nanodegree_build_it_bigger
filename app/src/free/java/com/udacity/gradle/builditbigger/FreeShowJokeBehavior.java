package com.udacity.gradle.builditbigger;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class FreeShowJokeBehavior extends ShowJokeBehaviorBase {

    private InterstitialAd mInterstitialAd;
    private String mCurrentJokeTitle;
    private String mCurrentJokeContent;

    public FreeShowJokeBehavior(Context context) {
        super(context);
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_unit_id));
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if(mCurrentJokeTitle != null && mCurrentJokeContent != null) {
                    launchShowJokeActivity(mCurrentJokeTitle, mCurrentJokeContent);
                }
                requestNewInterstitial();
            }
        });
    }

    @Override
    public void showJoke(String title, String content) {
        mCurrentJokeTitle = title;
        mCurrentJokeContent = content;

        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            launchShowJokeActivity(title, content);
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
