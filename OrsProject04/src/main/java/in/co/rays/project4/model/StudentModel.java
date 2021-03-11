package in.co.rays.project4.model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project4.bean.CollegeBean;
import in.co.rays.project4.bean.StudentBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DataBaseException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.JDBCDataSource;
/**
 * JDBC Implementation of Student Model
 * @author Damini
 *
 */
public class StudentModel {
    //private static Logger log = Logger.getLogger(StudentModel.class);

    /**
     * Find next PK of Student
     *
     * @throws DatabaseException
     */
    public Integer nextPK() throws DataBaseException {
        //log.debug("Model nextPK Started");
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn
                    .prepareStatement("SELECT MAX(ID) FROM ST_STUDENT");
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
        //log.debug("Model nextPK End");
        return pk + 1;
    }

    /**
     * Add a Student
     *
     * @param bean
     * @throws SQLException 
     * @throws DatabaseException
     *
     */
    public int add(StudentBean bean) throws ApplicationException,
            DuplicateException, SQLException {
        //log.debug("Model add Started");
        Connection conn = null;

        // get College Name
        CollegeModel cModel = new CollegeModel();
        CollegeBean collegeBean = cModel.findByPK(bean.getCollege_Id());
//      String cname=  bean.setCollege_Name(collegeBean.getName());
        String cname=collegeBean.getName();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cname);

        StudentBean duplicateName = findByEmailId(bean.getEmail());
        int pk = 0;

