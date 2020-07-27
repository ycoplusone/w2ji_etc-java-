package piechart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

class PieChart extends JPanel implements ComponentListener {	
	private List<DataModel> _dt = new ArrayList<DataModel>();
	
	private int _w=0;
	private int _h=0;
	
	private int _piew=0;
	private int _pieh=0;
	
	private String _title = "";
	
	PieChart( List<DataModel> dt , String title) {
		this._dt = dt;
		this._title = title;
	}

	public void paint(Graphics g){
		this._w = getWidth();
	    this._h = getHeight();
	    this._piew = (int) (this._h *0.8);
	    this._pieh = (int) (this._h *0.8);
	    
	    System.out.println( this._w+" : " + this._h);
		paintPie((Graphics2D) g, getBounds() , _dt);
      
   }
	   
	   void paintPie(Graphics2D g, Rectangle area,  List<DataModel> chartdt ) {
		   
	      int sum = 0;
			for (DataModel dm : chartdt) {
				sum += dm.getValue();
			}

	      int cur = 0;
	      int start = 0;
	      for (DataModel dm : chartdt) {
	         start = (int) (cur * 360 / sum);
	         int arc = (int) (dm.getValue() * 360 / sum);	         
	         g.setColor( dm.getColor() );
	         g.fillArc(area.x, area.y + 70, _piew,  _pieh , start, arc);
	         cur += dm.getValue();
	      }
	      
	      int setX = 410;
	      int setY = 10;
	      int fontX = 420;
	      int fontY = 30;
	      
	      
    	  g.setColor( Color.WHITE );
    	  g.fillRect( this._w / 3 , 5    , 150, 30);
    	  
    	  g.setColor(Color.black);
	      g.drawString( this._title ,  this._w / 3 , 20);
	      
	      
	      
	      for (DataModel dm : chartdt) {
	    	  g.setColor( dm.getColor() );
	    	  g.fillRect( area.x + _piew + 50 , area.y + setY + 70    , 150, 30);
	    	  g.setColor(Color.white);
		      g.drawString( dm.getName()+" : "+dm.getValue() ,  _piew + 50 , fontY + 70);
		      setY += 40;
		      fontY += 40;		      
	      }
	   }

	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println("componentResized");
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {		
	}

	@Override
	public void componentHidden(ComponentEvent e) {		
	}

	

}