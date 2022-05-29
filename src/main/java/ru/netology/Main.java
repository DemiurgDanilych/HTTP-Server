package ru.netology;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();

        server.addHandler("GET", "/messages", ((request, out) -> {
            System.out.println("Привет из Handler: \r\n" + request.getRequestLine());
            out.write(("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 43\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    "<html><head></head><body>GET message</body><html>\n"
            ).getBytes());
            out.flush();
        }));

        server.addHandler("POST", "/messages", ((request, out) -> {
            System.out.printf("Привет из Handler: \r\n" + "%s%n%s%n", request.getPath(), request.getQueryParameters());
            out.write(("HTTP/1.1 200 OK\r\n" +
                    "Content-Length: 43\r\n" +
                    "Connection: close\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n" +
                    "<html><head></head><body>POST messages</body><html>\n"
            ).getBytes());
            out.flush();
        }));

        server.listen(9999);
    }
}



