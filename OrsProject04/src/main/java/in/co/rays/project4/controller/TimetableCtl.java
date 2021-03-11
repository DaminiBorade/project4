package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.bean.CourseBean;
import in.co.rays.project4.bean.SubjectBean;
import in.co.rays.project4.bean.TimetableBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.CourseModel;
import in.co.rays.project4.model.SubjectModel;
import in.co.rays.project4.model.TimetableModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 *  TimeTable functionality Controller. Performs operation for add, update, delete
 * and get TimeTable
 * @author Damini
 *
 */

@WebServlet(name="TimetableCtl",urlPatterns={"/ctl/TimetableCtl"})
public class TimetableCtl extends BaseCtl{
	/** The Constant serialVersionUID. */
	private static final int serialVersionUID = 1;
	
	/** The log. */
	//private static Logger log = Logger.getLogger(TimeTableCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	protected void preload(HttpServletRequest request) {
		CourseModel cmodel = new CourseModel();
		SubjectModel smodel = new SubjectModel();
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		try {
			clist = cmodel.list();
			slist = smodel.list();
			request.setAttribute("CourseList", clist);
			request.setAttribute("SubjectList", slist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {
		//log.debug("validate method of TimeTable Ctl started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Extime"))) {
			request.setAttribute("Extime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}
		
		
	//	log.debug("validate method of TimeTable Ctl End");
		return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected TimetableBean populateBean(HttpServletRequest request) {
	//	log.debug("populateBean method of TimeTable Ctl start");
		System.out.println("populate bean innnnnn");
		TimetableBean bean = new TimetableBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));
		System.out.println("id>>>>>>>>>>>>>>>>>>>>>"+request.getParameter("id"));
		
		bean.setSubject_Id(DataUtility.getInt(request.getParameter("subjectId")));
		System.out.println("subjectid>>>>>>>>>>>>>>>>>"+request.getParameter("subjectId"));
	//bean.setSubjectName(DataUtility.getString(request.getParameter("subjectname")));
		bean.setCourse_Id(DataUtility.getInt(request.getParameter("courseId")));
		System.out.println("courseid>>>>>>>>>>>>>>>>>"+request.getParameter("subjectId"));
//	bean.setCourseName(DataUtility.getString(request.getParameter("coursename")));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		System.out.println(DataUtility.getString(request.getParameter("semester")));
	//	bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setExam_Date(DataUtility.getDate(request.getParameter("ExDate")));
		System.out.println("exdateeee>>>>>>>>>>>>>>>>>"+request.getParameter("ExDate"));

		bean.setExam_Time(DataUtility.getString(request.getParameter("Extime")));
		System.out.println("extimeeee>>>>>>>>>>>>>>>>>"+request.getParameter("Extime"));

		
		/*System.out.println("<<<<<<__________>>>>>>>>");
		System.out.println(request.getParameter("ExDate"));
		System.out.println("<<<<<<__________>>>>>>>>");
*/		populateDTO(bean, request);
		//log.debug("populateBean method of TimeTable Ctl End");
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
		//log.debug("do Get method of TimeTable Ctl Started");
	//System.out.println("Timetable ctl .do get started........>>>>>");

//		String op = DataUtility.getString(request.getParameter("operation"));
		int id = DataUtility.getInt(request.getParameter("id"));
	
		TimetableModel model = new TimetableModel();
		TimetableBean bean = null;
		if (id > 0) {
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
			//	log.error(e);
				ServletUtility.handleException(e, request, response);
			}
		}

		//log.debug("do Get method of TimeTable Ctl End");
		System.out.println("Timetable ctl .do get End........>>>>>");
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
	//	log.debug("do post method of TimeTable Ctl start");
		System.out.println("dopost in");

		List list;
		String op = DataUtility.getString(request.getParameter("operation"));
		int id = DataUtility.getInt(request.getParameter("id"));

		TimetableModel model = new TimetableModel();
	
		
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) 
		{
			TimetableBean bean = (TimetableBean)populateBean(request);
			try {
				if(id>0){
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" TimeTable is Successfully Updated", request);
					
				}else{

				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" TimeTable is Successfully Added", request);
				
	
				} 
				/*ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" TimeTable is Successfully Saved", request);
				*/
			}catch (ApplicationException e) {
				//log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateException e) {
				e.printStackTrace();
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Time Table already Exists", request);
			}
		}
		else if (OP_CANCEL.equalsIgnoreCase(op))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}
		else if ( OP_RESET.equalsIgnoreCase(op))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		
	
		ServletUtility.forward(getView(), request, response);
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_VIEW;
	}

}
