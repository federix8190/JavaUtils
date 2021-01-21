package py.com.konecta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
	
	public static String sumarDias(String fecha, int dias) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(fecha));
		c.add(Calendar.DATE, dias);  // number of days to add
		String dt = sdf.format(c.getTime());
		return dt;
	}

}
