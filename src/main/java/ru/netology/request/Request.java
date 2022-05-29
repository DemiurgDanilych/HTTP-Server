package ru.netology.request;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private final RequestLine requestLine;
    private final Map<String, String> headers = new HashMap<>();
    private final Body body;

    public Request(List<String> requestLines) throws IOException {
        requestLine = new RequestLine(requestLines.remove(0));
        System.out.println("Строка запроса :\r\n" + requestLine);

        for (String str : requestLines) {
            if (str.equals("")) break;
            String[] strArr = str.split(":");
            headers.put(strArr[0].trim(), strArr[1].trim());
        }
        System.out.println("Хэдеры :\r\n" + headers);

        body = new Body(requestLines.stream()
                .dropWhile(line -> !line.equals(""))
                .collect(Collectors.toList()));
        System.out.println("Тело :\r\n" + body);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Body getBody() {
        return body;
    }

    public List<NameValuePair> getQueryParameters() {
        return requestLine.getQueryParameters().getQueryParams();
    }

    public List<NameValuePair> getQueryParameter(String param) {
        return requestLine.getQueryParameters().getQueryParam(param);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                ", body=" + body +
                '}';
    }
}
