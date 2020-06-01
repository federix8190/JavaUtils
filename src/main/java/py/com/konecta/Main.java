package py.com.konecta;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

public class Main {
		
	public static void main(String[] args) throws InvalidFormatException, IOException {
		
		System.out.println("");
		
		/*System.out.println("Testeando llamada HTTP");
		String datos = getDatos("localhost", 80, "/test/saldo.json");
		System.out.println(datos);*/
		
		String[] cols = {"equipo_1", "equipo_2", "goles_1"};
		List<Object[]> paises = Database.select(Database.POSTGRES, "localhost", "nucleo", "federix", "konecta", "resultados", cols);
		printLista(paises);
		
		List<String> datos = Database.getCTE();
		System.out.println(datos);
		
		System.out.println("");
		ExcelReader.procesar("");
		
		System.out.println("");
	}
	
	public static void printLista(List<Object[]> lista) {
		
		System.out.println("");
		int i = 1;
		for (Object[] datos : lista) {
			System.out.println("");
			System.out.print("Item " + i + " : ");
			for (Object s : datos) {
				System.out.print(s + ", ");
			}
			i++;
		}
		System.out.println("");
	}
	
	public static String getDatos(String server, int port, String path) {
        
        InputStream is = null;
                
        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            HttpHost host = new HttpHost(server, port);
            
            HttpEntityEnclosingRequest request;
            request = new BasicHttpEntityEnclosingRequest("GET", path);
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