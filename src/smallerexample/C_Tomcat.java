package smallerexample;

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
//	@Port(name = "configure", type = PortType.enforceable),
	@Port(name = "start", type = PortType.enforceable),
	@Port(name = "running", type = PortType.enforceable),
	@Port(name = "stop", type = PortType.enforceable),
	@Port(name = "fail", type = PortType.enforceable)
})
@ComponentType(initial = "Undeployed", name = "elements.Tomcat")
public class C_Tomcat {

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
		@Transition(name = "deploy", source = "Undeployed", target = "Deployed", guard = "canDeploy")
	})
	public void deploy() {
		logger.info(id + ": is deployed on {" + vId + "}\t-----\n");
		state = Components_States.Deployed;
	}
	
	@Transitions({
		@Transition(name = "undeploy", source = "Deployed", target = "Undeployed", guard = ""),
//		@Transition(name = "undeploy", source = "Active", target = "Undeployed", guard = "")
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
		@Transition(name = "start", source = "Stopped", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "InActive", target = "Active", guard = "canStart")
	})
	public void start() {
		logger.info(id + ": is running on {" + vId + "}, connected to {" + depInfor + "}\t-----\n");
		state = Components_States.Active;
		//depInfor = "";
	}
	
//	@Transitions({
//		@Transition(name = "stop", source = "Active", target = "Inactive", guard = "canStopTomcat")
//	})
//	public void stop() {
//		logger.info(id + ": is stopped" + "\t-----\n");
//		state = Components_States.Inactive;
//		depInfor = "";
//		runningTime = 0;
//	}
	
//	@Transitions({
////		@Transition(name = "configure", source = "InActive", target = "Inactive", guard = ""),
//		@Transition(name = "configure", source = "Deployed", target = "Inactive", guard = "")
//	})
//	public void configure() {
//		logger.info(id + ": is configuring" + "\t-----\n");
//		state = Components_States.Inactive;
//		depInfor = "";
//		runningTime = 0;
//	}
	
	@Transitions({
		@Transition(name = "stop", source = "Active", target = "Stopped", guard = ""),
//		@Transition(name = "stop", source = "Deployed", target = "Stopped", guard = ""),
//		@Transition(name = "stop", source = "Inactive", target = "Stopped", guard = "")
	})
	public void stop() {
		logger.info(id + " {" + state + "}: STOPPED" + "\t-----\n");
		state = Components_States.Inactive;
		depInfor = "";
		vId = "";
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
	@Data(name = "tInfo", accessTypePort = AccessType.any)
	public String sqlId() {
		return id + "-" + state.toString();
	}
	
	@Guard(name = "canStart")
	public boolean canStart(@Data(name = "sqlInfo") String _dId, @Data(name = "vId") String _vId) {
//		logger.info(id + "\t+++++ " + id + " current: " + depInfor + "\t new coming: " + _dId + "\n");
//		String[] _vInfo = _vId.split("-");
		
		if (vId == null || vId.equals("")) {
			vId = _vId;
		} else
		if (_vId.contains("Running")) {
			
			
//			logger.info(id + "\t+++++ " + id + " current: " + depInfor + "\t new coming: " + _dId + "\n");
			if (_dId.contains("Active")) {
				if (depInfor == null) {
					depInfor = _dId;
				}else
//				logger.info(id + "\t+++++ " + id + " current: " + depInfor + "\t new coming: " + _dId + "\n");
				if (!(_dId.contains("Active") && depInfor.contains("Active"))) {
					depInfor = _dId;
				}
				state = Components_States.Active;
				return true;
			}
		}
		
		return false;
	}
	
	@Guard(name = "canStopTomcat")
	public boolean canStopTomcat(@Data(name = "sqlInfo") String _dId) {
		String[] split = _dId.split("-");
//		logger.info("\tCheck STOP " + id + ": " + depInfor + " -- " + _dId + ": " + split[0] + "\n");
		if (this.depInfor != null) {
			if (this.depInfor.contains("Failure")) {
				state = Components_States.Inactive;
				logger.info(id + " should STOP 2: " + depInfor + " -- " + _dId + ": " + split[0] + "\n");
				return true;
			} else			
			if (this.depInfor.contains(split[0])) { //connecting mysql
				if (_dId.contains("Inactive") || _dId.contains("Failure") || _dId.contains("Undeployed")) {
					depInfor = _dId;
					state = Components_States.Inactive;
					logger.info(id + " should STOP 1: " + depInfor + " -- " + _dId + ": " + split[0] + "\n");
					return true;
				}
			}
			
			
		}
		return false;
//		return true;
	}
}
