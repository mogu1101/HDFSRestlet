package Server;

import org.restlet.Component;
import org.restlet.data.Protocol;


public class HdfsServer {
	public static void main(String[] args) throws Exception {
		Component comp = new Component();
		comp.getServers().add(Protocol.HTTP, 12345);
		comp.getDefaultHost().attach("/HdfsResource", new HdfsApplication());
		comp.getDefaultHost().attach("/SparkResource", new SparkApplication());
		comp.start();
	}
}
