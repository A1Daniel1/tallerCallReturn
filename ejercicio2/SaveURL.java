import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SaveURL {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa tu url: ");
        String url = scanner.nextLine();

        scanner.close();

        System.out.println(url);

        URI uri = new URI(url);
        URL google = uri.toURL();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(google.openStream()));
             PrintWriter pw = new PrintWriter(new FileWriter("resultado.html"))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                pw.println(inputLine);
            }
            System.out.println("archivo creado");
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}