package rmi;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import model.Player;


@SuppressWarnings("serial")
public class ServerDbImplementation extends UnicastRemoteObject implements ServerDb {

	private Player player1;
	private Player player2;

	protected ServerDbImplementation() throws RemoteException {
		player1 = null;
		player2 = null;
	}

	@Override
	public Player getPlayer1() throws RemoteException {
		return player1;
	}

	@Override
	public Player getPlayer2() throws RemoteException {
		return player2;
	}

	@Override
	public void addPlayer(Player pl) throws RemoteException {
		if (player1 == null)
			player1 = pl;
		else
			player2 = pl;
	}

	public static void main(String[] args) {
		try {
			Registry reg = RegistryManager.get();
			reg.rebind(ServerDbImplementation.RMI_NAME, new ServerDbImplementation());
			System.out.println("Server ready...");
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void removePlayers() throws RemoteException {
		player1 = null;
		player2 = null;
	}
}
