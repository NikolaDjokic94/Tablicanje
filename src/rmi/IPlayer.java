package rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.Card;
import model.CardButton;


public interface IPlayer extends Remote {

	public void receiveMove(List<CardButton> cards, int score, int tables, boolean addedOnTable, boolean zavrsenPotez) throws RemoteException;
	
	public void newDeal(List<Card> handCards, List<Card> tableCards, String username) throws RemoteException;
	
	public void connectionLost() throws RemoteException;

}
