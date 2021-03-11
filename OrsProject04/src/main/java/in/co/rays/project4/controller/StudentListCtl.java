package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.StudentBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.model.CollegeModel;
import in.co.rays.project4.model.StudentModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * Student List functionality Controller. Performs operation for list, search
 * operations of Student
 *
 * @author Damini
 *
 */
@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/StudentListCtl" })
public class StudentListCtl extends BaseCtl{
	//private static Logger log = Logger.getLogger(StudentListCtl.class);
	@Override
    protected void preload(HttpServletRequest request){
       
       
    	CollegeModel cmodel= new CollegeModel();
    	
    	try{
    	    List clist= cmodel.list();
    	    
    	    request.setAttribute("CollegeList",clist);
          
            }
            catch(ApplicationException e){
            e.printStackTrace();
            }
            }
    
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        StudentBean bean = new StudentBean();

        bean.setFrist_Name(DataUtility.getString(request
                .getParameter("firstName")));
        bean.setLast_Name(DataUtility.getString(request.getParameter("lastName")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));
bean.setCollege_Id(DataUtility.getInt(request.getParameter("collegeid")));
System.out.println(">>>>>>>>>>>>>>>>>.collegeid"+request.getParameter("collegeid"));
        return bean;
    }

    /**
     * Contains Display logics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        //log.debug("StudentListCtl doGet Start");
        List list = null;

        int pageNo = 1;

        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        StudentBean bean = (StudentBean) populateBean(request);

        String op = DataUtility.getString(request.getParameter("operation"));

        StudentModel model = new StudentModel();
        try {
            list = model.search(bean, pageNo, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
            ServletUtility.setList(list, request);

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            //log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
     //   log.debug("StudentListCtl doGet End");
    }

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
      //  log.debug("StudentListCtl doPost Start");
    //	log.debug("StudentListCtl doPost Start");
        List list = null;
        String op = DataUtility.getString(request.getParameter("operation"));
        
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        StudentBean bean = (StudentBean) populateBean(request);
        
        String[] ids = request.getParameterValues("ids");
        StudentModel model = new StudentModel();


        if (OP_SEARCH.equalsIgnoreCase(op)) {
            pageNo = 1;
        } else if (OP_NEXT.equalsIgnoreCase(op)) {
            pageNo++;
        } else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
            pageNo--;
        }
        else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		}else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
		}  
        else if (OP_DELETE.equalsIgnoreCase(op)) {
            pageNo = 1;
            if (ids != null && ids.length > 0) {
            	StudentBean deletebean = new StudentBean();
           
                for (String id : ids) {
                    deletebean.setId(DataUtility.getInt(id));
                    try {
        				model.delete(deletebean);
						
					} catch (ApplicationException e) {
						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
						return;
					}System.out.println("20");
                    ServletUtility.setSuccessMessage(" Student Data Successfully Deleted", request);
                }
            } 
            else {
                ServletUtility.setErrorMessage(
                        "Select at least one record", request);
            }
        }

   
    try {
		list = model.search(bean, pageNo, pageSize);
	} catch (ApplicationException e) {
	//	log.error(e);
		ServletUtility.handleException(e, request, response);
		return;
	}
    if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
        ServletUtility.setErrorMessage("No record found ", request);
    }
    ServletUtility.setBean(bean, request);
    ServletUtility.setList(list, request);
    ServletUtility.setPageNo(pageNo, request);
    ServletUtility.setPageSize(pageSize, request);
    ServletUtility.forward(getView(), request, response);

//log.debug("StudentListCtl doGet End");
}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_LIST_VIEW;
	}

}
