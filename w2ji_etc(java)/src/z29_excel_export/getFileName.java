package z29_excel_export;

public class getFileName {
	public String getName(String str) {
		int pos = str.lastIndexOf( "." );
		String _fileName = str.substring(0, pos);
		return _fileName;
	}
}
