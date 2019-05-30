package com.khanhvu.api;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khanhvu.dto.BuildingDTO;
import com.khanhvu.service.IBuildingService;
import com.khanhvu.service.impl.BuildingService;
import com.khanhvu.utils.HttpUtil;


@WebServlet(urlPatterns = {"/api-admin-building"})
public class BuildingAPI extends HttpServlet {

	private static final long serialVersionUID = -915988021506484384L;
	//private static final Object BuildingModel = null;
	
	
	
	  private IBuildingService buildingService; 
	  public BuildingAPI() {
	  buildingService = new BuildingService(); 
	  }
	 
	 
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO buildingDTO = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
		//logic
		buildingDTO = buildingService.save(buildingDTO);
		mapper.writeValue(response.getOutputStream(), buildingDTO);
	}
	 
	
	/*
	 * protected void doPut(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException {
	 * request.setCharacterEncoding("UTF-8");
	 * response.setContentType("application/json");
	 * HttpUtil.of(request.getReader());}
	 */
 
	
	/*
	 * protected void doDelete(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException {
	 * request.setCharacterEncoding("UTF-8");
	 * response.setContentType("application/json");
	 * HttpUtil.of(request.getReader()); }
	 */
	 
	 
	 
	
}