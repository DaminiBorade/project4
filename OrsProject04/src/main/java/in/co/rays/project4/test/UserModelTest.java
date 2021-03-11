package in.co.rays.project4.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.bean.UserBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.exception.RecordNotFoundException;
import in.co.rays.project4.model.UserModel;

public class UserModelTest {

	/**
	 * Model object to test
	 */

	public static UserModel model = new UserModel();

	/**
	 * Main method to call test methods.
	 *
	 * @param args
	 * @throws Exception
	 * @throws DuplicateRecordException
	 */
	public static void main(String[] args) throws Exception {
		 testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByLogin();
		// testSearch();
		// testGetRoles();
		// testList();
		//testAuthenticate();
		//testRegisterUser();
		// testchangePassword();
		// testforgetPassword();
		// testresetPassword();

	}

	/**
	 * Tests add a User
	 *
	 * @throws ParseException
	 * @throws SQLException 
	 * @throws DuplicateRecordException
	 */
	public static void testAdd() throws ParseException, DuplicateException, SQLException {

		try {
			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

			// bean.setId(5234L);
			bean.setFrist_Name("Sonal");
			bean.setLast_Name("Soni");
			bean.setLogin("sonalsoni@gmail.com");
			bean.setPassword("pass34");
			bean.setDOB(sdf.parse("29/07/1994"));
			bean.setRoll_Id(4);
			bean.setCreated_By("admin");
			bean.setMobile_No("9100373567");
			bean.setModified_By("admini");
			bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
			bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
			bean.setGender("female");
			// bean.setUnSuccessfulLogin(2);
			// bean.setLastLogin(new Timestamp(new Date().getTime()));
			// bean.setLock("Yes");
			// bean.setConfirmPassword("pass1234");
			int pk = model.add(bean);
			// UserBean addedbean = model.findByPK((int) pk);
			System.out.println("inserted");
			// if (addedbean == null) {
			// System.out.println("Test add fail");
			// }
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests delete a User
	 */
	public static void testDelete() {

		try {
			UserBean bean = new UserBean();
			int pk = 3;
			bean.setId(3);
			model.delete(bean);
			System.out.println("Test Delete succ" + bean.getId());
			UserBean deletedbean = model.findByPK(pk);
			// if (deletedbean == null) {
			// System.out.println("Test Delete fail");
			// }
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests update a User
	 */
	public static void testUpdate() {

		try {
			UserBean bean = model.findByPK(11);
			bean.setFrist_Name("mnc limited");
			bean.setLast_Name("ind ltd");
			bean.setLogin("boradedamini1@gmail.com");
			bean.setPassword("658986");

			model.update(bean);
			System.out.println("Updated");

			UserBean updatedbean = model.findByPK(2);
			if ("dr".equals(updatedbean.getLogin())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests find a User by PK.
	 */
	public static void testFindByPK() {
		try {
			UserBean bean = new UserBean();
			int pk = 1;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFrist_Name());
			System.out.println(bean.getLast_Name());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDOB());
			System.out.println(bean.getRoll_Id());
			// System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			// System.out.println(bean.getLastLogin());
			// System.out.println(bean.getLock());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests find a User by Login.
	 */
	public static void testFindByLogin() {
		try {
			UserBean bean = new UserBean();
			bean = model.findByLogin("boradedamini1@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFrist_Name());
			System.out.println(bean.getLast_Name());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDOB());
			System.out.println(bean.getRoll_Id());
			// System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			// System.out.println(bean.getLastLogin());
			// System.out.println(bean.getLock());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests get Roles.
	 */
	public static void testGetRoles() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setRoll_Id(1);
			list = model.getRoles(bean);
			if (list.size() < 0) {
				System.out.println("Test Get Roles fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFrist_Name());
				System.out.println(bean.getLast_Name());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDOB());
				System.out.println(bean.getRoll_Id());
				// System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				// System.out.println(bean.getLastLogin());
				// System.out.println(bean.getLock());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests get Search
	 */
	public static void testSearch() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setLogin("boradedamini1@gmail.com");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFrist_Name());
				System.out.println(bean.getLast_Name());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDOB());
				System.out.println(bean.getRoll_Id());
				// System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				// System.out.println(bean.getLastLogin());
				// System.out.println(bean.getLock());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests get List.
	 */
	public static void testList() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			list = model.list(1, 5);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFrist_Name());
				System.out.println(bean.getLast_Name());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDOB());
				System.out.println(bean.getRoll_Id());
				// System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				// System.out.println(bean.getLastLogin());
				// System.out.println(bean.getLock());
				System.out.println(bean.getMobile_No());
				System.out.println(bean.getCreated_By());
				System.out.println(bean.getModified_By());
				System.out.println(bean.getCreated_Date_Time());
				System.out.println(bean.getModified_Date_Time());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests authenticate User.
	 * 
	 * @throws Exception
	 */
	public static void testAuthenticate() throws Exception {

		try {
			UserBean bean = new UserBean();
			bean.setLogin("boradedamini1@gmail.com");

		//bean.setPassword("admin");
	//	bean = model.authenticate(bean.getLogin(), bean.getPassword());
			model.authenticate("boradedamini1@gmail.com", "admin");
	//	System.out.println();
			if (bean != null) {
				System.out.println("Successfully login");

			} else {
				System.out.println("Invalied login Id & password");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests add a User register
	 *
	 * @throws ParseException
	 * @throws SQLException 
	 */

	public static void testRegisterUser() throws ParseException, SQLException {
		try {
			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			// bean.setId(8);
			bean.setFrist_Name("vipin");
			// bean.setLastName("kumawat");
			bean.setLogin("rranjitch11ou1dhay@gmail.com");
			bean.setPassword("rr");
			// bean.setConfirmPassword("4444");
			bean.setDOB(sdf.parse("11/20/2015"));
			bean.setGender("Male");
			bean.setRoll_Id(2);
			int pk = model.registerUser(bean);
			System.out.println("Successfully register");
			System.out.println(bean.getFrist_Name());
			System.out.println(bean.getLogin());
			System.out.println(bean.getLast_Name());
			System.out.println(bean.getDOB());
			UserBean registerbean = model.findByPK(pk);
			if (registerbean != null) {
				System.out.println("Test registation fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests changepassword
	 *
	 * @throws ParseException
	 */
	public static void testchangePassword() {

		try {
			UserBean bean = model.findByLogin("boradedamini1123@gmail.com");
			String oldPassword = bean.getPassword();
			bean.setId(11);
			bean.setPassword("12345");
			bean.setNewPassword("12345");
			// bean.setConfirmPassword("kk");
			String newPassword = bean.getPassword();
			try {
				model.changePassword(11, oldPassword, newPassword);
				System.out.println("password has been change successfully");
			} catch (RecordNotFoundException e) {
				e.printStackTrace();
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests fogetPassword
	 * @throws ApplicationException 
	 *
	 * @throws ParseException
	 */
	public static void testforgetPassword() throws ApplicationException {
		try {
			boolean b = model.forgetPassword("boradedamini1@gmail.com");

			System.out.println("Sucess : Test Forget Password Success");

		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
		//catch (ApplicationException e) {
//			e.printStackTrace();
		//}
	}

	/**
	 * Tests resetPassword
	 *
	 * @throws ParseException
	 */
	public static void testresetPassword() {
		UserBean bean = new UserBean();
		try {
			bean = model.findByLogin("ranjitchoudhary20@gmail.com");
			if (bean != null) {
				boolean pass = model.resetPassword(bean);
				if (pass = false) {
					System.out.println("Test Update fail");
				}
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}
