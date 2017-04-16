package Server;

import org.apache.hadoop.conf.Configuration;

public class HdfsContext {
//	public static final String HOST = "hdfs://10.103.240.5:9010";
//	public static final String HTTPHOST = "http://10.103.240.5:8088";
//	public static final String ROOTPATH = HOST + "/user/tseg";
	public static final Configuration conf = new Configuration();
	public static final String HOST = "hdfs://10.103.242.66:9090";
	public static final String HTTPHOST = "http://10.103.242.66:8088";
	public static final String ROOTPATH = HOST + "/user/zyrf";
}
