package rmi;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import main.GameBoard;
import model.Card;
import model.CardButton;

public class PlayerImplementation extends UnicastRemoteObject implements IPlayer {

	private static final long serialVersionUID = -7824713675325483939L;
	
	private GameBoard gb;

	public PlayerImplementation(GameBoard gb) throws RemoteException {
		this.gb = gb;
	}
	
	@Override
	public void connectionLost() throws RemoteException {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				gb.connectionLost();
			}
		});
	}

	@Override
	public void receiveMove(List<Card> cards, int score, int tables, int numberOfCardsInEnemyHand, boolean lastTook, boolean endOfDeck) throws RemoteException {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				gb.reciveMove(cards, score, tables, numberOfCardsInEnemyHand, lastTook, endOfDeck);
			}
		});
	}

	@Override
	public void newDeal(List<Card> handCards, List<Card> tableCards, String username) throws RemoteException {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				gb.recieveDeal(handCards, tableCards, username);
			}
		});
	}
}
