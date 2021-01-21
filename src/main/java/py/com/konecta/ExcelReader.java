package py.com.konecta;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

public class ExcelReader {
	
    public static final String SAMPLE_XLSX_FILE_PATH = "/home/konecta/Proyectos/Datos_POI.xlsx";

	public static void procesar(String args) throws IOException, InvalidFormatException {
		
		FileWriter myWriter = new FileWriter("/home/konecta/Proyectos/insert_planilla.sql");

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

        // Retrieving the number of sheets in the Workbook
        //System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        // 1. You can obtain a sheetIterator and iterate over it
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        //System.out.println("Retrieving Sheets using Iterator");
        /*while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
        }*/
        
        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // 1. You can obtain a rowIterator and columnIterator and iterate over them
        //System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
        Iterator<Row> rowIterator = sheet.rowIterator();
        
        int i = 0;
        
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // Now let's iterate over the columns of the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            int nroCol = 0;
            String sql = "insert into dbo.planilla(cedula, linea_presupuestaria, objeto_gasto, "
            		+ "presupuestado, devengado, fuente_financiamiento, programa, subprograma, "
            		+ "des_programa, des_subprograma, tipo_presupuesto, des_tipo_presupuesto, cod_departamento, "
            		+ "departamento, cod_distrito, distrito, cod_ubicacion, des_ubicacion, cod_circunscripcion,  "
            		+ "cod_ubicacion_padre, cod_subprograma, tipo, nro_presupuesto, anho, mes) values(";
            
            while (cellIterator.hasNext()) {
                
	            Cell cell = cellIterator.next();
	            
	            if (i > 0) {
	                String cellValue = dataFormatter.formatCellValue(cell);
	                
	                if (nroCol == 0) {
	                	cellValue = cellValue.substring(0, cellValue.length() - 2);
	                	sql = sql + cellValue + ", ";
	                }
	                if (nroCol == 5 || nroCol == 6 || nroCol == 8 || nroCol == 9 
	                		|| nroCol == 10 || nroCol == 11 || nroCol == 12 || nroCol == 15
	                		|| nroCol == 17 || nroCol == 19 || nroCol == 21
	                		|| nroCol == 23 || nroCol == 24 || nroCol == 25
	                		|| nroCol == 26 || nroCol == 27) {
	                	
	                	sql = sql + cellValue + ", ";
	                }
	                if (nroCol == 13 || nroCol == 14 || nroCol == 16 || nroCol == 18) {
	                	sql = sql + "'" + cellValue + "', ";
	                }
	                if (nroCol == 22 || nroCol == 20) {
	                	cellValue = cellValue.replaceAll("'", "*");
	                	sql = sql + "'" + cellValue + "', ";
	                }
	                nroCol++;
	                //System.out.print(cellValue + "\t");
            	}
            }
            i++;
            sql = sql + "2020, 4); \n";
            myWriter.write(sql);
            //System.out.println(sql);
        }
        
        // Closing the workbook
        workbook.close();
        myWriter.close();
        
	}

}
