package HdfsOperate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import Server.HdfsContext;

public class HdfsReadFile extends ServerResource{
	static Configuration conf = new Configuration();
	static String rootPath = HdfsContext.ROOTPATH;
	
	@Get  
    public String readFile() {  
		String content = "";
		try {
			// 获取url参数
			Form form = getRequest().getResourceRef().getQueryAsForm();
			String path = form.getFirstValue("path");
			FileSystem fs = FileSystem.get(conf);
			Path p = new Path(rootPath + "/" + path);
			System.out.println(p.toString());
			InputStream in = null;
			BufferedReader buff = null;
			// 判断要读取的路径是文件还是目录
			// 如果是文件则直接读取，如果是目录则读取目录中的所有文件内容
			if (fs.isFile(p)) {
				in = fs.open(p);
				buff = new BufferedReader(new InputStreamReader(in));
				String str = null;
				while ((str = buff.readLine()) != null) {
					content += str + "=";
					System.out.println(str);
				}
				buff.close();
				in.close();
			} else {
				//
				FileStatus[] status = fs.listStatus(p);
				for (FileStatus f : status) {
					String pathStr = f.getPath().toString();
					System.out.println(f.getPath().getName() + " " + f.isDirectory());
					if (f.isDirectory()) {
						System.out.println(f.getPath().toString() + " is Directory");
					} else {
						in = fs.open(f.getPath());
						buff = new BufferedReader(new InputStreamReader(in));
						String str = null;
						while ((str = buff.readLine()) != null) {
							content += str + "=";
							System.out.println(str);
						}
						buff.close();
						in.close();
					}
				}
			}
			fs.close();
		} catch (Exception e) {
			return "fail";
		}
		return content;
    }
}
