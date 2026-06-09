import java.net.*;
import java.io.*;

public class CalculatorServer {
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

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        String inputLine;
        String function = "cos";
        double value = 0.0;

        if ((inputLine = in.readLine()) != null) {
            String[] parts = inputLine.split(" ");

            if (parts.length > 1) {
                String completeRoute = parts[1];

                if (completeRoute.contains("?")) {
                    String[] parameters = completeRoute.split("\\?");
                    String functionParameter = parameters[0];
                    String valueParameters = parameters[1];

                    if (functionParameter.contains("sin")) {
                        function = "sin";
                    }

                    if (valueParameters.contains("=")) {
                        String valueString = valueParameters.split("=")[1];

                        try {
                            value = Double.parseDouble(valueString);
                        } catch (NumberFormatException e) {
                            System.err.println("El valor no es un numero valido");
                        }
                    }
                }
            }
        }

        String headerLine;
        while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
        }

        double result = 0.0;

        if (function.equals("sin")) {
            result = Math.sin(value);
        } else if (function.equals("cos")) {
            result = Math.cos(value);
        }

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println(""); 
        out.println(
                "<!DOCTYPE html><html><body><h1>Resultado</h1><p>El resultado es: " + result + "</p></body></html>");

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}