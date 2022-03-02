package com.kopcheski.tweets.consumer;

import com.fasterxml.jackson.databind.JsonNode;

public class Tweet {

	private JsonNode jsonNode;

	private Tweet(JsonNode jsonNode) {
		this.jsonNode = jsonNode;
	}
	
	static Tweet from(JsonNode jsonNode) {
		return new Tweet(jsonNode);
	}

	public String language() {
		return jsonNode.get("lang").asText();
	}

	public String text() {
		return jsonNode.get("text").asText();
	}

}
