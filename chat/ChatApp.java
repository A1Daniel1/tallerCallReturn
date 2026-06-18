import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.rmi.RemoteException;
import java.net.InetAddress;

public class ChatApp implements ChatClientInterface {
    private String username;

    public ChatApp(String username) {
        this.username = username;
    }

    /**
     * Este método es llamado remotamente por otro cliente para entregarnos un mensaje.
     */
    @Override
    public void recibirMensaje(String usuario, String msg) throws RemoteException {
        System.out.println("\n[" + usuario + "]: " + msg);
        System.out.print("> "); 
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("--- RMI P2P Chat Application ---");
            System.out.print("Ingrese su nombre de usuario: ");
            String username = scanner.nextLine();

            // Intentar detectar la IP local para que el stub de RMI sea accesible remotamente
            String localIP = InetAddress.getLocalHost().getHostAddress();
            System.out.print("Su IP local detectada es " + localIP + ". ¿Desea usar otra? (Presione Enter para usar " + localIP + ", o ingrese la IP): ");
            String inputIP = scanner.nextLine();
            if (!inputIP.isEmpty()) {
                localIP = inputIP;
            }
            // Configurar la propiedad para que RMI use la IP correcta en los stubs
            System.setProperty("java.rmi.server.hostname", localIP);

            System.out.print("Ingrese el puerto local para publicar su servicio de recepción: ");
            int localPort = Integer.parseInt(scanner.nextLine());

            // 1. Publicar el objeto remoto local
            ChatApp localApp = new ChatApp(username);
            ChatClientInterface stub = (ChatClientInterface) UnicastRemoteObject.exportObject(localApp, 0);

            Registry registry = LocateRegistry.createRegistry(localPort);
            registry.rebind("ChatService", stub);
            System.out.println("Servicio publicado y listo en " + localIP + ":" + localPort);

            // 2. Conectarse al objeto remoto del otro cliente
            System.out.println("\n--- Conexión Remota ---");
            System.out.print("Ingrese la dirección IP del otro cliente: ");
            String remoteIP = scanner.nextLine();
            System.out.print("Ingrese el puerto del otro cliente: ");
            int remotePort = Integer.parseInt(scanner.nextLine());

            System.out.println("Conectando con el servicio remoto en " + remoteIP + ":" + remotePort + "...");
            Registry remoteRegistry = LocateRegistry.getRegistry(remoteIP, remotePort);
            ChatClientInterface remoteChat = (ChatClientInterface) remoteRegistry.lookup("ChatService");
            System.out.println("¡Conexión establecida con éxito!");

            // 3. Bucle de chat
            System.out.println("\nYa puede empezar a chatear. Escriba sus mensajes a continuación.");
            System.out.println("(Escriba 'salir' para terminar la aplicación)");
            
            while (true) {
                System.out.print("> ");
                String msg = scanner.nextLine();
                
                if (msg.equalsIgnoreCase("salir")) {
                    break;
                }
                
                if (!msg.trim().isEmpty()) {
                    try {
                        remoteChat.recibirMensaje(username, msg);
                    } catch (RemoteException e) {
                        System.err.println("Error al enviar el mensaje: " + e.getMessage());
                        System.out.println("¿Desea intentar reconectar? (s/n)");
                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                             remoteChat = (ChatClientInterface) remoteRegistry.lookup("ChatService");
                             System.out.println("Reconectado.");
                        }
                    }
                }
            }

            System.out.println("Saliendo del chat...");
            System.exit(0);

        } catch (Exception e) {
            System.err.println("Error en la ejecución de la aplicación:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
