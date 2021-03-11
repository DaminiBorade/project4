package in.co.rays.project4.bean;

/**
 *  College JavaBean encapsulates College attributes
 * @author Damini
 *
 */
public class CollegeBean extends BaseBean {
private String Name ;
private String Address;
private String State;
private String City;
private String Phone_No;
public  CollegeBean(){
	//Default constructor;

}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public void setAddress(String address) {
	Address = address;
}
public void setState(String state) {
	State = state;
}
public void setCity(String city) {
	City = city;
}
public void setPhone_No(String phone_No) {
	Phone_No = phone_No;
}
public String getAddress() {
	return Address;
}
public String getState() {
	return State;
}
public String getCity() {
	return City;
}
public String getPhone_No() {
	return Phone_No;
}
public String getkey() {
	// TODO Auto-generated method stub
	return id + "";
}
public String getvalue() {
	// TODO Auto-generated method stub
	return Name;
}


}
