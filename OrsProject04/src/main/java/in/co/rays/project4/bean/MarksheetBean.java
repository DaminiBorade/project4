package in.co.rays.project4.bean;

/**
 * Marksheet JavaBean encapsulates Marksheet attributes
 * @author Damini
 *
 */
public class MarksheetBean extends BaseBean {
private String Roll_No;
private int Student_Id;
private String Name;
private int Physics;
private int Chemistry;
private int Maths;
public  MarksheetBean(){
	//Default Const;
}
public void setRoll_No(String roll_No) {
	Roll_No = roll_No;
}
public void setStudent_Id(int student_Id) {
	Student_Id = student_Id;
}
public void setName(String name) {
	Name = name;
}
public void setPhysics(int physics) {
	Physics = physics;
}
public void setChemistry(int chemistry) {
	Chemistry = chemistry;
}
public void setMaths(int maths) {
	Maths = maths;
}
public String getRoll_No() {
	return Roll_No;
}
public int getStudent_Id() {
	return Student_Id;
}
public String getName() {
	return Name;
}
public int getPhysics() {
	return Physics;
}
public int getChemistry() {
	return Chemistry;
}
public int getMaths() {
	return Maths;
}
public String getkey() {
	return id+"";
	// TODO Auto-generated method stub
	
}
public String getvalue() {
	return Roll_No;
	// TODO Auto-generated method stub
	
}

}
