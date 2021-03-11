package in.co.rays.project4.bean;

import java.util.Date;

/**
 * User JavaBean encapsulates User attributes
 * @author Damini
 *
 */
public class UserBean extends BaseBean {

	private String Frist_Name;
	private String Last_Name;
	private String Login;
	private String Password;
	private String newPassword;
    private Date DOB;
	private String Mobile_No;
	private int Roll_Id;
	private String Gender;
	

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getFrist_Name() {
		return Frist_Name;
	}

	public void setFrist_Name(String frist_Name) {
		Frist_Name = frist_Name;
	}

	public String getLast_Name() {
		return Last_Name;
	}

	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public String getMobile_No() {
		return Mobile_No;
	}

	public void setMobile_No(String mobile_No) {
		Mobile_No = mobile_No;
	}

	public int getRoll_Id() {
		return Roll_Id;
	}

	public void setRoll_Id(int roll_Id) {
		Roll_Id = roll_Id;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public int getUnSuccessfulLogin() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getvalue() {
		// TODO Auto-generated method stub
		return Frist_Name;
	}

	@Override
	public String toString() {
		return "UserBean [Password=" + Password + ", newPassword=" + newPassword + ", DOB=" + DOB + "]";
	}

}
