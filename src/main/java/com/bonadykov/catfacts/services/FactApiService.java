package com.bonadykov.catfacts.services;

import org.springframework.stereotype.Service;

import com.bonadykov.catfacts.api_interfaces.IFactAPI;
import com.bonadykov.catfacts.api_interfaces.IJokeAPI;
import com.bonadykov.catfacts.models.Fact;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Single;

@Service
public class FactApiService {
	private static final String CAT_FACTS_URL = "https://cat1-fact.herokuapp.com";
	private static final String JOKES_BACKUP_URL = "https://v2.jokeapi.dev";
	private IFactAPI factApi;

	FactApiService() {
		factApi = formRetrofit(CAT_FACTS_URL).create(IFactAPI.class);
	}

	private Retrofit formRetrofit(String baseUrl) {
		return new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
	}

	/**
	 * @return new Single fact or a joke if something fails
	 */
	public Single<Fact> getNewFactSingle(){
		return factApi.getNewFactSingle();
	}
	
	/**
	 * @return joke (if something fails with a cat fact)
	 */
	public Single<String> getBackupJoke() {
		return formRetrofit(JOKES_BACKUP_URL)
				.create(IJokeAPI.class)
				.getNewJokeSingle()
				.map(joke -> joke.getJoke());
	}

}
