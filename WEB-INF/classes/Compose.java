import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import oracle.jdbc.OraclePreparedStatement;
public class Compose extends HttpServlet{
	Connection cn;
	static PrintWriter out;
	static String e;
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
			String to=req.getParameter("to");
			String sub=req.getParameter("sub");
			String msg=req.getParameter("msg");
			boolean flag=false,flag1=false;
			int i=to.indexOf('@');
				int j=to.indexOf('.');
				String tocon=to.substring(0,i)+""+to.substring(i+1,j)+""+to.substring(j+1,to.length());
    	   String e=(String)session.getAttribute("mail");
    	   String email=(String)session.getAttribute("email");
    	   	
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		    out.println(email+ " " +e+" "+to+" "+tocon);
			
	    	PreparedStatement p=cn.prepareStatement("select * from register where email=?");
               p.setString(1,to);
			  ResultSet r=p.executeQuery();
			   if(r.next())
			   {
                 	out.println("\nemail is registered");
               PreparedStatement ps=cn.prepareStatement("select * from "+e+" where emails=?");
               ps.setString(1,to);
			  ResultSet rs=ps.executeQuery();
			//	out.println(rs.)
			 // out.println(rs.next());
			   if(rs.next())
			   {
                 out.println("that email is in your contacts");
               }
               else{
               	  PreparedStatement ps1=cn.prepareStatement("insert into "+e+" values(?)");
               	  ps1.setString(1,to);
               	  int rs1=ps1.executeUpdate();
              // 	  rs1.next();
               	  if(rs1>0){
               	  		out.println("Inserted the contact into your contacts");
               	  }
               	  else{
               	  	out.println("error in inserting the contact in your table");
               	  }
               }
               String nn=e+"outbox";
               out.println("mail name"+nn);
               PreparedStatement ps2=cn.prepareStatement("insert into "+nn+" values(?,?)");
               String inmsg=sub+" "+msg;
               /* OraclePreparedStatement st = (OraclePreparedStatement) cn.prepareStatement(sql1);
			   //where connection is your connection with Oracle 
st.setStringForClob(1,inmsg);
st.setString(2,"ganesh"); ///*String variable which contains the CLOB string*/ 
/*st.executeUpdate();
                 PreparedStatement ps3=cn.prepareStatement("select * from test where email=?");
               ps3.setString(1,"ganesh");
			  ResultSet rs3=ps3.executeQuery();
			  while(rs3.next()){
			  	Clob clob = rs3.getClob("mails");
out.println(clob.getSubString(1, (int) clob.length()));
			  	out.println("  "+rs3.getString(2));
			  }*/
             //  String inmsg="getttt";
              ps2.setString(1,inmsg);
               
               ps2.setString(2,to);
			  int rs2=ps2.executeUpdate();
			  //rs2.next();
			  if(rs2>0){
                  flag=true;
                  out.println("email is sent");
                  String sql1="insert into test values(?,?)";
			 
			  }
			  else{
			  	out.println("error in inserting the email into your table");
			  }


			   PreparedStatement ps3=cn.prepareStatement("select * from "+tocon+" where emails=?");
               ps3.setString(1,email);
			  ResultSet rs3=ps3.executeQuery();
				//rs3.next();
			   if(rs3.next())
			   {
                 out.println("the email is present in your friends contact");
               }
               else{
               	  PreparedStatement ps4=cn.prepareStatement("insert into "+tocon+" values(?)");
               	  ps4.setString(1,email);
               	  int rs4=ps4.executeUpdate();
               	  //rs4.next();
               	  if(rs4>0){
                       out.println("contact is inserted into your friends table");
               	  }
               	  else{
               	  	out.println("error in inserting the contact in your friend's table");
               	  }
               }
               String nn1=tocon+"inbox";
               PreparedStatement ps5=cn.prepareStatement("insert into "+nn1+" values(?,?)");
               String outmsg=sub+" "+msg;              
               ps5.setString(1,outmsg);
               
               ps5.setString(2,email);
			  int rs5=ps5.executeUpdate();
			  //rs5.next();
			  if(rs5>0){
			  	out.println("email is received");
                  flag1=true;
			  }
			  else{
			  	out.println("error in inserting the email into your friend's table");
			  }
			  if(flag==true && flag1==true)
              {
              	RequestDispatcher rd=req.getRequestDispatcher("display");
					rd.forward(req,res);
              }   
            }
            else{
            	out.println("not a registered email");
            }
         
            
        }
        catch(Exception e12){
            	//rs.redirect("   ");
            }
        }
    }
