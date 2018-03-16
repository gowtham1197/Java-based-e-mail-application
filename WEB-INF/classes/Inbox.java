import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
public class Inbox extends HttpServlet
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
		 try{
        //String e=new String(Display.e);
        HttpSession session=req.getSession(false);
        String e=(String)session.getAttribute("mail");
    	   String email=(String)session.getAttribute("email");
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
			
	   
			
               PreparedStatement ps=cn.prepareStatement("select * from "+e+"inbox");
			  ResultSet rs=ps.executeQuery();
				//rs.next();
			   while(rs.next())
			   {
                 out.println("");
               }
        }
        catch(Exception e1){
        	e1.printStackTrace();

        }
    }
}
