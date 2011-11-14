package conexion;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateDeserializer implements JsonDeserializer<Date> {
	@Override
	public Date deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		java.util.Date parsedDate =null;
		   try {
			   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			   parsedDate = dateFormat.parse(json.getAsString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return new Date(parsedDate.getTime());
	}
}
