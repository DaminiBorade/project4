package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.FacultyBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.model.CollegeModel;
import in.co.rays.project4.model.CourseModel;
import in.co.rays.project4.model.FacultyModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;


/**
 * Faculty List functionality Controller. Performs operation  operations of Faculty
 * @author Damini
 *
 */
@WebServlet(name="FacultyListCtl",urlPatterns={"/ctl/FacultyListCtl"})
public class FacultyListCtl extends BaseCtl {
	//private static Logger log = Logger.getLogger(FacultyListCtl.class);
	
	 /* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	    protected void preload(HttpServletRequest request){
	       
	       CollegeModel cmodel = new CollegeModel();
	    	CourseModel comodel= new CourseModel();
	    	
	    	try{
	    	    List clist= cmodel.list();
	    	    List colist= comodel.list();
	    	    
	    	    request.setAttribute("CollegeList",clist);
	            request.setAttribute("CourseList",colist);
	            }
	            catch(ApplicationException e){
	            e.printStackTrace();
	            }
	            }
	    
	

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		FacultyBean bean = new FacultyBean();

		bean.setFrist_Name(DataUtility.getString(request.getParameter("firstname")));
		bean.setLast_Name(DataUtility.getString(request.getParameter("lastname")));
		bean.setEmail_id(DataUtility.getString(request.getParameter("login")));
		System.out.println("in populate ------------");
		bean.setCollege_id(DataUtility.getInt(request.getParameter("collegeid")));
		System.out.println(">>>>>>>>>>>>>>>>>>>"+bean.getCollege_id());
		bean.setCourse_id(DataUtility.getInt(request.getParameter("courseid")));
		System.out.println(">>>>>>>>>>>>>>>>>>"+ bean.getCourse_id());
		
		
	return bean;
	}
   
   /**
    * Contains Display logics.
    *
    * @param request the request
    * @param response the response
    * @throws ServletException the servlet exception
    * @throws IOException Signals that an I/O exception has occurred.
    */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		List list;
		List nextList=null;
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		FacultyModel model = new FacultyModel();
		FacultyBean bean = (FacultyBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
	  //  String[] ids = request.getParameterValues("ids");
	    	
		
		try {
			list = model.search(bean, pageNo, pageSize);
			
			  nextList=model.search(bean,pageNo+1,pageSize);
				
				request.setAttribute("nextlist", nextList.size());
				
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
               ServletUtility.setErrorMessage("No record found ", request);
           }
			ServletUtility.setList(list, request);
	        ServletUtility.setPageNo(pageNo, request);
	        ServletUtility.setPageSize(pageSize, request);
	        ServletUtility.forward(getView(), request, response);
	        
       } catch (ApplicationException e) {
			e.printStackTrace();
     //  	log.error(e);
			ServletUtility.handleException(e, request, response);
			return ;
		}
	    
	//	log.debug(" DoGet Method of Faculty Model End");
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
		List list;
		List nextList=null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		String op = DataUtility.getString(request.getParameter("operation"));

		FacultyBean bean = (FacultyBean)populateBean(request);
		FacultyModel model = new FacultyModel();

		String[] ids = (String[]) request.getParameterValues("ids");

		
				if (OP_SEARCH.equalsIgnoreCase(op)) 
				{
					pageNo = 1;
				} 
				else if (OP_NEXT.equalsIgnoreCase(op)) 
				{
					pageNo++;
				} 
				else if (OP_PREVIOUS.equalsIgnoreCase(op)) 
				{
					if(pageNo>1){
						pageNo--;
					}
					else{
					pageNo=1;
				}
				}
				else if (OP_NEW.equalsIgnoreCase(op)) 
				{
					ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
					return ;
				}				
				else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
					return;
				}
				
				else if (OP_DELETE.equalsIgnoreCase(op)) 
				{	
						pageNo =1;
						if(ids !=null && ids.length !=0){
						FacultyBean deletebean = new FacultyBean();
							for (String id : ids) {
								deletebean.setId(DataUtility.getInt(id));
								try {
									model.delete(deletebean);
								} catch (ApplicationException e) {
									e.printStackTrace();
						//			log.error(e);
									ServletUtility.handleException(e, request, response);
									return;
								}
								ServletUtility.setSuccessMessage("Data Deleted Succesfully", request);
							}
							
						}else{
							ServletUtility.setErrorMessage("Select at least one record", request);
						}
					}	
				try {
					list=model.search(bean, pageNo, pageSize);
					
					  nextList=model.search(bean,pageNo+1,pageSize);
						
						request.setAttribute("nextlist", nextList.size());
						
				   ServletUtility.setBean(bean, request);
				} catch (ApplicationException e) {
					e.printStackTrace();
					ServletUtility.handleException(e, request, response);
					return;
				}
				
				if(list == null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
					ServletUtility.setErrorMessage("No Record Found", request);
				}
				
				ServletUtility.setList(list, request);
				ServletUtility.setBean(bean, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);
	
				System.out.println("===faculty list ctl==="+list.size()+list+op);
//				 log.debug("UserListCtl doPost End");	
		}	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_LIST_VIEW;
	}

}
