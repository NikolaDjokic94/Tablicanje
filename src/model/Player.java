package model;

import java.io.Serializable;

public class Player implements Serializable{

	
	private static final long serialVersionUID = -7824713675325483939L;
	private String username;
	private int score;
	private int numberOfTakenCards;
	private int tableCounter;
	private String host;
	private int numberOfCardsInHand;

	public Player(String username, String host, boolean isDealer, boolean onMove) {
		this.username = username;
		this.score = 0;
		this.numberOfTakenCards = 0;
		this.tableCounter = 0;
		this.host = host;
		this.numberOfCardsInHand = 0;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNumberOfTakenCards() {
		return numberOfTakenCards;
	}

	public void setNumberOfTakenCards(int numberOfTakenCards) {
		this.numberOfTakenCards = numberOfTakenCards;
	}

	public int getTableCounter() {
		return tableCounter;
	}

	public void incrementTableCounter() {
		this.tableCounter++;
	}
	
	public int getNumberOfCardsInHand() {
		return numberOfCardsInHand;
	}

	public void setNumberOfCardsInHand(int numberOfCardsInHand) {
		this.numberOfCardsInHand = numberOfCardsInHand;
	}
}
