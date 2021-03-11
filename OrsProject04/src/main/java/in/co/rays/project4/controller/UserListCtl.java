package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.UserBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.model.RoleModel;
import in.co.rays.project4.model.UserModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * User List functionality Controller. Performs operation for list, search and
 * delete operations of User
 *
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(UserListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel rmodel = new RoleModel();
		UserModel umodel = new UserModel();
		try {
			List rl = rmodel.list();
			List ul = umodel.list();
			request.setAttribute("Rolem", rl);
			request.setAttribute("Userm", ul);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();
		// bean.setId(DataUtility.getInt(request.getParameter("firstn")));

		bean.setFrist_Name(DataUtility.getString(request.getParameter("firstName")));

		bean.setRoll_Id(DataUtility.getInt(request.getParameter("roleid")));
		System.out.println("ROLE>>>>>>>>>>>>>>>>>" + request.getParameter("roleid"));

		// bean.setLast_Name(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		System.out.println("LOGIN>>>>>>>>>>>>>>>>>" + request.getParameter("login"));

		return bean;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserListCtl doGet Start");
		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		UserBean bean = (UserBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		UserModel model = new UserModel();
		try {
			list = model.search(bean, pageNo, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
				return;
			} else {
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);

			}
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		// log.debug("UserListCtl doPOst End");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserListCtl doPost Start");

		List list;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		UserBean bean = (UserBean) populateBean(request);
		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		UserModel model = new UserModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				UserBean deletebean = new UserBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("User is Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {

			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("UserListCtl doGet End");

	}

	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}

}
