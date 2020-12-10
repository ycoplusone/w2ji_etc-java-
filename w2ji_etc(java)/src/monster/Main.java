package monster;

import javax.swing.*;
//import java.util.InputMismatchException;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import monster.Monster;
import monster.RankingPanel;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

public class Main extends JFrame{	
	
	String name1[]={"팀소개","페인팅","글사랑"};
	JMenuItem mi1[]=new JMenuItem[3];
	    
	    
	String name2[]={"게임시작","점수조회"};
    JMenuItem mi2[]=new JMenuItem[2];
   
<<<<<<< HEAD
    GamePanel gp;
	private static int currentID =-1;	
	private ImageIcon [] images = new ImageIcon [3]; // 이미지 객체 배열
	  
   //생성자     
   public Main(){      
	   
	   JMenuBar mb = new JMenuBar();
       JMenu jm1 = new JMenu("기존과제");
       JMenu jm2 = new JMenu("게임수행");
       JMenu jm3 = new JMenu("라디오재생");
       JMenu jm4 = new JMenu("채팅");   
      
      
      setTitle("proj2");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
      setSize(500, 500);
      setResizable(false);
      setLayout(null);
      Container c = getContentPane();
     
      MyPanel mypanel = new MyPanel();  //마이패널 객체 생성
      mypanel.setBounds(50,70,400,300);  //마이패널 사이즈 설정
      
      
      
           
      
            for(int i=0; i<images.length; i++)  
      			images[i] = new ImageIcon("imag" + i + ".jpeg");
            
            for(int i=0; i<mi1.length; i++){
                mi1[i]=new JMenuItem(name1[i]);
                jm1.add(mi1[i]);
            }
            
            
            for(int i=0; i<mi2.length; i++){
                mi2[i]=new JMenuItem(name2[i]);
                jm2.add(mi2[i]);
            }

            mi1[0].addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {            	   
            	  currentID+=1;            	   
            	   if(currentID>2) {
            		   currentID=0;
            	   }
            	   mypanel.ta.setVisible(false);
                   mypanel.tf.setVisible(false);
                   mypanel.tl.setVisible(false);
            	   
            	   	mypanel.setBackground(Color.gray);
            	   	mypanel.point=null;
            	   
            	   
            	   	Image selectImage=images[currentID].getImage().getScaledInstance(400,300,Image.SCALE_SMOOTH);
            	   	ImageIcon selectImageIcon = new ImageIcon(selectImage);
            	   	mypanel.teamintro.setIcon(selectImageIcon);   
            	 
            	 	mypanel.teamintro.setVisible(true);
            	 	mypanel.ta.setText("");
               }

            });
            
            
            
          
           mi1[1].addActionListener(new ActionListener() {    

            	@Override
            	public void actionPerformed(ActionEvent e) {
            		
            	    mypanel.teamintro.setVisible(false);
            		mypanel.ta.setVisible(false);
                    mypanel.tf.setVisible(false);
                    mypanel.tl.setVisible(false);
            		
            		
            		mypanel.setBackground(Color.white);
             	
                      
            		mypanel.ta.setText("");
            	}

            	});

    
            
                       
            
            
            mi1[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  mypanel.teamintro.setVisible(false);
                  mypanel.setBackground(Color.gray);
                  mypanel.point=null;      
                   
                   mypanel.ta.setVisible(true);
                   mypanel.tf.setVisible(true);
                   mypanel.tl.setVisible(true);
                   currentID= -1;
                }
            });
            
            mi2[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	System.out.println("시작");
                	c.removeAll();
                	c.repaint();
                	if(gp!=null) {
                		gp.th.stop();
                	}               	
                	
	                	gp = new GamePanel();
	                    gp.setBounds(50,70,400,300);  //마이패널 사이즈 설정
                		c.add(gp);
                		gp.Game("@", "M", 'q', 200);
                    	gp.setFocusable(true);		
                    	gp.requestFocus(); // GamePanel이 키를 입력받을 수 있도록 포커스를 설정한다.
                	//}                	
                	
                }
            });
            
            mi2[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	System.out.println("점수");
                	               	
        			RankingPanel rp =  new RankingPanel();
        			rp.addWindowListener(new WindowAdapter() {
        				public void windowClosed(WindowEvent we) {	//종료됨 이벤트
        				}
        			});
                }
            });
            
  
      
      //마이프레임설정    
      //c.add(mypanel);
      //c.add(gp);
      mb.add(jm1);
      mb.add(jm2);
      mb.add(jm3);
      mb.add(jm4);
      this.setJMenuBar(mb);
      this.setLocationRelativeTo(null);
      setVisible(true);
         
