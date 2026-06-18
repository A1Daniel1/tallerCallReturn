# Network Programming Workshop (tallerCallReturn)

**Author:** Daniel Alexander Ahumada León

This repository contains a set of network programming exercises in Java, exploring URLs, TCP Sockets, HTTP Servers, UDP/Datagrams, and Remote Method Invocation (RMI).

---

## Table of Contents
1. [Exercise 1: URL Parser](#exercise-1-url-parser)
2. [Exercise 2: Save URL Content to HTML](#exercise-2-save-url-content-to-html)
3. [Exercise 3: Square Calculation Echo Server](#exercise-3-square-calculation-echo-server)
4. [Exercise 4: Trigonometric HTTP Server](#exercise-4-trigonometric-http-server)
5. [Exercise 5: Basic HTTP Web Server](#exercise-5-basic-http-web-server)
6. [Exercise 6: Static File HTTP Server](#exercise-6-static-file-http-server)
7. [Exercise 7: UDP Datagram Time Server](#exercise-7-udp-datagram-time-server)
8. [Exercise 8: RMI Echo Server](#exercise-8-rmi-echo-server)
9. [Bonus: Peer-to-Peer RMI Chat Application](#bonus-peer-to-peer-rmi-chat-application)
10. [How to Run the Exercises](#how-to-run-the-exercises)

---

## Exercise 1: URL Parser
This program takes a URL and displays its structural components using Java's `java.net.URL` class. 

For example, using the URL:
`https://www.ejemplo.com:8080/docs/indice.html?usuario=daniel#seccion1`

The application extracts and prints the Protocol, Authority, Host, Port, Path, Query, File, and Reference/Anchor components.
*(Visual output reference: `image-1.png`)*

## Exercise 2: Save URL Content to HTML
Building on Exercise 1, this program prompts the user via the console to input a URL, reads its full HTML source code using an `InputStreamReader`, and saves/writes the downloaded content into a local file called `resultado.html` using a `PrintWriter`. This file can then be opened directly in any web browser.
*(Visual output references: `image-2.png`, `image-3.png`)*

## Exercise 3: Square Calculation Echo Server
A TCP socket-based server that listens on port `35000`. It processes incoming text requests, extracts a number from the input or query path, computes the square of that number, and returns the calculated result to the client.
*(Visual output reference: `image-4.png`)*

## Exercise 4: Trigonometric HTTP Server
An extension of the custom HTTP server implementation. It parses incoming HTTP GET requests looking for specific URI routes such as `/sin?val=...` or `/cos?val=...`. It dynamically extracts the value parameter, calculates its mathematical sine or cosine using `Math.sin()` or `Math.cos()`, and responds with a fully formatted HTML webpage showing the calculated result.

## Exercise 5: Basic HTTP Web Server
A low-level single-connection HTTP server running on port `35000`. It prints incoming HTTP request headers to the standard output and replies to the client with a basic HTML template response containing a "My Web Site" message along with the trailing metadata of the request.

## Exercise 6: Static File HTTP Server
A looping HTTP server that operates on port `35000` capable of serving real local static files. It parses the requested path from the incoming HTTP request line (e.g., `GET /index.html` or `GET /cat.png`), determines the appropriate MIME type header (`text/html`, `image/png`, `image/jpeg`, etc.), reads the raw bytes from disk, and streams them back over the socket connection.

## Exercise 7: UDP Datagram Time Server
A time service implemented using connectionless UDP packets (`DatagramSocket` and `DatagramPacket`).
*   **DatagramTimeServer:** Listens on port `4445`, receives an empty trigger packet from a client, looks up the current system date and time, and sends a UDP packet containing the string representation back to the client's origin address.
*   **DatagramTimeClient:** Initiates a packet to the server at `127.0.0.1:4445`, blocks until it receives the server's response packet, and prints the timestamp to the console.

## Exercise 8: RMI Echo Server
An introduction to Remote Method Invocation (RMI) where a remote service is declared and registered into an RMI Registry. 
*   `EchoServer.java`: Defines the remote interface extending `java.rmi.Remote`.
*   `EchoServerImpl.java`: Implements the remote interface, adding a `"desde el servidor: "` prefix to any incoming string.
*   `EchoClient.java`: Looks up the registered `echoServer` service from the registry and invokes the remote echo method.

## Bonus: Peer-to-Peer RMI Chat Application
Located in the `chat/` directory, this is an interactive console-based peer-to-peer chat application built on RMI.
*   Unlike typical client-server architectures, each instance exposes its own remote interface (`ChatClientInterface`) on a user-defined local port.
*   Upon startup, each node prompts for a local username, local registry port, and the target destination IP and port of another peer node.
*   Once both nodes have cross-referenced their stubs, users can engage in real-time, bi-directional console messaging.

---

## How to Run the Exercises

Execute the commands from the root directory of the project.

### Exercise 1: URL Parser
```bash
javac ejercicio1/URLReader.java
java -cp ejercicio1 URLReader
```

### Exercise 2: Save URL Content
```bash
javac ejercicio2/SaveURL.java
java -cp ejercicio2 SaveURL
```

### Exercise 3: Square Echo Server
```bash
javac ejercicio3/EchoServer.java
java -cp ejercicio3 EchoServer
```

### Exercise 4: Trigonometric Server
```bash
javac ejercicio4/CalculatorServer.java
java -cp ejercicio4 CalculatorServer
```

### Exercise 5: Basic HTTP Server
```bash
javac ejercicio5/HttpServer.java
java -cp ejercicio5 HttpServer
```

### Exercise 6: Static File Server
```bash
javac ejercicio6/FileServer.java
java -cp ejercicio6 FileServer
```

### Exercise 7: UDP Datagram Time Server & Client
Run the server in one terminal and the client in another:
```bash
# Terminal 1: Server
javac ejercicio7/DatagramTimeServer.java ejercicio7/DatagramTimeClient.java
java -cp ejercicio7 DatagramTimeServer

# Terminal 2: Client
java -cp ejercicio7 DatagramTimeClient
```

### Exercise 8: RMI Echo Server & Client
```bash
javac ejercicio8/*.java
# Note: Ensure an RMI registry is running or configured matching your local environment parameters.
```

### RMI P2P Chat Application
Run two separate instances in separate terminals to establish a chat link:
```bash
# Compile everything
javac chat/*.java

# Run instance
java chat.ChatApp
```
