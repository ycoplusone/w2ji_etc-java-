package piechart;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class loadFile {
	public List<DataModel> dm = new ArrayList<DataModel>();
	public String title = "";
	loadFile(){
		loadingfile();
	}
	
	public void loadingfile() {
		System.out.println("--- loadFile : start ---");
		
		String path = loadFile.class.getResource("").getPath().substring(1); 
		BufferedReader br = null;
	    try{
	        //File file = new File(path +"animal_data.txt");
	    	 br = Files.newBufferedReader(Paths.get( path +"AnimalData.txt" ));
	    	 String line = "";
	           while((line = br.readLine()) != null){
	        	   System.out.println("line  : "+line);
	        	   if(title.equals("")) {
	        		   title = line;
	        	   }else {
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
	        	   
	           }
       
	    }catch (FileNotFoundException e) {	
	    }catch(IOException e){
	        System.out.println(e);
	    }
	    
    
	}	

}
