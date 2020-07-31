package z29_excel_export;

import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	ReadExcel(){}
	public List<BookVo> Read(String filename) {
		List<BookVo> lists = new ArrayList<BookVo>();
		System.out.println("filename : "+filename);
		try {
			
			FileInputStream file = new FileInputStream( filename );
	        XSSFWorkbook workbook = new XSSFWorkbook( file );
            int row_index = 3;
            int col_index = 0;
            XSSFSheet sheet=workbook.getSheetAt(0);
            int rows=sheet.getPhysicalNumberOfRows();
            for(row_index = 3; row_index <rows ; row_index++){
            	XSSFRow row = sheet.getRow(row_index);
            	if( row != null ) {
            		int cells = 17;
            		BookVo bv = new BookVo();
                	for(col_index = 0; col_index <= cells ; col_index++){
                		XSSFCell cell=row.getCell(col_index);
                		String value="";
                		if(cell == null) {                			
                			continue;
                		}else {
                			switch( cell.getCellType() ){
	                            case XSSFCell.CELL_TYPE_FORMULA:
	                                value=cell.getCellFormula();
	                                break;
	                            case XSSFCell.CELL_TYPE_NUMERIC:
	                                value=cell.getNumericCellValue()+"";
	                                break;
	                            case XSSFCell.CELL_TYPE_STRING:
	                                value=cell.getStringCellValue()+"";
	                                break;
	                            case XSSFCell.CELL_TYPE_BLANK:
	                                value=cell.getBooleanCellValue()+"";
	                                break;
	                            case XSSFCell.CELL_TYPE_ERROR:
	                                value=cell.getErrorCellValue()+"";
	                                break;
                            }
                			//System.out.println(col_index+" : "+value);
                			
                			
                		}
                		
                		if(col_index == 0) {
                			double tmp = Double.parseDouble(value);
                			String v = Integer.toString( (int)tmp );
                			bv.setSeq(v);
                		}else if( col_index == 1 ) {
                			if(value.equals("false")) {
                				bv.setIsbn("false");
                			}else {
                				double tmp = Double.parseDouble(value);
                				NumberFormat f = NumberFormat.getInstance();
                				f.setGroupingUsed(false);
                				bv.setIsbn( f.format(tmp) );
                			}
                			
                			
                		}else if( col_index == 2 ) {
                			bv.setTitle(value);
                		}else if( col_index == 3 ) {
                			bv.setCompany(value);
                		}else if( col_index == 4 ) {
                			double tmp = Double.parseDouble(value);
                			String v = Integer.toString( (int)tmp );
                			bv.setCost_amt(v);
                		}else if( col_index == 5 ) {
                			double tmp = Double.parseDouble(value);
                			String v = Integer.toString( (int)tmp );
                			bv.setSale_amt(v);
                		}else if( col_index == 6 ) {
                			double tmp = Double.parseDouble(value);
                			String v = Integer.toString( (int)tmp );                
                			bv.setList_amt( v );
                		}else if( col_index == 11 ) {
                			bv.setWriter(value);
                		}else if( col_index == 17 ) {
                			bv.setPub_date(value);
                		}
                		                		
                		//System.out.println(row_index+"번 행 : "+col_index+"번 열 값은: "+value);
                	}
                	if( bv.getSeq() != null ) {
                		lists.add(bv);
                	}
                	
            	}
            	
            }
            
            /*
            for (BookVo list : lists) {
				System.out.println( list.getlist());
			}
            */
            
            
            
	        
	        
	        
		}catch (Exception e) {
			System.out.println("ReadExcel : "+e.getMessage());
		}
		return lists;
		
	}
}
