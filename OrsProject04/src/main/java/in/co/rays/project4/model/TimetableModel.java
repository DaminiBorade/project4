package in.co.rays.project4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.project4.bean.CourseBean;
import in.co.rays.project4.bean.SubjectBean;
import in.co.rays.project4.bean.TimetableBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.JDBCDataSource;
/**
 * JDBC Implementation of Timetable Model
 * @author Damini
 *
 */
public class TimetableModel {
	/** The log. */
	//private static Logger log = Logger.getLogger(TimeTableModel.class);
	
	private String CourseName;
	private String SubjcetName;

	/**
	 * Find next PK of TIMETABLE.
	 *
	 * @return the integer
	 * @throws ApplicationException the application exception
	 */

	public Integer nextPk() throws ApplicationException {
		//log.debug("Timetable model nextPk method Started ");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM ST_TIMETABLE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("database Exception ...", e);
			throw new ApplicationException("Exception in NextPk of TIMETABLE Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("TimeTable model nextpk method end");
		return pk + 1;
	}

	/**
	 * Add a TIMETABLE.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */

	public int add(TimetableBean bean) throws ApplicationException, DuplicateException {
	//	log.debug("TimeTable model Add method End");
		Connection conn = null;
		int pk = 0;

		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPk(bean.getCourse_Id());
		String CourseName= coubean.getCourse_Name();
		
		SubjectModel  smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPk(bean.getSubject_Id());
		String SubjcetName= sbean.getSubject_Name();
		
		System.out.println("______________________________>>>>>"+bean.getExam_Date());
		TimetableModel model = new TimetableModel();
	
		TimetableBean bean1 = checkBycds(bean.getCourse_Id(), bean.getSemester(),  new java.sql.Date(bean.getExam_Date().getTime()));
		TimetableBean bean2 = checkBycss(bean.getCourse_Id(), bean.getSubject_Id(), bean.getSemester());
		 if(bean1 != null || bean2 != null){ 
		//	 throw new DuplicateException("TimeTable Already Exsist"); 
			 
		 }
	
		try {
			System.out.println(bean.getExam_Date());
			System.out.println(bean.getSemester());
			
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, CourseName);
			pstmt.setInt(3, bean.getCourse_Id());
			pstmt.setString(4, SubjcetName);
			pstmt.setInt(5, bean.getSubject_Id());
		    pstmt.setDate(6, new java.sql.Date(bean.getExam_Date().getTime()));
			pstmt.setString(7, bean.getExam_Time());
			pstmt.setString(8, bean.getSemester());
			pstmt.setString(9, bean.getCreated_By());
			pstmt.setString(10, bean.getModified_By());
			pstmt.setTimestamp(11, bean.getCreated_Date_Time());
			pstmt.setTimestamp(12, bean.getModified_Date_Time());
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in the Rollback of TIMETABLE Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Add method of TIMETABLE Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("TimeTable model Add method End");
		return pk;
		
	}

	/**
	 * Delete a TimeTable.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */

	public void delete(TimetableBean bean) throws ApplicationException {
		//log.debug("TIMETABLE Model Delete method Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("database Exception ...", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Rollback of Delte Method of TIMETABLE Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delte Method of TIMETABLE Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("TIMETABLE Model Delete method End");
	}

	/**
	 * Update a TIMETABLE.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */

	public void update(TimetableBean bean) throws ApplicationException, DuplicateException {
	//	log.debug("TIMETABLE Model update method Started");
		Connection conn = null;
//		CourseModel coumodel= new CourseModel();
//		CourseBean coubean=    coumodel.findByPk(bean.getCourse_Id());
//		String courseName= coubean.getCourse_Name();
//		
//		SubjectModel  smodel = new SubjectModel();
//		SubjectBean sbean = smodel.findByPk(bean.getSubject_Id());
//		String subjectName= sbean.getSubject_Name();
//	

		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPk(bean.getCourse_Id());
		String CourseName= coubean.getCourse_Name();
		
		SubjectModel  smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPk(bean.getSubject_Id());
		String SubjcetName= sbean.getSubject_Name();
		
		TimetableBean bean1 = checkBycds(bean.getCourse_Id(), bean.getSemester(),  new java.sql.Date(bean.getExam_Date().getTime()));
		TimetableBean bean2 = checkBycss(bean.getCourse_Id(), bean.getSubject_Id(), bean.getSemester());
		 if(bean1 != null || bean2 != null){ 
			 throw new DuplicateException("TimeTable Already Exsist"); 
			 
		 }
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_TIMETABLE SET COURSE_NAME=?,COURSE_ID=?,SUBJECT_NAME=?,SUBJECT_ID=?,EXAM_DATE=?,EXAM_TIME=?,SEMESTER=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			
			pstmt.setString(1, CourseName);
			pstmt.setInt(2, bean.getCourse_Id());
			pstmt.setString(3, SubjcetName);
			pstmt.setInt(4, bean.getSubject_Id());
		    pstmt.setDate(5, new java.sql.Date(bean.getExam_Date().getTime()));
			pstmt.setString(6, bean.getExam_Time());
			pstmt.setString(7, bean.getSemester());
			pstmt.setString(8, bean.getCreated_By());
			pstmt.setString(9, bean.getModified_By());
			pstmt.setTimestamp(10, bean.getCreated_Date_Time());
			pstmt.setTimestamp(11, bean.getModified_Date_Time());
			pstmt.setInt(12, bean.getId());
			
		pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			//log.error("database Exception....", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception in Rollback of Update Method of TimeTable Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in update Method of TimeTable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("TimeTable Model Update method End");
	}

	/**
	 * Find User by TimeTable Name.
	 *
	 * @param name            : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */

	public TimetableBean findByName(String name) throws ApplicationException {
		//log.debug("TimeTable Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE SubjectName = ?");
		Connection conn = null;
		TimetableBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();

				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
			    bean.setSemester(rs.getString(8));
				bean.setCreated_By(rs.getString(9));
				bean.setModified_By(rs.getString(10));
				bean.setCreated_Date_Time(rs.getTimestamp(11));
				bean.setModified_Date_Time(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByName Method of TimeTable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("TimeTable Model findByName method End");
		return bean;
	}

	/**
	 * Find User by TimeTable PK.
	 *
	 * @param pk            : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public TimetableBean findByPk(int pk) throws ApplicationException {
		//log.debug("TimeTable Model findBypk method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		Connection conn = null;
		TimetableBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();

				bean.setId(rs.getInt(1));
                bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
			    bean.setSemester(rs.getString(8));
			    bean.setCreated_By(rs.getString(9));
				bean.setModified_By(rs.getString(10));
				bean.setCreated_Date_Time(rs.getTimestamp(11));
				bean.setModified_Date_Time(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByPk Method of TimeTable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		//log.debug("TimeTable Model findByPk method End");
		return bean;
	}

	/**
	 * Search TimeTable.
	 *
	 * @param bean            : Search Parameters
	 * @return the list
	 * @throws ApplicationException the application exception
	 */

	public List search(TimetableBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search TimeTable with pagination.
	 *
	 * @param bean            : Search Parameters
	 * @param pageNo            : Current Page No.
	 * @param pageSize            : Size of Page
	 * @return list : List of Users
	 * @throws ApplicationException the application exception
	 */

	public List search(TimetableBean bean, int pageNo, int pageSize) throws ApplicationException {
	//	log.debug("TimeTable Model search method Started");

		Connection conn = null;
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");

		System.out.println("date   "+DataUtility.getDateString(bean.getExam_Date()));
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			if(bean.getCourse_Id() > 0){
				sql.append(" AND Course_ID = " + bean.getCourse_Id()); 
			}
			if(bean.getSubject_Id() > 0){
				sql.append(" AND Subject_ID = " + bean.getSubject_Id()); 
			}
			if (bean.getExam_Date() !=null && bean.getExam_Date().getTime() >0 ){
				
				System.out.println("===============...>>>>"+bean.getExam_Date());
			
				Date d = new Date(bean.getExam_Date().getTime());
				
				System.out.println("sql statement ==="+d);
				sql.append(" AND Exam_Date= '" + DataUtility.getDateString(bean.getExam_Date()) + "'");
			
			}
			
			if (bean.getCourse_Name() !=null && bean.getCourse_Name().length() >0 ) {
				sql.append(" AND Course_Name like '" + bean.getCourse_Name() + "%'");
			}
			
			if (bean.getSubject_Name() !=null && bean.getSubject_Name().length() >0 ) {
				sql.append(" AND Subject_Name like '" + bean.getSubject_Name() + "%'");
			}
			
		}

		// Page Size is greater then Zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		System.out.println("sql query "+sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();

				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
                bean.setSemester(rs.getString(8));
			    bean.setCreated_By(rs.getString(9));
				bean.setModified_By(rs.getString(10));
				bean.setCreated_Date_Time(rs.getTimestamp(11));
				bean.setModified_Date_Time(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		//	log.error("database Exception....", e);
			throw new ApplicationException("Exception in search Method of TimeTable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("TimeTable Model search method End");
		return list;
	}

	/**
	 * Get List of TimeTable.
	 *
	 * @return list : List of TimeTable
	 * @throws ApplicationException the application exception
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of TimeTable with pagination.
	 *
	 * @param pageNo            : Current Page No.
	 * @param pageSize            : Size of Page
	 * @return list : List of TimeTable
	 * @throws ApplicationException the application exception
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		//log.debug("TimeTable Model list method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE ");

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		System.out.println("------->>>>>>>>>>---"+sql);
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				TimetableBean bean = new TimetableBean();

				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));		
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreated_By(rs.getString(9));
				bean.setModified_By(rs.getString(10));
				bean.setCreated_Date_Time(rs.getTimestamp(11));
				bean.setModified_Date_Time(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
		e.printStackTrace();
			//log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("Timetable Model list method End");
		return list;
	}
	
    /**
     * Check bycss.
     *
     * @param CourseId the course id
     * @param SubjectId the subject id
     * @param i the semester
     * @return the time table bean
     * @throws ApplicationException the application exception
     */
    public   TimetableBean checkBycss(int CourseId , int SubjectId , String i) throws ApplicationException
    {
    	System.out.println("in from css.........................<<<<<<<<<<<>>>> ");
    	Connection conn = null ; 
    	TimetableBean bean=null;
  //  	java.util.Date ExamDAte,String ExamTime
    	StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Subject_ID=? AND Semester=?");
    	
    	try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, CourseId);
			ps.setInt(2, SubjectId);
			ps.setString(3, i);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				bean = new TimetableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreated_By(rs.getString(9));
				bean.setModified_By(rs.getString(10));
				bean.setCreated_Date_Time(rs.getTimestamp(11));
				bean.setModified_Date_Time(rs.getTimestamp(12));
			}rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			//log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("Timetable Model list method End");
		System.out.println("out from css.........................<<<<<<<<<<<>>>> ");
		return bean;
    }

    
    
        /**
         * Check bycds.
         *
         * @param CourseId the course id
         * @param i the semester
         * @param ExamDate the exam date
         * @return the time table bean
         * @throws ApplicationException the application exception
         */
        public  TimetableBean checkBycds(int CourseId, String i ,Date ExamDate) throws ApplicationException
    {
        	System.out.println("in from cds.........................<<<<<<<<<<<>>>> ");
        	StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Semester=? AND Exam_Date=?");
        	
        	Connection conn = null;
    	TimetableBean bean=null;
//    	Date ExDate = new Date(ExamDAte.getTime());
    	
    	
    	try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, CourseId);
			ps.setString(2, i);
			ps.setDate(3, (java.sql.Date)ExamDate);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				bean = new TimetableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
			    bean.setCreated_By(rs.getString(9));
				bean.setModified_By(rs.getString(10));
				bean.setCreated_Date_Time(rs.getTimestamp(11));
				bean.setModified_Date_Time(rs.getTimestamp(12));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			//log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("Timetable Model list method End");
	System.out.println("out from cds.........................<<<<<<<<<<<>>>> ");
		return bean;
    }
    
   /**
    * Check bysemester.
    *
    * @param CourseId the course id
    * @param SubjectId the subject id
    * @param semester the semester
    * @param ExamDAte the exam D ate
    * @return the time table bean
    */
   public static TimetableBean checkBysemester(int CourseId,int SubjectId,String semester,java.util.Date ExamDAte)
    {
    	
    	TimetableBean bean=null;
    	
    	Date ExDate = new Date(ExamDAte.getTime());
    	
    	StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND"
    			+ " SEMESTER=? AND EXAM_DATE=?");
    	
    	try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, CourseId);
			ps.setInt(2, SubjectId);
			ps.setString(3, semester);
			//ps.setDate(4, ExDate);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				bean = new TimetableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
			    bean.setSemester(rs.getString(8));
			    bean.setCreated_By(rs.getString(9));
				bean.setModified_By(rs.getString(10));
				bean.setCreated_Date_Time(rs.getTimestamp(11));
				bean.setModified_Date_Time(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
    }
    
    /**
     * Check by course name.
     *
     * @param CourseId the course id
     * @param ExamDate the exam date
     * @return the time table bean
     */
    public static TimetableBean checkByCourseName(int CourseId,java.util.Date ExamDate)
    {
    	Connection conn = null;
    	TimetableBean bean=null;
    	
    	Date Exdate = new Date(ExamDate.getTime());
    	
    	StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLE WHERE COURSE_ID=? "
    			+ "AND EXAM_DATE=?");
    	
    	try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
//			ps.setInt(1, CourseId);
//			ps.setDate(2, Exdate);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				bean = new TimetableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Id(rs.getInt(2));
				bean.setCourse_Name(rs.getString(3));
				bean.setSubject_Id(rs.getInt(4)); 
				bean.setSubject_Name(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExam_Date(rs.getDate(7));
				bean.setExam_Time(rs.getString(8));
				bean.setCreated_By(rs.getString(9));
				bean.setModified_By(rs.getString(10));
				bean.setCreated_Date_Time(rs.getTimestamp(11));
				bean.setModified_Date_Time(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
    }
    

}
