package in.co.rays.project4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.bean.TimetableBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.TimetableModel;

public class TimetableModelTest {

	
		/** The model. */
		public static TimetableModel model = new TimetableModel();

		/**
		 * The main method.
		 *
		 * @param args the arguments
		 * @throws ParseException the parse exception
		 */
		public static void main(String[] args) throws ParseException {
		//testAdd();
		       // testDelete();	 
			// testFindByPK();
			// testFindByEmailId();
		 testSearch();
		 //testList();
	  	//testUpdate();
		
	}
		
		/**
		 * Test add.
		 *
		 * @throws ParseException the parse exception
		 */
		public static void testAdd() throws ParseException {
			try {
				TimetableBean bean = new TimetableBean();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			
				//bean.setCourse_Name("hindi");
				bean.setCourse_Id(2);
				//bean.setSubject_Name("CS");
				bean.setSubject_Id(2);
				bean.setExam_Date(sdf.parse("6/7/2021"));
				bean.setExam_Time("07:00 AM to 8:00 AM");
				bean.setSemester("4");
				bean.setCreated_By("Hr");
				bean.setModified_By("Admin");
				bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
				bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
				 model.add(bean);
				 System.out.println("Inserted");
//				TimetableBean addedbean = model.findByPk(pk);
//				if (addedbean == null) {
//					System.out.println("Test add fail");
//				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (DuplicateException e) {
				e.printStackTrace();
			}

		}

		
		/**
		 * Test find by PK.
		 */
		public static void testFindByPK() {
			try {
			    TimetableBean bean = new TimetableBean();
				int pk = 2;
				bean = model.findByPk(pk);
				if (bean == null) {
					System.out.println("Test Find By PK fail");
				}
				System.out.println(bean.getId());
				System.out.println(bean.getCourse_Name());
				System.out.println(bean.getCourse_Id());
				System.out.println(bean.getSubject_Name());
				System.out.println(bean.getSubject_Id());
				System.out.println(bean.getExam_Date());
				System.out.println(bean.getExam_Time());
				System.out.println(bean.getSemester());
				System.out.println(bean.getCreated_By());
				System.out.println(bean.getModified_By());
			} catch (ApplicationException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Test search.
		 * @throws ParseException 
		 */
		public static void testSearch() throws ParseException {

			try {
				TimetableBean bean = new TimetableBean();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy");
				List list = new ArrayList();
			//	bean.setSubject_Name("Angular");
				bean.setExam_Date(sdf.parse("2021/03/05"));
			//	bean.setExam_Date"(2021-02-26);"
				list = model.search(bean, 0, 0);
				if (list.size() < 0) {
					System.out.println("Test Serach fail");
				}
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (TimetableBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getCourse_Id());
					System.out.println(bean.getCourse_Name());
					System.out.println(bean.getExam_Time());
					System.out.println(bean.getSemester());
					System.out.println(bean.getSubject_Id());
					System.out.println(bean.getSubject_Name());
					System.out.println(bean.getExam_Date());
					}

			} catch (ApplicationException e) {
				e.printStackTrace();
			}

		}
		
		
		/**
		 * Test list.
		 */
		public static void testList() {

			try {
				TimetableBean bean = new TimetableBean();
				List list = new ArrayList();
				list = model.list(1, 2);
				if (list.size() < 0) {
					System.out.println("Test list fail");
				}
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (TimetableBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getCourse_Id());
					System.out.println(bean.getCourse_Name());
					System.out.println(bean.getExam_Time());
					System.out.println(bean.getSemester());
					System.out.println(bean.getSubject_Id());
					System.out.println(bean.getSubject_Name());
					System.out.println(bean.getExam_Date());
					System.out.println(bean.getCreated_By());
					System.out.println(bean.getCreated_Date_Time());
					System.out.println(bean.getModified_By());
					System.out.println(bean.getModified_Date_Time());
				}

			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Test update.
		 * @throws ParseException 
		 */
		public static void testUpdate() throws ParseException {

			try {
				TimetableBean bean = model.findByPk(1);
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy");
				Date date=sdf.parse("14-9-1996");
				//bean.setId(1);
				bean.setCourse_Id(2);
				//bean.setCourse_Name("St.paul");
				bean.setSubject_Id(4);
				//bean.setSubject_Name("Java");
				//bean.setExam_Date("10-8-1996");
				bean.setExam_Time("2Pm");
				bean.setSemester("5");
				bean.setCreated_By("hr");
				bean.setModified_By("hr");
				bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
				bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
						
				model.update(bean);
				System.out.println("Upadated");

				TimetableBean updatedbean = model.findByPk(2);
				/*if (!"Material Technology".equals(updatedbean.getSubjectName())) {
					System.out.println("Test Update fail");
				}else{
					System.out.println("Test Update Successfully");
				}*/
				System.out.println("-------------------------------");
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (DuplicateException e) {
				e.printStackTrace();
			}
		}
	          
		/**
		 * Test delete.
		 */
		public static void testDelete() {

			try {
				TimetableBean bean = new TimetableBean();
				int pk = 4;
				bean.setId(pk);
				model.delete(bean);
				System.out.println("Test Delete successfully");

				TimetableBean deletedBean = model.findByPk(pk);
				if (deletedBean != null) {

					System.out.println("Test Delete fail");

				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}




	}


