package in.co.rays.project4.controller;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.RoleBean;
import in.co.rays.project4.bean.UserBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.exception.RecordNotFoundException;
import in.co.rays.project4.model.UserModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * User registration functionality Controller. Performs operation for User
 * Registration
 *
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name="UserRegistrationCtl",urlPatterns={"/UserRegistrationCtl"})
public class UserRegistrationCtl extends BaseCtl {

    public static final String OP_SIGN_UP = "SignUp";

   private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

      log.debug("UserRegistrationCtl Method validate Started");

        boolean pass = true;

        String login = request.getParameter("login");
        String dob = request.getParameter("dob");

        if (DataValidator.isNull(request.getParameter("firstName"))) {
            request.setAttribute("firstName",
                    PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("lastName"))) {
            request.setAttribute("lastName",
                    PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }
        if (DataValidator.isNull(login)) {
            request.setAttribute("login",
                    PropertyReader.getValue("error.require", "Login Id"));
            pass = false;
        } else if (!DataValidator.isEmail(login)) {
            request.setAttribute("login",
                    PropertyReader.getValue("error.email", "Login "));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("password"))) {
            request.setAttribute("password",
                    PropertyReader.getValue("error.require", "Password"));
            pass = false;
        }else if (!DataValidator.isPassword(request.getParameter("password"))) {
			  request.setAttribute("password","Password should contain 8 letter with alpha-numeric and special Character");
			  pass = false; 
		}

//        if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
//            request.setAttribute("confirmPassword", PropertyReader.getValue(
//                    "error.require", "ConfirmPassword"));
//            pass = false;
//        }
//        else if (!DataValidator.isPassword(request.getParameter("confirmPassword"))) {
//			  request.setAttribute("confirmPassword","Password should contain 8 letter with alpha-numeric and special Character");
//			  pass = false; 
//		}
        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender",
                    PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }
        if (DataValidator.isNull(dob)) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        } else if (!DataValidator.isDate(dob)) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.date", "Date Of Birth"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}
//        if (!request.getParameter("password").equals(
//                request.getParameter("confirmPassword"))
//                && !"".equals(request.getParameter("confirmPassword"))) {
//            ServletUtility.setErrorMessage(
//                    "Confirm  Password  should not be matched.", request);
//
//            pass = false;
//        }
//        log.debug("UserRegistrationCtl Method validate Ended");

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("UserRegistrationCtl Method populatebean Started");

        UserBean bean = new UserBean();

        bean.setId(DataUtility.getInt(request.getParameter("id")));

        bean.setRoll_Id(RoleBean.STUDENT);

        bean.setFrist_Name(DataUtility.getString(request
                .getParameter("firstName")));

        bean.setLast_Name(DataUtility.getString(request.getParameter("lastName")));

        bean.setLogin(DataUtility.getString(request.getParameter("login")));

        bean.setPassword(DataUtility.getString(request.getParameter("password")));

       // bean.setNewPassword(DataUtility.getString(request.getParameter("confirmPassword")));

        bean.setGender(DataUtility.getString(request.getParameter("gender")));

        bean.setDOB(DataUtility.getDate(request.getParameter("dob")));
        bean.setMobile_No(DataUtility.getString(request.getParameter("mobileno")));
        System.out.println("mobile>>>>>>>>>>."+request.getParameter("mobileno"));

        populateDTO(bean, request);

        log.debug("UserRegistrationCtl Method populatebean Ended");

        return bean;
    }

    /**
     * Display concept of user registration
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("UserRegistrationCtl Method doGet Started");
        ServletUtility.forward(getView(), request, response);

    }

    /**
     * Submit concept of user registration
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        System.out.println("in get method");
        log.debug("UserRegistrationCtl Method doPost Started");
        String op = DataUtility.getString(request.getParameter("operation"));
        // get model
        UserModel model = new UserModel();
        int id = DataUtility.getInt(request.getParameter("id"));
        if (OP_SIGN_UP.equalsIgnoreCase(op)) {
            UserBean bean = (UserBean) populateBean(request);
            try {
                int pk = model.add(bean);
                bean.setId(pk);
                request.getSession().setAttribute("UserBean", bean);
                ServletUtility.setSuccessMessage("Data is successfully saved", request);
                ServletUtility.forward(getView(), request, response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateException e) {
               log.error(e);
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Login id already exists",
                        request);
                ServletUtility.forward(getView(), request, response);
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
		}
        
       log.debug("UserRegistrationCtl Method doPost Ended");
    }

    @Override
    protected String getView() {
        return ORSView.USER_REGISTRATION_VIEW;
    }
}
