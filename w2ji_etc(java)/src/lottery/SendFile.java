package lottery;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class SendFile {
	String url = MemberVarible.url+"/fileupload";
	
	public void sendPost( File f1 ) throws Exception {
		String USER_AGENT = "Mozilla/5.0";
		String boundary = "--12o90amo23koeq8d";
		String CrLf = "\r\n";
        
		
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //add reuqest header
        con.setDoOutput(true);              //항상 갱신된내용을 가져옴.
        con.setRequestMethod("POST");
        con.setUseCaches(false);
        con.setChunkedStreamingMode(1024);
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type" , "multipart/form-data; boundary="+boundary);
        //con.setConnectTimeout(10000);       //컨텍션타임아웃 10초
        //con.setReadTimeout(5000);           //컨텐츠조회 타임아웃 5총

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        String header = "--"+boundary+CrLf;
        header += "Content-Disposition: form-data ; name=\"uploadfile\";filename=\""+f1.getName()+"\""+CrLf;
        header += "Content-Type : text/plain"+CrLf;
        
        wr.write(header.getBytes());
        FileInputStream upload1 = new FileInputStream(f1);
        int read_size = 1024;
        int remain_size;
        while( (remain_size = upload1.available() ) >0 ) {
        	byte[] read_data;
        	read_data = remain_size >= read_size ? new byte[read_size]:new byte[remain_size];
        	upload1.read(read_data);
        	wr.write(read_data);
        	wr.flush();
        }
        
        wr.write(("--"+boundary+"--"+CrLf).getBytes());
        wr.write(("--"+boundary+"--"+CrLf).getBytes());
        wr.flush();        
        wr.close();
        upload1.close();
        /*
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);        
        System.out.println("Response Code : " + responseCode);

        Charset charset = Charset.forName("UTF-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),charset));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        */

	}
	
	public String sendtest( File file0 , File file1 , File file2 , GiftVO gvo) throws Exception {
		String charset = "UTF-8";
		String param = "value";

		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.

		//System.out.println("sendfile : "+file.getName());
		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

		try (
		    OutputStream output = connection.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
		) {
			
			writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"nickname\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( gvo.getNickname() ).append(CRLF).flush();

		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"local\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append(gvo.getLocal()).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"rankgift\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append(gvo.getRankgift()).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"tel\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append(gvo.getTel()).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"kakao\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( gvo.getKakao() ).append(CRLF).flush();

		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"facebook\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( gvo.getFacebook() ).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"teletc\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( gvo.getTeletc() ).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"photo_comment\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( gvo.getPhoto_comment() ).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"photo_etc\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( gvo.getPhoto_etc() ).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"amt\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( gvo.getAmt() ).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"prodct\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( gvo.getProdct() ).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"info_id\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( gvo.getInfo_id() ).append(CRLF).flush();
		    
		    writer.append("--" + boundary).append(CRLF);
		    writer.append("Content-Disposition: form-data; name=\"endpoint\"").append(CRLF);
		    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		    writer.append(CRLF).append( "endpoint" ).append(CRLF).flush();
		    
		    if( file0 != null ) {
		    	writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"file0\"; filename=\"" + file0.getName() + "\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
			    writer.append(CRLF).flush();
			    Files.copy(file0.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.		    	
		    }		    
		    if( file1 != null ) {
		    	writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"file1\"; filename=\"" + file1.getName() + "\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
			    writer.append(CRLF).flush();
			    Files.copy(file1.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.		    	
		    }
		    if( file2 != null ) {
		    	writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"file2\"; filename=\"" + file2.getName() + "\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
			    writer.append(CRLF).flush();
			    Files.copy(file2.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.		    	
		    }
		    

		    
		    output.flush(); // Important before continuing with writer!
		    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

		    // End of multipart/form-data.
		    writer.append("--" + boundary + "--").append(CRLF).flush();
		    

		}
		
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
		
        return response.toString();
		/*
		// Request is lazily fired whenever you need to obtain information about response.
		int responseCode = ((HttpURLConnection) connection).getResponseCode();
		System.out.println(responseCode); // Should be 200
		*/
		
		
		
	}
	
	

}
