package in.co.rays.project4.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project4.util.ServletUtility;

/**
 * Main Controller performs session checking and logging operations before l *
 * calling any application controller. It prevents any user to access
 * application without login.
 * 
 * 
 * @author Session Facade
 * 
 */
@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontController implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		//System.out.println("Im in FrontCtrl");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {

			System.out.println("Front");

			request.setAttribute("message", " Your Session has been Expired... Please Login Again");

			// Set the URI
			String str = request.getRequestURI();
			System.out.println("uri front--" + str);

			session.setAttribute("uri", str);

			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
			// return;
		} else {
			chain.doFilter(req, resp);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig conf) throws ServletException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

}
