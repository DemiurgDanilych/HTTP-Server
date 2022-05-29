package ru.netology.request;

import java.util.List;

public class Body {
    List<String> body;

    public Body(List<String> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Body{" +
                "body=" + body +
                '}';
    }
}
