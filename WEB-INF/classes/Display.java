import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
public class Display extends HttpServlet{
	Connection cn;
	static PrintWriter out;
	static String e;
	static String email;
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
    	PrintWriter out=res.getWriter();
    	HttpSession session=req.getSession(false);
    //	HttpSession session1=req.getSession(false);
		email=(String)session.getAttribute("uname");
		e=(String)session.getAttribute("tabname");

		HttpSession session1=req.getSession();
		session1.setAttribute("mail",e);
		session1.setAttribute("email",email);
			res.setContentType("text/html");
		//	out.println(email+ "" +e);
						out.println("<!doctype html><html lang='en-US'><head><title>Osmania Email</title><link rel='icon'type='image/ico' href=''><meta charset='utf-8'>");
  out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
  out.println("<link rel='stylesheet' href='./css/bootstrap.min.css'><script src='./css/jquery.min.js'></script>");
  out.println("<script src='./css/bootstrap.min.js'></script><link rel='stylesheet' type='text/css'  media='only screen and (color)' href='index.css'>"); 
	    out.println("<script src='https://www.google.com/recaptcha/api.js'></script></head><body><div class='container-fluid'><div class='row' style='background-color:#7FB3D5'>");
		out.println("<div class='col-md-3'><br><br><form action='./addcontacts'><div class='row'><div class='form-group col-md-7'><input type='text' placeholder='Enter email' name='e1' class='form-control' required>");
		out.println("</div><div class='col-md-4'><button class='btn btn-primary'>Add Contacts</button></div></div></form>");
		out.println("<hr><div class='scrollbars'>");		
		out.println("<table class='table table-striped'><thead><tr><form action='./addcontacts'><div class='row but'><div class='col-md-2'><img src='inbox.png' width='40' height='40'/></div>");
	   out.println("<div class='col-md-4'><center><button type='submit' name='action1' value='id2' class='btn btn-lg but'>Inbox</button></div></div></form></tr><br>");

	  out.println("<tr><form action='./addcontacts'><div class='row but'><div class='col-md-2'><img src='outbox.png' width='40' height='40'/></div><div class='col-md-4'><center><button type='submit' name='action2' value='id3' class='btn btn-lg but'>Outbox</button></div></div></form></tr><br>");

out.println("<tr><div class='row but'><div class='col-md-2'><img src='compose.png' width='40' height='40'/></div><div class='col-md-4'><button href='#' class='btn btn-lg but' data-toggle='modal' data-target='#myModal'>Compose</button>");
out.println("<div id='myModal' class='modal fade' role='dialog'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'>");
        out.println("<h4 class='modal-title'>Compose Window</h4></div><div class='modal-body'><p><form action='./addcontacts'>To: <div class='form-group'><input type='text' placeholder='Enter email' name='to' class='form-control'></div>");
        out.println("Subject: <div class='form-group'><input type='text' class='form-control' name='sub' placeholder='optional'></div><textarea class='form-control'name='msg' placeholder='Type your message here' style='height:400px; width:570px;'></textarea><br><button type='submit' name='action' value='id1' align='right' class='btn btn-primary'>Send Message</button></form>");
out.println("</p></div></div></div></div>");
        out.println("</div></form></div><hr><i><h3 style='font-family:'my-font';color:#5D6D7E'>Contacts</h3><br>");
       /* contacts(email,e);
        inbox(email,e);
        outbox(email,e);*/
        PreparedStatement ps1=cn.prepareStatement("select * from "+e+" ");
				ResultSet rs1=ps1.executeQuery();

				//rs.next();
				while(rs1.next())
				{
					out.println("<tr><div class='row'><div class='col-md-3'><img class='cir' src='default.png' width='50' height='50'/></div>");
	   				out.println("<div class='col-md-9'><h5>"+rs1.getString(1)+"</h5></div></div></tr><br>");
				}
				out.println("</thead></table></div></div>");
				PreparedStatement ps2=cn.prepareStatement("select * from "+e+"inbox");
				ResultSet rs2=ps2.executeQuery();
					
