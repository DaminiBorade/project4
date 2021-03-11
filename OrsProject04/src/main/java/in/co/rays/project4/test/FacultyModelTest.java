package in.co.rays.project4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.bean.FacultyBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.FacultyModel;

public class FacultyModelTest {

		/** Model object to test. */
		public static FacultyModel model = new FacultyModel();

		/**
		 * Main method to call test methods.
		 *
		 * @param args the arguments
		 * @throws DuplicateRecordException the duplicate record exception
		 * @throws ParseException the parse exception
		 * @throws ApplicationException 
		 */
		public static void main(String[] args) throws DuplicateException, ParseException, ApplicationException {
			testAdd();
		 //testDelete();
			//testUpdate();
		// testFindByEmailId();
			//testFindByPK();
			//testSearch();
			//testList();

		}

		/**
		 * Tests add a College.
		 *
		 * @throws DuplicateRecordException the duplicate record exception
		 * @throws ParseException the parse exception
		 */
		public static void testAdd() throws DuplicateException, ParseException {

			try {
				FacultyBean bean = new FacultyBean();

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
				bean.setFrist_Name("szdfghj");
				bean.setLast_Name("asdfgtyhujiko");
				bean.setGender("Male");
				//bean.setDOJ("6/4/1993");
				bean.setDOJ(sdf.parse("6-8-1993"));
				bean.setQualification("BA");
				 bean.setEmail_id("Ma_Saxena006@gmail.com");
					bean.setMobile_No("8602789566");
					bean.setCollege_id(2);
					//bean.setCollege_Name("IPS");
					bean.setCourse_id(2);
					//bean.setCousre_Name("JAVA");
					bean.setSubject_id(2);
					//bean.setSubject_Name("Maths");
					bean.setCreated_By("Admin");
					bean.setModified_By("Admin");
					bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
					bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
					
				
	int pk = model.add(bean);
					System.out.println("Inserted");
			//	System.out.println("Test add succ");
		
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**
		 * Tests delete a College.
		 */

		public static void testDelete() {

			try {
				FacultyBean bean = new FacultyBean();
				int pk = 4;
				bean.setId(pk);
				model.delete(bean);
				System.out.println("Test Delete successfully");

				FacultyBean deletedBean = model.findByPk(pk);
				if (deletedBean != null) {

					System.out.println("Test Delete fail");

				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Tests update a College.
		 * @throws DuplicateException 
		 * @throws ApplicationException 
		 * @throws ParseException 
		 */
		public static void testUpdate() throws ApplicationException, DuplicateException, ParseException {

			//try {
				FacultyBean bean=new FacultyBean();
				
				SimpleDateFormat sdf=new SimpleDateFormat("mm/dd/yyyy");
				Date date=sdf.parse("10/10/1996");
				bean.setId(6);
				bean.setFrist_Name("Soni");
				bean.setLast_Name("dfghjk");
				bean.setGender("Male");
				bean.setDOJ(date);
				bean.setQualification("BCA");
				 bean.setEmail_id("Mohit_00876@gmail.com");
					bean.setMobile_No("9173454567");
					bean.setCollege_id(2);
					bean.setCollege_Name("IPS");
					bean.setCourse_id(2);
				bean.setCousre_Name("JAVA");
					bean.setSubject_id(2);
					bean.setCreated_By("Admin");
					bean.setModified_By("Admin");
					bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
					bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
				model.update(bean);
				

				/*FacultyBean updatedbean = model.findByPk(2);*/
				System.out.println("Update");
//				if (!"Sheekha".equals(updatedbean.getFrist_Name())) {
//					System.out.println("Test Update fail");
//				}else{
//					System.out.println("Test Update Successfully");
//				}
//			} catch (ApplicationException e) {
//				e.printStackTrace();
//			} catch (DuplicateException e) {
//				e.printStackTrace();
//			}
//			
					}


		

		/**
		 * Tests find a College by PK.
		 */
		public static void testFindByPK() {
			try {
				FacultyBean bean = new FacultyBean();
				int pk = 1;
				bean = model.findByPk(pk);
				if (bean == null) {
					System.out.println("Test Find By PK fail");
				}
				System.out.println(bean.getId());
				System.out.println(bean.getFrist_Name());
				System.out.println(bean.getLast_Name());
				System.out.println(bean.getDOJ());
				  System.out.println(bean.getQualification());
				  System.out.println(bean.getEmail_id());
				System.out.println(bean.getMobile_No());
				System.out.println(bean.getCollege_id());
				System.out.println(bean.getCourse_id());
			   System.out.println(bean.getSubject_id());
			 
				
			} catch (ApplicationException e) {
				e.printStackTrace();
			}

		}
		
		/**
		 * Tests search a College by Name.
		 */

		public static void testSearch() {
			try {
				FacultyBean bean = new FacultyBean();
				List list = new ArrayList();
		
				// bean.setCollegeName("LNCT");
				 // bean.setCousre_Name("");
				bean.setFrist_Name("Soni");
				list = model.search(bean, 1, 7);
				if (list.size() < 0) {
					System.out.println("Test Search fail");
				}
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (FacultyBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getFrist_Name());
					System.out.println(bean.getLast_Name());
					System.out.println(bean.getEmail_id());
					System.out.println(bean.getMobile_No());
					System.out.println(bean.getCreated_By());
					System.out.println(bean.getCreated_Date_Time());
					System.out.println(bean.getModified_By());
					System.out.println(bean.getModified_Date_Time());
					System.out.println(bean.getQualification());
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Tests get List a College.
		 */
		public static void testList() {

			try {
				FacultyBean bean = new FacultyBean();
				List list = new ArrayList();
				list = model.list(1, 18);
				if (list.size() < 0) {
					System.out.println("Test list fail");
				}
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (FacultyBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getFrist_Name());
					System.out.println(bean.getLast_Name());
					System.out.println(bean.getDOJ());
					System.out.println(bean.getMobile_No());
					System.out.println(bean.getEmail_id());
					System.out.println(bean.getCollege_id());
					System.out.println(bean.getCourse_id());
					System.out.println(bean.getSubject_id());
					System.out.println(bean.getCollege_Name());
					System.out.println(bean.getCousre_Name());
					System.out.println(bean.getSubject_Name());
					System.out.println(bean.getCreated_By());
					System.out.println(bean.getCreated_Date_Time());
					System.out.println(bean.getModified_By());
					System.out.println(bean.getModified_Date_Time());
					System.out.println(bean.getQualification());
				}

			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}
	      
		/**
		 * Test find by email id.
		 */
			public static void testFindByEmailId() {
				try {
					FacultyBean bean = new FacultyBean();
					bean = model.findByEmail("Mohit_00876@gmail.com");
					if (bean != null) {
						System.out.println("Test Find By EmailId fail");
					}
					System.out.println(bean.getId());
					System.out.println(bean.getFrist_Name());
					System.out.println(bean.getLast_Name());
					System.out.println(bean.getDOJ());
					System.out.println(bean.getMobile_No());
					System.out.println(bean.getEmail_id());
					System.out.println(bean.getCollege_id());
					System.out.println(bean.getCourse_id());
					System.out.println(bean.getSubject_id());
					System.out.println(bean.getQualification());
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
			}


	}


