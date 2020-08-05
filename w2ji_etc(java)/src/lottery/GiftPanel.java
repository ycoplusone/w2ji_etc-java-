package lottery;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.google.gson.*;
import com.mysql.fabric.xmlrpc.base.Array;

import word_20200614.WordDbConnect;




public class GiftPanel extends JFrame  implements ActionListener , KeyListener {

		String url_base = MemberVarible.url;
		
		String _id = "";
		
		CheckboxGroup cbg0 = new CheckboxGroup();
		Checkbox cb0_1 = null;
		Checkbox cb0_2 = null;
		JTextField jt0_3 = null;
		
		CheckboxGroup cbg1 = new CheckboxGroup();
		Checkbox cb1_1 = null;
		Checkbox cb1_2 = null;
		Checkbox cb1_3 = null;
		Checkbox cb1_4 = null;
		
		JTextField jt2_1 = null;
		JTextField jt2_2 = null;
		JTextField jt2_3 = null;
		JTextField jt2_4 = null;
		
		
		JLabel jl4_1 = null;
		JLabel jl4_2 = null;
		JLabel jl4_3 = null;
		
		JButton bnt1_1 = null;
		JButton bnt1_2 = null;
		
		JButton bnt2_1 = null;
		JButton bnt2_2 = null;
		
		JButton bnt3_1 = null;
		JButton bnt3_2 = null;

		JTextField jt4_1 = null;
		JTextField jt4_2 = null;
		
		File f1 = null;
		File f2 = null;
		File f3 = null;
		
		JButton summit_bnt = null;
		JButton cancel_bnt = null;
	
		public GiftPanel(String nickname){
			this._id = nickname;
			this.setLayout(null);
			
			JPanel jp1 = new JPanel();
			jp1.setLayout(null);
			jp1.setBorder( BorderFactory.createTitledBorder("지역") );
			jp1.setBounds(10 , 10 , 200 , 150);
	        
			cb0_1 = new Checkbox("전체", cbg0, true);    
			cb0_1.setBounds(10,30, 50,20);
			jp1.add(cb0_1);
			
			cb0_2 = new Checkbox("기타", cbg0, false);    
			cb0_2.setBounds(10,70, 50,20);
			jp1.add(cb0_2);
			
			jt0_3 = new JTextField();
			jt0_3.setBounds(10,110, 130,20);
			jp1.add( jt0_3 );
			
			this.add(jp1);
			
			JPanel jp2 = new JPanel();
			jp2.setLayout(null);
			jp2.setBorder( BorderFactory.createTitledBorder("등수 선물") );
			jp2.setBounds(210 , 10 , 250 , 150);
			
			cb1_1 = new Checkbox("3개 일치 4등", cbg1 , true);    
			cb1_1.setBounds(10,20, 180,20);
			jp2.add(cb1_1);
			
			cb1_2 = new Checkbox("4개 일치 3등", cbg1 , false);    
			cb1_2.setBounds(10,50, 180,20);
			jp2.add(cb1_2);
			
			cb1_3 = new Checkbox("5개 일치 2등", cbg1 , false);    
			cb1_3.setBounds(10,80, 180,20);
			jp2.add(cb1_3);
			
			cb1_4 = new Checkbox("6개 일치 1등", cbg1 , false);    
			cb1_4.setBounds(10,110, 180,20);
			jp2.add(cb1_4);
			
			this.add(jp2);
			
			
			JPanel jp3 = new JPanel();
			jp3.setLayout(null);
			jp3.setBorder( BorderFactory.createTitledBorder("연 락 처") );
			jp3.setBounds(10 , 170 , 200 , 180);
			
			JLabel jl1 = new JLabel("전 화 : ");
			jl1.setBounds(10,20, 60 ,20);
			jp3.add( jl1 );
			
			jt2_1 = new JTextField();
			jt2_1.setBounds(55,20, 120,20);
			jp3.add( jt2_1 );
			
			JLabel jl2 = new JLabel("카 톡 : ");
			jl2.setBounds(10,50, 60 ,20);
			jp3.add( jl2 );
			
			jt2_2 = new JTextField();
			jt2_2.setBounds(55,50, 120,20);
			jp3.add( jt2_2 );
			
			JLabel jl3 = new JLabel("페 북 : ");
			jl3.setBounds(10,80, 60 ,20);
			jp3.add( jl3 );
			
			jt2_3 = new JTextField();
			jt2_3.setBounds(55,80, 120,20);
			jp3.add( jt2_3 );
			
			JLabel jl4 = new JLabel("기 타 : ");
			jl4.setBounds(10,110, 60 ,20);
			jp3.add( jl4 );
			
			jt2_4 = new JTextField();
			jt2_4.setBounds(55,110, 120,20);
			jp3.add( jt2_4 );
			
			this.add(jp3);
			
			JPanel jp4 = new JPanel();
			jp4.setLayout(null);
			jp4.setBorder( BorderFactory.createTitledBorder("사 진 등 록 하 기") );
			jp4.setBounds(210 , 170 , 250 , 180);
			
			jl4_1 = new JLabel();
			jl4_1.setBounds(10 , 20 , 60 , 60 );
			jl4_1.setBorder( new LineBorder(Color.gray) );
			jp4.add( jl4_1 );
			
			bnt1_1 = new JButton("추가");			
			bnt1_1.setBounds(10 , 85 , 60 , 30 );
			bnt1_1.addActionListener(this);
			jp4.add( bnt1_1 );
			
			
			jl4_2 = new JLabel();
			jl4_2.setBounds(90 , 20 , 60 , 60 );
			jl4_2.setBorder( new LineBorder(Color.gray) );
			jp4.add( jl4_2 );
			
			bnt2_1 = new JButton("추가");			
			bnt2_1.setBounds(90 , 85 , 60 , 30 );
			bnt2_1.addActionListener(this);
			jp4.add( bnt2_1 );
			
			
			jl4_3 = new JLabel();
			jl4_3.setBounds(170 , 20 , 60 , 60 );
			jl4_3.setBorder( new LineBorder(Color.gray) );
			jp4.add( jl4_3 );
			
			bnt3_1 = new JButton("추가");			
			bnt3_1.setBounds(170 , 85 , 60 , 30 );
			bnt3_1.addActionListener(this);
			jp4.add( bnt3_1 );
			
			JLabel jl6 = new JLabel("설명 : ");
			jl6.setBounds(10,120, 60 ,20);
			jp4.add( jl6 );
			
			jt4_1 = new JTextField();
			jt4_1.setBounds(55,120, 180,20);
			jp4.add( jt4_1 );
			
			JLabel jl7 = new JLabel("기 타 : ");
			jl7.setBounds(10,150, 60 ,20);
			jp4.add( jl7 );
			
			jt4_2 = new JTextField();
			jt4_2.setBounds(55,150, 180,20);
			jp4.add( jt4_2 );			
			
			this.add(jp4);
			
			summit_bnt = new JButton("확인");
			summit_bnt.setBounds(108 , 353 , 100 , 50 );
			summit_bnt.addActionListener(this);
			this.add(summit_bnt);
			
			cancel_bnt = new JButton("취소");
			cancel_bnt.setBounds(212 , 353 , 100 , 50 );
			cancel_bnt.addActionListener(this);
			this.add(cancel_bnt);
		    
	        this.setVisible(true);	 
	        this.setTitle(nickname+" 선물하기");
	        this.setSize( 500 , 450);	 
	        this.setLocationRelativeTo(null);	 
	        this.setResizable(false);	 
	        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
	    }
   

