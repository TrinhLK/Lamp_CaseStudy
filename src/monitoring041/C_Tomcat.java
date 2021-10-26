package monitoring041;

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

import components.Components_States;

@Ports({
	@Port(name = "deploy", type = PortType.enforceable),
	@Port(name = "undeploy", type = PortType.enforceable),
	@Port(name = "configure", type = PortType.enforceable),
	@Port(name = "start", type = PortType.enforceable),
	@Port(name = "running", type = PortType.enforceable),
	@Port(name = "stop", type = PortType.enforceable),
	@Port(name = "makeError", type = PortType.enforceable),
	@Port(name = "fail", type = PortType.spontaneous)
})
@ComponentType(initial = "Undeployed", name = "monitor.Tomcat")
public class C_Tomcat{

	String id;
	Components_States state;
	static final Logger logger = LoggerFactory.getLogger(C_Tomcat.class);
	String vId;
	String depInfor;
	VM_States vStates;
	int runningTime;
	
	public C_Tomcat() {
		state = Components_States.Undeployed;
	}
	
	public C_Tomcat(String _id) {
		id = _id;
		state = Components_States.Undeployed;
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
		depInfor = "";
		state = Components_States.Undeployed;
		runningTime = 0;
	}
	
	@Transitions({
		@Transition(name = "start", source = "Deployed", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "Error", target = "Active", guard = "canStart"),
		@Transition(name = "running", source = "Active", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "Stopped", target = "Active", guard = "canRecover"),
//		@Transition(name = "start", source = "InActive", target = "Active", guard = "canStart")
	})
	public void start(@Data(name = "sqlInfo") String _dId) {
		depInfor = _dId;
		logger.info(id + ": is running on {" + vId + "}, connected to {" + depInfor + "}\t-----\n");
		state = Components_States.Active;
		runningTime++;
	}
	
	@Transitions({
		@Transition(name = "fail", source = "Active", target = "Failing", guard = ""),
		@Transition(name = "fail", source = "Deployed", target = "Failing", guard = ""),
	})
	public void spontaneousFail() {
		logger.info(id + " {" + state + "}: spontaneous FAILED" + "\t-----\n");
		state = Components_States.Failure;
		depInfor = "";
		vId = "";
		runningTime = 0;
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
//		depInfor = "";
		runningTime = 0;
	}
	
	/**
	 * DATA & GUARDS
	 * */
	@Data(name = "tomcatInfo", accessTypePort = AccessType.any)
	public String sqlId() {
		return id;
	}
	
	@Guard(name = "canStart")
	public boolean canStart(@Data(name = "sqlInfo") String _dId) {
//		logger.info(id + "\t---\t" + _dId);
		if (depInfor == null || depInfor.equals("")) {
			depInfor = _dId;
		}
		return (depInfor.equals(_dId));
	}
	
	@Guard(name = "canRecover")
	public boolean canRecover(@Data(name = "sqlInfo") String _dId) {
//		logger.info(id + "\t---\t" + _dId);
		if (!depInfor.equals(_dId)) {
			logger.info("\t check canRecover: " + depInfor + "\t" + _dId);
			return true;
		}
		return false;
	}
	
	@Guard(name = "canStop")
	public boolean canStop(@Data(name = "sqlInfo") String _dId) {
		if (depInfor.equals(_dId)) {
			logger.info("\t" + id + " can Stop: " + depInfor + " -- " + _dId);
			return true;
		}else {
			logger.info("\t" + id + " cannot Stop: " + depInfor + " -- " + _dId);
			return false;
		}
//		return (depInfor.equals(_dId));
	}
	
	@Guard(name = "canFail")
	public boolean canFail() {		
		return true;
	}
	
	@Guard(name = "canDeploy")
	public boolean canDeploy(@Data(name = "vId") String _vId) {
		if (vId == null || vId.equals("")) {
			vId = _vId;
		}
		return (vId.equals(_vId));
	}
	
	@Guard(name = "followingShutdown")
	public boolean followingShutdown(@Data(name = "vId") String _vId) {
		String[] infor = _vId.split("-");
		if (this.vId.contains(infor[0])) {
			return true;
		}
		return false;
	}
	
	@Guard(name = "canStopTomcat")
	public boolean canStopTomcat(@Data(name = "sqlInfo") String _dId) {
		if (depInfor.equals(_dId)) {
			return true;
		}
		return false;
//		String[] split = _dId.split("-");
//		
//		if (this.depInfor.contains(split[0])) {
////			logger.info("\tCheck STOP " + id + ": " + depInfor + " -- " + _dId + ": " + split[0] + "\n");
//			if (_dId.contains("InActive") || _dId.contains("Failure")) {
//				depInfor = _dId;
//				state = Components_States.Inactive;
//				logger.info(id + " should STOP");
//				return true;
//			}
//		}
//		return false;
	}

}
