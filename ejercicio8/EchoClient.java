import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EchoClient {
    public void ejecutarServicio(String ipRmiregistry, int puertoRMIregistry,
        String nombreServicio) {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            try {
                Registry registry = LocateRegistry.getRegistry(ipRmiregistry, puertoRMIregistry);
                EchoServer echoServer = (EchoServer) registry.lookup(nombreServicio);
                System.out.println(echoServer.echo("Hola como estas? "));
            } catch (Exception e) {
                System.err.println("Hay un problema: ");
                e.printStackTrace();
            }
    }

    public static void main(String[] args) {
        EchoClient ec = new EchoClient();
        ec.ejecutarServicio("127.0.0.1", 23000, "echoServer");
    }
}
