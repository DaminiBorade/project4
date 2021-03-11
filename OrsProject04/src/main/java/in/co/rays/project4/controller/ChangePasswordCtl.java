package in.co.rays.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.UserBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.RecordNotFoundException;
import in.co.rays.project4.model.UserModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;





/**
 * Change Password functionality Controller. Performs operation for Change Password
 * @author Damini
 *
 */
@WebServlet(name = "ChangePassword", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl {
	/** The Constant serialVersionUID. */
	private static final int serialVersionUID = 1;

	/** The Constant OP_CHANGE_MY_PROFILE. */
	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	/** The log. */
	// private static Logger log = Logger.getLogger(ChangePasswordCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		// log.debug("ChangePasswordCtl Method validate Started");

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {

			return pass;
		}
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		}

		/*
		 * else if
		 * (!DataValidator.isPassword(request.getParameter("oldPassword"))) {
		 * request.setAttribute("oldPassword",
		 * "Password should contain 8 letter with alpha-numeric and special Character"
		 * ); pass = false; }
		 */
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} else if (request.getParameter("oldPassword").equals(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Old password and New password should not be same!!");
			pass = false;
		}

		else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword",
					"Password should contain 8 letter with alpha-numeric,capital latter and special Character");
			pass = false;
		}

		/*
		 * if (!request.getParameter("newPassword").equals(request.getParameter(
		 * "confirmPassword")) &&
		 * !"".equals(request.getParameter("confirmPassword"))) {
		 * ServletUtility.setErrorMessage(
		 * "New and confirm passwords should be matched", request);
		 * 
		 * pass = false; }
		 */

		/*
		 * if (request.getParameter("oldPassword").equals(request.getParameter(
		 * "newPassword"))) { ServletUtility.setErrorMessage(
		 * "Old and NewPasswords should not be same", request);
		 * 
		 * pass = false; }
		 */

		// log.debug("ChangePasswordCtl Method validate Ended");

		return pass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		// log.debug("ChangePasswordCtl Method populatebean Started");
		System.out.println("In populate bean>>>>>>>>>>>>>jjkhkjh");

		UserBean bean = new UserBean();

		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));

		bean.setNewPassword(DataUtility.getString(request.getParameter("newPassword")));

		populateDTO(bean, request);
		// log.debug("ChangePasswordCtl Method populatebean Ended");

		return bean;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("Indopost method>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HttpSession session = request.getSession(true);
		System.out.println("post is called");
		// log.debug("ChangePasswordCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel model = new UserModel();

		UserBean bean = (UserBean) populateBean(request);

		UserBean UserBean = (UserBean) session.getAttribute("user");
		System.out.println(">>>>>>>>>>>>>>>session>>>>>>>>>>"+session.getAttribute("user"));

		System.out.println("9999---->" + UserBean);
		String newPassword = (String) request.getParameter("newPassword");

		System.out.println("--newPassword--------------->" + newPassword);

		int id = UserBean.getId();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				System.out.println("inDopost");
				boolean flag = model.changePassword(id, bean.getPassword(), newPassword);

				if (flag == true) {
					bean = model.findByLogin(UserBean.getLogin());
					session.setAttribute("user", bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Password has been changed Successfully.", request);
				}
			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}
		} else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;

		}
		ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
		// log.debug("ChangePasswordCtl Method doGet Ended");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
