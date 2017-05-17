package rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.Card;


public interface IPlayer extends Remote {

	public void receiveMove(List<Card> cards, int score, int tables, int numberOfCardsInEnemyHand, boolean lastTook, boolean endOfDeck) throws RemoteException;
	
	public void newDeal(List<Card> handCards, List<Card> tableCards, String username) throws RemoteException;
	
	public void connectionLost() throws RemoteException;

}
