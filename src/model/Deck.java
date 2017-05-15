package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Deck {
	
	// List of cards.
	private List<Card> deck;
	// Maps card number to list of icons related to that card.
	private Map<Integer, List<String>> cardDesign;
	
	public Deck() {
		this.cardDesign = new HashMap<>();
		populateMap();
		this.deck = new ArrayList<>();
		for(int i = 1; i <= 14; i++) {
			if(i == 11) {
				continue;
			}
			int j = 0;
			for(CardSuit suit : CardSuit.values()) {
				deck.add(new Card(i, suit, cardDesign.get(i).get(j)));
				j++;
			}
		}
	}
	
	public List<Card> getDeck() {
		return this.deck;
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	private void populateMap() {
		for(int i = 1; i <= 14; i++) {
			if(i == 11) {
				continue;
			}
			List<String> iconsPaths = new ArrayList<>();
			for(CardSuit suit : CardSuit.values()) {
				iconsPaths.add("Cards/"+i+suit.name()+".png");
			}
			cardDesign.put(i, iconsPaths);
		}
	}
	
	public String getPath(int key, int value) {
		return cardDesign.get(key).get(value);
	}
	
	public List<String> getPath(int key) {
		return cardDesign.get(key);
	}

	public Map<Integer, List<String>> getCardDesign() {
		return cardDesign;
	}
}
