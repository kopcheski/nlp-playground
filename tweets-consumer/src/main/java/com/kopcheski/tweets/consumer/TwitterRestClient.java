package com.kopcheski.tweets.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
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

	public String worldWideTrendingTopic() throws URISyntaxException, IOException, InterruptedException {
		String response = authenticatedRequest("https://api.twitter.com/1.1/trends/place.json?id=1");
		JsonNode jsonNode = stringJsonToJsonNode(response);
		return JsonParser.firstTrendingTopic(jsonNode);
	}

	public List<String> hashtag(String hashtag) throws URISyntaxException, IOException, InterruptedException {
		JsonNode jsonNode = tweetsAsJson(hashtag);
		Iterator<JsonNode> elements = jsonNode.elements().next().iterator();
		List<String> tweets = new ArrayList<>();
		while (elements.hasNext()) {
			Tweet tweet = Tweet.from(elements.next());
			if ("en".equals(tweet.language())) {
				tweets.add(tweet.text());
			}
		}
		return tweets;
	}

	private JsonNode tweetsAsJson(String hashtag) throws IOException, URISyntaxException, InterruptedException {
		String tweets = getTweets(hashtag);
		return stringJsonToJsonNode(tweets);
	}

	private JsonNode stringJsonToJsonNode(String tweets) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readTree(tweets);
	}

	private String getTweets(String hashtag) throws IOException, InterruptedException, URISyntaxException {
		return authenticatedRequest("https://api.twitter.com/1.1/search/tweets.json?q=" + hashtag + "&result_type=recent");
	}

	private String authenticatedRequest(String url) throws URISyntaxException, IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest build = HttpRequest.newBuilder()
				.uri(new URI(url))
				.header("Authorization", System.getProperty("TwitterBearerToken"))
				.GET()
				.build();

		return client.send(build, HttpResponse.BodyHandlers.ofString()).body();
	}
}
