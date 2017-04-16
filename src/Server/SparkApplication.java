package Server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import SparkOperate.SparkAppKill;
import SparkOperate.SparkAppStatus;
import SparkOperate.SparkPackageTrans;
import SparkOperate.SparkSubmit;


public class SparkApplication extends Application{

	@Override
	public Restlet createInboundRoot() {
		// TODO Auto-generated method stub
		Router router = new Router(getContext());
		router.attach("/submit", SparkSubmit.class);
		router.attach("/appStatus", SparkAppStatus.class);
		router.attach("/fileTrans", SparkPackageTrans.class);
		router.attach("/appKill", SparkAppKill.class);

		return router;
	}
}
