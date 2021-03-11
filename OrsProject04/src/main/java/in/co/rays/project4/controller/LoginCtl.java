package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.RoleBean;
import in.co.rays.project4.bean.UserBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.model.RoleModel;
import in.co.rays.project4.model.UserModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * Login functionality Controller. Performs operation for Login
 *
 * @author Damini
 * 
 */

@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

	private static final int serialVersionUID = 1;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	// private static Logger log = Logger.getLogger(LoginCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		// log.debug("LoginCtl Method validate Started");

		boolean pass = true;

		String op = request.getParameter("operation");
//		System.out.println("in sign up click.........................................");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
//		else if (!DataValidator.isPassword(request.getParameter("password"))) {
//		  request.setAttribute("password","Password should contain 8 letter with alpha-numeric and special Character");
//		  pass = false; 
//		  }

		// log.debug("LoginCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		// log.debug("LoginCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		// log.debug("LoginCtl Method populatebean Ended");
		System.out.println(bean.getLogin() + "" + bean.getPassword());

		return bean;
	}

	/**
	 * Display Login form
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		// log.debug(" Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		if (OP_LOG_OUT.equals(op)) {
			System.out.println("doget login");
			session.invalidate();
			ServletUtility.setSuccessMessage("User logout successfully", request);
			ServletUtility.forward(getView(), request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Submitting or login action performing
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		// log.debug(" Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		int id = DataUtility.getInt(request.getParameter("id"));

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			try {

				bean = model.authenticate(bean.getLogin(), bean.getPassword());

				if (bean != null) {

					session.setAttribute("user", bean);

					int rollId = bean.getRoll_Id();
					System.out.println("my role id:- " + bean.getRoll_Id());
					System.out.println("roll id: " + rollId);

					
					RoleBean rolebean = role.findByPK(rollId);

					if (rolebean != null) { 
						// System.out.println(rolebean.getName());
						session.setAttribute("role", rolebean.getName());
					}
					
					
					//front-controller
					String str = (String) session.getAttribute("uri");
					System.out.println("URI---- " + str);

					if (str == null) {
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						ServletUtility.redirect(str, request, response);
						return;
					}


				/*	ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
					return;*/
				} else {
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					//bussiness validation
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}

			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (OP_LOG_OUT.equals(op)) {

			// session = request.getSession();
			System.out.println("login dopost");

			session.invalidate();

			ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);

			return;

		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		// log.debug("UserCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
