package SparkOperate;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import util.JsonHelper;
import Server.HdfsContext;

public class SparkAppStatus extends ServerResource {
	static String appsURL = HdfsContext.HTTPHOST + "/ws/v1/cluster/apps/";
	@Get
	public String sparkStatus() throws IOException {
		Form form = getRequest().getResourceRef().getQueryAsForm();
		// 从url参数中获取app_id
		String app_id = form.getFirstValue("app_id");
		String url = appsURL + app_id;
		// 从网页获取spark程序运行状态的json字符串
		Document doc = Jsoup.connect(url).ignoreContentType(true).get();
		String content = doc.getElementsByTag("body").toString().replaceAll("<.*?>", "");
		// 解析json
		Map<String, String> appStatus= JsonHelper.toMap(JsonHelper.toMap(content).get("app"));
		// 获取state、finalStatus、diagnostics、startedTime、finishedTime、elapsedTime并将其拼接成json字符串返回
		String jsonStr = "{\"state\":\"" + appStatus.get("state") +
					   "\",\"finalStatus\":\"" + appStatus.get("finalStatus") +
//					   "\",\"diagnostics\":\"" + appStatus.get("diagnostics") +
					   "\",\"startedTime\":\"" + appStatus.get("startedTime") +
					   "\",\"finishedTime\":\"" + appStatus.get("finishedTime") +
					   "\",\"elapsedTime\":\"" + appStatus.get("elapsedTime") + "\"}";
		return jsonStr;
	}
}
