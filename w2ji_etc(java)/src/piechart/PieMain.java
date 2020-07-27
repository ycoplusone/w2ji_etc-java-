package piechart;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JFrame;




public class PieMain{
	
	
	public static void main(String[] argv) {
		System.out.println("----- PieMain Start -----");
		System.out.println("read file");
		loadFile lf = new loadFile();
		
		JFrame fr = new JFrame();
		/*
		fr.addComponentListener(new ComponentAdapter(){  
		       public void componentResized(ComponentEvent evt) {
		           Component c = (Component)evt.getSource();
		           //update your view/canvas
		           System.out.println("asdfasdf");
		       }
		});
		*/
		PieChart pc = new PieChart( lf.dm  , lf.title );		
		fr.setTitle( "this is pie chart" );
		fr.getContentPane().add( pc );
		fr.setSize(700, 500);
		fr.setVisible(true);
	}
	
}


