import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class registrarClienteImpl implements ChatServerInterface {
    private List<ChatClientInterface> clientes;

    public registrarClienteImpl() {
        this.clientes = new ArrayList<>();
    }

    @Override
    public void registrarCliente(ChatClientInterface cliente) throws RemoteException {
        clientes.add(cliente);
        System.out.println("Nuevo cliente registrado.");
    }

    @Override
    public void difundirMensaje(String usuario, String msg) throws RemoteException {
        System.out.println("[" + usuario + "]: " + msg);
        for (ChatClientInterface cliente : clientes) {
            try {
                cliente.recibirMensaje(usuario, msg);
            } catch (RemoteException e) {
                System.err.println("Error al enviar mensaje a un cliente. Se ignorará.");
            }
        }
    }

    public static void main(String[] args) {
        try {
            int port = 1099;
            String nombreDePublicacion = "ChatServer";
            
            registrarClienteImpl server = new registrarClienteImpl();
            ChatServerInterface stub = (ChatServerInterface) UnicastRemoteObject.exportObject(server, 0);
            
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(port);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(port);
            }
            
            registry.rebind(nombreDePublicacion, stub);
            System.out.println("Servidor de Chat (registrarClienteImpl) listo en el puerto " + port);
            
        } catch (Exception e) {
            System.err.println("Excepción en el servidor:");
            e.printStackTrace();
        }
    }
}
