import java.sql.*;
import java.util.ArrayList;
public class DBUtils {
	
	private static final String URL = "jdbc:mysql://localhost:3306/mindmap";
	private static final String USER = "root";
	private static final String PASSWORD = "Ratuldb@123";
	public static String getQuestion(){
		ArrayList<String> results = new ArrayList<String>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = con.createStatement();
			String query = "select question from tbl_questions where answer is null";
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				results.add(rs.getString("question"));
				System.out.println("DBUTILS : "+rs.getString("question")+"\n");
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		int randomIndex = (int)(Math.random() * (results.size()));
		String result = results.get(randomIndex);
		return result;
	}
	public static ArrayList<String> getAnswer(){
		ArrayList results = new ArrayList();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = con.createStatement();
			String query = "select question,answer from tbl_questions where answer is not null";
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				results.add(rs.getString("question")+"~"+rs.getString("answer"));
				System.out.println("DBUTILS : "+rs.getString("question")+"~"+rs.getString("answer")+"\n");
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		System.out.println(results.toString());
		return results;
	}
	public static boolean postQuestion(String question){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = con.createStatement();
			String query = "insert into tbl_questions (question, dateOfQuestion) values(?,CURDATE())";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, question);
			ps.executeUpdate();
			return true;
		}
		catch(Exception e){
			System.err.println(e.getMessage());
			return false;
		}
		
	}
	public static boolean postAnswer(String question, String answer){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("updating answerfor the question");
			Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = con.createStatement();
			question = question.trim();
			answer = answer.trim();
			String query = "update  tbl_questions set answer = ?,dateOfAnswer = CURDATE()  where question =?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, answer);
			ps.setString(2, question);
			ps.executeUpdate();
			return true;
		}
		catch(Exception e){
			System.err.println(e.getMessage());
			return false;
		}
		
	}
}
