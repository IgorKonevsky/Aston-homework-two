package com.example.restApiCrudApp.servlets;

import com.example.restApiCrudApp.dto.IdDTO;
import com.example.restApiCrudApp.services.TeacherService;
import com.example.restApiCrudApp.services.impl.TeacherServiceImpl;
import com.example.restApiCrudApp.util.JsonHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "TeacherToGroupLinkServlet", value = "/api/link")
public class TeacherToGroupLinkServlet extends HttpServlet {


    private final TeacherService teacherService;

    private final JsonHandler jsonHandler;

    public TeacherToGroupLinkServlet() {
        this.teacherService = new TeacherServiceImpl();
        this.jsonHandler = new JsonHandler();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IdDTO ids = jsonHandler.readJson(req, IdDTO.class);

        jsonHandler.writeJson(resp, teacherService.linkTeacherToGroup(ids));

        resp.setStatus(HttpServletResponse.SC_CREATED);

    }

}
