package moviecafe;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	public static Connection getMysql() {
		Connection con = null;
		try {
			String id = "movicecafe";	//db 아이디
			String ps = "java2020";		//db 패스워드
			String db = "movicecafe";	//db 명
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db+"?user="+id+"&password="+ps);
		}catch (Exception e) {
			System.out.println("class MysqlConnecter error"+e.toString());
		}
		return con;		
	}
	
	

}
