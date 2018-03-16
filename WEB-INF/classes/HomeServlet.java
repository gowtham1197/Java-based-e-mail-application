import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
public class HomeServlet extends HttpServlet{
	Connection cn;
	public void init()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			cn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","chat","chat");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		try{
			HttpSession session=req.getSession(false);
			HttpSession session1=req.getSession(false);
			String email=(String)session.getAttribute("uname");
			int i=email.indexOf('@');
			int j=email.indexOf('.');
			String e=email.substring(0,i)+""+email.substring(i+1,j)+""+email.substring(j+1,email.length());
			//HttpSession s1=req.getSession();
			session.setAttribute("uname",email);
			session1.setAttribute("tabname",e);
			RequestDispatcher rd=req.getRequestDispatcher("display");
			rd.forward(req,res);
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}