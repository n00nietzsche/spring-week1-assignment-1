package com.codesoom.assignment.controllers;

import com.codesoom.assignment.services.TaskService;
import com.codesoom.assignment.utils.JsonConverter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public abstract class Controller {
    protected static final String TO_JSON_FAIL = "Json conversion fail.";
    protected static final String INVALID_REQUEST = "Invalid request.";
    protected static final String INVALID_ID = "Invalid id";

    protected final static TaskService TASK_SERVICE = new TaskService();

    protected void sendResponse(
            final HttpExchange exchange, final int statusCode, final String content
    ) throws IOException {
        final OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(statusCode, content.getBytes().length);
        outputStream.write(content.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }

    protected void sendObject(final HttpExchange exchange, final int httpStatusCode, final Object object) throws IOException {
        final Optional<String> jsonStringOptional = JsonConverter.toJson(object);
        if (jsonStringOptional.isEmpty()) {
            sendResponse(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, TO_JSON_FAIL);
            return;
        }
        sendResponse(exchange, httpStatusCode, jsonStringOptional.get());
    }
}
