package in.co.rays.project4.bean;

import java.util.Date;

/**
 * Student JavaBean encapsulates Student attributes
 * @author Damini
 *
 */
public class StudentBean extends BaseBean {
private int College_Id;
private String College_Name;
private String Frist_Name;
private String Last_Name;
private Date Date_of_Birth;
private String Mobile_No;
private String Email;
public  StudentBean(){
	//Default Const;
}
public int getCollege_Id() {
	return College_Id;
}
public String getCollege_Name() {
	return College_Name;
}
public String getFrist_Name() {
	return Frist_Name;
}
public String getLast_Name() {
	return Last_Name;
}
public Date getDate_of_Birth() {
	return Date_of_Birth;
}
public String getMobile_No() {
	return Mobile_No;
}
public String getEmail() {
	return Email;
}
public void setCollege_Id(int college_Id) {
	College_Id = college_Id;
}
public void setCollege_Name(String college_Name) {
	College_Name = college_Name;
}
public void setFrist_Name(String frist_Name) {
	Frist_Name = frist_Name;
}
public void setLast_Name(String last_Name) {
	Last_Name = last_Name;
}
public void setDate_of_Birth(Date date_of_Birth) {
	Date_of_Birth = date_of_Birth;
}
public void setMobile_No(String mobile_No) {
	Mobile_No = mobile_No;
}
public void setEmail(String email) {
	Email = email;
}

public String getkey() {
	// TODO Auto-generated method stub
	return id+"";
}

public String getvalue() {
	// TODO Auto-generated method stub
	return Frist_Name + " " +Last_Name;
}



}
