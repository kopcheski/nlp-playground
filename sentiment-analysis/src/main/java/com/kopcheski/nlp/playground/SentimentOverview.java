package com.kopcheski.nlp.playground;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentimentOverview {

	private Map<String, Integer> sentimentsOccurrence = new HashMap<>();

	public void add(List<Sentiment> sentiments) {
		sentiments.forEach(sentiment -> {
			sentimentsOccurrence.computeIfPresent(sentiment.getName(), (name, occur) -> occur + 1);
			sentimentsOccurrence.putIfAbsent(sentiment.getName(), 1);
		});
	}

	@Override
	public String toString() {
		return "SentimentOverview{" +
				"sentimentsOccurrence=" + sentimentsOccurrence +
				'}';
	}
}
