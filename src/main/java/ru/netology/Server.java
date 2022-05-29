package ru.netology;

import ru.netology.request.Request;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int POOL_SIZE = 64;
    private final ExecutorService threadPull;
    private static final Map<String, Map<String, Handler>> handlers = new HashMap<>();

    public Server() {
        threadPull = Executors.newFixedThreadPool(POOL_SIZE);

    }

    public void listen(int port) {
        try (final ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Ожидаю клиентов");
            while (true) {
                final Socket socket = serverSocket.accept();
                threadPull.submit(() -> handler(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handler(Socket socket) {

        try (final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             final BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream())) {
            final Request request;
            request = new Request(readRequest(in));

            if (!handlers.containsKey(request.getMethod())
                    && !handlers.get(request.getMethod()).containsKey(request.getPath())) {
                out.write((
                        "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Length: 48\r\n" +
                                "Connection: close\r\n" +
                                "Content-Type: text/html\r\n" +
                                "\r\n" +
                                "<html><head></head><body>Not Found</body><html>\n").getBytes());
                out.flush();
            }
            Handler hand = handlers.get(request.getMethod()).get(request.getPath());
            hand.handle(request, out);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    public void addHandler(String method, String path, Handler handler) {
        handlers.put(method, new HashMap<>());
        handlers.get(method).put(path, handler);
    }

    private static List<String> readRequest(BufferedReader in) throws IOException {
        List<String> requests = new LinkedList<>();
        String requestLine;
        while (!(requestLine = in.readLine()).equals("")) {
            requests.add(requestLine);
        }
        return requests;
    }
}