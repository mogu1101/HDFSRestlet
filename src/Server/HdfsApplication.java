package Server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import HdfsOperate.HdfsCreateFile;
import HdfsOperate.HdfsDelete;
import HdfsOperate.HdfsDownload;
import HdfsOperate.HdfsListStatus;
import HdfsOperate.HdfsMkDir;
import HdfsOperate.HdfsReadFile;
import HdfsOperate.HdfsRename;
import HdfsOperate.HdfsUpload;

public class HdfsApplication extends Application{

	@Override
	public Restlet createInboundRoot() {
		// TODO Auto-generated method stub
		Router router = new Router(getContext());
		router.attach("/listStatus", HdfsListStatus.class);
		router.attach("/download", HdfsDownload.class);
		router.attach("/upload", HdfsUpload.class);
		router.attach("/mkdir", HdfsMkDir.class);
		router.attach("/delete", HdfsDelete.class);
		router.attach("/rename", HdfsRename.class);
		router.attach("/create", HdfsCreateFile.class);
		router.attach("/read", HdfsReadFile.class);
		router.attach("/test", PostTest.class);
		return router;
	}
	
}
