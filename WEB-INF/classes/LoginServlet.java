import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
public class LoginServlet extends HttpServlet
{
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
		String u=req.getParameter("u1");
		String p=req.getParameter("p1");
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		try
		{
			RequestDispatcher request=req.getRequestDispatcher("home");
			 PreparedStatement ps4=cn.prepareStatement("select * from register where email=? and pass=?");
			  ps4.setString(1,u);
			  ps4.setString(2,p);
				ResultSet rs=ps4.executeQuery();
				//rs.next();
				while(rs.next()){
					HttpSession session=req.getSession();
					session.setAttribute("uname",u);
					request.forward(req,res);
				}
		}
        catch(Exception e){
        	e.printStackTrace();
        }
    }
}
	