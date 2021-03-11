package in.co.rays.project4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.bean.CollegeBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.CollegeModel;

public class CollegeModelTest {
    public static CollegeModel model = new CollegeModel();
    /**
     * Main method to call test methods.
     *
     * @param args
     * @throws DuplicateRecordException
     */
    public static void main(String[] args) throws DuplicateException {
         //testAdd();
       // testDelete();
         testUpdate();
        // testFindByName();
       // testFindByPK();
       //  testSearch();
       // testList();

    }

    /**
     * Tests add a College
     *
     * @throws DuplicateRecordException
     */
    public static void testAdd() throws DuplicateException {

        try {
            CollegeBean bean = new CollegeBean();
            // bean.setId(2L);
            bean.setName("MataGujri");
            bean.setAddress("asdasdcf");
            bean.setState("mp");
            bean.setCity("bhopal");
            bean.setPhone_No("0731245244");
            bean.setCreated_By("Admin");
            bean.setModified_By("Admin");
            bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
            bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
            int pk = model.add(bean);
            System.out.println("Test add succ");
            CollegeBean addedBean = model.findByPK(pk);
            if (addedBean == null) {
                System.out.println("Test add fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests delete a College
     */
    public static void testDelete() {

        try {
            CollegeBean bean = new CollegeBean();
            int pk = 2;
            bean.setId(pk);
            model.delete(bean);
            System.out.println("Test Delete succ");
            CollegeBean deletedBean = model.findByPK(pk);
            if (deletedBean != null) {
                System.out.println("Test Delete fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests update a College
     */
    public static void testUpdate() {

        try {
            CollegeBean bean = model.findByPK(2);
            bean.setName("oit");
            bean.setAddress("vv");
            model.update(bean);
            System.out.println("Test Update succ");
            CollegeBean updateBean = model.findByPK(2);
            if (!"oit".equals(updateBean.getName())) {
                System.out.println("Test Update fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests find a College by Name.
     */

    public static void testFindByName() {

        try {
            CollegeBean bean = model.findByName("jsp");
            if (bean == null) {
                System.out.println("Test Find By Name fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getAddress());
            System.out.println(bean.getState());
            System.out.println(bean.getCity());
            System.out.println(bean.getPhone_No());
            System.out.println(bean.getCreated_By());
            System.out.println(bean.getCreated_Date_Time());
            System.out.println(bean.getModified_By());
            System.out.println(bean.getModified_Date_Time());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests find a College by PK.
     */
    public static void testFindByPK() {
        try {
            CollegeBean bean = new CollegeBean();
            int pk = 1;
            bean = model.findByPK(pk);
            if (bean == null) {
                System.out.println("Test Find By PK fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getAddress());
            System.out.println(bean.getState());
            System.out.println(bean.getCity());
            System.out.println(bean.getPhone_No());
            System.out.println(bean.getCreated_By());
            System.out.println(bean.getCreated_Date_Time());
            System.out.println(bean.getModified_By());
            System.out.println(bean.getModified_Date_Time());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests search a College by Name
     */

    public static void testSearch() {
        try {
            CollegeBean bean = new CollegeBean();
            List list = new ArrayList();
            bean.setName("VITS College");
          //  bean.setAddress("borawan");
            list = model.search(bean, 1, 5);
            if (list.size() < 0) {
                System.out.println("Test Search fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (CollegeBean) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getName());
                System.out.println(bean.getAddress());
                System.out.println(bean.getState());
                System.out.println(bean.getCity());
                System.out.println(bean.getPhone_No());
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
     * Tests get List a College.
     */
    public static void testList() {

        try {
            CollegeBean bean = new CollegeBean();
            List list = new ArrayList();
            list = model.list(1, 10);
            if (list.size() < 0) {
                System.out.println("Test list fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (CollegeBean) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getName());
                System.out.println(bean.getAddress());
                System.out.println(bean.getState());
                System.out.println(bean.getCity());
                System.out.println(bean.getPhone_No());
                System.out.println(bean.getCreated_By());
                System.out.println(bean.getCreated_Date_Time());
                System.out.println(bean.getModified_By());
                System.out.println(bean.getModified_Date_Time());
            }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }



//	public static void main(String[] args) {
//Testadd();
//	}
//
//	private static void Testadd() {
//		try{
//		CollegeBean bean=new CollegeBean();
//		bean.setId(1);
//		bean.setName("St.paul");
//		bean.setAddress("LalRam Nagar");
//		bean.setCity("Indore");
//		bean.setState("MP");
//		bean.setPhone_No("07314045831");
//		bean.setCreated_By("Admin");
//		bean.setModified_By("Admin");
//		bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
//		bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
//			
//	}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//	}

}
