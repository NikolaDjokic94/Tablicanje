package rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.CardButton;


public interface IPlayer extends Remote {

	public void primiPotez(List<CardButton> cards, boolean zavrsenPotez, int points) throws RemoteException;
	
	public void newDeal(List<CardButton> handCards, List<CardButton> tableCards) throws RemoteException;
	
	public void connectionLost() throws RemoteException;

}
