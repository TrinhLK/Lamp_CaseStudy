/**
 * Copyright (c) 2016-2017 Inria
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * - Lê Khánh Trình <trinh.le-khanh@inria.fr>
 *
 * Generated at Sat Oct 30 16:06:34 CEST 2021 from platform:/resource/mlamp/model/mlamp.occie by org.eclipse.cmf.occi.core.gen.connector
 */
package monitor5;

import org.javabip.glue.GlueBuilder;

public class GlueBuilder_Specification extends GlueBuilder
{
	@Override
	public void configure() {
		//(VM.run)` - (MySQL.deploy)
		port(MySQLConnector.class, "deploy").requires(VirtualMachineConnector.class, "run");
//		port(VirtualMachineConnector.class, "run").requires(MySQLConnector.class, "deploy");
//		port(MySQLConnector.class, "start").requires(VirtualMachineConnector.class, "run");
//		port(MySQLConnector.class, "run").requires(VirtualMachineConnector.class, "run");
		
		port(MySQLConnector.class, "deploy").accepts(VirtualMachineConnector.class, "run");
//		port(MySQLConnector.class, "start").accepts(VirtualMachineConnector.class, "run", MySQLConnector.class, "deploy", "run");
//		port(MySQLConnector.class, "run").accepts(VirtualMachineConnector.class, "run", MySQLConnector.class, "deploy", "start");
		port(VirtualMachineConnector.class, "run").accepts(MySQLConnector.class, "deploy");
		
		//(VM.undeploy)` - (VM.fail)` - (VM.stop)` -(MySQL.undeploy)
//		port(MySQLConnector.class, "undeploy").requires(VirtualMachineConnector.class, "undeploy");
//		port(MySQLConnector.class, "undeploy").requires(VirtualMachineConnector.class, "run");
//		port(MySQLConnector.class, "undeploy").requires(VirtualMachineConnector.class, "fail");
//		port(MySQLConnector.class, "undeploy").requires(VirtualMachineConnector.class, "stop");
//		
//		port(MySQLConnector.class, "undeploy").accepts(VirtualMachineConnector.class, "undeploy", "fail", "stop", "run");
//		port(VirtualMachineConnector.class, "stop").accepts(MySQLConnector.class, "undeploy");
//		port(VirtualMachineConnector.class, "fail").accepts(MySQLConnector.class, "undeploy");
//		port(VirtualMachineConnector.class, "undeploy").accepts(MySQLConnector.class, "undeploy");
		
		//data transfer
		data(VirtualMachineConnector.class, "vId").to(MySQLConnector.class, "vId");
//		data(VirtualMachineConnector.class, "nOMySQL").to(MySQLConnector.class, "nOMySQL");
//		data(MySQLConnector.class, "mId").to(VirtualMachineConnector.class, "mId");
		data(MySQLConnector.class, "mIns").to(VirtualMachineConnector.class, "mIns");
		
//		//(MySQL.start)`-(Tomcat.start)
//		port(TomcatConnector.class, "start").requires(MySQLConnector.class, "start");
//
//		port(MySQLConnector.class, "start").accepts(TomcatConnector.class, "start");
//		port(TomcatConnector.class, "start").accepts(MySQLConnector.class, "start");
//
//		//(Tomcat.start)`-(Apache.start)
//		port(ApacheConnector.class, "start").requires(TomcatConnector.class, "start");
//
//		port(TomcatConnector.class, "start").accepts(ApacheConnector.class, "start");
//		port(ApacheConnector.class, "start").accepts(TomcatConnector.class, "start");
//
//		//[(MySQL.start)-(Apache.start)]`-(Apps.start)
//		port(AppsConnector.class, "start").requires(MySQLConnector.class, "start", ApacheConnector.class, "start");
//		port(MySQLConnector.class, "start").requires(ApacheConnector.class, "start");
//		port(ApacheConnector.class, "start").requires(MySQLConnector.class, "start");
//
//		port(AppsConnector.class, "start").accepts(MySQLConnector.class, "start", ApacheConnector.class, "start");
//		port(MySQLConnector.class, "start").accepts(AppsConnector.class, "start", ApacheConnector.class, "start");
//		port(ApacheConnector.class, "start").accepts(AppsConnector.class, "start", MySQLConnector.class, "start");

		//prop: (and (>= Apps.Active 1) (= Apache.Active 1))

		//prop: (and (>= Apache.Active 1) (>= Tomcat.Active 1))

		//prop: (and (>= Apps.Active 1) (= MySQL.Active 1))

		//prop: (=> (and (>= Apache.Active 1) (= Tomcat.Error 1)) (>= Tomcat.Active 1))

		// Start of user code Developer policies
		// TODO Declare fixed policies
		// End of user code
	}
}
