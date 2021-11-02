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
 * - Faiez Zalila <faiez.zalila@inria.fr>
 * - Lê Khánh Trình <trinh.le-khanh@inria.fr>
 *
 * Generated at Sat Oct 30 16:06:34 CEST 2021 from platform:/resource/mlamp/model/mlamp.occie by org.eclipse.cmf.occi.core.gen.connector
 */
package monitor5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.javabip.annotations.*;
import org.javabip.api.PortType;
import org.javabip.api.BIPActor;
import org.javabip.api.DataOut.AccessType;

// Start of user code import
	//Add permanent libs
// End of user code
/**
 * Connector implementation for the OCCI kind:
 * - scheme: http://occiware.org/mlamp#
 * - term: mysql
 * - title: 
 */
@Ports({
// Start of user code Ports
	//Add permanent libs
// End of user code
@Port(name = "undeploy", type = PortType.enforceable)
, @Port(name = "s_fail", type = PortType.spontaneous)
, @Port(name = "fail", type = PortType.enforceable)
, @Port(name = "start", type = PortType.enforceable)
, @Port(name = "run", type = PortType.enforceable)
, @Port(name = "stop", type = PortType.enforceable)
, @Port(name = "configure", type = PortType.enforceable)
, @Port(name = "deploy", type = PortType.enforceable)
})
@ComponentType(initial = "Undeployed", name = "mlamp.connector.MySQL")
public class MySQLConnector
{
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MySQLConnector.class);
	//MlampSingleton instance = MlampSingleton.getInstance();
	// Start of user code variables
	// Add permanent variable
	String mId;
	private int mIns = 1;
	String vId = "";
	// End of user code

	// Start of user code Mysqlconnector_constructor
	/**
	 * Constructs a mysql connector.
	 */
	MySQLConnector()
	{
		//final BIPActor executor = instance.engine.register(this, getTitle(), true);
		LOGGER.debug("Constructor called on " + this);
		// TODO: Implement this constructor.
	}
	
	MySQLConnector(String _mId){
		mId = _mId;
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//
	
	
	//
	// MySQL actions.
	//

	
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/mlamp/virtualmachine/action#
     * - term: undeploy
     * - title: 
	 */
	@Transitions({
		@Transition(name = "undeploy", source = "Active", target = "Undeployed", guard = "canUndeploy"),
		@Transition(name = "undeploy", source = "Deployed", target = "Undeployed", guard = "canUndeploy"),
		@Transition(name = "undeploy", source = "Inactive", target = "Undeployed", guard = "canUndeploy"),
	})
	//Start of user code undeploy
	public void undeploy()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action undeploy() called on " + this);
		LOGGER.info(mId + " - action undeploy() from " + vId);
		// TODO: Implement how to undeploy this MySQL.
		vId = "";
	}
	//End of user code
	
	
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/mlamp/virtualmachine/action#
     * - term: s_fail
     * - title: 
	 */
	@Transitions({
		@Transition(name = "s_fail", source = "Active", target = "PseudoError"),
		@Transition(name = "s_fail", source = "Deployed", target = "PseudoError"),
		@Transition(name = "s_fail", source = "Inactive", target = "PseudoError"),
		@Transition(name = "s_fail", source = "Undeployed", target = "PseudoError"),
	})
	//Start of user code s_fail
	public void s_fail()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action s_fail() called on " + this);
		LOGGER.info(mId + " - action Spontaneous FAIL()");
		// TODO: Implement how to s_fail this MySQL.
	}
	//End of user code
	
	@Transitions({
		@Transition(name = "fail", source = "PseudoError", target = "Error"),
		@Transition(name = "fail", source = "PseudoError", target = "Error"),
		@Transition(name = "fail", source = "PseudoError", target = "Error"),
		@Transition(name = "fail", source = "PseudoError", target = "Error"),
		@Transition(name = "fail", source = "Error", target = "Error"),
})
	//Start of user code s_fail
	public void fail()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action fail() called on " + this);
		LOGGER.info(mId + " - action FAIL()");
		// TODO: Implement how to fail this MySQL.
	}
	//End of user code
	
	
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/mlamp/virtualmachine/action#
     * - term: start
     * - title: 
	 */
	@Transitions({
		@Transition(name = "start", source = "Inactive", target = "Active", guard = "canStart"),
//		@Transition(name = "start", source = "Undeployed", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "Error", target = "Active", guard = "canStart"),
//		@Transition(name = "run", source = "Active", target = "Active", guard = "canStart"),
	})
	//Start of user code start
	public void start()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action start() called on " + this);
		LOGGER.info(mId + " - action run() on " + vId);
		// TODO: Implement how to start this MySQL.
	}
	//End of user code
	
	
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/mlamp/virtualmachine/action#
     * - term: stop
     * - title: 
	 */
	@Transitions({
		@Transition(name = "stop", source = "Active", target = "Inactive", guard = "canStop"),
	})
	//Start of user code stop
	public void stop()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action stop() called on " + this);
		LOGGER.info(mId + " - action stop()");
		// TODO: Implement how to stop this MySQL.
	}
	//End of user code
	
	
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/mlamp/virtualmachine/action#
     * - term: configure
     * - title: 
	 */
	@Transitions({
		@Transition(name = "configure", source = "Deployed", target = "Inactive"),
//		@Transition(name = "configure", source = "Inactive", target = "Inactive"),
	})
	//Start of user code configure
	public void configure()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action configure() called on " + this);
		LOGGER.info(mId + " - action configure() on " + vId);
		// TODO: Implement how to configure this MySQL.
	}
	//End of user code
	
	
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/mlamp/virtualmachine/action#
     * - term: deploy
     * - title: 
	 */
	@Transitions({
		@Transition(name = "deploy", source = "Undeployed", target = "Deployed", guard="canDeploy"),
		@Transition(name = "deploy", source = "Error", target = "Deployed", guard="canDeploy"),
	})
	//Start of user code deploy
	public void deploy()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action deploy() called on " + this);
		LOGGER.info(mId + " - action deploy()" + " - to " + vId);
		// TODO: Implement how to deploy this MySQL.
	}
	//End of user code
	
	// Start of user code other functions
	@Guard(name = "canDeploy")
	public boolean canDeploy(@Data(name = "vId") String _vId) {
		if (this.vId == null || this.vId.equals("")) {
			this.vId = _vId;
		}
		
		return (!vId.equals(""));
	}
	
	@Data(name = "mId", accessTypePort = AccessType.any)
	public String getmId() {
		return mId;
	}
	
	@Data(name = "mIns", accessTypePort = AccessType.any)
	public int getmInstance() {
		return mIns;
	}
	
	@Guard(name = "canStart")
	public boolean canStart() {
		
		return (!vId.equals(""));
	}
	
	@Guard(name = "canStop")
	public boolean canStop() {

		return false;
	}
	
	@Guard(name = "canUndeploy")
	public boolean canUndeploy() {
		
		return (!vId.equals(""));
	}
	// End of user code
}	
