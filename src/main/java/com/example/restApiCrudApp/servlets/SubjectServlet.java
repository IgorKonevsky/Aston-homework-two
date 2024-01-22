package com.example.restApiCrudApp.servlets;

import com.example.restApiCrudApp.dto.SubjectDTO;
import com.example.restApiCrudApp.services.SubjectService;
import com.example.restApiCrudApp.services.impl.SubjectServiceImpl;
import com.example.restApiCrudApp.util.JsonHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SubjectServlet", value = "/api/subject/*")
public class SubjectServlet extends HttpServlet {
    private final SubjectService subjectService;
    private final JsonHandler jsonHandler;

    public SubjectServlet() {
        this.subjectService = new SubjectServiceImpl();
        this.jsonHandler = new JsonHandler();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();


        if (pathInfo == null || pathInfo.equals("/")) {
            List<SubjectDTO> subjectDTOS = subjectService.findAll();
            jsonHandler.writeJson(resp, subjectDTOS);
        } else {
            String stringId = pathInfo.substring(1);
            Long id = Long.parseLong(stringId);
            SubjectDTO subjectDTO = subjectService.findById(id);
            jsonHandler.writeJson(resp, subjectDTO);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectDTO subjectDTO = jsonHandler.readJson(req, SubjectDTO.class);

        jsonHandler.writeJson(resp, subjectService.create(subjectDTO));

        resp.setStatus(HttpServletResponse.SC_CREATED);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectDTO subjectDTO = jsonHandler.readJson(req, SubjectDTO.class);

        Long id = Long.parseLong(req.getPathInfo().substring(1));
        subjectDTO.setId(id);

        jsonHandler.writeJson(resp, subjectService.update(subjectDTO));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String stringId = pathInfo.substring(1);
        Long id = Long.parseLong(stringId);
        subjectService.delete(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
