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
		List<DataModel> dm  = load_file();
		/*
		for (DataModel dataModel : dm) {
			System.out.println( "dataModel : "+dataModel.getName()+" , "+dataModel.getColor() )  ;
		}
		*/		
		PieChart pc = new PieChart(dm);
			
		
		JFrame fr = new JFrame();
		fr.setTitle("Pie Chart");
		fr.getContentPane().add( pc );
		fr.setSize(700, 500);
		fr.setVisible(true);
		
	}
	
	public static List<DataModel> load_file() {
		List<DataModel> dm = new ArrayList<DataModel>();
		String path = PieMain.class.getResource("").getPath().substring(1); 
		BufferedReader br = null;
	    try{
	        File file = new File(path +"animal_data.txt");
	    	 br = Files.newBufferedReader(Paths.get( path +"animal_data.txt" ));
	    	 String line = "";
	           while((line = br.readLine()) != null){
	        	   DataModel ttt = new DataModel();
	               String array[] = line.split(",");
	               ttt.setName( array[0] );
	               ttt.setValue( Integer.parseInt( array[1] ) );
	               int r = (int) (Math.random() * 255 + 1);
	               int g = (int) (Math.random() * 255 + 1);
	               int b = (int) (Math.random() * 255 + 1);
	               Color rgb = new Color(r,g,b);
	               ttt.setColor(rgb);	               	               
	               dm.add(ttt);
	           }
	    }catch (FileNotFoundException e) {	
	    }catch(IOException e){
	        System.out.println(e);
	    }
	    return dm;	    
	}	
	
}


