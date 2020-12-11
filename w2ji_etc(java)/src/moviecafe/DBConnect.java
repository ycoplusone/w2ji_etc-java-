package moviecafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;



public class DBConnect {
	//db접속자
	public static Connection getMysql() {
		Connection con = null;
		try {
			String id = "root";	//db 아이디
			String ps = "java2020";		//db 패스워드
			String db = "MovieCafe";	//db 명
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db+"?user="+id+"&password="+ps);
			String url = "jdbc:mysql://localhost:3306/"+db+"?serverTimezone=UTC";
			con = DriverManager.getConnection(url , id , ps);
			/*
			             Class.forName("com.mysql.cj.jdbc.Driver");
 		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			 * */
			
		}catch (Exception e) {
			System.out.println("class MysqlConnecter error"+e.toString());
		}
		return con;		
	}
	
	// 로그인 검사
	public static boolean getLogin(String id , String ps) {
		boolean temp = false;		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = "select * from usermanagement where user_type = '"+id+"' and password = '"+ps+"'";
			ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다.
			ResultSetMetaData rmdata = result.getMetaData();
			int cols = rmdata.getColumnCount();
			temp = result.next();
			st.close();
			con.close();	
	   }catch(Exception e){
		   System.out.println("SQLException: "+e.getMessage());
	   }
		return temp;
	}	
	//비밀번호 변경
	public static boolean setChangePassword(String user_type , String now_pass , String new_pass ) {
		boolean temp = false;
		 try{
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql ="UPDATE usermanagement     SET  password='"+new_pass+"'    WHERE user_type='"+user_type+"' and password ='"+now_pass+"'";
			 int a = st.executeUpdate(strSql);
			 temp = a ==1?true:false;			 
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }
		 return temp;		
	}
	
	// 상품등록
	public static void setProdAdd(String prod , String price ) {
		 try{
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql ="INSERT INTO productmanagement(prod_nm, price, amount, user_yn)  VALUES('"+prod+"', "+price+", 0, 'Y')";
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }
		
	}
	
	//상품 리스트 
	public static ArrayList<String[]> getProdList(){
		ArrayList al = new ArrayList();
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = "select prod_cd , prod_nm ,  amount , price  from productmanagement where user_yn = 'Y' " ;		
		
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
	
	// 상품 삭제
	public static void delProd(String prod_cd) {
		 try{
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql ="UPDATE productmanagement     SET  user_yn='N'    WHERE  prod_cd = '"+prod_cd+"'";
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }		
	}
	
	public static void updateProd(String prod_cd, int in,int out) {
		 try{
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql ="UPDATE productmanagement     SET  amount = amount + ("+(in-out)+")    WHERE prod_cd='"+prod_cd+"' ";
			 System.out.println("updateProd : "+strSql);
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }		
	}
	
	//상품 입출고 및 판매 기록
	public static void setProdHist(String prod_cd, int in,int out ,int price, String type) {	//type (m:입풀고 , s : 판매)
		 try{
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql ="INSERT INTO product_hist(prod_cd, type, out_amount, in_amount, price, reg_date) VALUES( '"+prod_cd+"', '"+type+"', "+out+", "+in+", "+price+", now())";
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }		
	}
	
	
	//판매 리스트 
	public static ArrayList<String[]> getSaleList(){
			ArrayList al = new ArrayList();
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = getMysql();			
				Statement st=con.createStatement();			   
				String strSql = "" ;
				strSql += "select a.prod_cd , a.prod_nm , a.price "; 
				strSql += ", sum( case when b.out_amount is null then 0 else b.out_amount end) out_amount "; 
				strSql += ", sum( case when b.out_amount is null or b.price = 0 then 0 else b.out_amount * b.price end) sale ";
				strSql += "from productmanagement a ";
				strSql += "left outer join product_hist b on ( a.prod_cd = b.prod_cd and b.type = 'S' ) ";
				strSql += "where a.user_yn = 'Y' ";
				strSql += "group by a.prod_cd , a.prod_nm , a.price ";				
			
				ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다.
				ResultSetMetaData rmdata = result.getMetaData();
				int cols = rmdata.getColumnCount();
				
				while(result.next()){
					String str[] = new String[5];
					str[0] = result.getString(1);
					str[1] = result.getString(2);
					str[2] = result.getString(3);
					str[3] = result.getString(4);
					str[4] = result.getString(5);
					al.add(str);				
				}
				st.close();
				con.close();
		
		   }catch(Exception e){
			   System.out.println("SQLException: "+e.getMessage());
		   }
			return al;		
		}
	
	//판매 총계
	public static String[] getSaleTotal(){
		String str[] = new String[2];
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = "" ;
			strSql += "select  "; 
			strSql += " sum( case when b.out_amount is null then 0 else b.out_amount end) out_amount "; 
			strSql += ", sum( case when b.out_amount is null or b.price = 0 then 0 else b.out_amount * b.price end) sale ";
			strSql += "from productmanagement a ";
			strSql += "left outer join product_hist b on ( a.prod_cd = b.prod_cd and b.type = 'S' ) ";
			strSql += "where a.user_yn = 'Y' ";
		
			ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다.
			ResultSetMetaData rmdata = result.getMetaData();
			int cols = rmdata.getColumnCount();
			
			while(result.next()){
				
				str[0] = result.getString(1);
				str[1] = result.getString(2);
								
			}
			st.close();
			con.close();
	
	   }catch(Exception e){
		   System.out.println("SQLException: "+e.getMessage());
	   }
		return str;		
	}

	
	

}
