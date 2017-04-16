package HdfsOperate;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import Server.HdfsContext;

public class HdfsDelete extends ServerResource{
	static Configuration conf = new Configuration();
	static String rootPath = HdfsContext.ROOTPATH;
	
	@Get  
	public String deleteFile() throws IOException{  
		// 初始化文件系统
		FileSystem fs = FileSystem.get(HdfsContext.conf);
		// 获取url参数
		Form form = getRequest().getResourceRef().getQueryAsForm();
		String path = form.getFirstValue("path");
		// 获取完整路径
		String absPath = rootPath +"/" + path;
		Path p = new Path(absPath);
		System.out.println(p.toString());
		if (!fs.isDirectory(p) && !fs.isFile(p)) {
			return "file not exist";
		}
		if (!path.startsWith("iron/")) {
			return "file not exist";
		}
		// 获取上一级目录权限
		String lastPath = absPath.substring(0, absPath.length()-absPath.split("/")[absPath.split("/").length-1].length());
		if (fs.getFileStatus(new Path(lastPath)).getPermission().toString().charAt(7) == '-') {
			return "no permission";
		}
		
		// 参数：路径、是否递归删除
		boolean isSuccess = fs.delete(p, true);
		fs.close();
		
		return isSuccess ? "succeed" : "fail";
    }
}
