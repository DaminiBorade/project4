package in.co.rays.project4.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.MarksheetBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.MarksheetModel;
import in.co.rays.project4.model.StudentModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * /**
 * Marksheet functionality Controller. Performs operation for add, update,
 * delete and get Marksheet
 
 * @author Damini
 *
 */
@WebServlet(name="MarksheetCtl",urlPatterns={"/ctl/MarksheetCtl"})
public class MarksheetCtl extends BaseCtl{
	// private static Logger log = Logger.getLogger(MarksheetCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request){
		StudentModel model=new StudentModel();
		try{
			List l=model.list();
			request.setAttribute("Studentlist", l);
			}catch(ApplicationException e){
				//log.error(e);
			}
	}
	
@Override
protected boolean validate(HttpServletRequest request){
    //log.debug("MarksheetCtl Method validate Started");
boolean pass = true;
if (DataValidator.isNull(request.getParameter("rollNo"))) {
	request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
	pass = false;
}
//else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
//	request.setAttribute("rollNo", "RollNumber should be in 00EC0000");
//	pass = false;
//}
/*if (DataValidator.isNull(request.getParameter("StudentId"))) {
	request.setAttribute("StudentId", PropertyReader.getValue("error.require", "Student Name"));
	pass = false;
}*/
if (DataValidator.isNull(request.getParameter("StudentId"))) {
	request.setAttribute("StudentId", PropertyReader.getValue("error.require", "Student Name"));
	pass = false;
}
if (DataValidator.isNull(request.getParameter("physics"))) {
	request.setAttribute("physics", PropertyReader.getValue("error.require", "physics marks"));
	pass = false;
} else if (DataUtility.getInt(request.getParameter("physics")) < 0) {
	request.setAttribute("physics", "Marks can not less than 0");
	pass = false;
} else if (DataUtility.getInt(request.getParameter("physics")) > 100) {
	request.setAttribute("physics", "Marks can not be more than 100");
	pass = false;
} else if (DataValidator.isNotNull(request.getParameter("physics"))
		&& !DataValidator.isInteger(request.getParameter("physics"))) {
	request.setAttribute("physics", PropertyReader.getValue("error.integer", "Physics marks"));
	pass = false;
}

if (DataValidator.isNull(request.getParameter("chemistry"))) {
	request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry Mark"));
	pass = false;
} else if (DataUtility.getInt(request.getParameter("chemistry")) > 100) {
	request.setAttribute("chemistry", "Marks can Not More then 100");
	pass = false;
} else if (DataUtility.getInt(request.getParameter("chemistry")) < 0) {
	request.setAttribute("chemistry", "Marks can Not less then 0 ");
	pass = false;
} else if (DataValidator.isNotNull(request.getParameter("chemistry"))
		&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
	request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Chemistry Marks"));
	pass = false;
}

if (DataValidator.isNull(request.getParameter("maths"))) {
	request.setAttribute("maths", PropertyReader.getValue("error.require", "Maths Marks"));
	pass = false;
} else if (DataUtility.getInt(request.getParameter("maths")) > 100) {
	request.setAttribute("maths", "Marks can Not More then 100");
	pass = false;
} else if (DataUtility.getInt(request.getParameter("maths")) < 0) {
	request.setAttribute("maths", "Marks can Not less then 0 ");
	pass = false;
} else if (DataValidator.isNotNull(request.getParameter("maths"))
		&& !DataValidator.isInteger(request.getParameter("maths"))) {
	request.setAttribute("maths", PropertyReader.getValue("error.integer", "Chemistry Marks"));
	pass = false;
}

//log.debug("MarksheetCtl Method validate Ended");
return pass;
	
}
protected BaseBean populateBean(HttpServletRequest request){

   // log.debug("MarksheetCtl Method populatebean Started");
	MarksheetBean bean = new MarksheetBean();
	bean.setId(DataUtility.getInt(request.getParameter("id")));
	bean.setRoll_No(DataUtility.getString(request.getParameter("rollNo")));
	//bean.setName(DataUtility.getString(request.getParameter("name")));
	//System.out.println(">>>>>>>>>>>>>>>>>>>>>"+request.getParameter("name"));
	bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
	System.out.println(">>>>>>>>>>>>>>>>"+request.getParameter("physics"));

	bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
	System.out.println(">>>>>>>>>"+request.getParameter("chemistry"));

	bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
	System.out.println(">>>>>>>>>>>>>>"+request.getParameter("maths"));

	bean.setStudent_Id(DataUtility.getInt(request.getParameter("StudentId")));
	populateDTO(bean, request);
	System.out.println("Populate is done");
    //log.debug("MarksheetCtl Method populatebean Ended");

	return bean;
	}
/**Contain display logics
 * @throws ServletException 
 * @throws IOException **/
protected void doGet(HttpServletRequest request ,HttpServletResponse response) throws IOException, ServletException{
//    log.debug("MarksheetCtl Method doGet Started");
String op =DataUtility.getString(request.getParameter("operation"));
MarksheetModel model =new MarksheetModel();
int id =DataUtility.getInt(request.getParameter("id"));
System.out.println("inside doget>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");

if(id>0){
	MarksheetBean bean ;
	try{
		bean=model.findByPK(id);
		ServletUtility.setBean(bean, request);
		
	}catch(ApplicationException e){
		//log.error(e);
		ServletUtility.handleException(e, request, response);
		return;
	}
}
ServletUtility.forward(getView(), request, response);
//log.debug("MarksheetCtl Method doGet Ended");

}
/**Contain Submit Logic
 * @throws ServletException 
 * @throws IOException **/
protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
   // log.debug("MarksheetCtl Method doPost Started");
	//log.debug("MarksheetCtl Method doPost Started");

	String op = DataUtility.getString(request.getParameter("operation"));
	int id = DataUtility.getInt(request.getParameter("id"));
	MarksheetBean bean = (MarksheetBean) populateBean(request);
	
	// get model
	MarksheetModel model = new MarksheetModel();

	if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
		try {
			if (id > 0) {
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Marksheet is Successfully Updated ", request);

			} else {
				int pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Marksheet is Successfully Added ", request);
		
			}
			

		} catch (ApplicationException e) {
			//log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		} catch (DuplicateException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Roll no already exists", request);
		}

	} else if (OP_RESET.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
		return;
	} else if (OP_CANCEL.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
		return;
	}
	ServletUtility.setBean(bean, request);
	ServletUtility.forward(getView(), request, response);

	//log.debug("MarksheetCtl Method doPost Ended");
}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_VIEW;
	}

}
