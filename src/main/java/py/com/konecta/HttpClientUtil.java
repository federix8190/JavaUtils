package py.com.konecta;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

public class HttpClientUtil {
	
	public static String getDatos(String server, int port, String path) {
		
		return getDatos(server, port, path, null);
	}
	
	public static String getDatos(String server, int port, String path, String token) {
        
        InputStream is = null;
                
        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            HttpHost host = new HttpHost(server, port);
            
            HttpEntityEnclosingRequest request;
            request = new BasicHttpEntityEnclosingRequest("GET", path);
            if (token != null) {
            	request.setHeader("Authorization", token);
            }
            HttpResponse res = client.execute(host, request);
            HttpEntity entityResponse = res.getEntity();
            is = entityResponse.getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer);
            String str = writer.toString();
            return str;
            /*JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(str);
            return obj;*/
            
        } catch (Exception e) {
            
        	System.out.println("Ocurrio un error : " + e.getMessage());
            return null;
            
        } finally {
        	
            try {
                if (is != null) is.close(); 
            } catch (Exception e) {
                System.out.println("Error al cerrar InputStream : " + e.getMessage());
            }
        }
    }

}
