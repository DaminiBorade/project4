package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.RoleBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.model.RoleModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * Role List functionality Controller. Performs operation for list, search
 * operations of Role
 *
 * @author Damini
 *
 */
@WebServlet(name = "RoleListCtl", urlPatterns = { "/ctl/RoleListCtl" })

public class RoleListCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(RoleListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel rmodel = new RoleModel();
		try {
			List rlist = rmodel.list();
			request.setAttribute("roleList", rlist);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		RoleBean bean = new RoleBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
		System.out.println("name>>>>>>" + request.getParameter("name"));
		bean.setId(DataUtility.getInt(request.getParameter("roleid")));
		System.out.println("Role<<<<<<<<<<<<<<<<<<<" + request.getParameter("roleid"));
		return bean;
	}

	/**
	 * Contains Display logics
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// log.debug("RoleListCtl doGet Start");
		System.out.println(">>>>>>>inside doGet>>>>");
		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		RoleBean bean = (RoleBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		RoleModel model = new RoleModel();
		try {
			list = model.Search(bean, pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("do get end>>>>>>>>>");
	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// log.debug("RoleListCtl doPost Start");
		List list = null;

		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		RoleBean bean = (RoleBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
//		System.out.println("...............................................>>>>>" + ids[0]);
		RoleModel model = new RoleModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			System.out.println("...................................Delete........................................");

			if (ids != null && ids.length > 0) {
				RoleBean deletebean = new RoleBean();

				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					// int d=Integer.parseInt(id);
					// deletebean.setId(id);
					try {
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Role is Deleted Successfully ", request);
					} catch (ApplicationException e) {
						// log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					}

				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			list = model.Search(bean, pageNo, pageSize);

			nextList = model.Search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

		} catch (Exception e) {
			// log.error(e);
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

		// log.debug("RoleListCtl doPost End");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_LIST_VIEW;
	}

}
