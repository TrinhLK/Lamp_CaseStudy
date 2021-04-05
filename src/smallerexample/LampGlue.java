package smallerexample;

import org.javabip.glue.GlueBuilder;

public class LampGlue extends GlueBuilder{

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		/**
		 * --------------------------------------------------------------------------------------------
		 * VM and MySQL
		 * --------------------------------------------------------------------------------------------
		 * */
		port(C_MySQL.class,"deploy").requires(C_VirtualMachine.class, "running");
		port(C_MySQL.class,"start").requires(C_VirtualMachine.class, "running");
//		port(MySQL.class,"running").requires(VirtualMachine.class, "running");
		//VM shutdowned --> MySQL undeployed
//		port(C_MySQL.class,"undeploy").requires(C_VirtualMachine.class, "delete");
		port(C_VirtualMachine.class, "delete").requires(C_MySQL.class,"undeploy");
		
//		port(C_MySQL.class,"undeploy").requires(C_VirtualMachine.class, "pause");
		port(C_VirtualMachine.class, "pause").requires(C_MySQL.class,"undeploy");
		
		//MySQL undeploy --> Tomcat Stop
//		port(C_MySQL.class,"undeploy").requires(C_Tomcat.class, "stop");
		/**
		 * --------------------------------------------------------------------------------------------
		 * Tomcat and MySQL
		 * --------------------------------------------------------------------------------------------
		 * */
//		//MySQL running --> Tomcat can run
		port(C_Tomcat.class,"deploy").requires(C_VirtualMachine.class, "running");
		port(C_Tomcat.class,"start").requires(C_MySQL.class, "running");
		port(C_Tomcat.class,"stop").requires(C_MySQL.class, "running");
		//VM shutdowned --> MySQL undeployed
//		port(C_Tomcat.class,"undeploy").requires(C_VirtualMachine.class, "delete");
		port(C_VirtualMachine.class, "delete").requires(C_Tomcat.class,"undeploy");
		
//		port(C_Tomcat.class,"undeploy").requires(C_VirtualMachine.class, "pause");
		port(C_VirtualMachine.class, "pause").requires(C_Tomcat.class,"undeploy");
		/**
		 * --------------------------------------------------------------------------------------------
		 * Apache and Tomcat
		 * --------------------------------------------------------------------------------------------
		 * */
//		//Tomcat running --> Apache can run
//		port(Apache.class,"deploy").requires(VirtualMachine.class, "running");
//		port(Apache.class,"start").requires(Tomcat.class, "running");
//		//VM shutdowned --> Apache undeployed
//		port(Apache.class,"undeploy").requires(VirtualMachine.class, "delete");
//		port(Apache.class,"undeploy").requires(VirtualMachine.class, "pause");
		
		/**
		 * --------------------------------------------------------------------------------------------
		 * Accepts part
		 * --------------------------------------------------------------------------------------------
		 * */
		//port(VirtualMachine.class,"active").accepts(MySQL.class, "deploy", "start", Tomcat.class, "deploy", "start", Apache.class, "deploy", "start");
		port(C_VirtualMachine.class,"running").accepts(C_MySQL.class, "deploy", "start", C_Tomcat.class, "deploy", "start");
		
		port(C_MySQL.class, "deploy").accepts(C_VirtualMachine.class, "running");
		port(C_MySQL.class, "start").accepts(C_VirtualMachine.class,  "running", C_Tomcat.class, "start");
		port(C_MySQL.class, "running").accepts(C_VirtualMachine.class, "running", C_Tomcat.class, "start");
		
		port(C_Tomcat.class, "deploy").accepts(C_VirtualMachine.class, "running");
		port(C_Tomcat.class, "start").accepts(C_VirtualMachine.class, "running", C_MySQL.class, "running", "start");
		port(C_Tomcat.class, "stop").accepts(C_VirtualMachine.class, "running", C_MySQL.class, "running", "start");
		
//		port(C_MySQL.class,"undeploy").requires(C_Tomcat.class, "stop");
//		port(C_MySQL.class,"running").requires(C_Tomcat.class, "stop");
//		port(C_Tomcat.class, "stop").accepts(C_MySQL.class, "undeploy", "running");
		
//		
//		port(Apache.class, "deploy").accepts(VirtualMachine.class, "running");
//		port(Apache.class, "start").accepts(VirtualMachine.class, "running", Tomcat.class, "running", "start", MySQL.class, "running", "start");
//		port(Apache.class, "running").accepts(VirtualMachine.class, "running", Tomcat.class, "start", "running", MySQL.class, "running", "start");
//		
//		port(VirtualMachine.class, "delete").accepts(Apache.class,"undeploy",Tomcat.class,"undeploy",MySQL.class,"undeploy");
//		port(VirtualMachine.class, "pause").accepts(Apache.class,"undeploy",Tomcat.class,"undeploy",MySQL.class,"undeploy");
//		port(Apache.class,"undeploy").accepts(VirtualMachine.class, "delete", "pause", Tomcat.class,"undeploy", MySQL.class,"undeploy");
//		port(Tomcat.class,"undeploy").accepts(VirtualMachine.class, "delete", "pause", MySQL.class,"undeploy", Apache.class,"undeploy");
//		port(MySQL.class,"undeploy").accepts(VirtualMachine.class, "delete", "pause", Tomcat.class,"undeploy", Apache.class,"undeploy");
		
		port(C_VirtualMachine.class, "delete").accepts(C_MySQL.class, "undeploy", C_Tomcat.class, "undeploy");
		port(C_VirtualMachine.class, "pause").accepts(C_MySQL.class, "undeploy", C_Tomcat.class, "undeploy");
//		port(Apache.class,"undeploy").accepts(VirtualMachine.class, "delete", "pause", Tomcat.class,"undeploy", MySQL.class,"undeploy");
//		port(Tomcat.class,"undeploy").accepts(VirtualMachine.class, "delete", "pause", MySQL.class,"undeploy", Apache.class,"undeploy");
		port(C_MySQL.class,"undeploy").accepts(C_VirtualMachine.class, "delete", "pause");
		port(C_Tomcat.class,"undeploy").accepts(C_VirtualMachine.class, "delete", "pause");
		/**
		 * --------------------------------------------------------------------------------------------
		 * Data transfer
		 * --------------------------------------------------------------------------------------------
		 * */
		data(C_VirtualMachine.class, "vId").to(C_MySQL.class, "vId");
		data(C_VirtualMachine.class, "vId").to(C_Tomcat.class, "vId");
//		data(VirtualMachine.class, "vId").to(Apache.class, "vId");
		
		data(C_MySQL.class, "sqlInfo").to(C_Tomcat.class, "sqlInfo");
//		data(Tomcat.class, "tInfo").to(Apache.class, "tInfo");
	}

}
