import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clienSocket = null;

        String inputLine;
        String wantedFile = "";

        while (true) {

            try {
                clienSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed");
                System.exit(1);
            }

            try {

                PrintWriter out = new PrintWriter(clienSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                clienSocket.getInputStream()));

                if ((inputLine = in.readLine()) != null) {
                    String[] parts = inputLine.split(" ");
                    if (parts.length > 1) {
                        wantedFile = parts[1].substring(1);
                    }

                    String contentType = "text/plain";

                    if (wantedFile.endsWith(".html")) {
                        contentType = "text/html";
                    } else if (wantedFile.endsWith(".png")) {
                        contentType = "image/png";
                    } else if (wantedFile.endsWith(".jpg")) {
                        contentType = "image/jpeg";
                    }

                    Path route = Path.of(wantedFile);

                    try {
                        byte[] content = Files.readAllBytes(route);
                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: " + contentType);
                        out.println("");
                        out.flush();

                        clienSocket.getOutputStream().write(content);
                        clienSocket.getOutputStream().flush();
                    } catch (IOException e) {
                        System.err.println("archivo no encontrado");
                    }

                }

                out.close();
                in.close();
                clienSocket.close();
            } catch (IOException e) {
                System.err.println("Error en la entrada");
            }
        }

    }
}
