package br.sergio.bakbata_mansion;

import org.springframework.http.HttpStatusCode;

public record Message(int statusCode, String message) {

    public Message {
        if (statusCode < 0) {
            throw new IllegalArgumentException("Illegal status code: " + statusCode);
        }
        if (message == null) {
            message = "Error";
        }
    }

    public Message(HttpStatusCode code, String message) {
        this(code.value(), message);
    }

}
