package booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class WordDbConnect {
	
	
	public static Connection getMysql() {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/com_mall?user=com&password=com01");
		}catch (Exception e) {
			System.out.println("class MysqlConnecter error"+e.toString());
		}
		return con;
		
	}
	
	public static void setMember(String id , String ps , String email , String name) {
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql ="INSERT INTO w_member( m_id, m_pw, m_name, m_email, c_date) VALUES('"+id+"', '"+ps+"', '"+name+"' , '"+email+"', now())";
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }
		
	}
	
	public static WMember getLoginSucc(String id , String ps) {
		System.out.println("getLoginSucc : "+id+" : "+ps);
		WMember wm = new WMember();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = "select * from w_member where m_id = '"+id+"' and m_pw like '"+ps+"'";
		
		
			ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다.
			ResultSetMetaData rmdata = result.getMetaData();
			int cols = rmdata.getColumnCount();
			result.next();
			wm.setSeq( result.getString(1) );
			wm.setMId( result.getString(2) );
			wm.setMPw( result.getString(3) );
			wm.setMName(  result.getString(4) );
			wm.setMEmail( result.getString(5) );
			wm.setCDate( result.getString(6) );
			
			
			st.close();
			con.close();
	
	   }catch(Exception e){
		   System.out.println("SQLException: "+e.getMessage());
	   }		
		
		return wm;
	}
	
	public static ArrayList<String[]> getBookingList(){
		ArrayList al = new ArrayList();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = "select a.seq , a.m_id , b.m_name , a.m_sit_no from w_booking a join w_member b on ( a.m_id = b.m_id ) " ;
		
		
			ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다.
			ResultSetMetaData rmdata = result.getMetaData();
			int cols = rmdata.getColumnCount();
			
			while(result.next()){
				String str[] = new String[4];
				str[0] = result.getString(1);
				str[1] = result.getString(2);
				str[2] = result.getString(3);
				str[3] = result.getString(4);
				al.add(str);				
			}	
	
			st.close();
			con.close();
	
	   }catch(Exception e){
		   System.out.println("SQLException: "+e.getMessage());
	   }
		return al;		
	}		
	
	public static void setBooking(String id , String sit ) {
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql ="INSERT INTO  w_booking( m_id, m_sit_no, c_date)  VALUES('"+id+"', '"+sit+"' , now())";
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }
		
	}	
	
	public static void setUnBooking(String seq){
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql = "DELETE FROM w_booking WHERE  m_sit_no = '"+seq+"' ";
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }
	}	
	
	
	
	

}

