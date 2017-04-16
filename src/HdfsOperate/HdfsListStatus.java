package HdfsOperate;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import Server.HdfsContext;

/**
 * 查询hdfs文件系统         GET方式
 * 访问http://<HOST>:<PORT>/HdfsResource/getPath?name=/
 * 以json格式返回该目录下所有文件信息：[{"id":"String", "name":"String", "isParent":boolean},...]
 */
public class HdfsListStatus extends ServerResource{
	static Configuration conf = new Configuration();
	static String rootPath = HdfsContext.ROOTPATH;
	
	@Get  
    public String listStatus() throws IOException{  
//		while (true){}
		// 初始化文件系统
		FileSystem fs = FileSystem.get(conf);
		// 获取url参数
		Form form = getRequest().getResourceRef().getQueryAsForm();
		String s = form.getFirstValue("name");
		if (s == null) s = "";
		// 获取完整路径
		Path dir = new Path(rootPath +"/" + s);
		// 获取该目录下的所有文件
		FileStatus[] status = fs.listStatus(dir);
		// 拼接字符串返回json格式的信息
		String pathList_json = "[";
		for (int i=0; i<status.length; i++) {
			FileStatus f = status[i];
			// 获取文件权限，若该文件无访问权限则不返回
			String permission = f.getPermission().toString();
			if (permission.charAt(6) == '-') continue;
			String[] nameArr = f.getPath().toString().split("/");
			String name = nameArr[nameArr.length-1];
			boolean isParent = true;
			if (f.isDirectory()) {
				isParent = true;
			} else {
				isParent = false;
			}
			pathList_json += "{\"id\":\"" + f.getPath().toString().substring(rootPath.length()+1) + "\",\"name\":\"" + name + "\",\"isParent\":\"" + isParent + "\"}";
			if (i != status.length-1) {
				pathList_json += ",";
			}
		}
		pathList_json += "]";
		fs.close();
		return pathList_json; 
    }  
	
	@Post
	public String getHdfsPathList(String s) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		if (s == null) s = "";
		Path dir = new Path(rootPath +"/" + s);
		FileStatus[] status = fs.listStatus(dir);
		String pathList_json = "";
		for (FileStatus f : status) {
			
			if (f.isDirectory()) {
				String str = f.getPath().toString() + " Directory";
				System.out.println(str);
				pathList_json += str + "\n";
			} else {
				
				String str = f.getPath().toString() + " File";
				System.out.println(str);
				pathList_json += str + "\n";
			}
		}
		fs.close();
		return pathList_json;
	}
}
