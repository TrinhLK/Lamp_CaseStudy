package monitoring04;

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

import components.Components_States;

@Ports({ 
	@Port(name = "start", type = PortType.enforceable),
	@Port(name = "active", type = PortType.enforceable),
	@Port(name = "fail", type = PortType.enforceable),
	@Port(name = "pause", type = PortType.enforceable),
	@Port(name = "running", type = PortType.enforceable),
	@Port(name = "delete", type = PortType.enforceable) 
})
@ComponentType(initial = "off", name = "monitor.C_PhPApp")
public class C_PhPApp {
	String id;
	Components_States state;
	String vId;
	String depInfor;
	static final Logger logger = LoggerFactory.getLogger(C_PhPApp.class);
	
	public C_PhPApp(String _id) {
		// TODO Auto-generated constructor stub
		id = _id;
	}
	
	/**
	 * PORTS
	 * */
	@Transitions({
		@Transition(name = "deploy", source = "Undeployed", target = "Deployed", guard = "canDeploy"),
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
		state = Components_States.Undeployed;
	}
	
	@Transitions({
		@Transition(name = "start", source = "Deployed", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "Error", target = "Active", guard = "canStart"),
		@Transition(name = "running", source = "Active", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "Stopped", target = "Active", guard = "canRecover"),
//		@Transition(name = "start", source = "InActive", target = "Active", guard = "canStart")
	})
	public void start(@Data(name = "sqlInfo") String _dId) {
		logger.info(id + ": is running on {" + vId + "}, connected to {" + depInfor + "}\t-----\n");
		state = Components_States.Active;
	}
	
	@Transitions({
		@Transition(name = "fail", source = "Active", target = "Failing", guard = ""),
		@Transition(name = "fail", source = "Deployed", target = "Failing", guard = ""),
	})
	public void spontaneousFail() {
		logger.info(id + " {" + state + "}: spontaneous FAILED" + "\t-----\n");
		state = Components_States.Failure;
		vId = "";
	}
	
	@Transitions({
		@Transition(name = "makeError", source = "Failing", target = "Error", guard = ""),
	})
	public void makeError() {
		logger.info(id + " {" + state + "}: from Failing to Error" + "\t-----\n");
	}
	
	@Transitions({
		@Transition(name = "stop", source = "Active", target = "Stopped", guard = "canStop"),
	})
	public void stop() {
		logger.info(id + ": is stopped" + "\t-----\n");
		state = Components_States.Inactive;
	}
	
	@Guard(name = "canDeploy")
	public boolean canDeploy(@Data(name = "vId") String _vId) {
		if (vId == null || vId.equals("")) {
			vId = _vId;
		}
		return (vId.equals(_vId));
	}
}
