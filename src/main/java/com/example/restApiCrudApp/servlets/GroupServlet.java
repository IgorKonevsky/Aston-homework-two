package com.example.restApiCrudApp.servlets;

import com.example.restApiCrudApp.dto.GroupDTO;
import com.example.restApiCrudApp.services.GroupService;
import com.example.restApiCrudApp.services.impl.GroupServiceImpl;
import com.example.restApiCrudApp.util.JsonHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GroupServlet", value = "/api/group/*")
public class GroupServlet extends HttpServlet {
    private final GroupService groupService;

    private final JsonHandler jsonHandler;

    public GroupServlet() {
        this.groupService = new GroupServiceImpl();
        this.jsonHandler = new JsonHandler();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();


        if (pathInfo == null || pathInfo.equals("/")) {
            List<GroupDTO> groupDTOS = groupService.findAllWithTeachers();
            jsonHandler.writeJson(resp, groupDTOS);
        } else {
            String stringId = pathInfo.substring(1);
            Long id = Long.parseLong(stringId);
            GroupDTO groupDTO = groupService.findById(id);
            jsonHandler.writeJson(resp, groupDTO);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GroupDTO groupDTO = jsonHandler.readJson(req, GroupDTO.class);

        jsonHandler.writeJson(resp, groupService.create(groupDTO));

        resp.setStatus(HttpServletResponse.SC_CREATED);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GroupDTO groupDTO = jsonHandler.readJson(req, GroupDTO.class);

        Long id = Long.parseLong(req.getPathInfo().substring(1));
        groupDTO.setId(id);

        jsonHandler.writeJson(resp, groupService.update(groupDTO));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String stringId = pathInfo.substring(1);
        Long id = Long.parseLong(stringId);
        groupService.delete(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