=======
    Container c;
    GamePanel gp;
    
	private static int currentID =-1;	
	private ImageIcon [] images = new ImageIcon [3]; // 이미지 객체 배열
	  
   //생성자     
   public Main(){      
	   
	   JMenuBar mb = new JMenuBar();
       JMenu jm1 = new JMenu("기존과제");
       JMenu jm2 = new JMenu("게임수행");
       JMenu jm3 = new JMenu("라디오재생");
       JMenu jm4 = new JMenu("채팅");   
      
      
      setTitle("proj2");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
      setSize(500, 500);
      setResizable(false);
      setLayout(null);
      c = getContentPane();
     
      MyPanel mypanel = new MyPanel();  //마이패널 객체 생성
      mypanel.setBounds(50,70,400,300);  //마이패널 사이즈 설정
      
      
      
           
      
            for(int i=0; i<images.length; i++)  
      			images[i] = new ImageIcon("imag" + i + ".jpeg");
            
            for(int i=0; i<mi1.length; i++){
                mi1[i]=new JMenuItem(name1[i]);
                jm1.add(mi1[i]);
            }
            
            
            for(int i=0; i<mi2.length; i++){
                mi2[i]=new JMenuItem(name2[i]);
                jm2.add(mi2[i]);
            }

            mi1[0].addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {            	   
            	  currentID+=1;            	   
            	   if(currentID>2) {
            		   currentID=0;
            	   }
            	   mypanel.ta.setVisible(false);
                   mypanel.tf.setVisible(false);
                   mypanel.tl.setVisible(false);
            	   
            	   	mypanel.setBackground(Color.gray);
            	   	mypanel.point=null;
            	   
            	   
            	   	Image selectImage=images[currentID].getImage().getScaledInstance(400,300,Image.SCALE_SMOOTH);
            	   	ImageIcon selectImageIcon = new ImageIcon(selectImage);
            	   	mypanel.teamintro.setIcon(selectImageIcon);   
            	 
            	 	mypanel.teamintro.setVisible(true);
            	 	mypanel.ta.setText("");
               }

            });
            
            
            
          
           mi1[1].addActionListener(new ActionListener() {    

            	@Override
            	public void actionPerformed(ActionEvent e) {
            		
            	    mypanel.teamintro.setVisible(false);
            		mypanel.ta.setVisible(false);
                    mypanel.tf.setVisible(false);
                    mypanel.tl.setVisible(false);
            		
            		
            		mypanel.setBackground(Color.white);
             	
                      
            		mypanel.ta.setText("");
            	}

            	});

    
            
                       
            
            
            mi1[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  mypanel.teamintro.setVisible(false);
                  mypanel.setBackground(Color.gray);
                  mypanel.point=null;      
                   
                   mypanel.ta.setVisible(true);
                   mypanel.tf.setVisible(true);
                   mypanel.tl.setVisible(true);
                   currentID= -1;
                }
            });
            
            mi2[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	System.out.println("시작");
                		
                		initPanel();// 게임 화면 초기화
                	
	                	gp = new GamePanel();
	                    gp.setBounds(50,70,400,300);  //마이패널 사이즈 설정
                		c.add(gp);
                		gp.Game("@", "M", 'q', 200);
                    	gp.setFocusable(true);		
                    	gp.requestFocus(); // GamePanel이 키를 입력받을 수 있도록 포커스를 설정한다.
                	//}                	
                	
                }
            });
            
            mi2[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	System.out.println("점수");
                	
        			RankingPanel rp =  new RankingPanel();
        			rp.addWindowListener(new WindowAdapter() {
        				public void windowClosed(WindowEvent we) {	//종료됨 이벤트
        				}
        			});
                }
            });
            
  
      
      //마이프레임설정    
      //c.add(mypanel);
      //c.add(gp);
      mb.add(jm1);
      mb.add(jm2);
      mb.add(jm3);
      mb.add(jm4);
      this.setJMenuBar(mb);
      this.setLocationRelativeTo(null);
      setVisible(true);
         
   }
   
   public void initPanel(){	// 화면 초기화 할 부분
	   
	   c.removeAll();
	   c.repaint();
	   // 게임 초기화부분
	   if(gp!=null) {
		   gp.th.stop();
   		}
	   
>>>>>>> branch 'master' of https://github.com/ycoplusone/w2ji_etc-java-.git
   }
   
   
      class MyPanel extends JPanel{
    	  public JLabel teamintro =new JLabel();    	  
    	  public JTextField tf=new JTextField(10);
   	      public JTextArea ta=new JTextArea();
   	      public JLabel tl =new JLabel("<엔터를 치면 다음 행으로 이동>");
   	   private Point point;
      
      //생성자   
      public MyPanel() {           
    	  point=new Point();
    	  this.setBackground(Color.green);
    	  setLayout(null);
    	  
    	  teamintro.setBounds(0,10,460,300);
    	  
    	 
    	  
    	  addMouseListener(new MouseAdapter() {
    		  
    		  @Override
    		  public void mousePressed(MouseEvent e) {
    			  
    			  if(getBackground() == Color.white) {
    				  
    				  point = new Point(e.getX(), e.getY());
    				  MyPanel.this.repaint();
    				  
    			  }
    		  }
    		  
    		  
    		  
    	  });
    	  
    	  tf.setBounds(15,10,360,50);
    	  tl.setBounds(120,70,300,20);
    	  ta.setBounds(15,100,360,180);
    	  
    	  
    	  tf.addActionListener(new ActionListener() {
    		
    		  @Override
    		  
    		  public void actionPerformed(ActionEvent e) {
    			  
    			  JTextField t =(JTextField)e.getSource();
    			  System.out.println(t.getText().equals(""));
    			  
    			  if(t.getText().equals("")) {
    				  
    				  ta.append(t.getText()+"\n");
    				  t.setText("");      
    				  
    			  }
    			  else {
    				  ta.append(t.getText()+"\n");
    				  t.setText("");;
    			  }
    			  
    			  
    			
    		  }
    		  
    		  

    	  });
    	  
    	  
    	  this.add(teamintro);
    	  this.add(tf);
    	  this.add(ta);
    	  this.add(tl);
    	  
    	  
    	  //마이패널 초기
    	  teamintro.setVisible(false);
    	  tl.setVisible(false);
    	  ta.setVisible(false);
    	  tf.setVisible(false);
    	 
      }  
      @Override
      
      protected void paintComponent(Graphics g) {
    	  
    	  super.paintComponent(g);
    	  
    	  int cr =(int)(Math.random()*256);
    	  int cg =(int)(Math.random()*256);
    	  int cb =(int)(Math.random()*256);
    	  
    	  
    	  int n = (int)(Math.random() * 10) +1;
    	  
    	  
    	  try {
    		  if(getBackground()==Color.white) {
    			  g.setColor(new Color(cr,cg,cb));
    			  g.drawOval(point.x,point.y,n,n);
    			  
    			  
    		  }
    	  }
    	  
    	  catch(Exception e) {
    		  
    	  }
    	  
    	  
    	  
      }
     
     
      
      }
     
     
      
      
      
      
      
      
      
      
      
    
      
      
    public static void main(String[] args) {
      new Main();
   }
   
      
}





