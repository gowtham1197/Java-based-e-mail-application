import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
public class retreive extends HttpServlet
{
	Connection cn;
	public void init()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			cn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","ganesha");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		try
		{
			PreparedStatement ps=cn.prepareStatement("select * from register");
			ResultSet rs=ps.executeQuery();
			rs.next();
			while(rs.next())
			{
				out.println(rs.getString(1));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}