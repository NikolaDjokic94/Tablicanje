package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Player implements Serializable{

	
	private static final long serialVersionUID = -7824713675325483939L;
	private String username;
	private int score;
	private int numberOfTakenCards;
	private int tableCounter;
	private boolean isDealer;
	private boolean onMove;
	private String host;
	private List<Card> cardsInHand;

	public Player(String username, String host, boolean isDealer, boolean onMove) {
		this.username = username;
		this.score = 0;
		this.numberOfTakenCards = 0;
		this.tableCounter = 0;
		this.isDealer = isDealer;
		this.host = host;
		this.onMove = onMove;
		cardsInHand = new LinkedList<>();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public boolean isOnMove() {
		return onMove;
	}

	public void setOnMove(boolean onMove) {
		this.onMove = onMove;
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

	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}

	public List<Card> getCardsInHand() {
		return cardsInHand;
	}

	public void setCardsInHand(List<Card> cardsInHand) {
		this.cardsInHand = cardsInHand;
	}
}
