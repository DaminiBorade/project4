package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.CourseBean;
import in.co.rays.project4.bean.SubjectBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.model.CourseModel;
import in.co.rays.project4.model.SubjectModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;


/**
 * Subject List functionality Controller. Performs operation for list, search and
 * delete operations of Subject
 * @author Damini
 *
 */
@WebServlet(name="SubjectListCtl",urlPatterns={"/ctl/SubjectListCtl"})
public class SubjectListCtl extends BaseCtl{
	/** The log. */
//	private static Logger log = Logger.getLogger(SubjectListCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("Started preload");

		SubjectModel smodel = new SubjectModel();
		CourseModel cmodel = new CourseModel();

		List<SubjectBean> slist = null;
		List<CourseBean>  clist = null;

		try {
			clist = cmodel.list();
			slist = smodel.list();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		request.setAttribute("subjectList", slist);
		request.setAttribute("courseList", clist);
	}
     
	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		

		SubjectBean bean = new SubjectBean();
		System.out.println("enter doget");

		bean.setId(DataUtility.getInt(request.getParameter("subjectname")));
		bean.setCourse_Id(DataUtility.getInt(request.getParameter("coursename")));
        bean.setSubject_Name(DataUtility.getString(request.getParameter("name")));
		
		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains display logics.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		List nextList=null;


		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		SubjectBean bean = (SubjectBean) populateBean(request);
		SubjectModel model = new SubjectModel();
		/*
		 * String op = DataUtility.getString(request.getParameter("operation"));
		 * String[] ids = request.getParameterValues("ids");
		 */
		try {
			list = model.search(bean, pageNo, pageSize);
			
			 nextList=model.search(bean,pageNo+1,pageSize);
				
			request.setAttribute("nextlist", nextList.size());
				
			ServletUtility.setList(list, request);

			if (list == null && list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
		//	log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		//log.debug("do Get Method of SubjectList Ctl Ended ");
	}

	/**
	 * Contains Submit logics.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//log.debug("DoPost of SubjectlistCtl started");

		List list;
		List nextList=null;


		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		SubjectModel model = new SubjectModel();
		SubjectBean bean = (SubjectBean) populateBean(request);

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
			if (pageNo > 1) {
				pageNo--;
			} else {
				pageNo = 1;
			}
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				SubjectBean deletebean = new SubjectBean();

				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {
					//	log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					}
					ServletUtility.setSuccessMessage("Subject Deleted Successfully ", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			list = model.search(bean, pageNo, pageSize);
			
			 nextList=model.search(bean,pageNo+1,pageSize);
				
			request.setAttribute("nextlist", nextList.size());
				
			ServletUtility.setBean(bean, request);
	
		} catch (ApplicationException e) {
			e.printStackTrace();
			//log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		System.out.println("=============>>>>>>" + list + list.size());
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No Record Found", request);
		}
         ServletUtility.setBean(bean, request);
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_LIST_VIEW;
	}

	
}
