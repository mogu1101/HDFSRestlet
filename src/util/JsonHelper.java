package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {
	/**
     * 将Json对象转换成Map
     * 
     * @param jsonObject
     *            json对象
     * @return Map对象
     * @throws JSONException
     */
    public static Map<String, String> toMap(String jsonString){
    	Map<String, String> result = new HashMap<String, String>();
    	try {
	        JSONObject jsonObject = new JSONObject(jsonString);
	        
	        Iterator iterator = jsonObject.keys();
	        String key = null;
	        String value = null;
	        
	        while (iterator.hasNext()) {
	
	            key = (String) iterator.next();
	            value = jsonObject.getString(key);
	            result.put(key, value);
	
	        }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        return result;

    }
}
