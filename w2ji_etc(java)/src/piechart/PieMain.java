package piechart;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;




public class PieMain {
	
	
	public static void main(String[] argv) {
		System.out.println("----- PieMain Start -----");
		System.out.println("read file");
		loadFile lf = new loadFile();
		
		;
		
		
		for (DataModel dataModel : lf.dm) {
			System.out.println( "dataModel : "+dataModel.getName()+" , "+dataModel.getColor() )  ;
		}
		
		
		PieChart pc = new PieChart( lf.dm );
			
		
		JFrame fr = new JFrame();
		fr.setTitle( lf.title );
		fr.getContentPane().add( pc );
		fr.setSize(700, 500);
		fr.setVisible(true);
		
		
	}
	

	
}


