package in.co.rays.project4.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.bean.StudentBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.StudentModel;

public class StudentModelTest {

	    /**
	     * Model object to test
	     */

	    public static StudentModel model = new StudentModel();

	    /**
	     * Main method to call test methods.
	     *
	     * @param args
	     * @throws ParseException
	     * @throws DuplicateException 
	     * @throws ApplicationException 
	     */
	    public static void main(String[] args) throws ParseException, ApplicationException, DuplicateException {
//	         testAdd();
	       // testDelete();
	        // testUpdate();
	       // testFindByPK();
	        //testFindByEmailId();
	        testSearch();
	       //testList();

	    }

	    /**
	     * Tests add a Student
	     *
	     * @throws ParseException
	     * @throws DuplicateException 
	     * @throws ApplicationException 
	     * @throws SQLException 
	     */
	    public static void testAdd() throws ParseException, ApplicationException, DuplicateException, SQLException {

	        //try {
	            StudentBean bean = new StudentBean();
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	            //bean.setId(1);
	            //bean.setCollege_Name("Maheshwari COLG ");
	            bean.setFrist_Name("ankita");
	            bean.setLast_Name("pal");
	            bean.setDate_of_Birth(sdf.parse("05/07/1993"));
	            bean.setMobile_No("951784532");
	            bean.setEmail("Ankit@GMAIL.COM");
	            bean.setCollege_Id(2);
	            bean.setCreated_By("admin");
	            bean.setModified_By("admin");
	            bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
                 bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
	           
	            int pk = model.add(bean);
	            StudentBean addedbean = model.findByPK(pk);
	            System.out.println("Inserted");
	            if (addedbean == null) {
	                System.out.println("Test add fail");
	            }
//	        } catch (ApplicationException e) {
//	            e.printStackTrace();
//	        } catch (DuplicateException e) {
//	            e.printStackTrace();
//	        }

	    }

	    /**
	     * Tests delete a Student
	     * @throws ApplicationException 
	     */
	    public static void testDelete() throws ApplicationException {

	       // try {
	            StudentBean bean = new StudentBean();
	            int pk = 1;
	            bean.setId(pk);
	            model.delete(bean);
	            StudentBean deletedbean = model.findByPK(pk);
	            System.out.println("deleted");
	            if (deletedbean != null) {
	                System.out.println("Test Delete fail");
	            }
//	        } catch (ApplicationException e) {
//	            e.printStackTrace();
//	        }
	    }

	    /**
	     * Tests update a Student
	     * @throws DuplicateException 
	     * @throws ApplicationException 
	     */
	    public static void testUpdate() throws ApplicationException, DuplicateException {

	    //    try {
	            StudentBean bean = model.findByPK(3);
	            bean.setCollege_Id(2);
	            bean.setFrist_Name("MOHIT");
	            bean.setLast_Name("JAIN");
	            model.update(bean);

	            StudentBean updatedbean = model.findByPK(3);
	            System.out.println("Updated");
//	            if (!"rr".equals(updatedbean.getFrist_Name())) {
//	                System.out.println("Test Update fail");
//	            }
//	        } catch (ApplicationException e) {
//	            e.printStackTrace();
//	        } catch (DuplicateException e) {
//	            e.printStackTrace();
//	        }
	    }

	    /**
	     * Tests find a Student by PK.
	     */
	    public static void testFindByPK() {
	        try {
	            StudentBean bean = new StudentBean();
	            int pk = 5;
	            bean = model.findByPK(pk);
	            if (bean == null) {
	                System.out.println("Test Find By PK fail");
	            }
	            System.out.println(bean.getId());
	            System.out.println(bean.getFrist_Name());
	            System.out.println(bean.getLast_Name());
	            System.out.println(bean.getDate_of_Birth());
	            System.out.println(bean.getMobile_No());
	            System.out.println(bean.getEmail());
	            System.out.println(bean.getCollege_Id());
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }

	    }

	    /**
	     * Tests find a Student by Emailid.
	     * @throws ApplicationException 
	     */
	    public static void testFindByEmailId() throws ApplicationException {
	      //  try {
	            StudentBean bean = new StudentBean();
	            bean = model.findByEmailId("ROHITCHOU@GMAIL.COM");
	            if (bean != null) {
	                System.out.println("Test Find By EmailId fail");
	            }
	            System.out.println(bean.getId());
	            System.out.println(bean.getFrist_Name());
	            System.out.println(bean.getLast_Name());
	            System.out.println(bean.getDate_of_Birth());
	            System.out.println(bean.getMobile_No());
	            System.out.println(bean.getEmail());
	            System.out.println(bean.getCollege_Id());
//	        } catch (ApplicationException e) {
//	            e.printStackTrace();
//	        }
	    }

	    /**
	     * Tests get Search
	     * @throws ApplicationException 
	     */
	    public static void testSearch() throws ApplicationException {

	        //try {
	            StudentBean bean = new StudentBean();
	            List list = new ArrayList();
	            bean.setFrist_Name("SONALIKA");
	            list = model.search(bean, 0, 0);
	            if (list.size() < 0) {
	                System.out.println("Test Serach fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (StudentBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getFrist_Name());
	                System.out.println(bean.getLast_Name());
	                System.out.println(bean.getDate_of_Birth());
	                System.out.println(bean.getMobile_No());
	                System.out.println(bean.getEmail());
	                System.out.println(bean.getCollege_Id());
	            }

//	        } catch (ApplicationException e) {
//	            e.printStackTrace();
//	        }

	    }

	    /**
	     * Tests get List.
	     * @throws ApplicationException 
	     */
	    public static void testList() throws ApplicationException {

	       // try {
	            StudentBean bean = new StudentBean();
	            List list = new ArrayList();
	            list = model.list(1, 5);
	            if (list.size() < 0) {
	                System.out.println("Test list fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (StudentBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getFrist_Name());
	                System.out.println(bean.getLast_Name());
	                System.out.println(bean.getDate_of_Birth());
	                System.out.println(bean.getMobile_No());
	                System.out.println(bean.getEmail());
	                System.out.println(bean.getCollege_Id());
	                System.out.println(bean.getCreated_By());
	                System.out.println(bean.getCreated_Date_Time());
	                System.out.println(bean.getModified_By());
	                System.out.println(bean.getModified_Date_Time());
	            }

//	        } catch (ApplicationException e) {
//	            e.printStackTrace();
//	        }
	    }

	}


