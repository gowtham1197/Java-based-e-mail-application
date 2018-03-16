import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
public class AddContacts extends HttpServlet
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
		try
		{
		String action=req.getParameter("action");
		String action1=req.getParameter("action1");
		String action2=req.getParameter("action2");
		HttpSession session=req.getSession(false);
		String email=(String)session.getAttribute("mail");
		String e=(String)session.getAttribute("email");
		PrintWriter out = res.getWriter();
		if("id1".equals(action)){
			RequestDispatcher rd1=req.getRequestDispatcher("compose");
			rd1.forward(req,res);
		}
		else if("id2".equals(action1)){
			out.println("<!doctype html><html lang='en-US'><head><title>Osmania Email</title><link rel='icon'type='image/ico' href=''><meta charset='utf-8'>");
  out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
  out.println("<link rel='stylesheet' href='./css/bootstrap.min.css'><script src='./css/jquery.min.js'></script>");
  out.println("<script src='./css/bootstrap.min.js'></script><link rel='stylesheet' type='text/css'  media='only screen and (color)' href='index.css'>"); 
	    out.println("<script src='https://www.google.com/recaptcha/api.js'></script></head><body><div class='container-fluid'><div class='row' style='background-color:#7FB3D5'>");
		out.println("<div class='col-md-3'><br><br><form action='./addcontacts '><div class='row'><div class='form-group col-md-7'><input type='text' placeholder='Enter email' name='e1' class='form-control' required>");
		out.println("</div><div class='col-md-4'><button class='btn btn-primary'>Add Contacts</button></div></div></form>");
		out.println("<hr><div class='scrollbars'>");		
		out.println("<table class='table table-striped'><thead><tr><div class='row but'><div class='col-md-2'><img src='inbox.png' width='40' height='40'/></div>");
	   out.println("<div class='col-md-4'><center><button href='#' name='action1' value='id2' class='btn btn-lg but'>Inbox</button></div></div></tr><br>");

	  out.println("<tr><div class='row but'><div class='col-md-2'><img src='outbox.png' width='40' height='40'/></div><div class='col-md-4'><center><button href='#' name='action2' value='id3' class='btn btn-lg but'>Outbox</button></div></div></tr><br>");

out.println("<tr><div class='row but'><div class='col-md-2'><img src='compose.png' width='40' height='40'/></div><div class='col-md-4'><button href='#' class='btn btn-lg but' data-toggle='modal' data-target='#myModal'>Compose</button>");
out.println("<div id='myModal' class='modal fade' role='dialog'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'>");
        out.println("<h4 class='modal-title'>Compose Window</h4></div><div class='modal-body'><p><form action='./addcontacts'>To: <div class='form-group'><input type='text' placeholder='Enter email' name='to' class='form-control'></div>");
        out.println("Subject: <div class='form-group'><input type='text' class='form-control' name='sub' placeholder='optional'></div><textarea class='form-control'name='msg' placeholder='Type your message here' style='height:400px; width:570px;'></textarea><br><button type='submit' name='action' value='id1' align='right' class='btn btn-primary'>Send Message</button></form>");
out.println("</p></div></div></div></div>");

	out.println("</div></div></form><hr><i><h3 style='font-family:'my-font';color:#5D6D7E'>Contacts</h3><br>");
       /* contacts(email,e);
        inbox(email,e);
        outbox(email,e);*/
        PreparedStatement ps1=cn.prepareStatement("select * from "+email);
				ResultSet rs1=ps1.executeQuery();
				out.println("<table class='table'><thead>");

				//rs.next();
				while(rs1.next())
				{
					out.println("<tr><div class='row'><div class='col-md-3'><img class='cir' src='default.png' width='50' height='50'/></div>");
	   				out.println("<div class='col-md-9'><h5>"+rs1.getString(1)+"</h5></div></div></tr><br>");
				}
				out.println("</thead></table></div></div>");

        
        out.println("<div class='col-md-9'><div class='row' style='background-color:#7FB3D5'><div class='col-md-1'><br><img class='cir' src='default.png' width='70' height='70'/></div>");
			out.println("<div class='col-md-9'><b><br><br><h4>"+e+"</h4></b></div><div class='col-md-1'><br><a href='home.html'><button class='btn btn-warning btn-lg'>Logout</button></a>");
			out.println("</div></div><hr><div class='row'><div class='col-md-2'><h3 style='font-family:'my-font';'><b>Inbox</b></h3></div>");
			out.println("<div class='col-md-10'></div>");
						out.println("</div><div class='row' style='background-color:#A9CCE3;font-family:'my-font';'>");
						
			   String e12=email+"inbox";
               PreparedStatement ps3=cn.prepareStatement("select * from "+e12);
				ResultSet rs3=ps3.executeQuery();

				//rs.next();
				out.println("<table class='table table-hover'><tbody>");
				String msg="";
						int i=0;
			
				while(rs3.next())
				{
						msg= rs3.getString(1);
                        i= msg.indexOf(' ');
						out.println("<tr><div class='row but'><td><img src='images1.png' width='30' height='30'> From: "+rs3.getString(2)+"<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbspSubject: "+(rs3.getString(1).substring(0,i-1))+"<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbspEmail: "+(rs3.getString(1).substring(i+1,rs3.getString(1).length()))+"</td>");
						out.println("</div></tr><br>");
				}
        							out.println("</tbody></table></div></div></body></html>");
		
		}
		else if("id3".equals(action2))
		{
			out.println("<!doctype html><html lang='en-US'><head><title>Osmania Email</title><link rel='icon'type='image/ico' href=''><meta charset='utf-8'>");
  out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
  out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'><script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
  out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script><link rel='stylesheet' type='text/css'  media='only screen and (color)' href='index.css'>"); 
	    out.println("<script src='https://www.google.com/recaptcha/api.js'></script></head><body><div class='container-fluid'><div class='row' style='background-color:#7FB3D5'>");
		out.println("<div class='col-md-3'><br><br><form action='./addcontacts '><div class='row'><div class='form-group col-md-7'><input type='text' placeholder='Enter email' name='e1' class='form-control' required>");
		out.println("</div><div class='col-md-4'><button class='btn btn-primary'>Add Contacts</button></div></div></form>");
		out.println("<hr><div class='scrollbars'>");		
		out.println("<table class='table table-striped'><thead><tr><div class='row but'><div class='col-md-2'><img src='inbox.png' width='40' height='40'/></div>");
	   out.println("<div class='col-md-4'><center><button type='submit' name='action1' value='id2' class='btn btn-lg but'>Inbox</button></div></div></tr><br>");

	  out.println("<tr><div class='row but'><div class='col-md-2'><img src='outbox.png' width='40' height='40'/></div><div class='col-md-4'><center><button type='submit' name='action2' value='id3' class='btn btn-lg but'>Outbox</button></div></div></tr><br>");

out.println("<tr><div class='row but'><div class='col-md-2'><img src='compose.png' width='40' height='40'/></div><div class='col-md-4'><button href='#' class='btn btn-lg but' data-toggle='modal' data-target='#myModal'>Compose</button>");
out.println("<div id='myModal' class='modal fade' role='dialog'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'>");
        out.println("<h4 class='modal-title'>Compose Window</h4></div><div class='modal-body'><p><form action='./addcontacts'>To: <div class='form-group'><input type='text' placeholder='Enter email' name='to' class='form-control'></div>");
        out.println("Subject: <div class='form-group'><input type='text' class='form-control' name='sub' placeholder='optional'></div><textarea class='form-control'name='msg' placeholder='Type your message here' style='height:400px; width:570px;'></textarea><br><button type='submit' name='action' value='id1' align='right' class='btn btn-primary'>Send Message</button></form>");
out.println("</p></div></div></div></div>");

	out.println("</div></div></form><hr><i><h3 style='font-family:'my-font';color:#5D6D7E'>Contacts</h3><br>");
       /* contacts(email,e);
        inbox(email,e);
        outbox(email,e);*/
        PreparedStatement ps1=cn.prepareStatement("select * from "+email);
				ResultSet rs1=ps1.executeQuery();
				out.println("<table class='table'><thead>");

				//rs.next();
				while(rs1.next())
				{
					out.println("<tr><div class='row'><div class='col-md-3'><img class='cir' src='default.png' width='50' height='50'/></div>");
	   				out.println("<div class='col-md-9'><h5>"+rs1.getString(1)+"</h5></div></div></tr><br>");
				}
				out.println("</thead></table></div></div>");

        
        out.println("<div class='col-md-9'><div class='row' style='background-color:#7FB3D5'><div class='col-md-1'><br><img class='cir' src='default.png' width='70' height='70'/></div>");
			out.println("<div class='col-md-9'><b><br><br><h4>"+e+"</h4></b></div><div class='col-md-1'><br><a href='home.html'><button class='btn btn-warning btn-lg'>Logout</button></a>");
			out.println("</div></div><hr><div class='row'><div class='col-md-2'><h3 style='font-family:'my-font';'><b>Outbox</b></h3></div>");
			out.println("<div class='col-md-10'></div>");
						out.println("</div><div class='row' style='background-color:#A9CCE3;font-family:'my-font';'>");
						
			   String e12=email+"outbox";
               PreparedStatement ps3=cn.prepareStatement("select * from "+e12);
				ResultSet rs3=ps3.executeQuery();

				//rs.next();
				out.println("<table class='table table-hover'><tbody>");
				
				while(rs3.next())
				{
						String msg= rs3.getString(1);
                        int i= msg.indexOf(' ');
						out.println("<tr><div class='row but'><td><img src='images1.png' width='30' height='30'> From: "+rs3.getString(2)+"<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbspSubject: "+(rs3.getString(1).substring(0,i-1))+"<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbspEmail: "+(rs3.getString(1).substring(i+1,rs3.getString(1).length()))+"</td>");
						out.println("</div></tr><br>");
				}
        							out.println("</tbody></table></div></div></body></html>");
		
		}

		else{
		res.setContentType("text/html");
		
		String u=req.getParameter("e1");
		//out.println(u+"IN ADDCONTACTS SERVLET");
		HttpSession session1=req.getSession();
		session1.setAttribute("mail",email);
		session1.setAttribute("email",e);
		
		PreparedStatement ps3=cn.prepareStatement("select * from "+email+" where emails=?");
		ps3.setString(1,u);
		ResultSet rs3=ps3.executeQuery();
		if(rs3.next()){
               RequestDispatcher rd=req.getRequestDispatcher("display");
					rd.forward(req,res);
		}
		else{
			 PreparedStatement ps4=cn.prepareStatement("insert into "+email+" values(?)");
			  ps4.setString(1,u);
			int c=ps4.executeUpdate();
				//rs.next();
				if(c>0){
					RequestDispatcher rd=req.getRequestDispatcher("display");
					rd.forward(req,res);
				}
		}
			
		}
	}
        catch(Exception e1){
        	e1.printStackTrace();
        }
    }
}

	