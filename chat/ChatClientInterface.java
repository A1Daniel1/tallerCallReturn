import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientInterface extends Remote{
    void recibirMensaje(String usuario, String msg) throws RemoteException;
}
