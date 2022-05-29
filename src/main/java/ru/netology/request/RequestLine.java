package ru.netology.request;

public class RequestLine {
    private final String method;
    private final String path;
    private final String protocol;
    private final QueryParams queryParameters;

    public RequestLine(String requestLine) {
        // Пробуем распарсить строку запроса
        String[] partsRequestLine = requestLine.split(" ");
        if (partsRequestLine.length != 3) {
            throw new IllegalArgumentException("Invalid request line" + requestLine);
        }
        this.method = partsRequestLine[0];

        final String[] pathParts = partsRequestLine[1].split("\\?");
        this.path = pathParts[0];

        //Если есть квери параметры
        if (pathParts.length > 1) {
            this.queryParameters = new QueryParams(pathParts[1]);
            System.out.println(queryParameters);
        } else {
            this.queryParameters = null;
        }

        this.protocol = partsRequestLine[2];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public QueryParams getQueryParameters() {
        return queryParameters;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
