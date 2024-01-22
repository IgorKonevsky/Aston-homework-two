package com.example.restApiCrudApp.servlets;

import com.example.restApiCrudApp.dto.StudentDTO;
import com.example.restApiCrudApp.services.StudentService;
import com.example.restApiCrudApp.services.impl.StudentServiceImpl;
import com.example.restApiCrudApp.util.JsonHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentServlet", value = "/api/student/*")
public class StudentServlet extends HttpServlet {
    private final StudentService studentService;

    private final JsonHandler jsonHandler;

    public StudentServlet() {
        this.studentService = new StudentServiceImpl();
        this.jsonHandler = new JsonHandler();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();


        if (pathInfo == null || pathInfo.equals("/")) {
            List<StudentDTO> studentDTOS = studentService.findAll();
            jsonHandler.writeJson(resp, studentDTOS);
        } else {
            String stringId = pathInfo.substring(1);
            Long id = Long.parseLong(stringId);
            StudentDTO studentDTO = studentService.findById(id);
            jsonHandler.writeJson(resp, studentDTO);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentDTO studentDTO = jsonHandler.readJson(req, StudentDTO.class);

        jsonHandler.writeJson(resp, studentService.create(studentDTO));

        resp.setStatus(HttpServletResponse.SC_CREATED);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentDTO studentDTO = jsonHandler.readJson(req, StudentDTO.class);

        Long id = Long.parseLong(req.getPathInfo().substring(1));
        studentDTO.setId(id);

        jsonHandler.writeJson(resp, studentService.update(studentDTO));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String stringId = pathInfo.substring(1);
        Long id = Long.parseLong(stringId);
        studentService.delete(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
