package components;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.annotations.Transitions;
import org.javabip.api.PortType;
import org.javabip.api.DataOut.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({
	@Port(name = "deploy", type = PortType.enforceable),
	@Port(name = "undeploy", type = PortType.enforceable),
	@Port(name = "configure", type = PortType.enforceable),
	@Port(name = "start", type = PortType.enforceable),
	@Port(name = "running", type = PortType.enforceable),
	@Port(name = "stop", type = PortType.enforceable),
	@Port(name = "fail", type = PortType.spontaneous)
})
@ComponentType(initial = "Undeployed", name = "elements.Components")
public class MySQL {

	String id;
	Components_States state;
	static final Logger logger = LoggerFactory.getLogger(MySQL.class);
	String vId;
	String depInfor;
	VM_States vStates;
	int runningTime;
	
	public MySQL() {
		state = Components_States.Undeployed;
	}
	
	public MySQL(String _id) {
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
		@Transition(name = "start", source = "Error", target = "Active", guard = "canStart"),
		@Transition(name = "running", source = "Active", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "InActive", target = "Active", guard = "canStart")
	})
	public void start() {
		logger.info(id + ": is running on {" + vId + "}\t-----\n");
		state = Components_States.Active;
	}
	
	@Transitions({
		@Transition(name = "fail", source = "Active", target = "Error", guard = ""),
	})
	public void spontaneousFail() {
		logger.info(id + " {" + state + "}: spontaneous FAILED" + "\t-----\n");
		state = Components_States.Failure;
		depInfor = "";
		vId = "";
		runningTime = 0;
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
	
	/**
	 * DATA & GUARDS
	 * */
	@Data(name = "sqlInfo", accessTypePort = AccessType.any)
	public String sqlId() {
		return id + "-" + state.toString();
	}
	
	@Guard(name = "canStart")
	public boolean canStart(@Data(name = "vId") String _vId) {
		if (_vId.contains("Running")) {
			vId = _vId;
			return true;
		}
		
		return false;
	}
	
	@Guard(name = "canDeploy")
	public boolean canDeploy(@Data(name = "vId") String _vId) {
		if (_vId.contains("Running")) {
			vId = _vId;
			return true;
		}
		return false;
	}
	
	@Guard(name = "followingShutdown")
	public boolean followingShutdown(@Data(name = "vId") String _vId) {
		String[] infor = _vId.split("-");
		if (this.vId.contains(infor[0])) {
			return true;
		}
		return false;
	}
}
