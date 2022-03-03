package com.bonadykov.catfacts.api_interfaces;

import com.bonadykov.catfacts.models.Fact;

import retrofit2.http.GET;
import rx.Single;

public interface IFactAPI {
	
	@GET("facts/random")
	Single<Fact> getNewFactSingle();
	
}
