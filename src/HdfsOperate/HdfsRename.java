package HdfsOperate;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import Server.HdfsContext;

public class HdfsRename extends ServerResource{
	static Configuration conf = new Configuration();
	static String rootPath = HdfsContext.ROOTPATH;
	
	@Get
	public String rename() throws IllegalArgumentException, IOException {
		Form form = getRequest().getResourceRef().getQueryAsForm();
		String path = form.getFirstValue("path");
		String rename = form.getFirstValue("rename");

		FileSystem fs = FileSystem.get(conf);
		// 获取文件绝对路径
		String absPath = rootPath + "/" + path;
		String oldName = absPath.split("/")[absPath.split("/").length-1];
		String lastPath = absPath.substring(0, absPath.length()-oldName.length());
		// 文件重命名后的绝对路径
		String newPath = lastPath + rename;
//		System.out.println(absPath + "\n" + newPath);
		Path p1 = new Path(absPath);		
		Path p2 = new Path(newPath);
		// 文件写权限检测
//		System.out.println(fs.getFileStatus(new Path(lastPath)).getPermission().toString());
		if (fs.getFileStatus(new Path(lastPath)).getPermission().toString().charAt(7) == '-') {
			return "no permission";
		}
		// 重命名并返回是否成功
		boolean isSuccess = fs.rename(p1, p2);
		fs.close();
		System.out.println("重命名成功");

		return isSuccess ? "succeed" : "fail";
	}

}
