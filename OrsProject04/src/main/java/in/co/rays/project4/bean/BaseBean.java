package in.co.rays.project4.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *  Parent class of all Beans in application. It contains generic attributes.

 * @author Damini.
 *
 */
public abstract class BaseBean implements DropdownlistBean,Serializable,Comparable<BaseBean>{

 
 protected int id;
 protected String Created_By;
 protected String Modified_By;
 protected Timestamp Created_Date_Time;
 protected Timestamp Modified_Date_Time;
public int getId() {
	return id;
}
public void setId(int pk) {
	this.id = pk;
}
public String getCreated_By() {
	return Created_By;
}
public void setCreated_By(String created_By) {
	Created_By = created_By;
}
public String getModified_By() {
	return Modified_By;
}
public void setModified_By(String modified_By) {
	Modified_By = modified_By;
}
public Timestamp getCreated_Date_Time() {
	return Created_Date_Time;
}
public void setCreated_Date_Time(Timestamp created_Date_Time) {
	Created_Date_Time = created_Date_Time;
}
public Timestamp getModified_Date_Time() {
	return Modified_Date_Time;
}
public void setModified_Date_Time(Timestamp modified_Date_Time) {
	Modified_Date_Time = modified_Date_Time;
}


public int compareTo(BaseBean next) {
	return getvalue().compareTo(next.getvalue());
}
 
}
