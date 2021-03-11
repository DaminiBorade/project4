package in.co.rays.project4.bean;

/**
 * Subject JavaBean encapsulates Subject attributes
 * @author Damini
 *
 */
public class SubjectBean extends BaseBean {
private String Subject_Name;
private String Course_Name;
private int Course_Id;
private String Description;
public  SubjectBean(){
	//Default Const;
}
public String getSubject_Name() {
	return Subject_Name;
}
public String getCourse_Name() {
	return Course_Name;
}
public void setSubject_Name(String subject_Name) {
	Subject_Name = subject_Name;
}
public void setCourse_Name(String course_Name) {
	Course_Name = course_Name;
}
public void setDescription(String description) {
	Description = description;
}
public String getDescription() {
	return Description;
}
public int getCourse_Id() {
	return Course_Id;
}
public String getkey() {
	return id+"";
	// TODO Auto-generated method stub
	
}
public String getvalue() {
	return Subject_Name;
	// TODO Auto-generated method stub
	
}
public void setCourse_Id(int course_Id) {
	Course_Id = course_Id;
}
}
