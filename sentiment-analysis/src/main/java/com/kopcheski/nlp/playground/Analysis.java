package com.kopcheski.nlp.playground;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Analysis {

	private static StanfordCoreNLP pipeline;

	public Analysis() {
		init();
	}

	private static void init() {
		if (pipeline == null) {
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
			pipeline = new StanfordCoreNLP(props);
		}
	}

	public List<Sentiment> estimateSentiment(String text) {
		int sentimentInt;
		String sentimentName;
		Annotation annotation = pipeline.process(text);
		List<Sentiment> sentiments = new ArrayList<>();
		for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
			sentimentInt = RNNCoreAnnotations.getPredictedClass(tree);
			sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
			Sentiment sentiment = new Sentiment(sentimentName, sentimentInt, sentence.toString());
			sentiments.add(sentiment);
		}
		return sentiments;
	}
}
