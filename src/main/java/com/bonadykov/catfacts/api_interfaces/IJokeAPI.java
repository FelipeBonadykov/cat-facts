package com.bonadykov.catfacts.api_interfaces;

import com.bonadykov.catfacts.models.Joke;

import retrofit2.http.GET;
import rx.Single;

public interface IJokeAPI {

	/**
	 * @return a work suitable joke about programming
	 */
	@GET("joke/Programming?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=single")
	Single<Joke> getNewJokeSingle();
}
