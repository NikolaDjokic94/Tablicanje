package rmi;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RegistryManager {

	public static Registry get() throws RemoteException {
		String host = System.getProperty("java.rmi.server.hostname");
		if (host == null) {
			System.out.println("VM argument not defined, RMI registry will be available on localhost only.");
			host = "localhost";
		}
		return get(host);
	}

	public static Registry get(String host) throws RemoteException {
		try {
			return LocateRegistry.createRegistry(1099);
		} catch (RemoteException ex) {
			return LocateRegistry.getRegistry(host, 1099);
		}
	}
}
