package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class PostTest extends ServerResource {
	
	@Post
	public String sparkStatus(Representation rep) throws IOException {
		Form form = new Form(rep);
		String ip1 = form.getValues("ip");
		String ip = form.getFirstValue("ip");
		String username = form.getFirstValue("username");
		String password = form.getFirstValue("password");
		
		return "{\"result\":\"" + ip1 + "\"}";
	}
	
}
