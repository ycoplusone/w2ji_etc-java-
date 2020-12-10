package moviecafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

import monster.RankingPanel;

class MainScreen extends JFrame implements ActionListener {
	JButton MemberMgt, MovieMgt, ProductMgt, SalesMgt, ChangePassword;
	String[] num = {"1", "2", "3", "4", "5", "6", "7", "8"};
	JButton[] room = new JButton[8];
	
	String user_type="";
	
	MainScreen(String title ,String type) {
		this.user_type = type; //관리자 or 일반
		setTitle(title);
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		LineBorder lb = new LineBorder(Color.black);
		
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		top.setBackground(Color.white);
		
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(3,1));
		left.setBackground(Color.white);
		
		JPanel center = new JPanel();
		center.setLayout(null);
		
		MemberMgt = new JButton("회원관리");
		MovieMgt = new JButton("영화관리");
		ProductMgt = new JButton("상품관리");
		SalesMgt = new JButton("매출관리");
		ChangePassword = new JButton("비밀번호변경");
		
		MemberMgt.setBackground(new Color(240,240,240));
		MovieMgt.setBackground(new Color(240,240,240));
		ProductMgt.setBackground(new Color(240,240,240));
		SalesMgt.setBackground(new Color(240,240,240));
		ChangePassword.setBackground(new Color(240,240,240));
		
		top.add(MemberMgt);
		top.add(MovieMgt);
		top.add(ProductMgt);
		top.add(SalesMgt);
		top.add(ChangePassword);
		
		MemberMgt.addActionListener(this);
		MovieMgt.addActionListener(this);
		ProductMgt.addActionListener(this);
		SalesMgt.addActionListener(this);
		ChangePassword.addActionListener(this);
		
		ct.add(top, BorderLayout.NORTH);
		
		JLabel UsageStatus = new JLabel("이용현황");
		UsageStatus.setForeground(new Color(255,202,24));
		left.add(UsageStatus);
		left.setBorder(lb);
		
		ct.add(left, BorderLayout.WEST);
		
		for(int i=0; i<8; i++) {
			room[i] = new JButton(num[i]);
			room[i].setVisible(true);
			room[i].setBackground(Color.white);
			room[i].setFont(new Font("고딕", Font.BOLD | Font.ITALIC, 50));
			room[i].setForeground(new Color(80,124,52));
			center.add(room[i]);
		}
		room[0].setBounds(400,200,260,150);
		room[1].setBounds(700,200,260,150);
		room[2].setBounds(1000,200,260,150);
		room[3].setBounds(1300,200,260,150);
		room[4].setBounds(400,600,260,150);
		room[5].setBounds(700,600,260,150);
		room[6].setBounds(1000,600,260,150);
		room[7].setBounds(1300,600,260,150);
		
		/*
		 * RankingPanel rp =  new RankingPanel();
			rp.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent we) {	//종료됨 이벤트
				}
			});
		 * */
		
		
		center.setBorder(lb);
		ct.add(center, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s= ae.getActionCommand();
		if(s == "회원관리") {
			MemberManagement mm = new MemberManagement("회원관리");
			mm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mm.setSize(1300, 700);
			mm.setLocationRelativeTo(null);
			mm.show();
		}
		else if(s == "영화관리") {
			MovieManagement mv = new MovieManagement();
			mv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mv.setSize(600, 400);
			mv.setLocationRelativeTo(null);
			mv.setResizable(false);
			mv.show();
		}
		else if(s == "상품관리") {
			
			ProductRegist pm = new ProductRegist();
			pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			//pm.setSize(1300, 700);
			pm.setLocationRelativeTo(null);
			pm.show();
			
		}
		else if(s == "매출관리") {
			ProductSale pm = new ProductSale();
			pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			//pm.setSize(1300, 700);
			pm.setLocationRelativeTo(null);
			pm.show();
		}
		else if(s == "비밀번호변경") {
			ChangePassword pm = new ChangePassword( user_type);
			pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			//pm.setSize(1300, 700);
			pm.setLocationRelativeTo(null);
			pm.show();
		}
	}
}

