package components;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.annotations.Transitions;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({
	@Port(name = "deploy", type = PortType.enforceable),
	@Port(name = "undeploy", type = PortType.enforceable),
	@Port(name = "configure", type = PortType.enforceable),
	@Port(name = "start", type = PortType.enforceable),
	@Port(name = "stop", type = PortType.enforceable),
	@Port(name = "running", type = PortType.enforceable),
	@Port(name = "fail", type = PortType.enforceable)
})
@ComponentType(initial = "Undeployed", name = "elements.Apache")
public class Apache {

	String id;
	Components_States state;
	static final Logger logger = LoggerFactory.getLogger(Apache.class);
	String vId;
	String depInfor;
	VM_States vStates;
	int runningTime;
	
	public Apache() {
		state = Components_States.Undeployed;
	}
	
	public Apache(String _id) {
		id = _id;
		state = Components_States.Undeployed;
	}
	
	/**
	 * PORTS
	 * */
	@Transitions({
		@Transition(name = "deploy", source = "Undeployed", target = "Deployed", guard = "canDeploy")
	})
	public void deploy() {
		logger.info(id + ": is deployed on {" + vId + "}\t-----\n");
		state = Components_States.Deployed;
	}
	
	@Transitions({
		@Transition(name = "undeploy", source = "Deployed", target = "Undeployed", guard = ""),
		@Transition(name = "undeploy", source = "Active", target = "Undeployed", guard = "")
	})
	public void undeploy() {
		logger.info(id + ": is undeployed" + "\t-----\n");
		vId = "";
		depInfor = "";
		state = Components_States.Undeployed;
		runningTime = 0;
	}
	
	@Transitions({
		@Transition(name = "start", source = "Deployed", target = "Active", guard = "canStart"),
		@Transition(name = "running", source = "Active", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "Error", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "InActive", target = "Active", guard = "canStart")
	})
	public void start() {
		logger.info(id + ": is running on {" + vId + "}, connected to {" + depInfor + "}\t-----\n");
		state = Components_States.Active;
		depInfor = "";
		
	}
	
	@Transitions({
		@Transition(name = "stop", source = "Active", target = "Inactive", guard = "")
	})
	public void stop() {
		logger.info(id + ": is stopped" + "\t-----\n");
		state = Components_States.Inactive;
		depInfor = "";
		runningTime = 0;
	}
	
	@Transitions({
		@Transition(name = "configure", source = "InActive", target = "Inactive", guard = ""),
		@Transition(name = "configure", source = "Deployed", target = "Inactive", guard = "")
	})
	public void configure() {
		logger.info(id + ": is configuring" + "\t-----\n");
		state = Components_States.Inactive;
		depInfor = "";
		runningTime = 0;
	}
	
	@Transitions({
		@Transition(name = "fail", source = "Active", target = "Error", guard = "canFail"),
		@Transition(name = "fail", source = "Deployed", target = "Error", guard = "canFail"),
		@Transition(name = "fail", source = "Inactive", target = "Error", guard = "canFail")
	})
	public void fail() {
		logger.info(id + " {" + state + "}: FAILED" + "\t-----\n");
		state = Components_States.Failure;
		depInfor = "";
		vId = "";
		runningTime = 0;
	}
	
	/**
	 * DATA & GUARDS
	 * */
	
	@Guard(name = "canDeploy")
	public boolean canDeploy(@Data(name = "vId") String _vId) {
		if (_vId.contains("Running")) {
			vId = _vId;
			return true;
		}
		
		return false;
	}
	
	@Guard(name = "canFail")
	public boolean canFail() {
//		if (_vId.contains("Running")) {
//			vId = _vId;
//			return true;
//		}
//		
		return false;
	}
	
	@Guard(name = "canStart")
	public boolean canStart(@Data(name = "tInfo") String _dId, @Data(name = "vId") String _vId) {
		
		if (_vId.contains("Running")) {
			vId = _vId;
			
			if (_dId.contains("Active")) {
				depInfor = _dId;
				state = Components_States.Active;
				return true;
			}
		}
		
		return false;
	}
}
