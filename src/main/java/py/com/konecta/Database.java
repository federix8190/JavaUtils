package py.com.konecta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Database {
	
	public static final int POSTGRES = 0;
	public static final int SQLSERVER = 1;
	
	public static JSONArray select(int tipo, String host, String database, 
			String user, String pass, String tabla, String[] columnas) {
    	
    	Connection conn = null;
        Statement stmt = null;
        String driverClass = "";
        String url = "";
        if (tipo == POSTGRES) {
        	driverClass = "org.postgresql.Driver";
        	url = "jdbc:postgresql://" + host + ":5432/" + database;
        } else if (tipo == SQLSERVER) {
        	driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        	url = "jdbc:sqlserver://" + host + ":1433/" + database;
        }
        
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);
        List<Object[]> datos = new ArrayList<Object[]>();
        JSONArray json = new JSONArray();
        
        try {
        	
        	Class.forName(driverClass);
            conn = DriverManager.getConnection(url, props);
            stmt = conn.createStatement();
            String sql = "SELECT ";
            List<String> cols = Arrays.asList(columnas);
            for (String c : cols.subList(0, cols.size() - 1)) {
            	sql = sql + c + ", ";
            }
            sql = sql + cols.get(cols.size() - 1) + " FROM " + tabla;
            System.err.println("\nSQL : " + sql + "\n");
            
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	Object[] d = new String[cols.size()];
                String equipo_1 = rs.getString("equipo_1");
                String equipo_2 = rs.getString("equipo_2");
                String goles_1 = rs.getInt("goles_1") + "";
                d[0] = equipo_1;
                d[1] = equipo_2;
                d[2] = goles_1;
                json.add(d);
            }
            rs.close();
            
        } catch (SQLException se) {
        	System.err.println("Error al ejecutar sentencia SQL : " + se.getMessage());
        } catch (Exception e) {            
        	System.err.println("Error Generico : " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
            	System.err.println("Error al cerrar sentencia a BBDD : " + se.getMessage());
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
            	System.err.println("Error al cerrar conexion a BBDD : " + se.getMessage());
            }
        }
        
        return json;
    }
	
	public static List<String> getEquipos() {
    	
    	Connection conn = null;
        Statement stmt = null;
        String url = "jdbc:postgresql://localhost:5432/nucleo";
        Properties props = new Properties();
        props.setProperty("user", "federix");
        props.setProperty("password", "konecta");
        List<String> lineas = new ArrayList<String>();

        try {
        	
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, props);
            stmt = conn.createStatement();
            String sql = "select fecha, equipo_1, goles_1, equipo_2, goles_2 from resultados";
            
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String equipo_1 = rs.getString("equipo_1");
                lineas.add(equipo_1);
            }
            rs.close();
            
        } catch (SQLException se) {
        	System.err.println("Error al ejecutar sentencia SQL : " + se.getMessage());
        } catch (Exception e) {            
        	System.err.println("Error Generico : " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
            	System.err.println("Error al cerrar sentencia a BBDD : " + se.getMessage());
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
            	System.err.println("Error al cerrar conexion a BBDD : " + se.getMessage());
            }
        }
        
        return lineas;
    }
	
	public static List<String> getCTE() {
    	
    	Connection conn = null;
        Statement stmt = null;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=csj_poi";
        Properties props = new Properties();
        props.setProperty("user", "sa");
        props.setProperty("password", "Konecta_123");
        List<String> lineas = new ArrayList<String>();
        
        try {
        	
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, props);
            stmt = conn.createStatement();
            String sql = "SELECT nombre FROM departamento";
            
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                lineas.add(nombre);
            }
            rs.close();
            
        // Errores
        } catch (SQLException se) {
        	System.err.println("Error al ejecutar sentencia SQL : " + se.getMessage());
        } catch (Exception e) {            
        	System.err.println("Error Generico : " + e.getMessage());
        // Cerrar conexiones	
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
            	System.err.println("Error al cerrar sentencia a BBDD : " + se.getMessage());
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
            	System.err.println("Error al cerrar conexion a BBDD : " + se.getMessage());
            }
        }
        return lineas;
        	
	}

}