class MemberManagement extends JFrame implements ActionListener {
	MemberManagement (String title) {
		setTitle(title);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
	}
}

class MovieManagement extends JDialog implements ActionListener{
	String listTitle[] = {"영화코드", "영화제목", "상영시간", "장르"};
	String listSeach[] = {"영화제목", "장르"};
	String[] genre = {"장르", "액션", "SF", "코미디", "스릴러", "범죄", "음악", "스포츠", "로멘스", "기타"};
	JLabel jtl;
	JTextField searchIn;
	JTable movieList;
	JPanel p1, p2, searchPane, menuPane;
	JScrollPane scrollPane;
	JComboBox searchGenre;
	JButton btnAdd,btnModify, btnDelete, btnRefresh, btnSearch;
	DefaultTableModel model = new DefaultTableModel(listTitle, 0);
	
	public MovieManagement() {
		setTitle("영화관리");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		
		this.setModal(true);
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p2 = new JPanel();
		menuPane = new JPanel();
		searchPane = new JPanel();
		
		ct.add(p1, BorderLayout.NORTH);
		ct.add(p2, BorderLayout.CENTER);
		
		p1.setLayout(new GridLayout(2, 1));
		
		menuPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		btnAdd = new JButton("등록");
		btnModify = new JButton("수정");
		btnDelete = new JButton("삭제");
		btnRefresh = new JButton("새로고침");
		
		menuPane.add(btnAdd);
		menuPane.add(btnModify);
		menuPane.add(btnDelete);
		menuPane.add(btnRefresh);
		
		btnAdd.addActionListener(this);
		btnModify.addActionListener(this);
		btnDelete.addActionListener(this);
		btnRefresh.addActionListener(this);
		
		p1.add(menuPane);
		
		searchPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		searchGenre = new JComboBox<String>(genre);
		jtl = new JLabel("제목 : ");
		searchIn = new JTextField("", 15);
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(this);
		
		searchPane.add(searchGenre);
		searchPane.add(jtl);
		searchPane.add(searchIn);
		searchPane.add(btnSearch);
		
		p1.add(searchPane);
		
		p2.setLayout(new GridLayout(1, 1, 0, 10));
		movieList = new JTable(model);
		movieList.getColumnModel().getColumn(0).setPreferredWidth(40);
		movieList.getColumnModel().getColumn(1).setPreferredWidth(200);
		movieList.getColumnModel().getColumn(2).setPreferredWidth(50);
		movieList.getColumnModel().getColumn(3).setPreferredWidth(80);
		scrollPane = new JScrollPane(movieList);
		
		try {
			String sql = "select * from movie_info";			
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
				model.addRow(new Object[] {
						rs.getString("code"),
						rs.getString("title"),
						rs.getInt("time"),
						rs.getString("genre")
						});
			} 	//while 종료
			
			p2.add(scrollPane);
			rs.close();
			pstmt.close();
			con.close();
			}	//try 종료
		catch (Exception e){;}
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		if(s == "등록") {
			MovieAdd win = new MovieAdd();
			win.show();
		}
		else if(s == "수정") {
			int row = movieList.getSelectedRow();
			Object movieCode = movieList.getValueAt(row, 0);
			
			MovieModify win = new MovieModify(movieCode);
			win.show();
		}
		else if(s == "새로고침") {
			dispose();
			new MovieManagement().setVisible(true);
		}
		else if(s == "삭제"){
			int row = movieList.getSelectedRow();
			Object movieCode = movieList.getValueAt(row, 0);
			
			int result = JOptionPane.showConfirmDialog(this, "선택한 영화의 정보를 정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					String sql = "delete from movie_info where code ='"+movieCode+"';";
					
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
					
					Statement dbSt = con.createStatement();
					dbSt.execute(sql);
					
					PreparedStatement pstmt = con.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					
					while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
						model.addRow(new Object[] {
								rs.getString("code"),
								rs.getString("title"),
								rs.getInt("time"),
								rs.getString("genre")
								});
					} 	//while 종료
					
					remove(scrollPane);
					movieList = new JTable(model);
					scrollPane = new JScrollPane(movieList);
					
					rs.close();
					pstmt.close();
					con.close();
					}	//try 종료
				catch (Exception e){;}
			}
			this.dispose();
			new MovieManagement().setVisible(true);
		}
		else {
			String sql;
			
			String selectGenre = searchGenre.getSelectedItem().toString();
			String selectTitle = searchIn.getText();
			
			if(selectGenre == "장르" && selectTitle.isEmpty())sql = "select * from movie_info";
			else if (selectGenre == "장르") sql = "select * from movie_info where title like '%"+selectTitle+"%'";
			else if (selectTitle.isEmpty()) sql = "select * from movie_info where genre = '"+selectGenre+"'";
			else sql = "select * from movie_info where genre = '"+selectGenre+"' and title like '%"+selectTitle+"%'";
			System.out.println(sql);
			
			try {
				model.setNumRows(0);
				//Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {	//Table row마다 data get해서 JTable에 출력
					model.addRow(new Object[] {
							rs.getString("code"),
							rs.getString("title"),
							rs.getInt("time"),
							rs.getString("genre")
							});
				} 	//while 종료
				
				p2.add(scrollPane);
				rs.close();
				pstmt.close();
				con.close();
				}	//try 종료
			catch (Exception e){;}
		}
	}
}

