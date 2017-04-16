package SparkOperate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

public class SparkPackageTrans extends ServerResource {
	
	@Post
	public String sparkStatus(Representation rep) throws IOException {
		Form form = new Form(rep);
		String ip = form.getFirstValue("ip");
		String username = form.getFirstValue("username");
		String password = form.getFirstValue("password");
		String op = form.getFirstValue("op");
		String src = form.getFirstValue("src"); 
		String des = form.getFirstValue("des");
		System.out.println(ip);
		System.out.println(username);
		System.out.println(password);
		System.out.println(op);
		System.out.println(src);
		System.out.println(des);
		InputStream in = null;  
		try {
			Process proc;
			if (op.equals("upload")) {
				// 若目标文件路径不存在，则创建
				String[] dirs = des.split("/");
				String dir = "";
				for (int i=1; i<dirs.length; i++) {
					dir += "/" + dirs[i];
					makeDirs(dir);
				}
				String uploadCmd = "scp -r " + username + "@" + ip + ":" + src + " " + des;
				proc = Runtime.getRuntime().exec(uploadCmd);
				System.out.println(uploadCmd); 
			} else if (op.equals("download")){
				String downloadCmd = "scp -r " + src + " " + username + "@" + ip + ":" + des;
				proc = Runtime.getRuntime().exec(downloadCmd);
				System.out.println(downloadCmd);
			} else {
				return "operate error";
			}
			BufferedReader std = new BufferedReader(new InputStreamReader(proc.getInputStream()));  
            BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			String linein = null;  
            while ((linein = std.readLine()) != null) {  
                System.out.println(linein+"\n");  
            }  
            while ((linein = err.readLine()) != null) {  
                System.out.println("INFO: " + linein+"\n");  
                if (linein.contains("Error") || linein.contains("error") || linein.contains("Exception") || linein.contains("exception")) {
                	return "fail";
                }
            }  
            System.out.println(System.currentTimeMillis()+"文件传输命令执行完成...");
            int exitVal = proc.waitFor();  
            System.out.println("Process exitValue: " + exitVal);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		return "succeed";
	}
	
	// 依据目录存在情况创建文件目录
	public static void makeDirs(String dir) {
		try {
			File f = new File(dir);
			if (!f.exists()) {
				f.mkdirs();
				System.out.println("创建目录：" + f.toPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Post
//	public String sparkStatus(Representation rep) throws IOException {
//		Form form = new Form(rep);
//		String ip = form.getFirstValue("ip");
//		String username = form.getFirstValue("username");
//		String password = form.getFirstValue("password");
//		String op = form.getFirstValue("op");
//		String src = form.getFirstValue("src"); 
//		String des = form.getFirstValue("des");
//		System.out.println(ip);
//		System.out.println(username);
//		System.out.println(password);
//		System.out.println(op);
//		System.out.println(src);
//		System.out.println(des);
//		try {
//			Connection con = new Connection(ip);
//			con.connect();
//			boolean isAuthed = con.authenticateWithPassword(username, password);
//			if (!isAuthed) {
//				return "username or password error";
//			}
//			Session ssh = con.openSession();
//			// 建立SCP客户端
//			SCPClient scp = con.createSCPClient();
//			if (op.equals("upload")) {
//				// 上传本地文件
//				scp.get(src, des);
//			} else if (op.equals("download")){
//				// 下载文件到本地ip
//				scp.put(src, des);
//			} else {
//				return "operate error";
//			}
//            con.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return e.getMessage();
//		}
//		return "succeed";
//	}
}
