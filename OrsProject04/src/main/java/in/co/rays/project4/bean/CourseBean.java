package in.co.rays.project4.bean;

/**
 * Course JavaBean encapsulates Course attributes 
 * @author Damini
 *
 */
public class CourseBean extends BaseBean{
	private String Course_Name;
	private String Discription;
	private String Duration;
	
	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}

	public  CourseBean(){
		//Default constructor;
	}

	public String getCourse_Name() {
		return Course_Name;
	}

	public String getDiscription() {
		return Discription;
	}

	public void setCourse_Name(String course_Name) {
		Course_Name = course_Name;
	}

	public void setDiscription(String discription) {
		Discription = discription;
	}

	public String getkey() {
		// TODO Auto-generated method stub
		return id +"";
	}

	public String getvalue() {
		// TODO Auto-generated method stub
		return Course_Name;
	}

	



}
