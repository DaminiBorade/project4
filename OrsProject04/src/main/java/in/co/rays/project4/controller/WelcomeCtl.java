package in.co.rays.project4.controller;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.util.ServletUtility;

/**
 *  Welcome functionality Controller. Performs operation for add, update, delete
 * and get Welcome
 * @author Damini
 *
 */

@WebServlet(name = "WelcomeCtl", urlPatterns = { "/WelcomeCtl" })
public class WelcomeCtl extends BaseCtl {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	log.debug("WelcomeCtl Method doGet Started");
		ServletUtility.forward(getView(), req, resp); 

	}

	@Override
	protected String getView() {
		return ORSView.WELCOME_VIEW;
	}

}
