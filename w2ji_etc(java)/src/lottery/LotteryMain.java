package lottery;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.google.gson.*;

import word_20200614.WordDbConnect;




public class LotteryMain extends JFrame  implements ActionListener , KeyListener {

	SendPost sp = new SendPost();
	
    JButton logBtn;
   
    
    JTextField nick_name;
    
    JTextField num1;
    JTextField num2;
    JTextField num3;
    JTextField num4;
    JTextField num5;
    JTextField num6;
    
    JButton ok_btn;
    JButton getMyNumber;    
    JButton setMyNumber;
    JButton notice_btn;
    
    String notice_txt = "";
    
    JButton send_btn;
    
    String str ="";
    
    String id = "";
    String d_day = "";
    
    Boolean id_chk = false; //정상 아이디 체크 
    
    String data[][]= {};			  
    String column[]={"나의번호","추첨번호","당첨갯수"};     
    DefaultTableModel dtm = new DefaultTableModel(data,column){ public boolean isCellEditable(int i, int c){ return false; } };
    
    String data1[][]= {};			  
    String column1[]={"행번호","일자"};     
    DefaultTableModel dtm1 = new DefaultTableModel(data1 , column1){ public boolean isCellEditable(int i, int c){ return false; } };

    JTextField lab3;
	
	
	   public LotteryMain() {		   
		    this.setLayout(null);
		   
	        JLabel nick_lab = new JLabel("별 칭  : ");	 
	        nick_lab.setBounds(20, 5, 50, 20); // x , y , w , h
	        	        
	        nick_name= new JTextField(10);
	        nick_name.setBounds(70, 5, 130, 20); // x , y , w , h
	        
	        ok_btn = new JButton("확인");
	        ok_btn.addActionListener(this);
	        ok_btn.setBounds(210, 5, 60, 20); // x , y , w , h
	        
	        JTextField lab1 = new JTextField("이번 회차 정보");	 
	        lab1.setEditable(false);
	        lab1.setBounds(20, 30, 350, 20); // x , y , w , h	        
	        lab1.setHorizontalAlignment(JTextField.CENTER);
	        
	        JTextField lab2 = new JTextField("회차가 없습니다.");
	        lab2.setEditable(false);
	        lab2.setBounds(20, 55, 350, 20); // x , y , w , h
	        
	        String aa[] = getLotteryInfo();
	        id = aa[1]; // 이번 회차 id값
	        d_day = aa[3]; // 마감일자.
	        lab2.setText(   aa[0] );	        
	        
	        
	        num1 = new JTextField(2);
	        num1.setBounds(20, 90, 50, 35); // x , y , w , h
	        num1.setHorizontalAlignment(JTextField.CENTER);
	        num1.addKeyListener(this);
	        IntegerDocument  id1 = new IntegerDocument ();
	        num1.setDocument(id1);

	        
	        num2 = new JTextField(2);
	        num2.setBounds(80, 90, 50, 35); // x , y , w , h
	        num2.addKeyListener(this);
	        num2.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id2 = new IntegerDocument ();
	        num2.setDocument(id2);
	        
	        num3 = new JTextField(2);
	        num3.setBounds(140, 90, 50, 35); // x , y , w , h
	        num3.addKeyListener(this);
	        num3.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id3 = new IntegerDocument ();
	        num3.setDocument(id3);
	        	        
	        num4 = new JTextField(2);
	        num4.setBounds(200, 90, 50, 35); // x , y , w , h
	        num4.addKeyListener(this);
	        num4.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id4 = new IntegerDocument ();
	        num4.setDocument(id4);
	        
	        num5 = new JTextField(2);
	        num5.setBounds(260, 90, 50, 35); // x , y , w , h
	        num5.addKeyListener(this);
	        num5.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id5 = new IntegerDocument ();
	        num5.setDocument(id5);
	        
	        num6 = new JTextField(2);
	        num6.setBounds(320, 90, 50, 35); // x , y , w , h
	        num6.addKeyListener(this);
	        num6.setHorizontalAlignment(JTextField.CENTER);
	        IntegerDocument  id6 = new IntegerDocument ();
	        num6.setDocument(id6);
	        
	        setMyNumber = new JButton("번호 등록하기");
	        setMyNumber.addActionListener(this);
	        setMyNumber.setBounds(20, 140, 350, 30); // x , y , w , h
	        
	        lab3 = new JTextField("이번 회차 나의 등록번호");	 
	        lab3.setEditable(false);
	        lab3.setBounds(20, 180, 350, 20); // x , y , w , h	        
	        lab3.setHorizontalAlignment(JTextField.CENTER);
	        this.add(lab3);
	        
	        
		   
		  JTable jt=new JTable( dtm );
		  jt.getColumnModel().getColumn(0).setMaxWidth(130);
		  jt.getColumnModel().getColumn(1).setMaxWidth(130);
		  jt.getColumnModel().getColumn(2).setMaxWidth(90);
		  dtm.fireTableDataChanged();
		  JScrollPane jsp = new JScrollPane(jt);
		  jsp.setBounds(20,210,350,300);	// x , y , w , h
		  
		  this.add(jsp);
		  
	        notice_btn = new JButton("후원 정보 보기");
	        notice_btn.addActionListener(this);
	        notice_btn.setBounds(380, 5, 350, 90); // x , y , w , h
	        this.add(notice_btn);	
		  
	        JTextField lab4 = new JTextField("지난 회차 보기");	 
	        lab4.setEditable(false);
	        lab4.setBounds(380, 100, 350, 30); // x , y , w , h	        
	        lab4.setHorizontalAlignment(JTextField.CENTER);
	        this.add(lab4);		  
		  
		  JTable jt1=new JTable( dtm1 );
		  jt1.getColumnModel().getColumn(0).setMaxWidth(100);
		  jt1.getColumnModel().getColumn(1).setMaxWidth(250);
		  dtm1.fireTableDataChanged();
		  JScrollPane jsp1 = new JScrollPane(jt1);
		  jsp1.setBounds(380,140,350,370);	// x , y , w , h

		  
		  jt1.addMouseListener(new java.awt.event.MouseAdapter() {
			    @Override
			    public void mouseClicked(java.awt.event.MouseEvent evt) {
			    	int row = jt1.getSelectedRow();
					 Object value0 = jt1.getValueAt(row, 0);
					 Object value1 = jt1.getValueAt(row, 1);
					 System.out.println(value0+" : "+value1);
					 getMyLotteryNumber( value0.toString() , nick_name.getText() );
					 lab3.setText(value1.toString());
			    }
			});
		  
		  this.add(jsp1);
		          
	
	        
	        
	        this.add(nick_lab);
	        this.add(nick_name);
	        this.add(ok_btn);
	        this.add( lab1 );
	        this.add( lab2 );
	        
	        this.add( num1 );
	        this.add( num2 );
	        this.add( num3 );
	        this.add( num4 );
	        this.add( num5 );
	        this.add( num6 );
	        this.add(setMyNumber);
	        
	        id_load();	//별칭 가져오기
	        
	        getMyLotteryNumber(id , nick_name.getText() ); // 나의 등록 번호 가져오기
	        getPastLotteryInfo(); // 지난 이력 가져오기

	        this.setVisible(true);	 
	        this.setTitle("환영합니다-----------------!");
	        this.setSize( 768 , 576);	 
	        this.setLocationRelativeTo(null);	 
	        this.setResizable(false);	 
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	   

	   public void actionPerformed(ActionEvent e){
		   
		   if( e.getSource() == ok_btn ){ //별칭 확인 버튼
			   id_creat();
		   }else if( e.getSource() == getMyNumber ){			   
		   }else if( e.getSource() == setMyNumber ){
			   Boolean chk = checkNumber( num1.getText() , num2.getText() , num3.getText() , num4.getText() , num5.getText() , num6.getText()  );
			   if(chk ==true && id_chk == true ) {
				   JOptionPane.showMessageDialog(null, "등록되었습니다.");
					HashMap hm = new HashMap<String, String>();
					hm.put("id"		, id);
					hm.put("nick"	,nick_name.getText() );
					hm.put("num1"	,num1.getText() );
					hm.put("num2"	,num2.getText() );
					hm.put("num3"	,num3.getText() );
					hm.put("num4"	,num4.getText() );
					hm.put("num5"	,num5.getText() );
					hm.put("num6"	,num6.getText() );
					String url = "http://localhost:8080/w2ji_web/userlotteradd";
					String temp = sp.postRequest(url , hm);
					JsonParser Parser = new JsonParser();
					JsonObject jsonObj = (JsonObject) Parser.parse(temp);
					JsonArray memberArray = (JsonArray) jsonObj.get("list");					  
					dtm.setRowCount(0);
					for (int i = 0; i < memberArray.size(); i++) {
						JsonObject object = (JsonObject) memberArray.get(i);
						String [] ss = new String[3];
						ss[0] = object.get("mynum").toString().replace("\"", "");
						ss[1] = object.get("goal").toString().replace("\"", "");
						ss[2] = object.get("point").toString().replace("\"", "");
						dtm.addRow(ss);
					}
					num1.setText("");
					num2.setText("");
					num3.setText("");
					num4.setText("");
					num5.setText("");
					num6.setText("");	
					lab3.setText("이번 회차 나의 등록번호");

			   }else {
				   
			   }
			   
		   }else if( e.getSource() == notice_btn ){
			   JOptionPane.showMessageDialog(null, notice_txt);
		   }else {
			   
		   }
		   
		   
	   }
	   
	   // 등록번호 체크
	   public Boolean checkNumber(String n1 , String n2 , String n3 , String n4 , String n5 , String n6){
		   Boolean rtn = false;
		   SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		   Date d1 = null;
		   Date d2 = new Date();
		   try {
			   d1 = df.parse( d_day ) ;   
		   }catch(Exception e) {
			   
		   }
		   int compare = d1.compareTo(d2);
		   //System.out.println(compare);
		   
		   int maxNum = 45;
		   int a1 , a2 , a3 , a4 , a5 , a6;
		   a1 = n1.equals("")?0:Integer.parseInt(n1);
		   a2 = n2.equals("")?0:Integer.parseInt(n2);
		   a3 = n3.equals("")?0:Integer.parseInt(n3);
		   a4 = n4.equals("")?0:Integer.parseInt(n4);
		   a5 = n5.equals("")?0:Integer.parseInt(n5);
		   a6 = n6.equals("")?0:Integer.parseInt(n6);
		   if( a1 == a2 || a2 == a3 || a3 == a4 || a4 == a5 || a5 == a6 || a6 == a1){
			   JOptionPane.showMessageDialog(null, "같은수가 있습니다.");
			   rtn = false;
		   }else if( a1>maxNum || a2>maxNum || a3>maxNum || a4>maxNum || a5>maxNum || a6>maxNum ){
			   JOptionPane.showMessageDialog(null, "45 이상 입력할수 없습니다.");
			   rtn = false;
		   }else if(compare < 0 ){
			   JOptionPane.showMessageDialog(null, "마감일이 지났습니다.");
			   rtn = false;			   
		   }else {
			   rtn = true;
		   }		   
		   System.out.println(n1+" : "+n2+" : "+n3+" : "+n4+" : "+n5+" : "+n6);
		   return rtn;
	   }// 등록번호 체크
	   

	   // start - 회차 정보 가져오기
	   public String[] getLotteryInfo(){
		   StringBuilder sb = new StringBuilder();
			try {
				URL url = new URL("http://localhost:8080/w2ji_web/thislottery");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
				con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
				con.setRequestMethod("POST");
				//json으로 message를 전달하고자 할 때 
				con.setRequestProperty("Content-Type", "application/json");
				con.setDoInput(true);
				con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정 
				con.setUseCaches(false);
				con.setDefaultUseCaches(false);

				OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
				wr.write(""); //json 형식의 message 전달 
				wr.flush();				
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					//Stream을 처리해줘야 하는 귀찮음이 있음.
					BufferedReader br = new BufferedReader(
							new InputStreamReader(con.getInputStream(), "utf-8"));
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line).append("\n");
					}
					br.close();
					System.out.println("" + sb.toString());
				} else {
					System.out.println(con.getResponseMessage());
				}
			} catch (Exception e){
				System.err.println(e.toString());
			}
			JsonParser Parser = new JsonParser();
			JsonObject jsonObj = (JsonObject) Parser.parse(sb.toString());
			String txt[] = new String[4];
			txt[0] = jsonObj.get("txt").toString().replace("\"", "");
			txt[1] = jsonObj.get("id").toString().replace("\"", "");
			txt[2] = jsonObj.get("title").toString().replace("\"", "");
			txt[3] = jsonObj.get("d_day").toString().replace("\"", "");
			return txt;
	   }// end - 회차 정보 가져오기
	        
	public void id_load() {
	    try{
	        File file = new File("c:\\lottery_info\\info.dat");
	        FileReader filereader = new FileReader(file);
	        int singleCh = 0;
	        while((singleCh = filereader.read()) != -1){
	        	str += (char)singleCh;	                
	        }
	        filereader.close();
	    }catch (FileNotFoundException e) {
	
	    }catch(IOException e){
	        System.out.println(e);
	    }
	    String test = str.replace(" ", "");
	    System.out.println(test+" : "+test.length());
	    if( test.length() > 0) {
	    	nick_name.setText( str );
	    	id_chk = true;
	    }else {
	    	JOptionPane.showMessageDialog(null, "별칭을 등록하셔야 합니다.");
	    	id_chk = false;
	    }
	    
	}
   
	public void id_creat() {
		   String fileName = "c:\\lottery_info\\info.dat" ;			   
		   String path = "c:\\lottery_info"; //폴더 경로
		   File Folder = new File(path);
			// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
			if (!Folder.exists()) {
				try{
				    Folder.mkdir(); //폴더 생성합니다.
				    System.out.println("폴더가 생성되었습니다.");
				}catch(Exception ex){
				    ex.getStackTrace();
				}        
		         }else {
				System.out.println("이미 폴더가 생성되어 있습니다.");
			}
	     try{
	         File file = new File(fileName) ;
	         file.delete();
	         FileWriter fw = new FileWriter(file, true) ;
	         String test = nick_name.getText().replace(" ", "");
	         if( test.length() > 0 ) {
	        	 fw.write( nick_name.getText() );
	        	 id_chk = true;
	         }else {
	        	JOptionPane.showMessageDialog(null, "다시 별칭을 등록하셔야 합니다.");
	 	    	id_chk = false;
	         }
	         
	         
	         fw.flush();		 
	         fw.close();
	     }catch(Exception ex){
	         ex.printStackTrace();
	     }
	}  

	public void getMyLotteryNumber(String _id , String _name) {		
		HashMap hm = new HashMap<String, String>();
		hm.put("id"		, _id);
		hm.put("nick"	, _name );
		String url = "http://localhost:8080/w2ji_web/mylottery";
		String temp = sp.postRequest(url , hm);
		JsonParser Parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) Parser.parse(temp);
		JsonArray memberArray = (JsonArray) jsonObj.get("list");
		dtm.setRowCount(0);
		for (int i = 0; i < memberArray.size(); i++) {
			JsonObject object = (JsonObject) memberArray.get(i);
			String [] ss = new String[3];
			ss[0] = object.get("mynum").toString().replace("\"", "");
			ss[1] = object.get("goal").toString().replace("\"", "");
			ss[2] = object.get("point").toString().replace("\"", "");
			dtm.addRow(ss);
		}
	}

	public void getPastLotteryInfo() {		
		HashMap hm = new HashMap<String, String>();
		String url = "http://localhost:8080/w2ji_web/pastlottery";
		//String test = postRequest(url , hm);
		String temp = sp.postRequest(url , hm);
		JsonParser Parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) Parser.parse(temp);
		JsonArray memberArray = (JsonArray) jsonObj.get("list");
		dtm1.setRowCount(0);
		for (int i = 0; i < memberArray.size(); i++) {
			JsonObject object = (JsonObject) memberArray.get(i);
			String [] ss = new String[2];
			ss[0] = object.get("id").toString().replace("\"", "");
			ss[1] = object.get("txt").toString().replace("\"", "");
			dtm1.addRow(ss);
		}
		notice_txt = jsonObj.get("notice").toString().replace("\"", "");
	}
	

   


	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}



	
	   
	   
}