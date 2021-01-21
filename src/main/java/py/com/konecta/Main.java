package py.com.konecta;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import py.com.konecta.hilos.SingleThread;
import py.com.konecta.model.Gasto;
import py.com.konecta.model.Persona;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

public class Main {

	public static List<String> lista = new ArrayList<>();

	public static void main(String[] args) throws InvalidFormatException, IOException {

		System.out.println("");
		// getDataRest();
		// readInput();
		//getAllThread();
		//sort();
		testString();
		System.out.println("");
		System.out.println("Lista : " + lista.size());
	}

	private static void getAllThread() {

		hilos();
		int count = 0;
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for (Thread t : threadSet) {
			System.out.println(
					"threads : " + t.getId() + " - " + t.getName() + " " + t.isDaemon() + " " + t.getPriority());
			count++;
		}
		System.out.println("threads : " + count);
	}

	private static void getDataRest() {

		String datos = HttpClientUtil.getDatos("localhost", 8080, "/senac-recambio/api/estado/listar/all");
		System.out.println("datos : " + datos);
	}

	private static void readInput() {

		System.out.println("Bienvenidos a JavaUtils");
		System.out.println("Ingrese un comando o fede para salir");
		Scanner keyboard = new Scanner(System.in);
		System.out.print(">> ");
		String comando = keyboard.nextLine();

		while (!comando.equals("fede")) {
			procesar(comando);
			System.out.print(">> ");
			comando = keyboard.next();
		}

		keyboard.close();
	}

	private static void procesar(String comando) {

		if (comando.equals("gastos")) {
			gastos();
		} else if (comando.equals("hilos")) {
			hilos();
		} else if (comando.startsWith("sql")) {
			select(comando);
		}
	}

	private static void hilos() {

		SingleThread t1 = new SingleThread();
		t1.start();

		SingleThread t2 = new SingleThread("Hilo 1");
		t2.start();

		SingleThread t3 = new SingleThread("Hilo 2");
		t3.start();
	}

	private static void sort() {

		ArrayList<Persona> milista = new ArrayList<Persona>();
		milista.add(new Persona("Miguel"));
		milista.add(new Persona("Alicia"));
		milista.add(new Persona("Pedro"));

		/*Collections.sort(milista, new Comparator<Persona>() {
			public int compare(Persona p1, Persona p2) {
				return p1.getNombre().compareTo(p2.getNombre());
			}
		});*/
		
		Collections.sort(milista, (Persona p1, Persona p2) -> p1.getNombre().compareTo(p2.getNombre()));

		for (Persona p : milista) {
			System.out.println(p.getNombre());
		}

	}
	
	private static void testString() {

		StringBuilder cadena = new StringBuilder();
		long numero1 = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {

			cadena.append("hola");
			cadena.append("que");
			cadena.append("tal");
			cadena.append("estas");

		}
		long numero2 = System.currentTimeMillis();
		System.out.println(numero2 - numero1);
	}

	private static void gastos() {

		ArrayList<Gasto> lista = new ArrayList<Gasto>();

		lista.add(new Gasto("A", 80));
		lista.add(new Gasto("B", 50));
		lista.add(new Gasto("C", 70));
		lista.add(new Gasto("D", 95));

		double totalPago = lista.stream().mapToDouble(gasto -> gasto.getImporte() * 1.21).filter(gasto -> gasto < 100).sum();

		System.out.println(totalPago);
	}

	private static void select(String comando) {

		// System.out.println("Usted ingreso : " + comando);
		if (comando.startsWith("sql")) {
			// String sql = comando.substring(4);
			String sql = "select cedula_identidad from etl_sipoi_legajo_personal";
			List<String> lista1 = select(Database.SQLSERVER, "192.168.11.32", "CTE_Legajos", "cte_user", "konecta123",
					sql, "cedula_identidad");
			// System.out.println("Ejecutar : " + lista1.size());

			sql = "select cedula_identidad from usuario";
			List<String> lista2 = select(Database.SQLSERVER, "localhost", "csj_poi", "sa", "Konecta_123", sql,
					"cedula_identidad");
			// System.out.println("Ejecutar : " + lista2.size());

			List<String> tmp = new ArrayList<String>();
			for (String item : lista2) {
				if (!lista1.contains(item)) {
					tmp.add(item);
					System.out.println(item);
				}
			}
			// System.out.println("Ejecutar : " + tmp.size());
		}
	}

	private static List<String> select(int tipo, String host, String db, String user, String pass, String sql,
			String col) {

		List<String> lista = Database.select(tipo, host, db, user, pass, sql, col);
		return lista;
	}

	private static void buscarDestinos() {

		String sql = "select usuario from mimundo.Usuario where tipo_usuario = 'TMP' limit 100";
		List<String> cols = getList(new String[] { "usuario" });
		List<Object[]> lineas = Database.select(Database.POSTGRES, "10.150.44.14", "vasdev", "konecta_emp",
				"konecta2018", sql, cols);
		// printLista(lineas);

		for (Object[] l : lineas) {

			System.out.println("");
			String nro = (String) l[0];
			System.out.println("Linea : " + nro);
			String url = "/servicio-services/api/elementos-grupo-destino?identificador=" + nro;
			String datos = HttpClientUtil.getDatos("10.150.44.100", 8080, url);
			System.out.println(datos);
			System.out.println("");
		}
	}

	private static List<String> getList(String[] items) {

		List<String> res = new ArrayList<String>();
		for (String i : items) {
			res.add(i);
		}
		return res;
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

}