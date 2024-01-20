package com.example.restApiCrudApp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class JsonHandler {
    private static final String RESPONSE_TYPE_JSON = "application/json";
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    public <T> T readJson(HttpServletRequest request, Class<T> targetClass) throws IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        return mapper.readValue(requestBody, targetClass);
    }

    public void writeJson(HttpServletResponse response, Object dto) throws IOException {

        response.setContentType(RESPONSE_TYPE_JSON);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(mapper.writeValueAsString(dto));
    }


}
