package SparkOperate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.spark.launcher.SparkLauncher;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import util.JsonHelper;


public class SparkSubmit extends ServerResource {
	
	@Post
	public String submit(Representation rep) {
		Form form = new Form(rep);
		String algorithm = form.getFirstValue("algorithm"); 
		System.out.println(algorithm);
		Map<String, String> m = JsonHelper.toMap(algorithm);
		System.out.println(m);
		String path = (String) m.get("name");
		String name = path.split("/")[path.split("/").length-1];
		String params = (String) m.get("params");
		System.out.println(name);
		Map<String, String> paramMap = JsonHelper.toMap(params);
//		System.out.println(paramMap);
//		path = "/home/tseg/users/iron/component/relCom/20164805164810.jar";
		SparkLauncher sparkLaucher = new SparkLauncher()
//		 .setSparkHome("/home/tseg/hh/spark-1.4.0-bin-hadoop2.6.0")
               .setAppResource(path)
//               .setMainClass("WordCount")
               .setMaster("yarn-cluster")
//		 .addJar("/home/tseg/hh/spark-1.4.0-bin-hadoop2.6.0b/spark-assembly-1.4.0-hadoop2.6.0.jar")
               .setAppName(name)
//		 .setConf("spark.extraListeners", "org.apache.spark.JsonRelay")
//		 .setConf("spark.slim.host", "tseg0")
//		 .setConf("spark.slim.port", "8123")
               ;
		
		for (Map.Entry<String, String> e : paramMap.entrySet()) {
			sparkLaucher.addAppArgs("-" + e.getKey()).addAppArgs(e.getValue());
		}

       String app_id = "";
       try {
           Process p = sparkLaucher.launch();

           //System.out.println(p.getInputStream());

           BufferedInputStream in = new BufferedInputStream(p.getErrorStream());
           BufferedReader br = new BufferedReader(new InputStreamReader(in));
           String s;
           while ((s = br.readLine()) != null) {
               if (s.contains("INFO YarnClientImpl: Submitted application")) {
                   String[] ss = s.split(" ");
                   app_id = ss[ss.length - 1];
                   return app_id;
//                   break;
               }

           }
//           BufferedInputStream in1 = new BufferedInputStream(p.getInputStream());
//           BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
//           String s1;
//           while ((s1 = br1.readLine()) != null) {
//               System.out.println("~~~~~~~~~~~" + s1);
//
//           }

           p.waitFor();
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       return "RunError";
	}
}
