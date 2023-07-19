import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class CalculatorServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


	//http://localhost:8080/JavaCalculatorApi?Method=Add&Num1=1&Num2=2
	//http://localhost:8080/JavaCalculatorApi?Method=Multiply&Num1=1&Num2=2


		String requestUrl = request.getQueryString();

		int methodEndIndex = requestUrl.indexOf("method=")+"method=".length();

		//String remainingUrl = requestUrl.substring(methodEndIndex);
		int firstAndIndex = requestUrl.indexOf("&")+"&".length();

		
		//response.getOutputStream().println(requestUrl +" " + remainingUrl +" " + firstAndIndex +" "  );
		String methodName = requestUrl.substring(methodEndIndex, firstAndIndex-1);

		//remainingUrl = remainingUrl.substring(methodName.length()+1);


		int num1Index = requestUrl.indexOf("num1=")+"num1=".length();
		firstAndIndex = requestUrl.indexOf("&",firstAndIndex+1)+"&".length();
		
		String num1Value = requestUrl.substring(num1Index, firstAndIndex-1);
		int num2Index = requestUrl.indexOf("num2=")+"num2=".length();

		String num2Value = requestUrl.substring(num2Index);


		Double num1 = Double.parseDouble(num1Value);
		Double num2 = Double.parseDouble(num2Value);

		Object result = ProcessNumbers(methodName, num1, num2, response);
		if(result!=null){
			String json = "{\n";
			json += "\"Result\": " + JSONObject.quote(result.toString()) + ",\n";
			json += "}";
			response.getOutputStream().println(json);
		}
		else{
			response.getOutputStream().println("{Invalid request.}");
		}
	}	

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Double num1 = Double.parseDouble(request.getParameter("num1"));
		Double num2 = Double.parseDouble(request.getParameter("num2"));
		String methodName = request.getParameter("method");

		Object result = ProcessNumbers(methodName, num1, num2, response);
		if(result!=null){
			String json = "{\n";
			json += "\"Result\": " + JSONObject.quote(result.toString()) + ",\n";
			json += "}";
			response.getOutputStream().println(json);
		}
		else{
			response.getOutputStream().println("{Invalid request.}");
		}
	}
	

	public Object ProcessNumbers (String methodName, Double num1, Double num2, HttpServletResponse response) {


		Object result = null;
		if (methodName.toLowerCase().equals("add")) {
			result = MathFunctions.Add(num1, num2);
		}
		if (methodName.toLowerCase().equals("substract")) {
			result = MathFunctions.Substract(num1, num2);
		}
		if (methodName.toLowerCase().equals("multiply")) {
			result = MathFunctions.Multiply(num1, num2);
		}
		if (methodName.toLowerCase().equals("divide")) {
			result = MathFunctions.Divide(num1, num2);
		}
		

		return result;

	}

}