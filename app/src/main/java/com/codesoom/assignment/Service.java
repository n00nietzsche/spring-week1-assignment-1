package com.codesoom.assignment;

import com.codesoom.assignment.models.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Service {
    private ObjectMapper objectMapper = new ObjectMapper();
    private OutputStream outputStream;
    Repository repository = new Repository();

    public Task getTask(Long idValue) {
        return repository.findById(idValue);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public void createTask(Task postTask) {
        repository.create(postTask);
    }

    public void updateTask(Task updateTask, String title) {
        repository.update(updateTask, title);
    }

    public void deleteTask(Task deleteTask) {
        repository.remove(deleteTask);
    }

    public void writeContentWithOutputStream(HttpExchange exchange, String content) throws IOException {
        outputStream = exchange.getResponseBody();
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public Task jsonToTask(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Task.class);
    }

    public String tasksToJson(List<Task> tasks) throws IOException {
        outputStream = new ByteArrayOutputStream();
        objectMapper.writeValue(outputStream, tasks);
        return outputStream.toString();
    }

    public String taskToJson(Task task) throws IOException {
        outputStream = new ByteArrayOutputStream();
        objectMapper.writeValue(outputStream, task);
        return outputStream.toString();
    }

}
