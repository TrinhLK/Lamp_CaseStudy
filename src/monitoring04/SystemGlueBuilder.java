package monitoring04;

import org.javabip.glue.GlueBuilder;

public class SystemGlueBuilder extends GlueBuilder{

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		//(VirtualMachine.running)`-(MySQL.deploy)-(MySQL.start)
		port(C_MySQL.class, "deploy").requires(C_VirtualMachine.class, "running");
		port(C_MySQL.class, "start").requires(C_VirtualMachine.class, "running");
		
		//(VirtualMachine.running)`-(Tomcat.deploy)
		port(C_ApacheTomcat.class, "deploy").requires(C_VirtualMachine.class, "running");
		
		// (MySQL.running)`-(Tomcat.start)
		port(C_ApacheTomcat.class, "start").requires(C_MySQL.class, "running");
		
		// (VirtualMachine.delete)`-(MySQL.undeploy)-(Tomcat.undeploy)
		port(C_MySQL.class, "undeploy").requires(C_VirtualMachine.class, "delete");
		port(C_ApacheTomcat.class, "undeploy").requires(C_VirtualMachine.class, "delete");
		
		// (VirtualMachine.pause)`-(MySQL.undeploy)-(Tomcat.undeploy)
		port(C_MySQL.class, "undeploy").requires(C_VirtualMachine.class, "pause");
		port(C_ApacheTomcat.class, "undeploy").requires(C_VirtualMachine.class, "pause");
		
		//Stop-Fail
		port(C_ApacheTomcat.class, "stop").requires(C_MySQL.class, "makeError");
		port(C_MySQL.class, "makeError").requires(C_ApacheTomcat.class, "stop", C_ApacheTomcat.class, "stop");
		/**
		 * Accepts ports
		 * */
		//(VirtualMachine.delete)`-(MySQL.undeploy)-(Tomcat.undeploy)
		//(VirtualMachine.pause)`-(MySQL.undeploy)-(Tomcat.undeploy)
		port(C_MySQL.class, "undeploy").accepts(C_VirtualMachine.class, "delete", "pause");
		port(C_ApacheTomcat.class, "undeploy").accepts(C_VirtualMachine.class, "delete", "pause");
		port(C_VirtualMachine.class, "delete").accepts(C_MySQL.class, "undeploy", C_ApacheTomcat.class, "undeploy");
		port(C_VirtualMachine.class, "pause").accepts(C_MySQL.class, "undeploy", C_ApacheTomcat.class, "undeploy");
		
		// (VirtualMachine.running)`-(MySQL.deploy)-(MySQL.start)
		port(C_MySQL.class, "deploy").accepts(C_VirtualMachine.class, "running");
		port(C_MySQL.class, "start").accepts(C_VirtualMachine.class, "running");
		port(C_VirtualMachine.class, "running").accepts(C_MySQL.class, "deploy", "start");
		
		// (VirtualMachine.running)`-(Tomcat.deploy)
		port(C_ApacheTomcat.class, "deploy").accepts(C_VirtualMachine.class, "running");
		port(C_VirtualMachine.class, "running").accepts(C_ApacheTomcat.class, "deploy");
		
		// (MySQL.running)`-(Tomcat.start)
		port(C_ApacheTomcat.class, "start").accepts(C_MySQL.class, "running", C_VirtualMachine.class, "running");
		port(C_MySQL.class, "running").accepts(C_ApacheTomcat.class, "start", "running", C_VirtualMachine.class, "running");

		//Stop-Fail
		port(C_ApacheTomcat.class, "stop").accepts(C_MySQL.class, "makeError", C_ApacheTomcat.class, "stop");
		port(C_MySQL.class, "makeError").accepts(C_ApacheTomcat.class, "stop", "stop");
		/**
		 * Data transfer
		 * */
		data(C_VirtualMachine.class, "vId").to(C_MySQL.class, "vId");
		data(C_VirtualMachine.class, "vId").to(C_ApacheTomcat.class, "vId");
		
		data(C_MySQL.class, "sqlInfo").to(C_ApacheTomcat.class, "sqlInfo");
	}

}
