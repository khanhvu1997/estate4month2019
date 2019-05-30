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

@WebServlet(urlPatterns = { "/admin-home" })
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 3748061495209230892L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
		
		
		
		  UserDTO userDTO = new UserDTO(); 
		  userDTO.setFullName("Hello ae");
		  request.setAttribute("dto", userDTO);
		 
		 
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/home.jsp");
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
