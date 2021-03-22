package components;

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
		port(MySQL.class,"deploy").requires(VirtualMachine.class, "running");
		port(MySQL.class,"start").requires(VirtualMachine.class, "running");
		//VM shutdowned --> MySQL undeployed
		port(MySQL.class,"undeploy").requires(VirtualMachine.class, "delete");
		port(MySQL.class,"undeploy").requires(VirtualMachine.class, "pause");
		
		/**
		 * --------------------------------------------------------------------------------------------
		 * Tomcat and MySQL
		 * --------------------------------------------------------------------------------------------
		 * */
		//MySQL running --> Tomcat can run
		port(Tomcat.class,"deploy").requires(VirtualMachine.class, "running");
		port(Tomcat.class,"start").requires(MySQL.class, "running");
		//VM shutdowned --> MySQL undeployed
		port(Tomcat.class,"undeploy").requires(VirtualMachine.class, "delete");
		port(Tomcat.class,"undeploy").requires(VirtualMachine.class, "pause");
		
		/**
		 * --------------------------------------------------------------------------------------------
		 * Apache and Tomcat
		 * --------------------------------------------------------------------------------------------
		 * */
		//Tomcat running --> Apache can run
		port(Apache.class,"deploy").requires(VirtualMachine.class, "running");
		port(Apache.class,"start").requires(Tomcat.class, "running");
		//VM shutdowned --> Apache undeployed
		port(Apache.class,"undeploy").requires(VirtualMachine.class, "delete");
		port(Apache.class,"undeploy").requires(VirtualMachine.class, "pause");
		
		/**
		 * --------------------------------------------------------------------------------------------
		 * Accepts part
		 * --------------------------------------------------------------------------------------------
		 * */
		//port(VirtualMachine.class,"active").accepts(MySQL.class, "deploy", "start", Tomcat.class, "deploy", "start", Apache.class, "deploy", "start");
		port(VirtualMachine.class,"running").accepts(MySQL.class, "deploy", "start", "running", Tomcat.class, "deploy", "start", "running", Apache.class, "deploy", "start", "running");
		
		port(MySQL.class, "deploy").accepts(VirtualMachine.class, "running");
		port(MySQL.class, "start").accepts(VirtualMachine.class,  "running");
		port(MySQL.class, "running").accepts(VirtualMachine.class, "running", Tomcat.class, "start", Apache.class, "start");
		
		port(Tomcat.class, "deploy").accepts(VirtualMachine.class, "running");
		port(Tomcat.class, "start").accepts(VirtualMachine.class, "running", MySQL.class, "running", "start");
		port(Tomcat.class, "running").accepts(VirtualMachine.class, "running", MySQL.class, "running", Apache.class, "start", "running");
		
		port(Apache.class, "deploy").accepts(VirtualMachine.class, "running");
		port(Apache.class, "start").accepts(VirtualMachine.class, "running", Tomcat.class, "running", "start", MySQL.class, "running", "start");
		port(Apache.class, "running").accepts(VirtualMachine.class, "running", Tomcat.class, "start", "running", MySQL.class, "running", "start");
		
		port(VirtualMachine.class, "delete").accepts(Apache.class,"undeploy",Tomcat.class,"undeploy",MySQL.class,"undeploy");
		port(VirtualMachine.class, "pause").accepts(Apache.class,"undeploy",Tomcat.class,"undeploy",MySQL.class,"undeploy");
		port(Apache.class,"undeploy").accepts(VirtualMachine.class, "delete", "pause", Tomcat.class,"undeploy", MySQL.class,"undeploy");
		port(Tomcat.class,"undeploy").accepts(VirtualMachine.class, "delete", "pause", MySQL.class,"undeploy", Apache.class,"undeploy");
		port(MySQL.class,"undeploy").accepts(VirtualMachine.class, "delete", "pause", Tomcat.class,"undeploy", Apache.class,"undeploy");

		/**
		 * --------------------------------------------------------------------------------------------
		 * Data transfer
		 * --------------------------------------------------------------------------------------------
		 * */
		data(VirtualMachine.class, "vId").to(MySQL.class, "vId");
		data(VirtualMachine.class, "vId").to(Tomcat.class, "vId");
		data(VirtualMachine.class, "vId").to(Apache.class, "vId");
		
		data(MySQL.class, "sqlInfo").to(Tomcat.class, "sqlInfo");
		data(Tomcat.class, "tInfo").to(Apache.class, "tInfo");
	}

}
