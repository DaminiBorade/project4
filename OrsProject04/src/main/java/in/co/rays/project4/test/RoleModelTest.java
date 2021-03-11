package in.co.rays.project4.test;

import java.sql.Timestamp;
import java.text.ParseException;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.bean.RoleBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.exception.RecordNotFoundException;
import in.co.rays.project4.model.RoleModel;

public class RoleModelTest {
	public static RoleModel model = new RoleModel();

	public static void main(String[] args) throws ApplicationException, DuplicateException, RecordNotFoundException {
		// TestAdd();
		 TestDelete();
		// TestUpadte();
		//TestfindByPK();
		// TestfindByName();
		// TestListSeacrh();
		// TestList();

	}

	private static void TestList() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			list = model.search(bean);
			if (list.size() < 0) {
				System.out.println("Test Saecrh fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void TestListSeacrh() {
		RoleBean bean = new RoleBean();
		List list = new ArrayList();
		// bean.setId(2);
		// bean.setName("student");
		bean.setDescription("admin");
		list = model.Search(bean, 0, 0);
		if (list.size() < 0) {
			System.out.println("Test Serach fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (RoleBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		}

	}

	private static void TestfindByName() throws RecordNotFoundException {
		try {
			RoleBean bean = new RoleBean();
			bean = model.findByName("Sonali");
			if (bean == null) {
				System.out.println("Test Find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (Exception e) {
			// throw new RecordNotFoundException("name not found"
			// +e.getMessage());
			e.printStackTrace();
		}
	}

	// *Test FindByPk
	private static void TestfindByPK() {
		try {
			RoleBean bean = new RoleBean();
			int pk = 1;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// *Test Delete
	private static void TestDelete() {
		try {
			RoleBean bean = new RoleBean();
			int pk = 7;
			bean.setId(pk);
			System.out.println("Delete");
			model.delete(bean);

			// RoleBean deletedbean=new model.findByPK(pk);
			// if(deletedbean!=null){
			// System.out.println("Test Fail");
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TestAdd
	private static void TestAdd() throws ApplicationException, DuplicateException, RecordNotFoundException {
		RoleBean bean = new RoleBean();
		// SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
		// bean.setId(101);
		bean.setName("COLLEGE	");
		bean.setDescription("GUEST");
		bean.setCreated_By("ADM");
		bean.setModified_By("GUEST");
		bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
		bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
		int pk = model.add(bean);
		if (pk > 0) {
			System.out.println("inserted");
		}
		// RoleBean addedbean = model.findByPK(pk);
		// if (addedbean == null) {
		// System.out.println("Test add fail");
		// }
	}

	private static void TestUpadte() {
		try {
			RoleBean bean = model.findByPK(4);

			bean.setName("Newer");
			bean.setDescription("Upadte");
			// bean.setId(3);
			model.update(bean);
			System.out.println("Updated");
			RoleBean updatedbean = new RoleBean();
			if (!"4".equals(updatedbean.getName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