        if (duplicateName != null) {
          //  throw new DuplicateException("Email already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            // Get auto-generated next primary key
            System.out.println(pk + " in ModelJDBC");
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO ST_STUDENT VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, pk);
            pstmt.setInt(2, bean.getCollege_Id());
            pstmt.setString(3, cname);
            pstmt.setString(4, bean.getFrist_Name());
            pstmt.setString(5, bean.getLast_Name());
            pstmt.setDate(6, new java.sql.Date(bean.getDate_of_Birth().getTime()));
            //  pstmt.setDate(6, bean.getDate_of_Birth());
            pstmt.setString(7, bean.getMobile_No());
            pstmt.setString(8, bean.getEmail());
            pstmt.setString(9, bean.getCreated_By());
            pstmt.setString(10, bean.getModified_By());
            pstmt.setTimestamp(11, bean.getCreated_Date_Time());
            pstmt.setTimestamp(12, bean.getModified_Date_Time());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
        	e.printStackTrace();
        	conn.rollback();
           // log.error("Database Exception..", e);
           // try {
             //   conn.rollback();
        //  } 
       //     catch (Exception ex) {
//                throw new ApplicationException(
//                        "Exception : add rollback exception " + ex.getMessage());
//            }
//            throw new ApplicationException(
//                    "Exception : Exception in add Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
       // log.debug("Model add End");
        return pk;
    }

    /**
     * Delete a Student
     *
     * @param bean
     * @throws DatabaseException
     */
    public void delete(StudentBean bean) throws ApplicationException {
       // log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("DELETE FROM ST_STUDENT WHERE ID=?");
            pstmt.setInt(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();

        } catch (Exception e) {
          e.printStackTrace();
        	//  log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : Delete rollback exception "
                                + ex.getMessage());
            }
            throw new ApplicationException(
                    "Exception : Exception in delete Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
      //  log.debug("Model delete Started");
    }

    /**
     * Find User by Student
     *
     * @param Email
     *            : get parameter
     * @return bean
     * @throws DatabaseException
     */

    public StudentBean findByEmailId(String Email) throws ApplicationException {
       // log.debug("Model findBy Email Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM ST_STUDENT WHERE EMAIL=?");
        StudentBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, Email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getInt(1));
                bean.setCollege_Id(rs.getInt(2));
                bean.setCollege_Name(rs.getString(3));
                bean.setFrist_Name(rs.getString(4));
                bean.setLast_Name(rs.getString(5));
                bean.setDate_of_Birth(rs.getDate(6));
                bean.setMobile_No(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreated_By(rs.getString(9));
                bean.setModified_By(rs.getString(10));
                bean.setCreated_Date_Time(rs.getTimestamp(11));
                bean.setModified_Date_Time(rs.getTimestamp(12));

            }
            rs.close();
        } catch (Exception e) {
           e.printStackTrace();
        	// log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting User by Email");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        //log.debug("Model findBy Email End");
        return bean;
    }

    /**
     * Find Student by PK
     *
     * @param pk
     *            : get parameter
     * @return bean
     * @throws DatabaseException
     */

    public StudentBean findByPK(int pk) throws ApplicationException {
    //    log.debug("Model findByPK Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE ID=?");
        StudentBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getInt(1));
                bean.setCollege_Id(rs.getInt(2));
                bean.setCollege_Name(rs.getString(3));
                bean.setFrist_Name(rs.getString(4));
                bean.setLast_Name(rs.getString(5));
                bean.setDate_of_Birth(rs.getDate(6));
                bean.setMobile_No(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreated_By(rs.getString(9));
                bean.setModified_By(rs.getString(10));
                bean.setCreated_Date_Time(rs.getTimestamp(11));
                bean.setModified_Date_Time(rs.getTimestamp(12));
            }
            rs.close();
        } catch (Exception e) {
        	e.printStackTrace();
            //log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting User by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        //log.debug("Model findByPK End");
        return bean;
    }

    /**
     * Update a Student
     *
     * @param bean
     * @throws DatabaseException
     */

    public void update(StudentBean bean) throws ApplicationException,
            DuplicateException {
       // log.debug("Model update Started");
        Connection conn = null;

        StudentBean beanExist = findByEmailId(bean.getEmail());

        // Check if updated Roll no already exist
        if (beanExist != null && beanExist.getId() != bean.getId()) {
            throw new DuplicateException("Email Id is already exist");
        }

        // get College Name
        CollegeModel cModel = new CollegeModel();
        CollegeBean collegeBean = cModel.findByPK(bean.getCollege_Id());
        bean.setCollege_Name(collegeBean.getName());

        try {

            conn = JDBCDataSource.getConnection();

            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("UPDATE ST_STUDENT SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
            pstmt.setInt(1, bean.getCollege_Id());
            pstmt.setString(2, bean.getCollege_Name());
            pstmt.setString(3, bean.getFrist_Name());
            pstmt.setString(4, bean.getLast_Name());
            pstmt.setDate(5, new java.sql.Date(bean.getDate_of_Birth().getTime()));
            pstmt.setString(6, bean.getMobile_No());
            pstmt.setString(7, bean.getEmail());
            pstmt.setString(8, bean.getCreated_By());
            pstmt.setString(9, bean.getModified_By());
            pstmt.setTimestamp(10, bean.getCreated_Date_Time());
            pstmt.setTimestamp(11, bean.getModified_Date_Time());
            pstmt.setInt(12, bean.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
        	e.printStackTrace();
          //  log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : Delete rollback exception "
                                + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Student ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
      //  log.debug("Model update End");
    }

    /**
     * Search Student
     *
     * @param bean
     *            : Search Parameters
     * @throws DatabaseException
     */

    public List search(StudentBean bean) throws ApplicationException {
        return search(bean, 0, 0);
    }

    /**
     * Search Student with pagination
     *
     * @return list : List of Students
     * @param bean
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     *
     * @throws DatabaseException
     */

    public List search(StudentBean bean, int pageNo, int pageSize)
            throws ApplicationException {
      //  log.debug("Model search Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }
            if (bean.getCollege_Id() > 0) {
                sql.append(" AND College_Id = " + bean.getCollege_Id());
            }
            if (bean.getFrist_Name() != null && bean.getFrist_Name().length() > 0) {
                sql.append(" AND First_Name like '" + bean.getFrist_Name()
                        + "%'");
            }
            if (bean.getLast_Name() != null && bean.getLast_Name().length() > 0) {
                sql.append(" AND LAST_NAME like '" + bean.getLast_Name() + "%'");
            }
            if (bean.getDate_of_Birth() != null && bean.getDate_of_Birth().getDate()> 0) {
                sql.append(" AND Date_of_Birth = " + bean.getDate_of_Birth());
            }
            if (bean.getMobile_No() != null && bean.getMobile_No().length() > 0) {
                sql.append(" AND MOBILE_NO like '" + bean.getMobile_No() + "%'");
            }
            if (bean.getEmail() != null && bean.getEmail().length() > 0) {
                sql.append(" AND EMAIL like '" + bean.getEmail() + "%'");
            }
            if (bean.getCollege_Name() != null
                    && bean.getCollege_Name().length() > 0) {
                sql.append(" AND COLLEGE_NAME like " + bean.getCollege_Name());
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
                bean = new StudentBean();
                bean.setId(rs.getInt(1));
                bean.setCollege_Id(rs.getInt(2));
                bean.setCollege_Name(rs.getString(3));
                bean.setFrist_Name(rs.getString(4));
                bean.setLast_Name(rs.getString(5));
                bean.setDate_of_Birth(rs.getDate(6));
                bean.setMobile_No(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreated_By(rs.getString(9));
                bean.setModified_By(rs.getString(10));
                bean.setCreated_Date_Time(rs.getTimestamp(11));
                bean.setModified_Date_Time(rs.getTimestamp(12));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        	//  log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in search Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

       // log.debug("Model search End");
        return list;
    }

    /**
     * Get List of Student
     *
     * @return list : List of Student
     * @throws DatabaseException
     */

    public List list() throws ApplicationException {
        return list(0, 0);
    }

    /**
     * Get List of Student with pagination
     *
     * @return list : List of Student
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */

    public List list(int pageNo, int pageSize) throws ApplicationException {
        //log.debug("Model list Started");
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from ST_STUDENT");
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
                StudentBean bean = new StudentBean();
                bean.setId(rs.getInt(1));
                bean.setCollege_Id(rs.getInt(2));
                bean.setCollege_Name(rs.getString(3));
                bean.setFrist_Name(rs.getString(4));
                bean.setLast_Name(rs.getString(5));
                bean.setDate_of_Birth(rs.getDate(6));
                bean.setMobile_No(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreated_By(rs.getString(9));
                bean.setModified_By(rs.getString(10));
                bean.setCreated_Date_Time(rs.getTimestamp(11));
                bean.setModified_Date_Time(rs.getTimestamp(12));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
        	e.printStackTrace();
            //log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting list of Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        //log.debug("Model list End");
        return list;

    }

}
