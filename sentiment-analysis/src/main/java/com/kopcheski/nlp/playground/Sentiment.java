package com.kopcheski.nlp.playground;

public class Sentiment {

	private final String name;
	private final int code;
	private final String sentence;

	public Sentiment(String name, int sentimentInt, String sentence) {
		this.name = name;
		this.code = sentimentInt;
		this.sentence = sentence;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}

	public String getSentence() {
		return sentence;
	}

	@Override
	public String toString() {
		return "Sentiment{" +
				"sentiment: '" + name + '\'' +
				"; sentence: '" + sentence + '\'' +
				'}';
	}
}