class MovieAdd extends JDialog implements ActionListener{
	JLabel l1, l2, l3, l4;
	JTextField tf1, tf2, tf3;
	JButton btnOK, doubleCheck;
	JPanel p1, p2;
	JComboBox genreList;
	String[] genre = {"액션", "SF", "코미디", "스릴러", "범죄", "음악", "스포츠", "로멘스", "기타"};
	
	public MovieAdd() {
		setTitle("영화 등록");
		setSize(380, 250);
		setLocationRelativeTo(null);
		setResizable(false);
		this.setModal(true);
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		l1 = new JLabel("영  화   코  드");
		l2 = new JLabel("영  화   제  목");
		l3 = new JLabel("상영시간(분)");
		l4 = new JLabel("장              르");
		
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		
		doubleCheck = new JButton("중복확인");
		doubleCheck.addActionListener(this);

		genreList = new JComboBox<String>(genre);
		
		l1.setBounds(40, 15, 100, 25);
		l2.setBounds(40, 55, 100, 25);
		l3.setBounds(40, 95, 100, 25);
		l4.setBounds(40, 135, 100, 25);
		
		tf1.setBounds(140, 15, 45, 25);
		tf2.setBounds(140, 55, 200, 25);
		tf3.setBounds(140, 95, 35, 25);
		
		doubleCheck.setBounds(200, 15, 88, 25);
		
		genreList.setBounds(140, 135, 90, 25);
		
		p1.add(l1);	p1.add(tf1); p1.add(doubleCheck);
		p1.add(l2);	p1.add(tf2);
		p1.add(l3);	p1.add(tf3);
		p1.add(l4); p1.add(genreList);
		
		p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		
		btnOK = new JButton("확인");
		btnOK.setEnabled(false);
		btnOK.addActionListener(this);
		
		p2.add(btnOK);
		
		
		ct.add(p1, BorderLayout.CENTER);
		ct.add(p2, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String sql;
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			Statement dbSt = con.createStatement();
			
		
		if(s == "중복확인") {
			boolean ok = true;
			sql = "select * from movie_info where code = '"+tf1.getText()+"';";
			ResultSet result = dbSt.executeQuery(sql);
			
			while(result.next()) ok = false;
			
			if(!(tf1.getText().isEmpty())) {
				if(ok) {
					JOptionPane.showMessageDialog(this, "사용할 수 있는 코드입니다.", "코드 확인", JOptionPane.INFORMATION_MESSAGE);
					tf1.setEnabled(false);
					btnOK.setEnabled(true);
				}
				else {
					JOptionPane.showMessageDialog(this, "중복되는 코드입니다.", "코드 확인", JOptionPane.OK_OPTION);
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "먼저 코드를 입력해주세요.", "코드 확인", JOptionPane.OK_OPTION);
			}
		}
		
		else {
			if(tf2.getText().equals("")||tf3.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "모든 정보를 입력해주세요.", "입력 확인", JOptionPane.OK_OPTION);
			}
			else {
				sql = "insert into movie_info values ('"+tf1.getText()+"', '"+tf2.getText()+"', "+tf3.getText()+", '"+genreList.getSelectedItem().toString()+"')";
				dbSt.execute(sql);						
				JOptionPane.showMessageDialog(this, "정상적으로 등록되었습니다.\n새로고침 해주세요.", "입력 확인", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		}
		con.close();
		}catch(Exception e) {;}
	}
}

class MovieModify extends JDialog implements ActionListener{
	JLabel l1, l2, l3, l4;
	JTextField tf1, tf2, tf3;
	JButton btnOK;
	JPanel p1, p2;
	JComboBox genreList;
	String[] genre = {"액션", "SF", "코미디", "스릴러", "범죄", "음악", "스포츠", "로멘스", "기타"};
	
