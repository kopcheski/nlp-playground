package com.kopcheski.tweets.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TwitterRestClient {

	public List<String> hashtag(String hashtag) throws URISyntaxException, IOException, InterruptedException {
		JsonNode jsonNode = asJson(hashtag);
		Iterator<JsonNode> elements = jsonNode.elements().next().iterator();
		List<String> tweets = new ArrayList<>();
		while (elements.hasNext()) {
			JsonNode jsonNodeInternal = elements.next();
			if ("en".equals(jsonNodeInternal.get("lang").asText())) {
				JsonNode text = jsonNodeInternal.get("text");
				String tweet = text.asText();
				tweets.add(tweet);
			}
		}
		return tweets;
	}

	private JsonNode asJson(String hashtag) throws IOException, URISyntaxException, InterruptedException {
		String tweets = getTweets(hashtag);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readTree(tweets);
	}

	private String getTweets(String hashtag) throws IOException, InterruptedException, URISyntaxException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest build = HttpRequest.newBuilder()
				.uri(new URI("https://api.twitter.com/1.1/search/tweets.json?q=%23" + hashtag + "&result_type=recent"))
				.header("Authorization", System.getProperty("TwitterBearerToken"))
				.GET()
				.build();

		return client.send(build, HttpResponse.BodyHandlers.ofString()).body();
	}
}
