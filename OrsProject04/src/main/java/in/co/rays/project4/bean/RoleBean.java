package in.co.rays.project4.bean;

/**
 * Role JavaBean encapsulates Role attributes
 * @author hp
 *
 */
public class RoleBean extends BaseBean {
	
	public static final int ADMIN=1;
	public static final int STUDENT=2;
	public static final int COLLEGE_SCHOOL=3;
	public static final int KIOSK=4;
private String Name;
private String Description;

public  RoleBean(){
	
}

public void setName(String name) {
	Name = name;
}

public void setDescription(String description) {
	Description = description;
}

public String getName() {
	return Name;
}

public String getDescription() {
	return Description;
}

public String getkey() {
	// TODO Auto-generated method stub
	return id+"";
}

public String getvalue() {
	// TODO Auto-generated method stub
	return Name;
}



}
