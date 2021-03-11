package in.co.rays.project4.test;



import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.bean.SubjectBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.model.SubjectModel;


// TODO: Auto-generated Javadoc
/**
 * The Class SubjectModelTest.
 */
public class SubjectModelTest {


    /** The model. */
    public static SubjectModel model = new SubjectModel();

    /**
     * Main method to call test methods.
     *
     * @param args the arguments
     * @throws ParseException the parse exception
     */
    public static void main(String[] args) throws ParseException {
        // testAdd();
        // testDelete();
         //testUpdate();
        //testFindByPK();
      testSearch();
  // testList();

    }

    /**
     * Tests add a Student.
     *
     * @throws ParseException the parse exception
     * @throws SQLException 
     */
    public static void testAdd() throws ParseException, SQLException {

        try {
            SubjectBean bean = new SubjectBean();

          //  bean.setId(1);
            bean.setSubject_Name("Account");
            bean.setDescription("B.Eng");
           // bean.setCourse_Name("BE");
            bean.setCourse_Id(2);
           //bean.setCourse_Name("bca");
            bean.setCreated_By("Faculty");
            bean.setModified_By("Faculty");
            bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
            bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
            int pk = model.add(bean);
           System.out.println("data enter");
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests delete a Student.
     */
    public static void testDelete() {

        try {
            SubjectBean bean = new SubjectBean();
            int pk = 4;
            bean.setId(pk);
            model.delete(bean);
            SubjectBean deletedbean = model.findByPk(pk);
            System.out.println("Deleted");
            if (deletedbean != null) {
                System.out.println("Test Delete fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests update a Student.
     */
    public static void testUpdate() {

        try {
            SubjectBean bean = model.findByPk(2);
           
            bean.setSubject_Name("rtyui");
          
            model.update(bean);

            SubjectBean updatedbean = model.findByPk(2);
            System.out.println("Update");
            
//            if (!"c++".equals(updatedbean.getSubject_Name())) {
//                System.out.println("Test Update fail");
//            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests find a Student by PK.
     */
    public static void testFindByPK() {
        try {
            SubjectBean bean = new SubjectBean();
            int pk = 1;
            bean = model.findByPk(pk);
            if (bean == null) {
                System.out.println("Test Find By PK fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getCourse_Id());
            System.out.println(bean.getCourse_Name());
            System.out.println(bean.getDescription());
            System.out.println(bean.getCreated_By());
            System.out.println(bean.getSubject_Name());
            System.out.println(bean.getModified_By());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

      /**
       * Tests get Search.
       */
    
    public static void testSearch() {

		try {
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			bean.setSubject_Name("MPC");
			list = model.search(bean, 1, 10);
			if (list.size() <0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getSubject_Name());
				System.out.println(bean.getCourse_Name());
                System.out.println(bean.getCourse_Id());
				System.out.println(bean.getDescription());
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
            SubjectBean bean = new SubjectBean();
            List list = new ArrayList();
            list = model.list(1, 10);
            if (list.size() < 0) {
                System.out.println("Test list fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (SubjectBean) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getCourse_Id());
                System.out.println(bean.getCourse_Name());
                System.out.println(bean.getDescription());
                System.out.println(bean.getSubject_Name());
                            }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
    }

