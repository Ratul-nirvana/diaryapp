

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class RequestHandler
 */
@WebServlet("/RequestHandler")
public class RequestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parameter = request.getParameter("type");
		System.out.println(parameter);
		if(parameter == null){
			RequestDispatcher view = request.getRequestDispatcher("/index.html");
			view.forward(request, response);
			return;
		}
		if(parameter.equals("question")){
			response.getWriter().println(getQuestions());
		}
		else if(parameter.equals("answers")){
			ArrayList<String> resultArray = getAnswers();
			String result = "";
			for (String item : resultArray){
				result = result+"%"+item ;
			}
			System.out.println("RESPONSE :: "+result);
			response.getWriter().println(result);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("postType");
		String question = request.getParameter("postQuestion");
		String answer = request.getParameter("postAnswer");
		boolean status = false;
		System.out.println("POST::TYPE:"+type);
		/****************************/
		if(type.equals("question")){
			status = postQuestion(question);
		}
		else if(type.equals("answer")){
			status = postAnswer(question, answer);
		}
		if(status == true){
			response.getWriter().println("Data inserted successfully");
		}
		else{
			response.getWriter().println("Failed to insert data");
		}
	}
	private String getQuestions(){
		String myquestions = "";
		myquestions = myquestions+DBUtils.getQuestion();
		System.out.println(myquestions);
		return myquestions;
	}
	private ArrayList<String> getAnswers(){
		ArrayList<String> myAnswers = new ArrayList<String>();
		myAnswers = (DBUtils.getAnswer());
		return myAnswers;
	}
	private boolean postQuestion(String question){
		return DBUtils.postQuestion(question);
		
	}
	private boolean postAnswer(String question, String answer){
		return DBUtils.postAnswer(question, answer);
		
	}

}
