package com.kopcheski.tweets.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ConsumerTest {

	@Test
	void testFollowHashTag() throws URISyntaxException, IOException, InterruptedException {
		List<String> tweets = new TwitterRestClient().hashtag("%23voltaasaulas");
		assertFalse(tweets.isEmpty());
		tweets.forEach(System.out::println);
	}

	@Test
	void testReadLanguageFromTweet() throws IOException {
		File sampleTweet = Paths.get("src/test/resources/tweet.json").toFile();
		JsonNode jsonNode = new ObjectMapper().readTree(sampleTweet);
		assertEquals("en", jsonNode.get("lang").asText());
	}

	@Test
	void testReadFirstHashTagFromTrendingTopic() throws IOException {
		File sampleTweet = Paths.get("src/test/resources/trends.json").toFile();
		JsonNode jsonNode = new ObjectMapper().readTree(sampleTweet);
		assertEquals("%23GiftAGamer", JsonParser.firstTrendingTopic(jsonNode));
	}

}
