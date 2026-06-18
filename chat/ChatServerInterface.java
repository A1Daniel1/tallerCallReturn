import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerInterface extends Remote{
    void registrarCliente(ChatClientInterface cliente) throws RemoteException;
    void difundirMensaje(String usuario, String msg) throws RemoteException;
}
