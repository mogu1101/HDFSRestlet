package HdfsOperate;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import Server.HdfsContext;

public class HdfsUpload extends ServerResource{
	static Configuration conf = new Configuration();
	static String rootPath = HdfsContext.ROOTPATH;
	
	@Get  
    public String download() throws IOException{  
		// 初始化文件系统
		FileSystem fs = FileSystem.get(conf);
		System.out.println("上传成功");
		// 获取url参数
		Form form = getRequest().getResourceRef().getQueryAsForm();
		String src = form.getFirstValue("src");
		String des = form.getFirstValue("des");
		System.out.println(src + "---------------------------------");
		System.out.println(des + "---------------------------------");
		// 获取完整路径
		String absDes = rootPath + "/" + des;
		System.out.println(absDes + "---------------------------------");
		// 获取上一级目录权限
//		String lastPath = absDes.substring(0, absDes.length()-absDes.split("/")[absDes.split("/").length-1].length());
//		if (fs.getFileStatus(new Path(lastPath)).getPermission().toString().charAt(7) == '-') {
//			return "no permission";
//		}
		Path srcPath = new Path(src);
		Path desPath = new Path(absDes);
		// 参数：是否删除源文件、原路径、目标路径、是否使用原生本地文件系统
		fs.copyFromLocalFile(srcPath, desPath);
		fs.close();
		return "succeed";
    }
}
