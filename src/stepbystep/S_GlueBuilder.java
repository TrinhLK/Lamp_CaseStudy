package stepbystep;

import org.javabip.glue.GlueBuilder;

import components.MySQL;
import components.VirtualMachine;

public class S_GlueBuilder extends GlueBuilder{

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		port(C_MySQL.class, "deploy").requires(C_VirtualMachine.class, "running");
		port(C_MySQL.class, "start").requires(C_VirtualMachine.class, "running");
		port(C_MySQL.class,"undeploy").requires(C_VirtualMachine.class, "delete");
		port(C_MySQL.class,"undeploy").requires(C_VirtualMachine.class, "pause");
		
		port(C_Tomcat.class, "deploy").requires(C_VirtualMachine.class, "running");
		port(C_Tomcat.class, "start").requires(C_MySQL.class, "running");
		port(C_Tomcat.class,"undeploy").requires(C_VirtualMachine.class, "delete");
		port(C_Tomcat.class,"undeploy").requires(C_VirtualMachine.class, "pause");
		
		/**
		 * ACCEPTS
		 * */
		port(C_VirtualMachine.class, "running").accepts(C_MySQL.class, "deploy", "start", C_Tomcat.class, "deploy");
		port(C_VirtualMachine.class, "delete").accepts(C_MySQL.class, "undeploy", C_Tomcat.class, "undeploy");
		port(C_VirtualMachine.class, "pause").accepts(C_MySQL.class, "undeploy", C_Tomcat.class, "undeploy");
		
		port(C_MySQL.class, "deploy").accepts(C_VirtualMachine.class, "running");
		port(C_MySQL.class, "start").accepts(C_VirtualMachine.class, "running");
		port(C_MySQL.class, "running").accepts(C_Tomcat.class, "start");
		port(C_MySQL.class,"undeploy").accepts(C_VirtualMachine.class, "delete", "pause");
//		port(C_MySQL.class,"undeploy").accepts(C_VirtualMachine.class, "pause");
		
		port(C_Tomcat.class, "deploy").accepts(C_VirtualMachine.class, "running");
		port(C_Tomcat.class, "start").accepts(C_MySQL.class, "running");
		port(C_Tomcat.class,"undeploy").accepts(C_VirtualMachine.class, "delete", "pause");
		
		/**
		 * DATA TRANSFER
		 * */
		data(C_VirtualMachine.class, "vId").to(C_MySQL.class, "vId");
		data(C_VirtualMachine.class, "vId").to(C_Tomcat.class, "vId");
//		data(C_VirtualMachine.class, "vId").to(Apache.class, "vId");
		
		data(C_MySQL.class, "sqlInfo").to(C_Tomcat.class, "sqlInfo");
//		data(Tomcat.class, "tInfo").to(Apache.class, "tInfo");
	}

}
