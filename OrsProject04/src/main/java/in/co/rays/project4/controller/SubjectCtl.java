package in.co.rays.project4.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.bean.SubjectBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.CourseModel;
import in.co.rays.project4.model.SubjectModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;


/**
 *  Subject functionality Controller. Performs operation for add, update, delete
 * and get Subject
 * @author Damini
 *
 */
@WebServlet(name="SubjectCtl",urlPatterns={"/ctl/SubjectCtl"})
public class SubjectCtl extends BaseCtl {

	protected void preload(HttpServletRequest request){

		System.out.println("preload enter");
	CourseModel model =new CourseModel();
	try{
		List l =model.list();
		request.setAttribute("Courselist", l);
	}catch(ApplicationException e){
		e.printStackTrace();
	}
	System.out.println("Preload ended");
	}
	@Override
	protected boolean validate(HttpServletRequest request) {
		//log.debug("validate Method of Subject Ctl start");
		boolean pass =true;
		System.out.println("Validated in ");
	if(DataValidator.isNull(request.getParameter("name"))){
		request.setAttribute("name", PropertyReader.getValue("error.require","Student Name"));
		pass = false;
	}
//	else if(DataValidator.isName(request.getParameter("name"))){
//		request.setAttribute("name", PropertyReader.getValue("error.require","Invalid Name"));
//		pass = false;
//	}
	if(DataValidator.isNull(request.getParameter("description"))){
		request.setAttribute("description", PropertyReader.getValue("error.require","Description"));
		pass = false;
	}
	if(DataValidator.isNull(request.getParameter("coursename"))){
		request.setAttribute("coursename", PropertyReader.getValue("error.require","Course Name"));
		pass = false;
	}
//	log.debug("validate Method of Subject Ctl end");
	
	System.out.println("Validated out");
	return pass;
	}
	@Override
	protected SubjectBean populateBean(HttpServletRequest request){
		//log.debug("PopulatedBean methods subject ctl start");
		SubjectBean bean=new SubjectBean();
		System.out.println("populateBean in");
		bean.setId(DataUtility.getInt(request.getParameter("id")));
		System.out.println("id>>>>>>>>>>>>>>>>>>.."+request.getParameter("id"));
		bean.setSubject_Name(DataUtility.getString(request.getParameter("name")));
	System.out.println("Name>>>>>>>>>>>>>>>>>>>>"+request.getParameter("name"));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		System.out.println("Descrip>>>>>>"+request.getParameter("description"));
		bean.setCourse_Id(DataUtility.getInt(request.getParameter("coursename")));
		System.out.println("coursename>>>>>>>>>>>."+request.getParameter("coursename"));
		populateDTO(bean, request);
		//log.debug("PopulatedBean methods subject ctl ended");
		System.out.println("populatedbean out");
		return bean;
}
	/*Contains Display logics */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		//log.debug("Doget method of subject clt start")
		System.out.println("Do get in");
		
		String op=DataUtility.getString(request.getParameter("operation"));
		SubjectModel model =new SubjectModel();
		SubjectBean bean=null;
		
		int id =DataUtility.getInt(request.getParameter("id"));
		if(id > 0||op!=null){
			try {
				bean =model.findByPk(id);
				ServletUtility.setBean(bean, request);
			}catch(ApplicationException e){
				//log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		System.out.println("doget out");
		//log.debug("Doget Method subject ctl ended");
		ServletUtility.forward(getView(), request, response);
	}
	/*Contain Submit logic*/
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		System.out.println("doPost in");
		String op = DataUtility.getString(request.getParameter("operation"));
		int id =DataUtility.getInt(request.getParameter("id"));
 SubjectModel model =new SubjectModel();
 if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {	
		SubjectBean bean = (SubjectBean)populateBean(request);
	//	System.out.println("post in operaion save  ");
	
 try{
	 if(id>0){
		 model.update(bean);
		 ServletUtility.setBean(bean, request);
		 ServletUtility.setSuccessMessage("Subject is Succesfully update", request);
		  }else{
			  int pk =model.add(bean);
			  ServletUtility.setSuccessMessage("Subject is Succesfully add",request);
		  }
	 ServletUtility.setBean(bean, request);
 }
	 catch(ApplicationException e){
		 //log.debug(e);
		// e.printStackTrace();
		 ServletUtility.handleException(e, request, response);
		 return ;
	 
			  } catch (DuplicateException e) {
		// TODO Auto-generated catch block
				  ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Subject name already Exsist", request);
					} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				}
				else if (OP_RESET.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
					return;
				}
				else if (OP_CANCEL.equalsIgnoreCase(op) ) {
					ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
					return;
				}
		/*		else if (OP_DELETE.equalsIgnoreCase(op)) {
					SubjectBean bean =  populateBean(request);
					try {
						model.delete(bean);
					ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
					return;
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return ; 
					}
				}*/
				
			
				ServletUtility.forward(getView(), request, response);
				//log.debug("Do post Method of Subject Ctl End");
			}
			
			/* (non-Javadoc)
			 * @see in.co.rays.ors.controller.BaseCtl#getView()
			 */
		  
 
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_VIEW;
	}

}
