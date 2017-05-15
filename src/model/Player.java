package model;

public class Player {

	private String username;
	private int score;
	private int numberOfTakenCards;
	private int tableCounter;
	private boolean isDealer;
	private boolean onMove;
	private String host;

	public Player(String username, String host, boolean isDealer, boolean onMove) {
		this.username = username;
		this.score = 0;
		this.numberOfTakenCards = 0;
		this.tableCounter = 0;
		this.isDealer = isDealer;
		this.host = host;
		this.onMove = onMove;
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
}
