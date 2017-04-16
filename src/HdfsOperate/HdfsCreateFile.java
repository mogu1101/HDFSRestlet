package HdfsOperate;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import Server.HdfsContext;

public class HdfsCreateFile extends ServerResource{
	static Configuration conf = new Configuration();
	static String rootPath = HdfsContext.ROOTPATH;
	
	@Get  
    public String download() throws IOException{  
		// 初始化文件系统
		FileSystem fs = FileSystem.get(conf);
		// 获取url参数
		Form form = getRequest().getResourceRef().getQueryAsForm();
		String path = form.getFirstValue("path");
		// 获取完整路径
		String absPath = rootPath +"/" + path;
		Path p = new Path(rootPath +"/" + path);
		// 获取上一级目录权限
		String lastPath = absPath.substring(0, absPath.length()-absPath.split("/")[absPath.split("/").length-1].length());
		if (fs.getFileStatus(new Path(lastPath)).getPermission().toString().charAt(7) == '-') {
			return "no permission";
		}
		// 参数：是否删除源文件、原路径、目标路径、是否使用原生本地文件系统
		boolean isSuccess = fs.createNewFile(p);
		fs.setPermission(p, FsPermission.valueOf("-rw-rw-rw-"));
		fs.close();
		return isSuccess ? "succeed" : "fail";
    }
}
