/**
 * Copyright (c) 2016-2017 Inria
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * - Philippe Merle <philippe.merle@inria.fr>
 * - Lê Khánh Trình <trinh.le-khanh@inria.fr>
 *
 * Generated at Sat Oct 30 16:06:34 CEST 2021 from platform:/resource/mlamp/model/mlamp.occie by org.eclipse.cmf.occi.core.gen.connector
 */
package monitor5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.glue.GlueBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorSystem;

import java.util.Scanner;  // Import the Scanner class
import org.apache.log4j.PropertyConfigurator;

//Start of user code Mlamp
public class MlampTest
{
	private ActorSystem system;
    private EngineFactory engineFactory;
    static final Logger LOGGER = LoggerFactory.getLogger(MlampTest.class);
    
    private void initialize() {
        system = ActorSystem.create("MySystem");
        engineFactory = new EngineFactory(system);
    }
    
    private void cleanup() {
        system.shutdown();
    }
    
    private BIPGlue createGlue(String bipGlueFilename) {
		BIPGlue bipGlue = null;

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(bipGlueFilename);

			bipGlue = GlueBuilder.fromXML(inputStream);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return bipGlue;
	}
    
    public void running() {
    	
    	BIPGlue bipGlue = new GlueBuilder_Specification().build();
    	
    	BIPEngine engine = engineFactory.create("myEngine", bipGlue);
    	
    	Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        
        
    	// Start of user code BIP Actors
		// TODO Declare Instances and BIPActor here
    	VirtualMachineConnector vm1 = new VirtualMachineConnector("vm1");
    	VirtualMachineConnector vm2 = new VirtualMachineConnector("vm2");
    	MySQLConnector mysql1 = new MySQLConnector("mysql1");
    	MySQLConnector mysql2 = new MySQLConnector("mysql2");
    	MySQLConnector mysql3 = new MySQLConnector("mysql3");
    	MySQLConnector mysql4 = new MySQLConnector("mysql4");
//    	ApacheConnector tomcat1 = new ApacheConnector("tomcat1");
//    	ApacheConnector tomcat2 = new ApacheConnector("tomcat2");
//    	Apache apache1 = new Apache("apache1");
//    	Apache apache2 = new Apache("apache2");
    	
    	final BIPActor vm1_executor = engine.register(vm1, "vm1", true);
		final BIPActor vm2_executor = engine.register(vm2, "vm2", true);
		final BIPActor mysql1_executor = engine.register(mysql1, "mysql1", true);
		final BIPActor mysql2_executor = engine.register(mysql2, "mysql2", true);
		final BIPActor mysql3_executor = engine.register(mysql3, "mysql3", true);
		final BIPActor mysql4_executor = engine.register(mysql4, "mysql4", true);
//		final BIPActor tomcat1_executor = engine.register(tomcat1, "tomcat1", true);
//		final BIPActor tomcat2_executor = engine.register(tomcat2, "tomcat2", true);
		// End of user code
		engine.specifyGlue(bipGlue);
		engine.start();
		
		engine.execute();
		
//		String userName = "";
//		do {
//			userName = myObj.nextLine();  // Read user input
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//			if (vm1_executor.getState().equals("Active")) {
//				
//				try {
//					LOGGER.info("vm1_executor is fail");
//					Thread.sleep (10);
//					vm1_executor.inform("s_fail");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//		}while(userName.equals("0"));
		
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.stop();
		engineFactory.destroy(engine);
		
    }
    
    public static void main (String[] args){
    	MlampTest testTP = new MlampTest();
		PropertyConfigurator.configure("log4j.properties");
		testTP.initialize();
		testTP.running();
		testTP.cleanup();
    }
}
//End of user code