	public MovieModify(Object obj) {
		setTitle("영화 정보 수정");
		setSize(380, 250);
		setLocationRelativeTo(null);
		setResizable(false);
		this.setModal(true);
		
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		p1 = new JPanel();
		p1.setLayout(null);
		
		l1 = new JLabel("영  화   코  드");
		l2 = new JLabel("영  화   제  목");
		l3 = new JLabel("상영시간(분)");
		l4 = new JLabel("장              르");
		
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();

		genreList = new JComboBox<String>(genre);
		
		try {
			String sql = "select * from movie_info where code = '"+obj+"';";
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				tf1.setText(rs.getString("code"));
				tf2.setText(rs.getString("title"));
				tf3.setText(rs.getString("time"));
				
				for(int i = 0; i < 9; i++) {
					if(genre[i].equals(rs.getString("genre"))) {
						genreList.setSelectedIndex(i);
						break;
					}
				}
			}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {}
		tf1.setEnabled(false);
		
		
		
		l1.setBounds(40, 15, 100, 25);
		l2.setBounds(40, 55, 100, 25);
		l3.setBounds(40, 95, 100, 25);
		l4.setBounds(40, 135, 100, 25);
		
		tf1.setBounds(140, 15, 45, 25);
		tf2.setBounds(140, 55, 200, 25);
		tf3.setBounds(140, 95, 35, 25);
		
		genreList.setBounds(140, 135, 90, 25);
		
		p1.add(l1);	p1.add(tf1);
		p1.add(l2);	p1.add(tf2);
		p1.add(l3);	p1.add(tf3);
		p1.add(l4); p1.add(genreList);
		
		p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		
		btnOK = new JButton("수정");
		btnOK.addActionListener(this);
		
		p2.add(btnOK);
		
		
		ct.add(p1, BorderLayout.CENTER);
		ct.add(p2, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent ae) {
		try {
			String sql = "update movie_info set title='"+tf2.getText()+"', time="+tf3.getText()+", genre='"+genreList.getSelectedItem().toString()+"' where code='"+tf1.getText()+"';";
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieCafe?serverTimezone=UTC", "root", "java2020");
			Statement dbSt = con.createStatement();
			dbSt.execute(sql);
			
			JOptionPane.showMessageDialog(this, "정상적으로 수정되었습니다.\n새로고침 해주세요.", "입력 확인", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			con.close();
			
		}catch(Exception e) {}
	}
}

/*
class SalesManagement extends JFrame implements ActionListener {
	SalesManagement (String title) {
		setTitle(title);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
	}
}*/




	
	
	
	



public class MovieCafe {

	public static void main(String[] args) {
		
		Login li = new Login();
		li.show();
		
		
		
		
		

	}

}