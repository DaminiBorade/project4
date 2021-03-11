package in.co.rays.project4.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project4.bean.RoleBean;
import in.co.rays.project4.bean.UserBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DataBaseException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.exception.RecordNotFoundException;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.EmailBuilder;
import in.co.rays.project4.util.EmailMessage;
import in.co.rays.project4.util.EmailUtility;
import in.co.rays.project4.util.JDBCDataSource;
/**
 * JDBC Implementation of UserModel
 *
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class UserModel {


	 private static Logger log = Logger.getLogger(UserModel.class);

	/**
	 * Find next PK of user
	 *
	 * @throws DatabaseException
	 */
	private int roleId;

	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Integer nextPK() throws DataBaseException {
		// log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		// System.out.println("pk");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_USERS");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new DataBaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Add a User
	 *
	 * @param bean
	 * @throws SQLException 
	 * @throws DatabaseException
	 *
	 */
	public int add(UserBean bean) throws ApplicationException, DuplicateException, SQLException {
		// log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		/*
		 * UserBean existbean = findByLogin(bean.getLogin());
		 * 
		 * if (existbean != null) { throw new DuplicateException(
		 * "Login Id already exists"); }
		 */
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_USERS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFrist_Name());
			pstmt.setString(3, bean.getLast_Name());
			pstmt.setString(4, bean.getLogin());
			pstmt.setString(5, bean.getPassword());
			pstmt.setDate(6, new java.sql.Date(bean.getDOB().getTime()));
			System.out.println("bean" + bean.getDOB());
			pstmt.setString(7, bean.getMobile_No());
			pstmt.setInt(8, bean.getRoll_Id());
			pstmt.setString(9, bean.getGender());
			pstmt.setString(10, bean.getCreated_By());
			pstmt.setString(11, bean.getModified_By());
			pstmt.setTimestamp(12, bean.getCreated_Date_Time());
			pstmt.setTimestamp(13, bean.getModified_Date_Time());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
		// log.error("Database Exception..", e);
		/*
		 * try { conn.rollback(); } catch (Exception ex) { ex.printStackTrace();
		 * throw new ApplicationException( "Exception : add rollback exception "
		 * + ex.getMessage()); } // throw new ApplicationException(
		 * "Exception : Exception in add User"); }
		 */ finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;
	}

	/**
	 * Delete a User
	 *
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(UserBean bean) throws ApplicationException {
		// log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_USERS WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model delete Started");
	}

	/**
	 * Find User by Login
	 *
	 * @param login
	 *            : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public UserBean findByLogin(String login) throws ApplicationException {
		// log.debug("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USERS WHERE LOGIN=?");
		UserBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFrist_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDOB(rs.getDate(6));
				bean.setMobile_No(rs.getString(7));
				bean.setRoll_Id(rs.getInt(8));
				bean.setGender(rs.getString(9));
				bean.setCreated_By(rs.getString(10));
				bean.setModified_By(rs.getString(11));
				bean.setCreated_Date_Time(rs.getTimestamp(12));
				bean.setModified_Date_Time(rs.getTimestamp(13));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			// throw new ApplicationException( "Exception : Exception in getting
			// User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findByLogin End");
		return bean;
	}

	/**
	 * Find User by PK
	 *
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public UserBean findByPK(int pk) throws ApplicationException {
		// log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USERS WHERE ID=?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFrist_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDOB(rs.getDate(6));
				bean.setMobile_No(rs.getString(7));
				bean.setRoll_Id(rs.getInt(8));
				bean.setGender(rs.getString(9));
				bean.setCreated_By(rs.getString(10));
				bean.setModified_By(rs.getString(11));
				bean.setCreated_Date_Time(rs.getTimestamp(12));
				bean.setModified_Date_Time(rs.getTimestamp(13));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findByPK End");
		return bean;
	}

	/**
	 * Update a user
	 *
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(UserBean bean) throws ApplicationException, DuplicateException {
		// log.debug("Model update Started");
		Connection conn = null;

		// UserBean beanExist = findByLogin(bean.getLogin());
		// // Check if updated LoginId already exist
		// if (beanExist != null && !(beanExist.getId() == bean.getId())) {
		// throw new DuplicateException("LoginId is already exist");
		// }

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_USERS SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLL_ID=?,GENDER=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getFrist_Name());
			pstmt.setString(2, bean.getLast_Name());
			pstmt.setString(3, bean.getLogin());
			pstmt.setString(4, bean.getPassword());
			pstmt.setDate(5, new java.sql.Date(bean.getDOB().getTime()));
			pstmt.setString(6, bean.getMobile_No());
			pstmt.setInt(7, bean.getRoll_Id());

			pstmt.setString(8, bean.getGender());
			pstmt.setString(9, bean.getCreated_By());
			pstmt.setString(10, bean.getModified_By());
			pstmt.setTimestamp(11, bean.getCreated_Date_Time());
			pstmt.setTimestamp(12, bean.getModified_Date_Time());
			pstmt.setLong(13, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				// throw new ApplicationException(
				// "Exception : Delete rollback exception "
				// + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model update End");
	}

	/**
	 * Search User
	 *
	 * @param bean
	 *            : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(UserBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search User with pagination
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

	public List search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USERS WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFrist_Name() != null && bean.getFrist_Name().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFrist_Name() + "%'");
			}
			if (bean.getLast_Name() != null && bean.getLast_Name().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLast_Name() + "%'");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND Login like '" + bean.getLogin() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			if (bean.getDOB() != null && bean.getDOB().getDate() > 0) {
				sql.append(" AND DOB '" + bean.getDOB());
			}
			if (bean.getMobile_No() != null && bean.getMobile_No().length() > 0) {
				sql.append(" AND MOBILE_NO '" + bean.getMobile_No());
			}
			if (bean.getRoll_Id() > 0) {
				sql.append(" AND Roll_Id = " + bean.getRoll_Id());
			}

			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + bean.getGender() + "%'");
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFrist_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDOB((rs.getDate(6)));
				bean.setMobile_No(rs.getString(7));
				bean.setRoll_Id(rs.getInt(8));
				bean.setGender(rs.getString(9));
				bean.setCreated_By(rs.getString(10));
				bean.setModified_By(rs.getString(11));
				bean.setCreated_Date_Time(rs.getTimestamp(12));
				bean.setModified_Date_Time(rs.getTimestamp(13));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			//throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model search End");
		return list;
	}

	/**
	 * Get List of User
	 *
	 * @return list : List of User
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of User with pagination
	 *
	 * @return list : List of users
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_USERS");
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
				UserBean bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFrist_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDOB(rs.getDate(6));
				bean.setMobile_No(rs.getString(7));
				bean.setRoll_Id(rs.getInt(8));
				bean.setGender(rs.getString(9));
				bean.setCreated_By(rs.getString(10));
				bean.setModified_By(rs.getString(11));
				bean.setCreated_Date_Time(rs.getTimestamp(12));
				bean.setModified_Date_Time(rs.getTimestamp(13));

				;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model list End");
		return list;

	}

	/**
	 * @param id
	 *            : long id
	 * @param old
	 *            password : String oldPassword
	 * @param new
	 *            password : String newPassword
	 * @throws Exception
	 * @throws DatabaseException
	 */

	public UserBean authenticate(String login, String password) throws Exception {
		// log.debug("Model authenticate Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USERS WHERE LOGIN = ? AND PASSWORD = ?");
		UserBean bean = null;
		Connection conn = null;

		// RoleBean rbean=findByPK(bean.getId());
		try {
			// conn=JDBCDataSource.getConnection();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_04", "root", "root");
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, login);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			// System.out.println(rs);
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFrist_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDOB(rs.getDate(6));
				bean.setMobile_No(rs.getString(7));
				bean.setRoll_Id(rs.getInt(8));
				bean.setGender(rs.getString(9));
				bean.setCreated_By(rs.getString(10));
				bean.setModified_By(rs.getString(11));
				bean.setCreated_Date_Time(rs.getTimestamp(12));
				bean.setModified_Date_Time(rs.getTimestamp(13));

			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			// throw new ApplicationException("Exception : Exception in get
			// roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model authenticate End");
		return bean;
	}

	/**
	 * Lock User for certain time duration
	 *
	 * @return boolean : true if success otherwise false
	 * @param login
	 *            : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 *             : if user not found
	 */

	/*
	 * public boolean lock(String login) throws RecordNotFoundException,
	 * ApplicationException { // log.debug("Service lock Started"); boolean flag
	 * = false; UserBean beanExist = null; try { beanExist = findByLogin(login);
	 * if (beanExist != null) { beanExist.setLock(UserBean.Active);
	 * update(beanExist); flag = true; } else { throw new
	 * RecordNotFoundException("LoginId not exist"); } } catch
	 * (DuplicateException e) { //log.error("Application Exception..", e); throw
	 * new ApplicationException("Database Exception"); } // log.debug(
	 * "Service lock End"); return flag; }
	 */

	/**
	 * Get User Roles
	 *
	 * @return List : User Role List
	 * @param bean
	 * @throws ApplicationException
	 */

	public List getRoles(UserBean bean) throws ApplicationException {
		// log.debug("Model get roles Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USERS WHERE ROLL_Id=?");
		Connection conn = null;
		List list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, bean.getRoll_Id());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFrist_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDOB(rs.getDate(6));
				bean.setMobile_No(rs.getString(7));
				bean.setRoll_Id(rs.getInt(8));
				bean.setGender(rs.getString(9));
				bean.setCreated_By(rs.getString(10));
				bean.setModified_By(rs.getString(11));
				bean.setCreated_Date_Time(rs.getTimestamp(12));
				bean.setModified_Date_Time(rs.getTimestamp(13));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model get roles End");
		return list;
	}

	/**
	 * @param id
	 *            : long id
	 * @param old
	 *            password : String oldPassword
	 * @param newpassword
	 *            : String newPassword
	 * @throws DatabaseException
	 */

	public boolean changePassword(int id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {
		System.out.println("Change password model is called");
		// log.debug("model changePassword Started");
		boolean flag = false;
		UserBean beanExist = null;

		beanExist = findByPK(id);
		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
			
			beanExist.setPassword(newPassword);
			System.out.println("t");
			try {
				update(beanExist);
			} catch (DuplicateException e) {
				e.printStackTrace();
				// log.error(e);
				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;
		} else {
			 throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFrist_Name());
		map.put("lastName", beanExist.getLast_Name());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(beanExist.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		// log.debug("Model changePassword End");
		return flag;

	}

	public UserBean updateAccess(UserBean bean) throws ApplicationException {
		return null;
	}

	/**
	 * Register a user
	 *
	 * @param bean
	 * @throws ApplicationException
	 * @throws SQLException 
	 * @throws DuplicateRecordException
	 *             : throws when user already exists
	 */

	public int registerUser(UserBean bean) throws ApplicationException, DuplicateException, SQLException {

		// log.debug("Model add Started");

		System.out.println("user Registration");
		int pk = add(bean);
System.out.println("jg");
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successful for ORS Project SunilOS");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		return pk;
	}

	/**
	 * Reset Password of User with auto generated Password
	 *
	 * @return boolean : true if success otherwise false
	 * @param login
	 *            : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 *             : if user not found
	 */

	public boolean resetPassword(UserBean bean) throws ApplicationException {

		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		UserBean userData = findByPK(bean.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (DuplicateException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		map.put("firstName", bean.getFrist_Name());
		map.put("lastName", bean.getLast_Name());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}

	/**
	 * Send the password of User to his Email
	 *
	 * @return boolean : true if success otherwise false
	 * @param login
	 *            : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 *             : if user not found
	 */

	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		UserBean userData = findByLogin(login);
		boolean flag = false;
System.out.println("forget");
		
		if (userData == null) {
			throw new RecordNotFoundException("Email ID does not exists !");

		}
try{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFrist_Name());
		map.put("lastName", userData.getLast_Name());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;

		
	}catch(Exception e){
		e.printStackTrace();
	}
System.out.println("frogetpassword");
	return flag;
}
}

