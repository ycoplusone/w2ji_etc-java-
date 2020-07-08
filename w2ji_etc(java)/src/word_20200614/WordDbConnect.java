package word_20200614;

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
	
	public static WMember getLoginSucc(String id , String ps) {
		System.out.println("getLoginSucc : "+id+" : "+ps);
		WMember wm = new WMember();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = "select * from w_member where m_id = '"+id+"' and m_pw = '"+ps+"'";
		
		
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
	
	//전체 단어리스트 출력
	public static ArrayList<String[]> getWordAllList( WMember wm ){
		ArrayList al = new ArrayList();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = "select seq , w_spell , w_mean , type  from w_word where m_id = '"+wm.getMId()+"' order by seq desc" ;
		
		
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
	
	public static void setWord(String id , String word , String mean , String type){
	 try{
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection con = getMysql();
		 Statement st=con.createStatement();
		 String strSql = "INSERT INTO w_word( m_id, w_spell, w_mean, wr_count, type, c_date) VALUES('"+id+"', '"+word+"', '"+mean+"', 0, '"+type+"', now())";
		 st.executeUpdate(strSql);
		 st.close();
		 con.close();
	 }catch(Exception e){
		 System.out.println("SQLException: "+e.getMessage());
	 }
		
	}
	
	public static void setWordUpdate(String seq , String word , String mean , String type ){
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql = "UPDATE w_word     SET  w_spell='"+word+"' , w_mean='"+mean+"' , type='"+type+"'    WHERE  seq = '"+seq+"' ";
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }
	}
	
	public static void setWordDelete(String seq){
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql = "DELETE FROM w_word WHERE  seq = '"+seq+"' ";
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }
	}
	
	
	//단어 검색
	public static ArrayList<String[]> getWordSearch( WMember wm , String spell , String mean, String type){
		ArrayList al = new ArrayList();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			//String strSql = "select w_spell , w_mean from w_word where m_id = '"+wm.getMId()+"' " ;
			String strSql = " select w_spell , w_mean "; 
			strSql += " from w_word where m_id = '"+wm.getMId()+"' ";
			strSql += " and lower(w_spell) like  case when replace('"+spell+"',' ','') = '' then '%' else concat('%',lower('"+spell+"'),'%') end ";
			strSql += " and lower(w_mean) like  case when replace('"+mean+"',' ','') = '' then '%' else concat('%',lower('"+mean+"'),'%') end ";
			strSql += " and lower(type) like  case when replace('"+type+"',' ','') = '' then '%' else concat('%',lower('"+type+"'),'%') end ";
		
		
			ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다.
			ResultSetMetaData rmdata = result.getMetaData();
			int cols = rmdata.getColumnCount();
			
			while(result.next()){
				String str[] = new String[4];
				str[0] = result.getString(1);
				str[1] = result.getString(2);
				al.add(str);				
			}	
	
			st.close();
			con.close();
	
	   }catch(Exception e){
		   System.out.println("SQLException: "+e.getMessage());
	   }
		return al;
		
	}	
	
	
	//단어 검색
	public static String getWordCount( WMember wm){
		String cnt = "0";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = "select count(1) from w_word where m_id = '"+wm.getMId()+"' " ;

		
		
			ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다.
			ResultSetMetaData rmdata = result.getMetaData();
					
			result.next();				
			cnt = result.getString(1);
				
	
			st.close();
			con.close();
	
	   }catch(Exception e){
		   System.out.println("SQLException: "+e.getMessage());
	   }
		return cnt;
		
	}		
	
	//사용자 구분 목록
	public static ArrayList<String[]> getWordType( WMember wm){
		ArrayList al = new ArrayList();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = "select distinct type from w_word where m_id = '"+wm.getMId()+"' " ;

		
		
			ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다.
			ResultSetMetaData rmdata = result.getMetaData();
					
			while(result.next()){
				String str[] = new String[1];
				str[0] = result.getString(1);				
				al.add(str);				
			}	
				
	
			st.close();
			con.close();
	
	   }catch(Exception e){
		   System.out.println("SQLException: "+e.getMessage());
	   }
		return al;
		
	}		
	
	
	//시험 문제 출제
	public static String[] getWordExam( WMember wm , String type , String opt1 , String opt2){
		//ArrayList al = new ArrayList();
		String str[] = new String[3];
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = getMysql();			
			Statement st=con.createStatement();			   
			String strSql = " select seq , w_spell , w_mean from w_word " ;
			strSql 		 += " where m_id = '"+wm.getMId()+"' ";
			if( opt1.equals("F")&&opt2.equals("T") ) {
				strSql 		 += "  and wr_count >= 3 ";
			}else if( opt1.equals("F")&&opt2.equals("F") ) {
				strSql += " and lower(type) like  case when replace('"+type+"',' ','') = '' then '%' else concat('%',lower('"+type+"'),'%') end ";
			}
			strSql += " order by rand()  limit 1 ";

		
		
			ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다.
			ResultSetMetaData rmdata = result.getMetaData();
					
			result.next();
			str[0] = result.getString(1);
			str[1] = result.getString(2);
			str[2] = result.getString(3);
			
			st.close();
			con.close();
	
	   }catch(Exception e){
		   System.out.println("SQLException: "+e.getMessage());
	   }
		return str;
		
	}	
	
	public static void upDateExam(String seq , String chk){
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 Connection con = getMysql();
			 Statement st=con.createStatement();
			 String strSql = "UPDATE w_word SET  wr_count = wr_count "+chk+" 1 WHERE seq ='"+seq+"'";
			 st.executeUpdate(strSql);
			 st.close();
			 con.close();
		 }catch(Exception e){
			 System.out.println("SQLException: "+e.getMessage());
		 }
			
		}
	

}

