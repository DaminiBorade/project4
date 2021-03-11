package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.CourseBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.model.CourseModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.ServletUtility;

/**
 * Course List functionality Controller. Performs operation for list, search
 * and delete operations of Course
 * @author Damini
 *
 */

@WebServlet(name="CourseListCtl",urlPatterns={"/ctl/CourseListCtl"})
public class CourseListCtl extends BaseCtl{
	
	protected void preload(HttpServletRequest request){
		CourseModel model =new CourseModel();
		
		//list<CousreBean> l=null;//memory optimization 
		try{
			List l=model.list();
			request.setAttribute("clist", l);
		}catch(ApplicationException e){
			e.printStackTrace();
		}
		//request.setAttribute("CourseModel", l);(key or object pair)
	}
	@Override
protected BaseBean populateBean(HttpServletRequest request){
	CourseBean bean =new CourseBean();
	bean.setId(DataUtility.getInt(request.getParameter("cname")));
	System.out.println("PopulatedBean>>>>>>>>>>>>>>"+request.getParameter("cname"));
//	bean.setCourse_Name(DataUtility.getString(request.getParameter("name")));
//	System.out.println("name>>>>>>>>>>>>>>>>>>"+request.getParameter("name"));
	populateDTO(bean, request);
	
	return bean;
	
}
/*Contain Display Logics
 */
protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
	//Log.debug(Method Doget Started);
	List list =null;
	List nextlist =null;
	int pageNo=1;
	int pageSize =DataUtility.getInt(request.getParameter("pageSize"));
    CourseBean bean=(CourseBean)populateBean(request);	
    CourseModel model=new CourseModel();
//	String op = DataUtility.getString(request.getParameter("operation"));
	// String[] ids = request.getParameterValues("ids");
try{
	list=model.search(bean,pageNo,pageSize);
	nextlist =model.search(bean,pageNo+1,pageSize);
	request.setAttribute("nextlist", nextlist.size());
	ServletUtility.setList(list, request);
	if(list == null|| list.size()==0){
		ServletUtility.setErrorMessage("Record Not Found", request);
	}
	ServletUtility.setList(list,request);
	ServletUtility.setPageNo(pageNo, request);
	ServletUtility.setPageSize(pageSize, request);
	ServletUtility.forward(getView(), request, response);
}
catch(ApplicationException e){
	//log.error(e);
	ServletUtility.handleException(e, request, response);
	return ;
}
//log.debug(Method Doget Ended);
}

/*
 Contain Submit logics*/
protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
	List list =null;
	List nextlist=null;
	int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
	int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
	pageNo =(pageNo == 0)? 1:pageNo;
	pageSize =(pageSize == 0)? DataUtility.getInt(request.getParameter("pageZize")):pageSize;
	String op=DataUtility.getString(request.getParameter("operation"));
	String[] ids=request.getParameterValues("ids");
	CourseBean bean=(CourseBean)populateBean(request);
	CourseModel model = new CourseModel();
	System.out.println("idss>>>>>>>>>>>>>"+ids);
	if(OP_SEARCH.equalsIgnoreCase(op)){
		pageNo=1;
	}else if(OP_NEXT.equalsIgnoreCase(op)){
		pageNo++;
	}else if(OP_PREVIOUS.equalsIgnoreCase(op)){
		pageNo--;
	}else if(OP_NEW.equalsIgnoreCase(op)){
		ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
		return;
	}else if (OP_RESET.equalsIgnoreCase(op)){
		ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
		return;
	}else if(OP_DELETE.equalsIgnoreCase(op)){
		pageNo=1;
		if(ids !=null&& ids.length>0){
			CourseBean deletebean=new CourseBean();
			for(String id : ids){
				deletebean.setId(DataUtility.getInt(id));
				try{
					model.delete(deletebean);
					//ServletUtility.forward(getView(), request, response);
				}catch (ApplicationException e) {
					e.printStackTrace();
					ServletUtility.handleException(e, request, response);
					return;
				}
				ServletUtility.setSuccessMessage("Course Deleted Successfully", request);
			}
		} else {
			ServletUtility.setErrorMessage("Select at least one record", request);
		}
	}
	try {
	//	if (!OP_DELETE.equalsIgnoreCase(op)) {
			list = model.search(bean, pageNo, pageSize);
			
			  nextlist=model.search(bean,pageNo+1,pageSize);
				
				request.setAttribute("nextlist", nextlist.size());
				
		ServletUtility.setBean(bean, request);

		//	System.out.println("(-----------------)"+ list.size());
	//		ServletUtility.setList(list, request);
	//	}
			
	} catch (ApplicationException e) {
		e.printStackTrace();
		//log.error(e);
		ServletUtility.handleException(e, request, response);
		return;
	}
	 if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record Found", request);
		}

ServletUtility.setBean(bean, request);

	ServletUtility.setList(list, request);
	ServletUtility.setBean(bean, request);
	ServletUtility.setPageNo(pageNo, request);
	ServletUtility.setPageSize(pageSize, request);
ServletUtility.forward(getView(), request, response);
}

			
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_LIST_VIEW;
	}

}
