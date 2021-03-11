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
import in.co.rays.project4.bean.TimetableBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.model.CourseModel;
import in.co.rays.project4.model.SubjectModel;
import in.co.rays.project4.model.TimetableModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * Timetable List functionality Controller. Performs operation for list, search and
 * delete operations of Timetable
 
 * @author Damini
 *
 */
@WebServlet(name="TimeTableListCtl",urlPatterns={"/ctl/TimetableListCtl"})
public class TimeTableListCtl extends BaseCtl {
	/** The log. */
	//private static Logger log = Logger.getLogger(TimeTableListCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	protected void preload(HttpServletRequest request) {

		CourseModel model = new CourseModel();
		SubjectModel smodel = new SubjectModel();
		List<CourseBean> list = null;
		List<SubjectBean> list2 = null;
		try {
			list = model.list();
			list2 = smodel.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("courseList", list);
		request.setAttribute("subjectList", list2);

	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		TimetableBean bean = new TimetableBean();

       //bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setCourse_Id(DataUtility.getInt(request.getParameter("clist")));
		
		bean.setSubject_Id(DataUtility.getInt(request.getParameter("slist")));
		
		//bean.setSubjectName(DataUtility.getString(request.getParameter("slist")));
		System.out.println("populate");
		bean.setExam_Date(DataUtility.getDate(request.getParameter("Exdate")));
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>date>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+request.getParameter("Exdate"));
		
		System.out.println("populate bean==========>>>> " + bean.getExam_Date());
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
		List list  = null ;
       
		List nextList=null;
		
		int pageNo = 1;
		
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
          
		//System.out.println(pageSize+"hhhhhhh");
		
       TimetableModel model = new TimetableModel();
		
       TimetableBean bean =(TimetableBean) populateBean(request);

//		String op = DataUtility.getString(request.getParameter("operation"));
   //   String[] ids = request.getParameterValues("ids");
	    

		try {
			list = model.search(bean, pageNo, pageSize);
		
			nextList=model.search(bean,pageNo+1,pageSize);
			
			request.setAttribute("nextlist", nextList.size());
			
			ServletUtility.setBean(bean, request);
			
	//		ServletUtility.setList(list, request);
			
			if (list==null && list.size()==0) {
				
				ServletUtility.setErrorMessage("No record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);


		} catch (ApplicationException e) {
			e.printStackTrace();
		//log.error(e);
			ServletUtility.handleException(e, request, response);
		}
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

		List list=null;
		List nextList=null;
		
		String op = DataUtility.getString(request.getParameter("operation"));

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo = (pageNo == 0) ? 1 : pageNo;
		
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;


		TimetableBean bean = (TimetableBean) populateBean(request);	
		
		TimetableModel model = new TimetableModel();
		
		String[] ids = (String[]) request.getParameterValues("ids");
				
			        if (OP_SEARCH.equalsIgnoreCase(op)) {
				    pageNo = 1;
					} else if (OP_NEXT.equalsIgnoreCase(op)) {
						pageNo++;	
					} 
			        
					else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
						pageNo--;
					}
					else if (OP_NEW.equalsIgnoreCase(op)) 
					{
						ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
						return ;
					}
					
					else if (OP_RESET.equalsIgnoreCase(op)||OP_BACK.equalsIgnoreCase(op)) {
						ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
						return;
					}
					else if (OP_DELETE.equalsIgnoreCase(op)) {
						pageNo=1;
						if (ids != null && ids.length > 0) {
							TimetableBean bean3 = new TimetableBean();

							for (String id2 : ids) {
								int id1 = DataUtility.getInt(id2);
								bean3.setId(id1);
								try {
									model.delete(bean3);
								} catch (ApplicationException e) {
									e.printStackTrace();
									ServletUtility.handleException(e, request, response);
									return;
								}
								ServletUtility.setSuccessMessage("Data Deleted Succesfully", request);
							}
						
						}else{
							ServletUtility.setErrorMessage("Select at least one Record", request);
						}
					}
			    	try {
			    		
						list = model.search(bean, pageNo, pageSize);
						//System.out.println(">>>>>>>>>>>>>>>>.");
						
						nextList=model.search(bean,pageNo+1,pageSize);
						
						request.setAttribute("nextlist", nextList.size());
						
						ServletUtility.setBean(bean, request);
					}
					catch(ApplicationException e){	
						ServletUtility.handleException(e, request, response);
						return;
					}
			   if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)) 
			{
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
	        ServletUtility.forward(getView(), request, response);			
		}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	protected String getView() {
		return ORSView.TIMETABLE_LIST_VIEW;
	}


}
