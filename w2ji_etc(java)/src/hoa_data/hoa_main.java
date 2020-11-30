package hoa_data;

import java.io.FileInputStream;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class hoa_main {
	


	
	public static void main(String[] args) {
		
		String inputFile = "F:\\docx\\input.doc";
        String outputFile = "F:\\docx\\output.doc";
        POIFSFileSystem fs = null;

        String counts = "";

        try {
            for (int i = 0; i < 3; i++) {
                counts += String.valueOf(i) + "; ";
            }
            fs = new POIFSFileSystem(new FileInputStream(inputFile));
            HWPFDocument doc = new HWPFDocument(fs);
            doc = replaceText(doc, "$count", counts);
            saveWord(outputFile, doc);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

		
	}
}
