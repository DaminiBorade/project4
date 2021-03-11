  package in.co.rays.project4.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.CollegeBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DataBaseException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.JDBCDataSource;
/**
 * JDBC Implementation of CollegeModel
 * @author Damini
 *
 */

public class CollegeModel {

  private static Logger log = Logger.getLogger(CollegeModel.class);

    /**
     * Find next PK of College
     *
     * @throws DatabaseException
     */
    public Integer nextPK() throws DataBaseException {
      //  log.debug("Model nextPK Started");
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn
                    .prepareStatement("SELECT MAX(ID) FROM ST_COLLEGE");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();

        } catch (Exception e) {
        	e.printStackTrace();
            //log.error("Database Exception..", e);
            throw new DataBaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
      //  log.debug("Model nextPK End");
        return pk + 1;
    }

    /**
     * Add a College
     *
     * @param bean
     * @throws DatabaseException
     *
     */
    public int add(CollegeBean bean) throws ApplicationException,
            DuplicateException {
       log.debug("Model add Started");
        Connection conn = null;
        int pk = 0;

        CollegeBean duplicateCollegeName = findByName(bean.getName());

        if (duplicateCollegeName != null) {
            throw new DuplicateException("College Name already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            // Get auto-generated next primary key
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getName());
            pstmt.setString(3, bean.getAddress());
            pstmt.setString(4, bean.getState());
            pstmt.setString(5, bean.getCity());
            pstmt.setString(6, bean.getPhone_No());
            pstmt.setString(7, bean.getCreated_By());
            pstmt.setString(8, bean.getModified_By());
            pstmt.setTimestamp(9, bean.getCreated_Date_Time());
            pstmt.setTimestamp(10, bean.getModified_Date_Time());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
        	e.printStackTrace();
            //log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ApplicationException(
                        "Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException(
                    "Exception : Exception in add College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    /**
     * Delete a College
     *
     * @param bean
     * @throws DatabaseException
     */
    public void delete(CollegeBean bean) throws ApplicationException {
       // log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("DELETE FROM ST_COLLEGE WHERE ID=?");
            pstmt.setInt(1, bean.getId());
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
            throw new ApplicationException(
                    "Exception : Exception in delete college");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        //log.debug("Model delete Started");
    }

    /**
     * Find User by College
     *
     * @param login
     *            : get parameter
     * @return bean
     * @throws DatabaseException
     */
    public CollegeBean findByName(String name) throws ApplicationException {
     //   log.debug("Model findByName Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM ST_COLLEGE WHERE NAME=?");
        CollegeBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getInt(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhone_No(rs.getString(6));
                bean.setCreated_By(rs.getString(7));
                bean.setModified_By(rs.getString(8));
                bean.setCreated_Date_Time(rs.getTimestamp(9));
                bean.setModified_Date_Time(rs.getTimestamp(10));

            }
            rs.close();
        } catch (Exception e) {
            //log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting College by Name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
       // log.debug("Model findByName End");
        return bean;
    }

    /**
     * Find User by College
     *
     * @param 
     *            : get parameter
     * @return bean
     * @throws DatabaseException
     */
    public CollegeBean findByPK(int pk) throws ApplicationException {
        //log.debug("Model findByPK Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM ST_COLLEGE WHERE ID=?");
        CollegeBean bean = null;
        Connection conn = null;
        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getInt(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhone_No(rs.getString(6));
                bean.setCreated_By(rs.getString(7));
                bean.setModified_By(rs.getString(8));
                bean.setCreated_Date_Time(rs.getTimestamp(9));
                bean.setModified_Date_Time(rs.getTimestamp(10));

            }
            rs.close();
        } catch (Exception e) {
        	e.printStackTrace();
            //log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting College by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
       // log.debug("Model findByPK End");
        return bean;
    }

    /**
     * Update a College
     *
     * @param bean
     * @throws DatabaseException
     */
    public void update(CollegeBean bean) throws ApplicationException,
            DuplicateException {
       // log.debug("Model update Started");
        Connection conn = null;

        CollegeBean beanExist = findByName(bean.getName());

        // Check if updated College already exist
        if (beanExist != null && beanExist.getId() != bean.getId()) {

            throw new DuplicateException("College is already exist");
        }

        try {

            conn = JDBCDataSource.getConnection();

            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getAddress());
            pstmt.setString(3, bean.getState());
            pstmt.setString(4, bean.getCity());
            pstmt.setString(5, bean.getPhone_No());
            pstmt.setString(6, bean.getCreated_By());
            pstmt.setString(7, bean.getModified_By());
            pstmt.setTimestamp(8, bean.getCreated_Date_Time());
            pstmt.setTimestamp(9, bean.getModified_Date_Time());
            pstmt.setInt(10, bean.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
        	e.printStackTrace();
            //log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : Delete rollback exception "
                                + ex.getMessage());
            }
          //  throw new ApplicationException("Exception in updating College ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
       // log.debug("Model update End");
    }

    /**
     * Search College with pagination
     *
     * @return list : List of Users
     * @param bean
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     *
     * @throws DatabaseException
     */
    public List search(CollegeBean bean, int pageNo, int pageSize)
            throws ApplicationException {
     //   log.debug("Model search Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM ST_COLLEGE WHERE 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }
            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" AND NAME like '" + bean.getName() + "%'");
            }
            if (bean.getAddress() != null && bean.getAddress().length() > 0) {
                sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
            }
            if (bean.getState() != null && bean.getState().length() > 0) {
                sql.append(" AND STATE like '" + bean.getState() + "%'");
            }
            if (bean.getCity() != null && bean.getCity().length() > 0) {
                sql.append(" AND CITY like '" + bean.getCity() + "%'");
            }
            if (bean.getPhone_No() != null && bean.getPhone_No().length() > 0) {
                sql.append(" AND PHONE_NO = " + bean.getPhone_No());
            }

        }

        // if page size is greater than zero then apply pagination
        if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;

            sql.append(" Limit " + pageNo + ", " + pageSize);
            // sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList list = new ArrayList();
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getInt(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhone_No(rs.getString(6));
                bean.setCreated_By(rs.getString(7));
                bean.setModified_By(rs.getString(8));
                bean.setCreated_Date_Time(rs.getTimestamp(9));
                bean.setModified_Date_Time(rs.getTimestamp(10));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
        	e.printStackTrace();
            //log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in search college");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

       // log.debug("Model search End");
        return list;
    }

    /**
     * Search College
     *
     * @param bean
     *            : Search Parameters
     * @throws DatabaseException
     */
    public List search(CollegeBean bean) throws ApplicationException {
        return search(bean, 0, 0);
    }

    /**
     * Get List of College
     *
     * @return list : List of College
     * @throws DatabaseException
     */
    public List list() throws ApplicationException {
        return list(0, 0);
    }

    /**
     * Get List of College with pagination
     *
     * @return list : List of College
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException {
       // log.debug("Model list Started");
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from ST_COLLEGE");
        // if page size is greater than zero then apply pagination
        if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CollegeBean bean = new CollegeBean();
                bean.setId(rs.getInt(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhone_No(rs.getString(6));
                bean.setCreated_By(rs.getString(7));
                bean.setModified_By(rs.getString(8));
                bean.setCreated_Date_Time(rs.getTimestamp(9));
                bean.setModified_Date_Time(rs.getTimestamp(10));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
           // log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting list of users");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        //log.debug("Model list End");
        return list;

    }
}

//public class CollegeModel {
//
//	//private static final String DuplicateRecordException = null;
//	//private static Logger Log =Logger.getLogger(CollegeModel.class);
//	  /**
//     * Find next PK of College
//     *
//     * @throws DatabaseException
//     */
//    public Integer nextPK()throws DataBaseException{
//	//	log.debug("Model nextPK started");
//		Connection conn=null;
//		int pk=0;
//		try{
//			conn=JDBCDataSource.getConnection();
//			PreparedStatement pstmt=conn.prepareStatement("SELECT MAX(ID) FROM ST_COLLEGE");
//			
//			ResultSet rs=pstmt.executeQuery();
//			while(rs.next()){
//				pk=rs.getInt(1);
//			}
//			rs.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		//	log.error("DataException..",e);
//		//	throw new DataBaseException("Exception: Exception in getting PK");
//		}finally{
//			JDBCDataSource.closeConnection(conn);
//		}
//		//log.debug("Model nextPK End");
//		return pk+1;
//		}
//    /**
//     * Add a College
//     *
//     * @param bean   
//     * @throws DatabaseException
//     *
//     */
//    
//    public Int add(CollegeBean Bean) throws Exception{
//		
//			Connection conn =null;
//			int pk=0;
////			CollegeBean duplicateCollegeName=findByName(Bean.getName()); 
////			if(duplicateCollegeName!=null){
////				throw newDuplicateRecordException("College Name already exists");
////			}
//			try{
//				//conn =JDBCDataSource.getConnection();
//				pk=nextPK();
//				conn.setAutoCommit(false);
//				//Get auto generated next primary key;
//				PreparedStatement pstmt =conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?)");
//				pstmt.setInt(1, pk);
//				pstmt.setString(2,Bean.getName());
//				pstmt.setString(3, Bean.getAddress());
//				pstmt.setString(4, Bean.getState());
//				pstmt.setString(5, Bean.getCity());
//				pstmt.setString(6, Bean.getPhone_No());
//				pstmt.setString(7, Bean.getCreated_By());
//				pstmt.setString(8, Bean.getModified_By());
//				pstmt.setTimestamp(9, Bean.getCreated_Date_Time());
//				pstmt.setTimestamp(10, Bean.getModified_Date_Time());
//				//pstmt.setString(6, Bean.getPhone_No());
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			finally{
//				JDBCDataSource.closeConnection(conn);
//				
//			}
//			return pk;
//		}
//    	
//    
//
//	}
