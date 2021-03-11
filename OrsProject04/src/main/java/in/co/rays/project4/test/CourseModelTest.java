package in.co.rays.project4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.bean.CourseBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.exception.RecordNotFoundException;
import in.co.rays.project4.model.CourseModel;

public class CourseModelTest {

		public static CourseModel model =new CourseModel();
		public static void main(String[] args) throws ApplicationException, DuplicateException, RecordNotFoundException {

			
			
			//testAdd();
			//testDelete();
		//	testUpdate();
		//	testFindByName();	
	//testFindByPk();
			testSearch();	
		//testList();
		}

		
		
			public static void testAdd() throws ApplicationException, DuplicateException {
				
				CourseBean bean=new CourseBean();
//				String name="python";
//				bean=model.findByName(name);
//				if(bean!=null)
//				{
//					System.out.println("name already Exist");
//				}
				bean.setCourse_Name("Corporate ");
			    bean.setDiscription("dghgh");
				bean.setCreated_By("Admin");
				bean.setModified_By("Admin");
				bean.setCreated_Date_Time(new Timestamp(new Date().getTime()));
				bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
				bean.setDuration(" Months");
				model.add(bean);
				System.out.println("Updated");
				
			}

			public static void testDelete() throws ApplicationException, RecordNotFoundException {
				CourseBean bean =new CourseBean();
				
				bean.setId(4);
				model.delete(bean);
				System.out.println("Delete Success");
				
			}
			
			public static void testUpdate() throws ApplicationException, DuplicateException {
				
				CourseBean bean=new CourseBean();
						
				bean.setCourse_Name("Java Corporate Lang");
				//bean.setDuration("7 month");
				bean.setDiscription("core and Adv Java included");
				bean.setModified_By("Admin");
				bean.setModified_Date_Time(new Timestamp(new Date().getTime()));
				bean.setId(2);
				model.update(bean);
				System.out.println("UPDATE");
			}
			
				public static void testFindByName() throws ApplicationException {
					
					CourseBean bean=new CourseBean();
					//String name="IPS";
					//bean.setName("IPS");
					bean=model.findByName("Corporate Python");
					if(bean==null)
					{
						System.out.println("name not found");
					}
					
					System.out.println(bean.getId());
				System.out.println(bean.getCourse_Name());
				System.out.println(bean.getDiscription());
				System.out.println(bean.getDiscription());
				System.out.println(bean.getCreated_By());
				System.out.println(bean.getModified_By());
				System.out.println(bean.getCreated_Date_Time());
				System.out.println(bean.getModified_Date_Time());
					
				}
				
				
	public static void testFindByPk() throws ApplicationException {
					
					CourseBean bean=new CourseBean();
					//String name="IPS";
					//bean.setName("IPS");
					bean=model.findByPk(1);
					if(bean==null)
					{
						System.out.println("Id not found");
					}
					
					System.out.println(bean.getId());
				System.out.println(bean.getCourse_Name());
				System.out.println(bean.getDiscription());
				System.out.println(bean.getDiscription());
				System.out.println(bean.getCreated_By());
				System.out.println(bean.getModified_By());
				System.out.println(bean.getCreated_Date_Time());
				System.out.println(bean.getModified_Date_Time());
					
				}


				public static void testSearch() throws ApplicationException {
					
					CourseBean bean=new CourseBean();
					List list = new ArrayList();
					
					bean.setCourse_Name("Corporate Jav");
					
					list=model.search(bean, 1, 12);
					if(list.size()<=0)
					{
						System.out.println("record not found");
					}
					
					else {
						Iterator it= list.iterator();
						while(it.hasNext())
						{
							bean=(CourseBean) it.next();
							System.out.println(bean.getId());
							System.out.println(bean.getCourse_Name());
							System.out.println(bean.getDiscription());
							System.out.println(bean.getDiscription());
							System.out.println(bean.getCreated_By());
							System.out.println(bean.getModified_By());
							System.out.println(bean.getCreated_Date_Time());
							System.out.println(bean.getModified_Date_Time());

							
						}
					}
				}

					public static void testList() {
						
						CourseBean bean=new CourseBean();
						List list=new ArrayList();
						
						try {
							list=model.list(1, 10);
							if(list.size()<=0)
							{
								System.out.println("record not found");
							}
							Iterator it=list.iterator();
							while(it.hasNext())
							{
								bean= (CourseBean) it.next();
								System.out.println(bean.getId());
								System.out.println(bean.getCourse_Name());
								System.out.println(bean.getDiscription());
								System.out.println(bean.getCreated_By());
								System.out.println(bean.getModified_By());
								System.out.println(bean.getCreated_Date_Time());
								System.out.println(bean.getModified_Date_Time());

							}
						
						} catch (ApplicationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}


	}


