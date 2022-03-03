package com.bonadykov.catfacts.services;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.bonadykov.catfacts.models.Fact;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import rx.Observable;

@Service
public class MongoDBService {
	private MongoCollection<Document> collection;

	/**
	 * @param user - id of the user that is received through post
	 */
	public MongoDBService() {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("cat-facts");
		collection = database.getCollection("users");
	}

	/**
	 * @param userId     - id of user
	 * @param observable - new fact to add to the list of existing ones
	 */
	public void saveNewFact(String userId, Fact newFact) {
		collection.updateOne(eq("id", userId),
				new Document("$set", new Document("facts", 
						getAllFactsList(userId, newFact))));
	}

	/**
	 * @param userId - id of user
	 * @return all facts saved by a user
	 */
	@SuppressWarnings("unchecked")
	public Observable<Fact> getAllFacts(String userId) {
		Document doc = collection.find(eq("id", userId)).first();
		return Observable.from((ArrayList<Document>) doc.get("facts"))
				.map(document -> 
					new Fact(document.getString("factid"), 
							 document.getString("text")));
	}

	private ArrayList<Document> getAllFactsList(String userId, Fact newFact) {
		return Observable.just(newFact)
				.mergeWith(getAllFacts(userId))
				.map(fact -> 
					new Document()
						.append("factid", fact.getFactId())
						.append("text", fact.getFact()))
				.collect(ArrayList<Document>::new, List::add)
				.toBlocking()
				.single();
	}

}
