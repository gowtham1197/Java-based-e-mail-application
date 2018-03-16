import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
public class RegisterServlet extends HttpServlet
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

	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		String u=req.getParameter("u1");
		String p=req.getParameter("p1");
		String pp=req.getParameter("pp1");
		String e=req.getParameter("e1");
		String d=req.getParameter("d1");
		String g=req.getParameter("g1");
		String m=req.getParameter("m1");
		String c=req.getParameter("c1");
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		try
		{
			if(p.equals(pp)){
               PreparedStatement ps=cn.prepareStatement("insert into register values(?,?,?,?,?,?,?)");
			   ps.setString(1,u);
			   ps.setString(2,p);
			   ps.setString(3,e);
			   ps.setString(4,d);
			   ps.setString(5,g);
			   ps.setString(6,m);
			   ps.setString(7,c);
			   int count=ps.executeUpdate();

			   if(count>0)
			{
				int i=e.indexOf('@');
				int j=e.indexOf('.');
				String email=e.substring(0,i)+""+e.substring(i+1,j)+""+e.substring(j+1,e.length());
				PreparedStatement ps1=cn.prepareStatement("create table "+email+"(emails varchar2(100) primary key)");
				ps1.executeUpdate();
				PreparedStatement ps2=cn.prepareStatement("create table "+email+"inbox(emailid varchar2(100) ,emails varchar2(500))");
				ps2.executeUpdate();
				PreparedStatement ps3=cn.prepareStatement("create table "+email+"outbox(emailid varchar2(100),emails varchar2(500))");
				ps3.executeUpdate();
                HttpSession session=req.getSession();
    			//	HttpSession session1=req.getSession(false);
			   session.setAttribute("uname",e);
			   session.setAttribute("tabname",email);
			   RequestDispatcher request=req.getRequestDispatcher("display");
			   request.forward(req,res);
				/*out.println("<!doctype html><html lang='en-US'><head><title>Osmania Email</title><link rel='icon'type='image/ico' href=''><meta charset='utf-8'>");
  out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
  out.println("<link rel='stylesheet' href='./css/bootstrap.min.css'><script src='./css/jquery.min.js'></script>");
  out.println("<script src='./css/bootstrap.min.js'></script><link rel='stylesheet' type='text/css'  media='only screen and (color)' href='index.css'>"); 
	    out.println("<script src='https://www.google.com/recaptcha/api.js'></script></head><body><div class='container-fluid'><div class='row' style='background-color:#7FB3D5'>");
		out.println("<div class='col-md-3'><br><br><div class='row'><form action='./addcontacts'><div class='form-group col-md-7'><input type='text' placeholder='Enter email' name='e1' class='form-control' required>");
		out.println("</div><div class='col-md-4'><button class='btn btn-primary'>Add Contacts</button></div></form></div>");
		out.println("<hr><div class='scrollbars'>");		
		out.println("<table class='table table-striped'><thead><tr><form action='./addcontacts'><div class='row but'><div class='col-md-2'><img src='inbox.png' width='40' height='40'/></div>");
	   out.println("<div class='col-md-4'><center><button type='submit' name='action1' value='id2' class='btn btn-lg but'>Inbox</button></div></div></tr><br>");

	  out.println("<tr><div class='row but'><div class='col-md-2'><img src='outbox.png' width='40' height='40'/></div><div class='col-md-4'><center><button type='submit' name='action2' value='id3' class='btn btn-lg but'>Outbox</button></div></div></tr></form><br>");

out.println("<tr><form action='./addcontacts'><div class='row but'><div class='col-md-2'><img src='compose.png' width='40' height='40'/></div><div class='col-md-4'><button href='#' class='btn btn-lg but' data-toggle='modal' data-target='#myModal'>Compose</button>");
out.println("<div id='myModal' class='modal fade' role='dialog'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'>");
        out.println("<h4 class='modal-title'>Compose Window</h4></div><div class='modal-body'><p>To: <div class='form-group'><input type='text' placeholder='Enter email' name='to' class='form-control'></div>");
        out.println("Subject: <div class='form-group'><input type='text' class='form-control' name='sub' placeholder='optional'></div><textarea class='form-control'name='msg' placeholder='Type your message here' style='height:400px; width:570px;'></textarea><br><button type='submit' name='action' value='id1' align='right' class='btn btn-primary'>Send Message</button>");
out.println("</p></div></div></div></div>");
        out.println("</div></form></div><hr><i><h3 style='font-family:'my-font';color:#5D6D7E'>Contacts</h3><br>");
      

	    PreparedStatement ps4=cn.prepareStatement("select * from "+email+" ");
				ResultSet rs=ps4.executeQuery();

				rs.next();
				while(rs.next())
				{
					out.println("<tr><div class='row'><div class='col-md-3'><img class='cir' src='default.png' width='50' height='50'/></div>");
	   				out.println("<div class='col-md-9'><h5>"+rs.getString(1)+"</h5></div></div></tr><br>");
				}
				out.println("</thead></table></div></div>");


	    out.println("<div class='col-md-9'><div class='row' style='background-color:#7FB3D5'><div class='col-md-1'><br><img class='cir' src='default.png' width='70' height='70'/></div>");
		out.println("<div class='col-md-9'><b><br><br><h4>"+e+"</h4></b></div><div class='dropdown col-md-1'><br><a href='home.html'><button class='btn btn-warning btn-lg'>Logout</button></a>");
			out.println("</div></div><br><hr><div class='row'><div class='col-md-2'><h3 style='font-family:'my-font';'><b>Inbox</b></h3></div>");
			out.println("<div class='col-md-10'></div>");
						out.println("</div><div class='row' style='background-color:#A9CCE3;font-family:'my-font';'><table class='table table-hover but1'>");
							out.println("<thead></thead><tbody></tbody></table></div></div></body></html>");
				

				//out.println("User Registered");*/
			}
			else
			{
				out.println("Error in Registering...Please try again!!");
			}
			}
			else{
				out.println("<div class='danger' style='margin-bottom: 30px;padding: 16px 20px;'><p>Error in Inserting data or Passwords doesn't match.</p></div>");
				RequestDispatcher rr=req.getRequestDispatcher("register.html");
				rr.include(req,res);
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}