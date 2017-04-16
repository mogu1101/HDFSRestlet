package HdfsOperate;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import Server.HdfsContext;

public class HdfsDownload extends ServerResource{
	static Configuration conf = new Configuration();
	static String rootPath = HdfsContext.ROOTPATH;
	
	@Get  
    public String download() throws IOException{  
		// 初始化文件系统
		FileSystem fs = FileSystem.get(conf);
		// 获取url参数
		Form form = getRequest().getResourceRef().getQueryAsForm();
		String src = form.getFirstValue("src");
		String des = form.getFirstValue("des");
		// 获取完整路径
		String absSrc = rootPath +"/" + src;
		// 获取上一级目录权限
//		String lastPath = absSrc.substring(0, absSrc.length()-absSrc.split("/")[absSrc.split("/").length-1].length());
//		if (fs.getFileStatus(new Path(lastPath)).getPermission().toString().charAt(7) == '-') {
//			return "no permission";
//		}
		Path srcPath = new Path(absSrc);
		Path desPath = new Path(des);
		if (!fs.isDirectory(srcPath) && !fs.isFile(srcPath)) {
			return "file not exist";
		}
		String[] dirs = des.split("/");
		String dir = "";
		for (int i=1; i<dirs.length; i++) {
			dir += "/" + dirs[i];
			makeDirs(dir);
		}
		
		// 参数：是否删除源文件、原路径、目标路径、是否使用原生本地文件系统
		fs.copyToLocalFile(false, srcPath, desPath, true);
		fs.close();
		return "succeed";
    }
	
	// 依据目录存在情况创建文件目录
	public static void makeDirs(String dir) {
		try {
			File f = new File(dir);
			if (!f.exists()) {
				f.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
