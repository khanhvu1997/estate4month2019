package com.khanhvu.controller.admin;

import java.io.IOException;
import java.rmi.ServerException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.khanhvu.dto.UserDTO;

@WebServlet(urlPatterns = { "/admin-building" })
public class BuildingController extends HttpServlet {

	private static final long serialVersionUID = 3748061495209230892L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
		
	String action = request.getParameter("action");
	String url ="";
	if(action.equals("LIST")) {
		url ="/views/building/list.jsp";
	}else if(action.equals("EDIT")) {
		url ="/views.building/edit.jsp";
	}
		RequestDispatcher rd = request.getRequestDispatcher(url);
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServerException, IOException {

	}

}
