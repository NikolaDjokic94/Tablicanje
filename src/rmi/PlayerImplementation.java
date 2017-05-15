package rmi;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import main.GameBoard;
import model.CardButton;

public class PlayerImplementation extends UnicastRemoteObject implements IPlayer {

	private static final long serialVersionUID = -7824713675325483939L;
	
	private GameBoard gb;

	public PlayerImplementation(GameBoard gb) throws RemoteException {
		this.gb = gb;
	}

	/*@Override
	public void primiPotez(final int odPolje, final int doPolje, int brPreskocenog, boolean zavrsenPotez) throws RemoteException {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				chr.primiPotez(odPolje, doPolje, brPreskocenog, zavrsenPotez);
			}
		});
	}*/
	
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
	public void primiPotez(List<CardButton> cards, boolean zavrsenPotez, int points) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newDeal(List<CardButton> handCards, List<CardButton> tableCards) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
