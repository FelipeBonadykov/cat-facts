package com.bonadykov.catfacts.endpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bonadykov.catfacts.services.FactApiService;
import com.bonadykov.catfacts.services.MongoDBService;

@RestController
@RequestMapping("/facts")
public class FactRest {

	@Autowired
	private FactApiService facts;

	@Autowired
	private MongoDBService db;

	/**
	 * @return anew fact, publicly available
	 * @throws IOException
	 */
	@GetMapping("/new")
	public String getNewFact() {
		return facts.getNewFactSingle()
				.map(fact -> fact.getFact())
				.onErrorResumeNext(error -> facts.getBackupJoke())
				.toBlocking()
				.value();
	}

	/**
	 * @return a new fact and save it to mongodb
	 */
	@GetMapping("/save/{userId}")
	public String getAndSaveFact(@PathVariable(value = "userId") final String userId) {
		return facts.getNewFactSingle()
				.doOnSuccess(fact -> db.saveNewFact(userId, fact))
				.map(fact -> fact.getFact())
				.onErrorResumeNext(error -> facts.getBackupJoke())
				.toBlocking()
				.value();
	}

	/**
	 * @return all saved facts by a specific user
	 */
	@GetMapping("/all/{userId}")
	public ArrayList<String> getAllFacts(@PathVariable(value = "userId") final String userId) {
		return db.getAllFacts(userId)
				.map(fact -> fact.getFact())
				.collect(ArrayList<String>::new, List::add)
				.toBlocking()
				.single();
	}

}