				//rs.next();
			out.println("<div class='col-md-9'><div class='row' style='background-color:#7FB3D5'><div class='col-md-1'><br><img class='cir' src='default.png' width='70' height='70'/></div>");
			out.println("<div class='col-md-9'><b><br><br><h4>"+email+"</h4></b></div><div class='col-md-1'><br><a href='home.html'><button class='btn btn-warning btn-lg'>Logout</button></a>");
			out.println("</div></div><hr><div class='row'><div class='col-md-2'><h3 style='font-family:'my-font';'><b>Inbox</b></h3></div>");
			out.println("<div class='col-md-8'></div>");
						out.println("</div><div class='row' style='background-color:#A9CCE3;font-family:'my-font';'>");
						out.println("<table class='table table-hover but1'><tbody>");
				while(rs2.next())
				{
						String msg= rs2.getString(1);
                        int i= msg.indexOf(' ');
						out.println("<tr><div class='row but'><td><img src='images1.png' width='30' height='30'> From: "+rs2.getString(2)+"<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbspSubject: "+(rs2.getString(1).substring(0,i-1))+"<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbspEmail: "+(rs2.getString(1).substring(i+1,rs2.getString(1).length()))+"</td>");
						out.println("</div></tr><br>");
				}
				out.println("</tbody></table></div>");
				/*PreparedStatement ps3=cn.prepareStatement("select * from "+e+"outbox");
				ResultSet rs3=ps3.executeQuery();

				//rs.next();
				out.println("<table class='table table-hover'><tbody>");
			
				while(rs3.next())
				{
						out.println("<tr><td data-toggle='modal' data-target='#myModal1'><div class='checkbox'><label><input type='checkbox' value=''>"+rs3.getString(1)+"</label></div></td>");
					out.println("<div id='myModal1' class='modal fade' role='dialog'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'><h4 class='modal-title'>Inbox window</h4>");
					out.println("</div><div class='modal-body'><p></p></div><div class='modal-footer'><button type='button' class='btn btn-primary'>Close</button></div></div></div></div>");		
					out.println("</tr><br>");
				}
        							out.println("</tbody></table>");*/out.println("</div></div></body></html>");
						
    }
    catch(Exception e1)
    {
    	e1.printStackTrace();
    }
}            


    public void contacts(String email,String e){
    	try{
            PreparedStatement ps4=cn.prepareStatement("select * from "+e+" ");
				ResultSet rs=ps4.executeQuery();

				//rs.next();
				while(rs.next())
				{
					out.println("<table class='table table-hover'><thead><tr><div class='row'><div class='col-md-3'><img class='cir' src='default.png' width='50' height='50'/></div>");
	   				out.println("<div class='col-md-9'><h5>"+rs.getString(1)+"</h5></div></div></tr></thead></table>");
				}
    	}
    	catch(Exception e1){
             e1.printStackTrace();
    	}
    	 

    }
    public void inbox(String email,String e){
    	      try{
                  PreparedStatement ps4=cn.prepareStatement("select * from "+e+"inbox");
				ResultSet rs=ps4.executeQuery();

				//rs.next();
				
				while(rs.next())
				{
					out.println("<table class='table table-hover'><thead><tr><div class='row'><div class='col-md-3'><img class='cir' src='default.png' width='50' height='50'/></div>");
	   				out.println("<div class='col-md-9'><h5>"+rs.getString(1)+"</h5></div></div></tr></thead></table>");
				}
			
    	      }
    	      catch(Exception e1){
    	      	e1.printStackTrace();
    	      }

    }
    public void outbox(String email,String e){
    	 try{
    	 	PreparedStatement ps4=cn.prepareStatement("select * from "+e+"outbox");
				ResultSet rs=ps4.executeQuery();

				//rs.next();
			
				while(rs.next())
				{
					out.println("<table class='table table-hover'><thead><tr><div class='row'><div class='col-md-3'><img class='cir' src='default.png' width='50' height='50'/></div>");
	   				out.println("<div class='col-md-9'><h5>"+rs.getString(1)+"</h5></div></div></tr></thead></table>");
				}
			
    	 }
    	 catch(Exception e1){
    	 	e1.printStackTrace();
    	 }
    }
}

			

	   

	    