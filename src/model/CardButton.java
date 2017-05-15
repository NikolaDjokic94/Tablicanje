package model;

import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class CardButton extends JToggleButton {
	private Card card;
	
	public CardButton(Card card) {
		super("");
		this.card = card;
	}

	public Card getCard() {
		return card;
	}
	
}
