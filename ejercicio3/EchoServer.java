import java.net.*;
import java.io.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        int resultado = 0;

        if ((inputLine = in.readLine()) != null) {
            String[] partes = inputLine.split(" ");

            if (partes.length > 1) {
                String ruta = partes[1];
                String numeroStr = ruta.substring(1);

                try {
                    int numero = Integer.parseInt(numeroStr);
                    resultado = numero * numero;
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear el número: " + numeroStr);
                }
            }
        }

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("");
        out.println(resultado);

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
