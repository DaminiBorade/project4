package in.co.rays.project4.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project4.bean.CourseBean;
import in.co.rays.project4.bean.SubjectBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.JDBCDataSource;


	// TODO: Auto-generated Java doc
	/**
	 * JDBC Implementation of Subject Model.
	 *
	 * @author Session Facade
	 * @version 1.0
	 * Copyright (c) SunilOS
	 */

	public class SubjectModel {

		/** The log. */
		//private static Logger log = Logger.getLogger(SubjectModel.class);

		/**
		 * Find next PK of Subject.
		 *
		 * @return the integer
		 * @throws ApplicationException the application exception
		 */

		public Integer nextPk() throws ApplicationException {
			Connection conn = null;
			int pk = 0;

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM ST_SUBJECT");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					pk = rs.getInt(1);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				//log.error("database Exception ...", e);
				throw new ApplicationException("Exception in NextPk of subject Model");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			return pk + 1;
		}

		/**
		 * Add a Subject.
		 *
		 * @param bean the bean
		 * @return the long
		 * @throws ApplicationException the application exception
		 * @throws SQLException 
		 * @throws DuplicateRecordException the duplicate record exception
		 */

	 public int add(SubjectBean bean) throws ApplicationException,
	            DuplicateException, SQLException {
	     //   log.debug("Model add Started");
	        Connection conn = null;

	        // get Course Name
	        CourseModel cModel = new CourseModel();
	        CourseBean CourseBean = cModel.findByPk(bean.getCourse_Id());
	       System.out.println("id>>>>>>>>>>>"+CourseBean);
	        String cname= CourseBean.getCourse_Name();
	        System.out.println("Coursename>>>>>>>>>>>>>"+cname);
	        
	       //setCourse_Name(CourseBean.getCourse_Name());

	        SubjectBean duplicateName = findByName(bean.getSubject_Name());
       

	           
	        if (duplicateName != null) {
	        	 throw new DuplicateException("Subject Name already exists");	
	        }
	        int pk = 0;
	        try {
	        	 
	            conn = JDBCDataSource.getConnection();
	            pk = nextPk();
	            // Get auto-generated next primary key
	            System.out.println(pk + " in ModelJDBC");
	            
	            conn.setAutoCommit(false); // Begin transaction
	            
	            PreparedStatement pstmt = conn
	                    .prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");
	            
	            pstmt.setInt(1, pk);
	            pstmt.setString(2, bean.getSubject_Name());
	        //    pstmt.setInt(3, bean.getSubject_Id());
	            pstmt.setString(3, cname);
	            pstmt.setInt(4, bean.getCourse_Id());
	            pstmt.setString(5, bean.getDescription());   
	            pstmt.setString(6, bean.getCreated_By());
	            pstmt.setString(7, bean.getModified_By());
	            pstmt.setTimestamp(8, bean.getCreated_Date_Time());
	            pstmt.setTimestamp(9, bean.getModified_Date_Time());
	            pstmt.executeUpdate();
	            conn.commit(); // End transaction
	            pstmt.close();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	conn.rollback();
	            //log.error("Database Exception..", e);
//	            try {
//	                conn.rollback();
//	            } catch (Exception ex) {
//	                throw new ApplicationException(
//	                        "Exception : add rollback exception " + ex.getMessage());
//	            }
//	            throw new ApplicationException(
//	                    "Exception : Exception in add Subject");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	       // log.debug("Model add End");
	        return pk;
	    }
		
		/**
		 * Delete a Subject.
		 *
		 * @param bean the bean
		 * @throws ApplicationException the application exception
		 */

		public void delete(SubjectBean bean) throws ApplicationException {
			//log.debug("Subject Model Delete method Started");
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
				pstmt.setInt(1, bean.getId());
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				//log.error("database Exception ...", e);

				try {
					conn.rollback();
				} catch (Exception ex) {
					throw new ApplicationException(
							"Exception in Rollback of Delte Method of Subject Model" + ex.getMessage());
				}
				throw new ApplicationException("Exception in Delte Method of Subject Model");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			//log.debug("Subject Model Delete method End");
		}

		/**
		 * Update a Subject.
		 *
		 * @param bean the bean
		 * @throws ApplicationException the application exception
		 * @throws DuplicateRecordException the duplicate record exception
		 */

		 public void update(SubjectBean bean) throws ApplicationException,
	     DuplicateException {
	// log.debug("Model update Started");
	 Connection conn = null;

	// SubjectBean beanExist = findByName(bean.getCourse_Id());

	 // Check if updated id already exist
//	 if (beanExist != null && beanExist.getId() != bean.getId()) {
//	     throw new DuplicateException("Subject Name is already exist");
//	 }

	 // get Course Name
	 CourseModel cModel = new CourseModel();
	 CourseBean CourseBean = cModel.findByPk(bean.getCourse_Id());
	 bean.setCourse_Name(CourseBean.getCourse_Name());

	 try {

	     conn = JDBCDataSource.getConnection();

	     conn.setAutoCommit(false); // Begin transaction
	     PreparedStatement pstmt = conn
	             .prepareStatement("UPDATE ST_SUBJECT SET Subject_Name=?,Course_NAME=?,Course_ID=?,Discription=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
	     pstmt.setString(1, bean.getSubject_Name());
	     pstmt.setString(2, bean.getCourse_Name());
	     pstmt.setInt(3, bean.getCourse_Id());
	     pstmt.setString(4, bean.getDescription()); 
	     pstmt.setString(5, bean.getCreated_By());
	     pstmt.setString(6, bean.getModified_By());
	     pstmt.setTimestamp(7, bean.getCreated_Date_Time());
	     pstmt.setTimestamp(8, bean.getModified_Date_Time());
	     pstmt.setInt(9, bean.getId());
	     pstmt.executeUpdate();
	     conn.commit(); // End transaction
	     pstmt.close();
	 } catch (Exception e) {
		 e.printStackTrace();
	    // log.error("Database Exception..", e);
	     try {
	         conn.rollback();
	     } catch (Exception ex) {
	         throw new ApplicationException(
	                 "Exception : Delete rollback exception "
	                         + ex.getMessage());
	     }
	    // throw new ApplicationException("Exception in updating Subject ");
	 } finally {
	     JDBCDataSource.closeConnection(conn);
	 }
	// log.debug("Model update End");
	}

		/**
		 * Find User by Subject Name.
		 *
		 * @param i            : get parameter
		 * @return bean
		 * @throws ApplicationException the application exception
		 */

		public SubjectBean findByName(String i) throws ApplicationException {
			//log.debug("Subject Model findByName method Started");
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE SUBJECT_NAME=?");
			Connection conn = null;
			SubjectBean bean = null;

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, i);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					bean = new SubjectBean();
					
					bean.setId(rs.getInt(1));
					bean.setSubject_Name(rs.getString(2));	
					
					bean.setCourse_Name(rs.getString(3));
					bean.setCourse_Id(rs.getInt(4));
					bean.setDescription(rs.getString(5));
					bean.setCreated_By(rs.getString(6));
					bean.setModified_By(rs.getString(7));
					bean.setCreated_Date_Time(rs.getTimestamp(8));
					bean.setModified_Date_Time(rs.getTimestamp(9));
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				//log.error("database Exception....", e);
				e.printStackTrace();
				//throw new ApplicationException("Exception in findByName Method of Subject Model");
			} finally {
				JDBCDataSource.closeConnection(conn);

			}
			//log.debug("Subject Model findByName method End");
			return bean;
		}

		/**
		 * Find User by Subject PK.
		 *
		 * @param pk            : get parameter
		 * @return bean
		 * @throws ApplicationException the application exception
		 */
		public SubjectBean findByPk(int pk) throws ApplicationException {
			//log.debug("Subject Model findBypk method Started");
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
			Connection conn = null;
			SubjectBean bean = null;

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, pk);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					bean = new SubjectBean();
					
					bean.setId(rs.getInt(1));
					bean.setSubject_Name(rs.getString(2));
					bean.setCourse_Name(rs.getString(3));
					bean.setCourse_Id(rs.getInt(4));
					
					bean.setDescription(rs.getString(5));
					bean.setCreated_By(rs.getString(6));
					bean.setModified_By(rs.getString(7));
					bean.setCreated_Date_Time(rs.getTimestamp(8));
					bean.setModified_Date_Time(rs.getTimestamp(9));
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				//log.error("database Exception....", e);
				throw new ApplicationException("Exception in findByPk Method of Subject Model");
			} finally {
				JDBCDataSource.closeConnection(conn);

			}
			//log.debug("Subject Model findByPk method End");
			return bean;
		}
		
		
		
		/**
		 * Search Subject.
		 *
		 * @param bean            : Search Parameters
		 * @return the list
		 * @throws ApplicationException the application exception
		 */
		
		public List search(SubjectBean bean) throws ApplicationException{
			return search(bean,0,0);
		}
		
		/**
		 * Search Subject with pagination.
		 *
		 * @param bean            : Search Parameters
		 * @param pageNo            : Current Page No.
		 * @param pageSize            : Size of Page
		 * @return list : List of Users
		 * @throws ApplicationException the application exception
		 */


		public List search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
			//log.debug("Subject Model search method Started");
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1 ");
			System.out.println("model search" + bean.getId());
			
			if(bean!= null){
				if(bean.getId() > 0){
					sql.append(" AND id = " + bean.getId()); 
				}
				if(bean.getCourse_Id() > 0){
					sql.append(" AND Course_ID = " + bean.getCourse_Id()); 
				}
			
				if (bean.getSubject_Name() != null && bean.getSubject_Name().length() >0 ) {
					sql.append(" AND Subject_Name like '" +bean.getSubject_Name() + "%'");
				}
				if (bean.getCourse_Name() !=null && bean.getCourse_Name().length() >0 ) {
					sql.append(" AND Course_Name like '" + bean.getCourse_Id() + "%'");
				}
				if (bean.getDescription() !=null && bean.getDescription().length() >0 ) {
					sql.append(" AND description like '" + bean.getDescription() + " % ");
				}
				
				
			}
			
			// Page Size is greater then Zero then aplly pagination 
			if(pageSize>0){
				pageNo = (pageNo-1)*pageSize;
				sql.append(" limit "+pageNo+ " , " + pageSize);
			}
			System.out.println("sql is"+sql);
			Connection conn = null;
			ArrayList list = new ArrayList();
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
			
				while(rs.next()){
					bean = new SubjectBean();
				
					bean.setId(rs.getInt(1));
					bean.setSubject_Name(rs.getString(2));
					bean.setCourse_Name(rs.getString(3));
                    bean.setCourse_Id(rs.getInt(4));
					bean.setDescription(rs.getString(5));	
					bean.setCreated_By(rs.getString(6));
					bean.setModified_By(rs.getString(7));
					bean.setCreated_Date_Time(rs.getTimestamp(8));
					bean.setModified_Date_Time(rs.getTimestamp(9));
					list.add(bean);
				}
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			//	log.error("database Exception....", e);
				throw new ApplicationException("Exception in search Method of Subject Model");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			//log.debug("Subject Model search method End");		
			return list;
		}	
		
		/**
		 * Get List of Subject.
		 *
		 * @return list : List of Subject
		 * @throws ApplicationException the application exception
		 */
		public List list() throws ApplicationException{
			return list(0,0);
		}

		/**
		 * Get List of Subject with pagination.
		 *
		 * @param pageNo            : Current Page No.
		 * @param pageSize            : Size of Page
		 * @return list : List of Subject
		 * @throws ApplicationException the application exception
		 */
		public List list(int pageNo, int pageSize) throws ApplicationException {
			//log.debug("Subject Model list method Started");
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT ");
			
			// Page Size is greater then Zero then aplly pagination 
			if (pageSize>0) {
				pageNo = (pageNo-1)*pageSize;
				sql.append(" limit "+ pageNo+ " , " + pageSize);
			}
			
			Connection conn = null;
			ArrayList list = new ArrayList();
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					SubjectBean bean = new SubjectBean();
					
					bean.setId(rs.getInt(1));
					bean.setSubject_Name(rs.getString(2));
                    bean.setCourse_Name(rs.getString(3));
					bean.setCourse_Id(rs.getInt(4));
					bean.setDescription(rs.getString(5));
					bean.setCreated_By(rs.getString(6));
					bean.setModified_By(rs.getString(7));
					bean.setCreated_Date_Time(rs.getTimestamp(8));
					bean.setModified_Date_Time(rs.getTimestamp(9));
					list.add(bean);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				//log.error("database Exception....", e);
			//	throw new ApplicationException("Exception in list Method of Subject Model");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			//log.debug("Subject Model list method End");		
			return list;
		}
	}


