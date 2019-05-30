package com.khanhvu.api;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khanhvu.dto.UserDTO;
import com.khanhvu.utils.HttpUtil;


@WebServlet(urlPatterns = {"/api-admin-user"})
public class UserAPI extends HttpServlet {

	private static final long serialVersionUID = -915988021506484384L;
	//private static final Object BuildingModel = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		UserDTO userDTO = HttpUtil.of(request.getReader()).toModel(UserDTO.class);
		//logic
		mapper.writeValue(response.getOutputStream(), userDTO);
	}
}