package monitoring2;

import java.util.HashSet;
import java.util.*;  
import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.annotations.Transitions;
import org.javabip.api.BIPActor;
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
	@Port(name = "makeError", type = PortType.enforceable),
	@Port(name = "fail", type = PortType.spontaneous)
})
@ComponentType(initial = "Undeployed", name = "monitor.C_MySQL")
public class C_MySQL {

	String id;
	Components_States state;
	static final Logger logger = LoggerFactory.getLogger(C_MySQL.class);
	String vId;
	String depInfor;
	VM_States vStates;
	int runningTime;
	
	private HashSet<BIPActor> tomcats;
	
	public C_MySQL() {
		state = Components_States.Undeployed;
		this.tomcats = new HashSet<BIPActor>();
	}
	
	public C_MySQL(String _id) {
		id = _id;
		state = Components_States.Undeployed;
		this.tomcats = new HashSet<BIPActor>();
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
		@Transition(name = "start", source = "Stopped", target = "Active", guard = "canStart"),
		@Transition(name = "start", source = "InActive", target = "Active", guard = "canStart")
	})
	public void start() {
		logger.info(id + ": is running on {" + vId + "}\t-----\n");
		state = Components_States.Active;
		runningTime++;
	}
	
	@Transitions({
		@Transition(name = "fail", source = "Active", target = "Failing", guard = ""),
	})
	public void spontaneousFail() {
		logger.info(id + " {" + state + "}: spontaneous FAILED" + "\t-----\n");
		state = Components_States.Failure;
		depInfor = "";
		vId = "";
		runningTime = 0;
		System.out.println("\t ++ Check Tomcat size: " + tomcats.size());
		
		Iterator<BIPActor> itr = tomcats.iterator();
		while (itr.hasNext()) {
			if (itr.next() != null) {
				logger.info("C_MySQL fail check: " + itr.next().getState() + "\n");
				itr.next().inform("stop");
			}else {
				logger.info("Tomcat Actor is null.\n");
			}
			
		}
	}
	
	@Transitions({
		@Transition(name = "makeError", source = "Failing", target = "Error", guard = ""),
	})
	public void makeError() {
		logger.info(id + " {" + state + "}: from Failing to Error" + "\t-----\n");
	}
	
	@Transitions({
		@Transition(name = "stop", source = "Active", target = "Stopped", guard = "!canStop"),
	})
	public void stop() {
		logger.info(id + ": is stopped" + "\t-----\n");
		state = Components_States.Inactive;
		depInfor = "";
		runningTime = 0;
	}
	
	@Transitions({
		@Transition(name = "configure", source = "InActive", target = "InActive", guard = "canStart"),
		@Transition(name = "configure", source = "Deployed", target = "InActive", guard = "canStart")
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
		return id;
	}
	
	@Guard(name = "canStart")
	public boolean canStart(@Data(name = "vId") String _vId) {
		if (vId == null || vId.equals("")) {
			vId = _vId;
		}
//		System.out.println("Comming Tomcat Actor: " + _tomcatActor.getState());
//		tomcats.add(_tomcatActor);
		
		return (vId == _vId);
	}
	
	@Guard(name = "canConnectBIP")
	public boolean canConnectBIP(@Data(name = "tomcatActor1") BIPActor _tomcatActor) {
		
		tomcats.add(_tomcatActor);
		return true;
	}
	
	@Guard(name = "canStop")
	public boolean canStop() {		
		return true;
	}
	
	@Guard(name = "canFail")
	public boolean canFail() {		
		return (runningTime > 5);
	}
	
	@Guard(name = "canDeploy")
	public boolean canDeploy(@Data(name = "vId") String _vId) {
		if (vId == null || vId.equals("")) {
			vId = _vId;
			
		}
		return (vId == _vId);
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
