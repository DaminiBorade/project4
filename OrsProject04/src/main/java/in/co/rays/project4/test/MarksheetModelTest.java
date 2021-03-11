package in.co.rays.project4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.bean.MarksheetBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.MarksheetModel;

public class MarksheetModelTest {

	    

	    public static MarksheetModel model = new MarksheetModel();

	    /**
	     * Main method to call test methods.
	     *
	     * @param args
	     * @throws DuplicateException 
	     * @throws ApplicationException 
	     */
	    public static void main(String[] args) throws ApplicationException, DuplicateException {
	         testAdd();
	       //  testDelete();
	       //  testUpdate();
	        // testFindByRollNo();
	        // testFindByPK();
	      //  testSearch();
	        //testMeritList();
	      //  testList();

	    }

	    /**
	     * Tests add a Marksheet
	     * @throws DuplicateException 
	     * @throws ApplicationException 
	     */
	    public static void testAdd() throws ApplicationException, DuplicateException {

        
	            MarksheetBean bean = new MarksheetBean();
	            
	            //bean.setId(2);
	           bean.setName("Vaishali");
	              bean.setRoll_No("10");
            bean.setPhysics(90);
            bean.setChemistry(98);
            bean.setMaths(96);
            bean.setCreated_By("Admin");
            bean.setModified_By("me");
            bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
            bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
            
           bean.setStudent_Id(3);
	            int pk = model.add(bean);
	            if(pk>1){
	            	System.out.println("inserted");
	            }
	            

	            MarksheetBean addedbean = model.findByPK(pk);
	            if (addedbean == null) {
	                System.out.println("Test add fail");
	            }
	       
}

	    /**
	     * Tests delete a Marksheet
	     * @throws ApplicationException 
	     */
	    public static void testDelete() throws ApplicationException {

	       
	            MarksheetBean bean = new MarksheetBean();
	            int pk = 4;
	            bean.setId(pk);
	            model.delete(bean);
	            MarksheetBean deletedbean = model.findByPK(pk);
	            if (deletedbean != null) {
	                System.out.println("Test Delete fail");
	            }
	    }       

	    /**
	     * Tests update a Marksheet
	     * @throws DuplicateException 
	     * @throws ApplicationException 
	     */
	    public static void testUpdate() throws ApplicationException, DuplicateException {

	        
	            MarksheetBean bean = model.findByPK(3);
	            bean.setName("IIM");
	            bean.setChemistry(87);
	            bean.setMaths(90);
	           bean.setStudent_Id(4);
	            model.update(bean);

	            MarksheetBean updatedbean = model.findByPK(3);
	            System.out.println("Test Update succ");
	            if (!"IIM".equals(updatedbean.getName())) {
	                System.out.println("Test Update fail");
	            }
	           
	    }



	    /**
	     * Tests find a marksheet by Roll No.
	     */

	    public static void testFindByRollNo() {

	        try {
	            MarksheetBean bean = model.findByRollNo("34");
	            if (bean == null) {
	                System.out.println("Test Find By RollNo fail");
	            }
	            System.out.println(bean.getId());
	            System.out.println(bean.getRoll_No());
	            System.out.println(bean.getName());
	            System.out.println(bean.getPhysics());
	            System.out.println(bean.getChemistry());
	            System.out.println(bean.getMaths());
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }

	    }

	    /**
	     * Tests find a marksheet by PK.
	     */
	    public static void testFindByPK() {
	        try {
	            MarksheetBean bean = new MarksheetBean();
	            int pk = 3;
	            bean = model.findByPK(pk);
	            if (bean == null) {
	                System.out.println("Test Find By PK fail");
	            }
	            System.out.println(bean.getId());
	            System.out.println(bean.getRoll_No());
	            System.out.println(bean.getName());
	            System.out.println(bean.getPhysics());
	            System.out.println(bean.getChemistry());
	            System.out.println(bean.getMaths());
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }

	    }

	    /**
	     * Tests search a Marksheets
	     * @throws ApplicationException 
	     */

	    public static void testSearch() throws ApplicationException {
	        
	            MarksheetBean bean = new MarksheetBean();
	            List list = new ArrayList();
	            bean.setName("IPS");
	            list = model.search(bean, 1, 10);
	            if (list.size() < 1) {
	                System.out.println("Test Search fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (MarksheetBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getRoll_No());
	                System.out.println(bean.getName());
	                System.out.println(bean.getPhysics());
	                System.out.println(bean.getChemistry());
	                System.out.println(bean.getMaths());
	            
	       	    }
	    }
	    /**
	     * Tests get the meritlist of Marksheets
	     */
	    public static void testMeritList() {
	        try {
	            MarksheetBean bean = new MarksheetBean();
	            List list = new ArrayList();
	            list = model.getMeritList(1, 3);
	            if (list.size() < 0) {
	                System.out.println("Test List fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (MarksheetBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getRoll_No());
	                System.out.println(bean.getName());
	                System.out.println(bean.getPhysics());
	                System.out.println(bean.getChemistry());
	                System.out.println(bean.getMaths());
	            }
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }

	    }

	    /**
	     * Tests list of Marksheets
	     * @throws ApplicationException 
	     */
	    public static void testList() throws ApplicationException {
	        
	            MarksheetBean bean = new MarksheetBean();
	            List list = new ArrayList();
	            list = model.list(1, 3);
	            if (list.size() < 0) {
	                System.out.println("Test List fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (MarksheetBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getRoll_No());
	                System.out.println(bean.getName());
	                System.out.println(bean.getPhysics());
	                System.out.println(bean.getChemistry());
	                System.out.println(bean.getMaths());
	                System.out.println(bean.getCreated_By());
	                System.out.println(bean.getCreated_Date_Time());
	                System.out.println(bean.getModified_By());
	                System.out.println(bean.getModified_Date_Time());
	            }
	    }

	    
	


	
}

