package com.example.restApiCrudApp.servlets;

import com.example.restApiCrudApp.dto.TeacherDTO;
import com.example.restApiCrudApp.services.TeacherService;
import com.example.restApiCrudApp.services.impl.TeacherServiceImpl;
import com.example.restApiCrudApp.util.JsonHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "TeacherServlet", value = "/api/teacher/*")
public class TeacherServlet extends HttpServlet {
    private final TeacherService teacherService;

    private final JsonHandler jsonHandler;

    public TeacherServlet() {
        this.teacherService = new TeacherServiceImpl();
        this.jsonHandler = new JsonHandler();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();


        if (pathInfo == null || pathInfo.equals("/")) {
            List<TeacherDTO> teacherDTOS = teacherService.findAllWithGroups();
            jsonHandler.writeJson(resp, teacherDTOS);
        } else {
            String stringId = pathInfo.substring(1);
            Long id = Long.parseLong(stringId);
            TeacherDTO teacherDTO = teacherService.findById(id);
            jsonHandler.writeJson(resp, teacherDTO);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherDTO teacherDTO = jsonHandler.readJson(req, TeacherDTO.class);

        jsonHandler.writeJson(resp, teacherService.create(teacherDTO));

        resp.setStatus(HttpServletResponse.SC_CREATED);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherDTO teacherDTO = jsonHandler.readJson(req, TeacherDTO.class);
        Long id = Long.parseLong(req.getPathInfo().substring(1));
        teacherDTO.setId(id);
        jsonHandler.writeJson(resp, teacherService.update(teacherDTO));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String stringId = pathInfo.substring(1);
        Long id = Long.parseLong(stringId);
        teacherService.delete(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
