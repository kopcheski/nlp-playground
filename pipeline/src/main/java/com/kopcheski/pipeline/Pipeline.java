package com.kopcheski.pipeline;

import com.kopcheski.nlp.playground.Analysis;
import com.kopcheski.nlp.playground.Sentiment;
import com.kopcheski.nlp.playground.SentimentOverview;
import com.kopcheski.tweets.consumer.TwitterRestClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Pipeline {

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		List<String> tweets = new TwitterRestClient().hashtag("joebiden");
		SentimentOverview sentimentOverview = new SentimentOverview();
		tweets.forEach(tweet -> {
			List<Sentiment> sentiments = new Analysis().estimateSentiment(tweet);
			sentimentOverview.add(sentiments);
			System.out.println(sentiments);
			System.out.println("=======================");
		});
		System.out.println(sentimentOverview);
	}
}
