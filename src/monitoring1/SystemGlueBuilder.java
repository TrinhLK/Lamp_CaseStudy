package monitoring1;

import org.javabip.glue.GlueBuilder;

public class SystemGlueBuilder extends GlueBuilder{

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
		// [(Monitor.ready)-(VirtualMachine.running)]`-(MySQL.deploy)-(MySQL.start)
		port(C_VirtualMachine.class, "running").requires(C_SystemsMonitor.class, "VMready");
		port(C_SystemsMonitor.class, "VMready").requires(C_VirtualMachine.class, "running");
		port(C_MySQL.class, "deploy").requires(C_SystemsMonitor.class,"VMready", C_VirtualMachine.class, "running");
		port(C_MySQL.class, "start").requires(C_SystemsMonitor.class,"VMready", C_VirtualMachine.class, "running");
		
		// [(Monitor.ready)-(VirtualMachine.running)]`-(Tomcat.deploy)
		port(C_Tomcat.class, "deploy").requires(C_SystemsMonitor.class,"VMready", C_VirtualMachine.class, "running");
		
		// (MySQL.running)`-(Tomcat.start)
		port(C_Tomcat.class, "start").requires(C_MySQL.class, "running");
		
		// (Monitor.VMdown)-(VirtualMachine.delete)
		port(C_VirtualMachine.class, "delete").requires(C_SystemsMonitor.class, "VMdown");
		port(C_SystemsMonitor.class, "VMdown").requires(C_VirtualMachine.class, "delete");
		
		// (Monitor.VMdown)-(VirtualMachine.pause)
//		port(C_VirtualMachine.class, "pause").requires(C_SystemsMonitor.class, "VMdown");
//		port(C_SystemsMonitor.class, "VMdown").requires(C_VirtualMachine.class, "pause");
		
		// [(Monitor.VMdown)-(VirtualMachine.delete)]`-(MySQL.undeploy)-(Tomcat.undeploy)
		port(C_MySQL.class, "undeploy").requires(C_VirtualMachine.class, "delete", C_SystemsMonitor.class, "VMdown");
		port(C_Tomcat.class, "undeploy").requires(C_VirtualMachine.class, "delete", C_SystemsMonitor.class, "VMdown");
		
		//Tomcat.stop
		/**
		 * Accepts ports
		 * */
		// (Monitor.VMdown)-(VirtualMachine.delete)
		port(C_MySQL.class, "undeploy").accepts(C_VirtualMachine.class, "delete", C_SystemsMonitor.class, "VMdown");
		port(C_Tomcat.class, "undeploy").accepts(C_VirtualMachine.class, "delete", C_SystemsMonitor.class, "VMdown");
		port(C_VirtualMachine.class, "delete").accepts(C_MySQL.class, "undeploy", C_SystemsMonitor.class, "VMdown", C_Tomcat.class, "undeploy");
		port(C_SystemsMonitor.class, "VMdown").accepts(C_MySQL.class, "undeploy", C_VirtualMachine.class, "delete", C_Tomcat.class, "undeploy");

		// [(Monitor.ready)-(VirtualMachine.running)]`-(MySQL.deploy)-(MySQL.start)
		port(C_MySQL.class, "deploy").accepts(C_SystemsMonitor.class,"VMready", C_VirtualMachine.class, "running");
		port(C_MySQL.class, "start").accepts(C_SystemsMonitor.class,"VMready", C_VirtualMachine.class, "running");
				
		// [(Monitor.ready)-(VirtualMachine.running)]`-(Tomcat.deploy)
		port(C_Tomcat.class, "deploy").accepts(C_SystemsMonitor.class,"VMready", C_VirtualMachine.class, "running");
		port(C_SystemsMonitor.class, "VMready").accepts(C_VirtualMachine.class, "running", C_Tomcat.class, "deploy");
		port(C_VirtualMachine.class, "running").accepts(C_SystemsMonitor.class, "VMready", C_Tomcat.class, "deploy");
		
		port(C_SystemsMonitor.class, "VMready").accepts(C_VirtualMachine.class, "running", C_MySQL.class, "deploy", "start");
		port(C_VirtualMachine.class, "running").accepts(C_SystemsMonitor.class, "VMready", C_MySQL.class, "deploy", "start");
		
		// (MySQL.running)`-(Tomcat.start)
		port(C_Tomcat.class, "start").accepts(C_MySQL.class, "running", C_SystemsMonitor.class,"VMready", C_VirtualMachine.class, "running");
		port(C_MySQL.class, "running").accepts(C_Tomcat.class, "start", C_SystemsMonitor.class,"VMready", C_VirtualMachine.class, "running");
		port(C_SystemsMonitor.class,"VMready").accepts(C_MySQL.class, "running", C_Tomcat.class, "start", C_VirtualMachine.class, "running");


		/**
		 * Data transfer
		 * */
		data(C_VirtualMachine.class, "vId").to(C_SystemsMonitor.class, "vId");
		data(C_VirtualMachine.class, "vId").to(C_MySQL.class, "vId");
		data(C_VirtualMachine.class, "vId").to(C_Tomcat.class, "vId");
		
		data(C_MySQL.class, "sqlInfo").to(C_Tomcat.class, "sqlInfo");
	}

}
