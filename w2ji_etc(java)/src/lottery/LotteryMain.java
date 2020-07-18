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

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.google.gson.*;




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
    
    JButton send_btn;
    
    String str ="";
    
    String id = "";
	
	
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
	        nick_name.setText( str );	
	        
	        
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

	        this.setVisible(true);	 
	        this.setTitle("환영합니다-----------------!");
	        this.setSize( 768 , 576);	 
	        this.setLocationRelativeTo(null);	 
	        this.setResizable(false);	 
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	   

	   public void actionPerformed(ActionEvent e){
		   
		   if( e.getSource() == ok_btn){ //별칭 확인 버튼
			   
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
		            fw.write( nick_name.getText() );
		            fw.flush();		 
		            fw.close();
		        }catch(Exception ex){
		            ex.printStackTrace();
		        }
		        
		   
		   }else if( e.getSource() == getMyNumber ){			   
		   }else if( e.getSource() == setMyNumber ){
			   Boolean chk = checkNumber( num1.getText() , num2.getText() , num3.getText() , num4.getText() , num5.getText() , num6.getText()  );
			   
			   String param = "";
			   param+="id="+id;
			   param+="&nick="+nick_name.getText();
			   param+="&num1="+ num1.getText();
			   param+="&num2="+ num2.getText();
			   param+="&num3="+ num3.getText();
			   param+="&num4="+ num4.getText();
			   param+="&num5="+ num5.getText();
			   param+="&num6="+ num6.getText();
			   try {
				   sp.sendPost("http://localhost:8080/w2ji_web/UserLotterAdd" , param );
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			   
		   }else{
			   
		   }
		   
		   
	   }
	   
	   // 등록번호 체크
	   public Boolean checkNumber(String n1 , String n2 , String n3 , String n4 , String n5 , String n6){
		   Boolean rtn = false;
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
		   }else{
			   rtn = true;
		   }		   
		   System.out.println(n1+" : "+n2+" : "+n3+" : "+n4+" : "+n5+" : "+n6);
		   return rtn;
	   }// 등록번호 체크
	   

	   // start - 회차 정보 가져오기
	   public String[] getLotteryInfo(){
		   StringBuilder sb = new StringBuilder();
			try {
				URL url = new URL("http://localhost:8080/w2ji_web/lottery/thislottery.jsp");
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
			String txt[] = new String[2];
			txt[0] = jsonObj.get("txt").toString().replace("\"", "");
			txt[1] = jsonObj.get("id").toString().replace("\"", "");
			return txt;
	   }// end - 회차 정보 가져오기
	        
   
   public String post(String strUrl, String jsonMessage){
	   StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			//con.addRequestProperty("x-api-key", RestTestCommon.API_KEY); //key값 설정
			con.setRequestMethod("POST");
			// json으로 message를 전달하고자 할 때 
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoInput(true);
			con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정 
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(jsonMessage); //json 형식의 message 전달 
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
		return sb.toString();
   }
   

	// HTTP POST request
	

   


	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}



	
	   
	   
}