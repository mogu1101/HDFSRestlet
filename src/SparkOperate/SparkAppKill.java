package SparkOperate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


public class SparkAppKill extends ServerResource {
	
	@Get
	public String sparkStatus() throws IOException {
		try {
			Form form = getRequest().getResourceRef().getQueryAsForm();
			// 从url参数中获取app_id
			String app_id = form.getFirstValue("app_id");
			Process proc = Runtime.getRuntime().exec("yarn application -kill " + app_id);
			BufferedReader std = new BufferedReader(new InputStreamReader(proc.getInputStream()));  
	        BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			String linein = null;  
	        while ((linein = std.readLine()) != null) {  
	            System.out.println(linein+"\n");  
	        }  
	        while ((linein = err.readLine()) != null) {  
	            System.out.println(linein+"\n");  
	            return "fail";
	        }  
	        int exitVal = proc.waitFor();  
	        System.out.println("Process exitValue: " + exitVal);  
		} catch (Exception e) {
			return "fail";
		}
		return "succeed";
	}
}
