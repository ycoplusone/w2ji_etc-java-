package piechart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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

class PieChart extends JComponent {	
	private List<DataModel> _dt = new ArrayList<DataModel>();
	
	PieChart( List<DataModel> dt ) {
		this._dt = dt;
	}

	public void paint(Graphics g){
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
	         g.fillArc(area.x, area.y, 400, 400, start, arc);
	         cur += dm.getValue();
	      }
	      
	      int setX = 410;
	      int setY = 10;
	      int fontX = 420;
	      int fontY = 30;
	      for (DataModel dm : chartdt) {
	    	  g.setColor( dm.getColor() );
	    	  g.fillRect( area.x + setX , area.y + setY   , 150, 30);
	    	  g.setColor(Color.white);
		      g.drawString( dm.getName()+" : "+dm.getValue() , fontX, fontY);
		      setY += 40;
		      fontY += 40;		      
	      }
	   }



}