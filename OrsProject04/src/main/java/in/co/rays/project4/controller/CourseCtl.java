package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.CourseBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.CourseModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;


/**
 * Course functionality Controller. Performs operation for add, update, delete
 * and get Course

 * @author Damini
 *
 */
@WebServlet(name="CourseCtl",urlPatterns={"/ctl/CourseCtl"})
public class CourseCtl extends BaseCtl{
	
	/** The Constant serialVersionUID. */
	private static final int serialVersionUID = 1;
	
	/** The log. */
	//private static Logger log = Logger.getLogger(CourseCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {
		//log.debug("CourseCtl validate started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Course Name"));
			pass = false ;
		}else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", "Invalid Course"));
			 pass = false ;
		}
		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
			pass = false ;
		}
		if (DataValidator.isNull(request.getParameter("discription"))) {
			request.setAttribute("discription", PropertyReader.getValue("error.require", "Description"));
			pass = false ;
		}

		//log.debug("CourseCtl validate End");
		return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request){
		//log.debug("CourseCtl PopulatedBean started");
		CourseBean bean = new CourseBean();
		
		bean.setId(DataUtility.getInt(request.getParameter("id")));
		System.out.println("id>>>>>>>>>>>>>>>>>>>>"+request.getParameter("id"));
		bean.setCourse_Name(DataUtility.getString(request.getParameter("name")));
		//System.out.println("Name>>>>>>>>>"+request.getParameter("name"));
		bean.setDuration(DataUtility.getString(request.getParameter("duration")));
		//System.out.println("duration>>>>>>>>>"+request.getParameter("duration"));

		bean.setDiscription(DataUtility.getString(request.getParameter("discription")));
	
		populateDTO(bean, request);
		//log.debug("CourseCtl PopulatedBean End");
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
		//log.debug("Do get method od courseCtl started");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		// get Model
		CourseModel model = new CourseModel();
		int id = DataUtility.getInt(request.getParameter("id"));
		
		if(id>0){
			CourseBean bean;
			try{
			bean = model.findByPk(id);
			ServletUtility.setBean(bean, request);
			
			}catch(ApplicationException e){
				//log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
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
		//log.debug("Do Post method of CourseCtl started ");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		// Get Model
		CourseModel model = new CourseModel();
		int id = DataUtility.getInt(request.getParameter("id"));
	
		if(OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)){
			CourseBean bean =(CourseBean) populateBean(request);
		try{
			if(id>0){		
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Course is Successfully Updated", request);
				
			}else{
				 int pk = model.add(bean);
				 ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Course is Successfully Added", request);
				
			//		bean.setId(pk);
			}
				ServletUtility.setBean(bean, request);
			//ServletUtility.setSuccessMessage("Course is Successfully Added", request);
		
		}catch(ApplicationException e ){
			e.printStackTrace();
			//log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (DuplicateException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Course Name Already Exist", request);
			
		}		
		}/*else if (OP_DELETE.equalsIgnoreCase(op)) {
			CourseBean bean =(CourseBean) populateBean(request);
			try {
				model.delete(bean);;
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return ;
			}
		}*/
		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		//log.debug("Do Post method CourseCtl Ended");
	
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_VIEW;
	}

}
