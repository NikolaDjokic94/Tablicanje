package model;

public class Card {
	private int value;
	private int cardNumber;
	private boolean stih;
	private CardSuit cardSign;
	private String iconPath;
	private boolean playerCard;
	private boolean usedCard;

	public Card(int cardNumber, CardSuit cardSign, String iconPath) {
		this.iconPath = iconPath;
		this.cardNumber = cardNumber;
		this.cardSign = cardSign;
		this.playerCard = false;
		this.usedCard = false;
		if(cardNumber > 1 && cardNumber < 10) {
			this.stih = false;
		}
		else {
			this.stih = true;
			this.value = 1;
		}
		if(cardNumber == 2 && cardSign == CardSuit.Clubs) {
			this.value = 1;
		}
		if(cardNumber == 10 && cardSign == CardSuit.Diamonds) {
			this.value = 2;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cardNumber;
		result = prime * result + ((cardSign == null) ? 0 : cardSign.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardNumber != other.cardNumber)
			return false;
		if (cardSign != other.cardSign)
			return false;
		return true;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean isStih() {
		return stih;
	}

	public void setStih(boolean stih) {
		this.stih = stih;
	}
	
	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	
	public boolean isPlayerCard() {
		return playerCard;
	}
	
	public void setPlayerCard(boolean playerCard) {
		this.playerCard = playerCard;
	}

	public boolean isUsedCard() {
		return usedCard;
	}

	public void setUsedCard(boolean usedCard) {
		this.usedCard = usedCard;
	}

	@Override
	public String toString() {
		return cardNumber + " " + value + " " + cardSign.toString();
	}
}
