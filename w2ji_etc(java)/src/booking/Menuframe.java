package booking;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import booking.WordDbConnect;

public class Menuframe extends JFrame  implements ActionListener {
	
	JButton  book ;
	JButton unbook ;
	JButton exit ;	
		
	JInternalFrame jp_book =  new JInternalFrame("예약하기", true, true, true, true);
	JInternalFrame jp_unbook =  new JInternalFrame("퇴실하기", true, true, true, true);
	JButton[] bt = new JButton[50];
	
	WMember wm = new WMember();
	
	
	
	public Menuframe( WMember wm ) {
		this.wm = wm;
		
		this.setTitle("예약하기");
		createMenu(); // 메뉴 생성, 프레임에 삽입
		this.setSize(1024,788);
		this.setLayout(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void createMenu() {
		JMenuBar mb = new JMenuBar();
		
		book = new JButton("예약하기");
		book.addActionListener(this);		
		mb.add( book );
		
		unbook = new JButton("퇴실하기");
		unbook.addActionListener(this);		
		mb.add( unbook );
		
		exit = new JButton("Exit");
		exit.addActionListener(this);		
		mb.add( exit );
		
		this.setJMenuBar(mb);	
		
		
	}
	
	public void actionPerformed(ActionEvent e){
		//System.out.println(e.getSource());
		setVisible();
		if( e.getSource() == book) {
			bookF();			
		}else if( e.getSource() == unbook ) {
			unbookF();
		}else if( e.getSource() == exit ) {
			System.exit(0);
		}		
		
		this.revalidate();
		this.repaint();
	}
	
	public void bookF() {
		setVisible();
		ArrayList<String[]> al  = WordDbConnect.getBookingList(); //예약리스트
		
		
		
		
		jp_book  = new JInternalFrame("예약하기", true, true, true, true);  
		jp_book.setBounds(0, 0, 1000, 725);
		jp_book.setVisible(true);
		
		GridLayout fl = new GridLayout(5,10);
        //fl.setAlignment(FlowLayout.CENTER);
        jp_book.setLayout(fl);	        
        for (int i = 0; i < bt.length; i++) {
        	
        	for (String[] strings : al) {
        		if( strings[3].equals(  Integer.toString(i) ) ) {	        			
					bt[i] = new JButton( strings[2]+"님" );
        			bt[i].setEnabled(false);
        			break;
        		}else {
        			bt[i] = new JButton("빈자리");
    	            
    	            bt[i].addActionListener( new ActionListener(){	            
    		            public void actionPerformed(ActionEvent e) {
    		            	int result = JOptionPane.showConfirmDialog(null,"예약하시겠습니까?", "Confirm",JOptionPane.YES_NO_OPTION);
    		            	if(result == JOptionPane.YES_OPTION) {
    		            		for (int y = 0; y < bt.length; y++) {
	    		                    if (e.getSource().equals(bt[y])) {
	    		                    	System.out.println(y+" : "+wm.getMId());
	    		                    	WordDbConnect.setBooking(wm.getMId() , Integer.toString(y));		    		                        
	    		                    }
	    		                }
    		            		bookF();
    		            	}else {		            	}
    		            	
    		            }	            
    		        });
        		}
        	}
        	
            
            
            
            jp_book.add(bt[i]);
        }
        
		this.add(jp_book);
	}
	
	
	public void unbookF() {
		setVisible();
		ArrayList<String[]> al  = WordDbConnect.getBookingList(); //예약리스트
		
		
		
		
		jp_unbook  = new JInternalFrame("퇴실하기", true, true, true, true);  
		jp_unbook.setBounds(0, 0, 1000, 725);
		jp_unbook.setVisible(true);
		
		GridLayout fl = new GridLayout(5,10);
        //fl.setAlignment(FlowLayout.CENTER);
		jp_unbook.setLayout(fl);	        
        for (int i = 0; i < bt.length; i++) {
        	
        	for (String[] strings : al) {
        		if( strings[3].equals(  Integer.toString(i) ) && strings[1].equals(wm.getMId()) ) {	        			
					bt[i] = new JButton( strings[2]+"님" );
        			bt[i].setEnabled(true);
        			bt[i].addActionListener( new ActionListener(){	            
    		            public void actionPerformed(ActionEvent e) {
    		            	int result = JOptionPane.showConfirmDialog(null,"퇴실하시겠습니까?", "Confirm",JOptionPane.YES_NO_OPTION);
    		            	if(result == JOptionPane.YES_OPTION) {
    		            		for (int y = 0; y < bt.length; y++) {
	    		                    if (e.getSource().equals(bt[y])) {
	    		                    	System.out.println(y+" : "+wm.getMId());
	    		                    	WordDbConnect.setUnBooking( Integer.toString(y) );		    		                        
	    		                    }
	    		                }
    		            		unbookF();
    		            	}else {		            	}
    		            	
    		            }	            
    		        });
        			break;
        		}else if( strings[3].equals(  Integer.toString(i) ) ){
        			bt[i] = new JButton( strings[2]+"님" );
        			bt[i].setEnabled(false);
        			break;
        		}else {
        			bt[i] = new JButton("빈자리");
        			bt[i].setEnabled(false);
    	            
        		}
        	}
        	
            
            
            
        	jp_unbook.add(bt[i]);
        }
        
		this.add(jp_unbook);
	}	
	
	
	public void setVisible(  ){

		this.remove( jp_book );
		this.remove( jp_unbook );
		this.revalidate();
		this.repaint();
	}
	
	
	

	
	
}