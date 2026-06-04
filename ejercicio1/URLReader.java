import java.io.*;
import java.net.*;

public class URLReader {
    public static void main(String[] args) throws Exception {
        URI uri = new URI("https://www.ejemplo.com:8080/docs/indice.html?usuario=daniel#seccion1");
        URL google = uri.toURL();

        System.out.println("Protocolo: " + google.getProtocol());
        System.out.println("Autoridad: " + google.getAuthority());
        System.out.println("Host: " + google.getHost());
        System.out.println("Port: " + google.getPort());
        System.out.println("Path: " + google.getPath());
        System.out.println("Query: " + google.getQuery());
        System.out.println("File: " + google.getFile());
        System.out.println("Ref: " + google.getRef());
    }
}