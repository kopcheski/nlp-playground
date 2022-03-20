package com.kopcheski.tweets.consumer;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonParser {

	public static String firstTrendingTopic(JsonNode jsonNode) {
		return jsonNode.elements()
				.next()
				.elements()
				.next()
				.get(0)
				.get("query")
				.asText();
	}
}