		public void actionPerformed(ActionEvent e){
			
			if( e.getSource() == bnt1_1 ){
				try {
					loadImage( jl4_1 , f1 );
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}else if( e.getSource() == bnt2_1 ){
				try {
					loadImage( jl4_2 , f2 );
				} catch (Exception e1) {
					e1.printStackTrace();
				}				
			}else if( e.getSource() == bnt3_1 ){
				try {
					loadImage( jl4_3 , f3 );
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else if( e.getSource() == summit_bnt ){
				System.out.println("확인");
			}else if( e.getSource() == cancel_bnt ){
				this.setVisible(false);
			}
			
		}


	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		/*
		int keycode = e.getKeyCode(); // 37 왼쪽 , 39 오른쪽
		if( e.getSource() == num1 ){
			if( keycode==37 ){ num6.requestFocus(); }else{ num2.requestFocus(); }			
		}else if( e.getSource() == num2 ){
			if( keycode==37 ){ num1.requestFocus(); }else{ num3.requestFocus(); }
		}else if( e.getSource() == num3 ){
			if( keycode==37 ){ num2.requestFocus(); }else{ num4.requestFocus(); }
		}else if( e.getSource() == num4 ){
			if( keycode==37 ){ num3.requestFocus(); }else{ num5.requestFocus(); }
		}else if( e.getSource() == num5 ){
			if( keycode==37 ){ num4.requestFocus(); }else{ num6.requestFocus(); }
		}else if( e.getSource() == num6 ){
			if( keycode==37 ){ num5.requestFocus(); }else{ num1.requestFocus(); }
		}*/		
	}

	   public void loadImage(JLabel jl , File f ) throws Exception {
		   	jl.setIcon(null);
			f = null;
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("파일 불러오기");
	        fileChooser.setFileFilter(new FileNameExtensionFilter("","jpg", "png","gif","bmp")); // 파일필터
	        	        
	        
	        fileChooser.setMultiSelectionEnabled(false);// 다중 선택 불가
	        int returnVal = fileChooser.showOpenDialog(this); // show openDialog 
	        if (returnVal == JFileChooser.APPROVE_OPTION) { // 파일을 선택하였을 때
	            try{
	            	
	                //loadImage(fileChooser.getSelectedFile().toString());
	            	String url = fileChooser.getSelectedFile().toString();		            	
	            	f = new File(url);
	            	
	            	BufferedImage base = new BufferedImage(56, 56 , BufferedImage.TYPE_INT_RGB);
	            	BufferedImage  imgIcon = ImageIO.read( f );
	            	Image reimg = imgIcon.getScaledInstance(56, 56, Image.SCALE_SMOOTH);
	            	Graphics2D g = (Graphics2D) base.getGraphics();
	            	g.drawImage(reimg, 0, 0 , null);
	            	
	            	jl.setIcon( new ImageIcon(base) );

	            	
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

	   

	
	   
	   
}