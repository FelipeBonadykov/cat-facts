package com.bonadykov.catfacts.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Joke {
	@SerializedName("joke")
	@Expose
	private String joke;

	public void setJoke(String joke) {
		this.joke = joke;
	}

	public String getJoke() {
		return joke;
	}
}