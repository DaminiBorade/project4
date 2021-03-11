package in.co.rays.project4.bean;

import java.util.Date;

/**
 * Faculty JavaBean encapsulates Faculty attributes
 * @author Damini
 *
 */
public class FacultyBean extends BaseBean {
private String Frist_Name;
private String Last_Name;
private String Gender;
private Date DOJ;
private String Qualification;
private String Email_id;
private String Mobile_No ;
private int College_id;
private String College_Name;
private int Course_id;
private String Cousre_Name;
private int Subject_id;
private String Subject_Name;

public  FacultyBean(){
	//Default Const;
}
public void setFrist_Name(String frist_Name) {
	Frist_Name = frist_Name;
}
public void setLast_Name(String last_Name) {
	Last_Name = last_Name;
}
public void setGender(String gender) {
	Gender = gender;
}
public void setDOJ(Date dOJ) {
	DOJ = dOJ;
}
public void setQualification(String qualification) {
	Qualification = qualification;
}
public void setEmail_id(String email_id) {
	Email_id = email_id;
}
public void setMobile_No(String mobile_No) {
	Mobile_No = mobile_No;
}
public void setCollege_id(int college_id) {
	College_id = college_id;
}
public void setCollege_Name(String college_Name) {
	College_Name = college_Name;
}
public void setCourse_id(int course_id) {
	Course_id = course_id;
}
public void setCousre_Name(String cousre_Name) {
	Cousre_Name = cousre_Name;
}
public void setSubject_id(int subject_id) {
	Subject_id = subject_id;
}
public void setSubject_Name(String subject_Name) {
	Subject_Name = subject_Name;
}

public String getFrist_Name() {
	return Frist_Name;
}
public String getLast_Name() {
	return Last_Name;
}
public String getGender() {
	return Gender;
}
public Date getDOJ() {
	return DOJ;
}
public String getQualification() {
	return Qualification;
}
public String getEmail_id() {
	return Email_id;
}
public String getMobile_No() {
	return Mobile_No;
}
public int getCollege_id() {
	return College_id;
}
public String getCollege_Name() {
	return College_Name;
}
public int getCourse_id() {
	return Course_id;
}
public String getCousre_Name() {
	return Cousre_Name;
}
public int getSubject_id() {
	return Subject_id;
}
public String getSubject_Name() {
	return Subject_Name;
}
public String getkey() {
	// TODO Auto-generated method stub
	return id+ "";
}
public String getvalue() {
	// TODO Auto-generated method stub
	return Frist_Name;
}

}
