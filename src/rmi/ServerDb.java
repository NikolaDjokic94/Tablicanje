package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Player;


public interface ServerDb extends Remote {
	
	public static final String RMI_NAME = "ServerDb";

	public Player getPlayer1() throws RemoteException;
	
	public Player getPlayer2() throws RemoteException;
	
	public void addPlayer(Player pl) throws RemoteException;
	
	public void removePlayers() throws RemoteException;
	
	
}
