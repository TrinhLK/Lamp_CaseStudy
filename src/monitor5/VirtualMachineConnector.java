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
 * - term: virtualmachine
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
@ComponentType(initial = "Undeployed", name = "mlamp.connector.VirtualMachine")
public class VirtualMachineConnector
{
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(VirtualMachineConnector.class);
	//MlampSingleton instance = MlampSingleton.getInstance();
	// Start of user code variables
	// Add permanent variable
	String vId;
	int nOMySQL = 0;
	int nOTomcat;
	int nOApache;
	int nOApps;
	private int currentCapacity = 0;
	final int limits = 1;
	// End of user code

	// Start of user code Virtualmachineconnector_constructor
	/**
	 * Constructs a virtualmachine connector.
	 */
	VirtualMachineConnector()
	{
		//final BIPActor executor = instance.engine.register(this, getTitle(), true);
		LOGGER.debug("Constructor called on " + this);
		// TODO: Implement this constructor.
	}
	
	VirtualMachineConnector(String _vid){
		vId = _vid;
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//
	
	
	//
	// VirtualMachine actions.
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
		LOGGER.info(vId + " - action undeploy()");
		nOMySQL = 0;
		// TODO: Implement how to undeploy this VirtualMachine.
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
		LOGGER.info(vId + " - action spontaneous FAIL()");
		// TODO: Implement how to s_fail this VirtualMachine.
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
		LOGGER.info(vId + " - action FAIL()");
		nOMySQL = 0;
		// TODO: Implement how to fail this VirtualMachine.
	}
	//End of user code
	
	
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/mlamp/virtualmachine/action#
     * - term: start
     * - title: 
	 */
	@Transitions({
		@Transition(name = "start", source = "Inactive", target = "Active"),
		@Transition(name = "start", source = "Undeployed", target = "Active"),
		@Transition(name = "start", source = "Error", target = "Active"),
		@Transition(name = "run", source = "Active", target = "Active", guard="hasCapacity"),
	})
	//Start of user code start
	public void start()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action start() called on " + this);
		LOGGER.info(vId + " - action start and run()");
//		nOMySQL += (_mId.equals("")?0:1);
		// TODO: Implement how to start this VirtualMachine.
	}
	//End of user code
	
	
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/mlamp/virtualmachine/action#
     * - term: stop
     * - title: 
	 */
	@Transitions({
		@Transition(name = "stop", source = "Active", target = "Inactive", guard="canStop"),
	})
	//Start of user code stop
	public void stop()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action stop() called on " + this);
		LOGGER.info(vId + " - action stop()");
		nOMySQL = 0;
		// TODO: Implement how to stop this VirtualMachine.
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
		LOGGER.info(vId + " - action configure()");

		// TODO: Implement how to configure this VirtualMachine.
	}
	//End of user code
	
	
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/mlamp/virtualmachine/action#
     * - term: deploy
     * - title: 
	 */
	@Transitions({
		@Transition(name = "deploy", source = "Undeployed", target = "Deployed"),
		@Transition(name = "deploy", source = "Error", target = "Deployed"),
	})
	//Start of user code deploy
	public void deploy()
	{
		//instance.runningConfiguration();
		LOGGER.debug("Action deploy() called on " + this);
		LOGGER.info(vId + " - action deploy()");
		// TODO: Implement how to deploy this VirtualMachine.
	}
	//End of user code

	// Start of user code other functions
	@Data(name = "vId", accessTypePort = AccessType.any)
	public String getvId() {
		return vId;
	}

	public void setvId(String vId) {
		this.vId = vId;
	}

	@Data(name = "nOMySQL", accessTypePort = AccessType.any)
	public int getnOMySQL() {
		return nOMySQL;
	}

	public void setnOMySQL(int nOMySQL) {
		this.nOMySQL = nOMySQL;
	}
	
	@Data(name = "nOTomcat", accessTypePort = AccessType.any)
	public int getnOTomcat() {
		return nOTomcat;
	}

	public void setnOTomcat(int nOTomcat) {
		this.nOTomcat = nOTomcat;
	}

	@Data(name = "nOApache", accessTypePort = AccessType.any)
	public int getnOApache() {
		return nOApache;
	}

	public void setnOApache(int nOApache) {
		this.nOApache = nOApache;
	}

	@Data(name = "nOApps", accessTypePort = AccessType.any)
	public int getnOApps() {
		return nOApps;
	}

	public void setnOApps(int nOApps) {
		this.nOApps = nOApps;
	}

	@Data(name = "curr", accessTypePort = AccessType.any)
	public int getThresholds() {
		return limits;
	}
	
	@Guard(name = "hasCapacity")
	public boolean hasCapacity(@Data(name = "mIns") Integer _mIns) {
		
		if (nOMySQL + _mIns <= limits) {
			nOMySQL += _mIns;
		}
//		nOMySQL += _mIns;
		LOGGER.info(vId + ": current number of mysql instance vs. limitation: " + nOMySQL + " < "
				+ limits + " " + (nOMySQL < limits) + " mIns = " + _mIns);
		
		return (nOMySQL < limits);
	}
	
	@Guard(name = "canUndeploy")
	public boolean canUndeploy() {
		return false;
	}
	
	@Guard(name = "canStop")
	public boolean canStop() {

		return false;
	}
	// End of user code
}	
