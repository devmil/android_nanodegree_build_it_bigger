/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.udacity.gradle.builditbigger.jokesBackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.builditbigger.JokesService;
import com.udacity.gradle.builditbigger.entities.Joke;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "jokesBackend.builditbigger.gradle.udacity.com",
                ownerName = "jokesBackend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    private JokesService mJokesService = new JokesService();

    @ApiMethod(name = "getRandomJoke")
    public JokeBean getRandomJoke() {
        JokeBean response = new JokeBean();
        Joke joke = mJokesService.getRandomJoke();
        response.setTitle(joke.getTitle());
        response.setContent(joke.getContent());

        return response;
    }

}
