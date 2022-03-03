package com.bonadykov.catfacts.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fact {

	@SerializedName("factid")
	@Expose
	private String factId;

	@SerializedName("text")
	@Expose
	private String fact;

	public String getFactId() {
		return factId;
	}

	public void setFactId(String factId) {
		this.factId = factId;
	}

	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}

	public Fact(String factId, String fact) {
		this.factId = factId;
		this.fact = fact;
	}

}