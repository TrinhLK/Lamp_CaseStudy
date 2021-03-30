package mornitoring;

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
	@Port(name = "start", type = PortType.enforceable),
	@Port(name = "active", type = PortType.enforceable),
	@Port(name = "fail", type = PortType.enforceable),
	@Port(name = "pause", type = PortType.enforceable),
	@Port(name = "running", type = PortType.enforceable),
	@Port(name = "delete", type = PortType.enforceable) 
})
@ComponentType(initial = "off", name = "monitor.C_VirtualMachine")
public class C_VirtualMachine {

	String vId;
	VM_States vState;
	static final Logger logger = LoggerFactory.getLogger(C_VirtualMachine.class);
	int runningTime;
	
public C_VirtualMachine() {
		
	}
	
	public C_VirtualMachine(String _id) {
		vId = _id;
		vState = VM_States.OFF;
		runningTime = 0;
	}
	@Transitions({
		@Transition(name = "start", source = "off", target = "on", guard = ""),
		@Transition(name = "start", source = "error", target = "on", guard = "")
	})
	public void start() {
		logger.info(vId + ": is on" + "\t-\n");	
		vState = VM_States.ON;
		runningTime = 0;
	}
	
	@Transitions({
		@Transition(name = "active", source = "on", target = "running", guard = ""),
		@Transition(name = "active", source = "stopped", target = "running", guard = ""),
		@Transition(name = "running", source = "running", target = "running", guard = "")
	})
	public void active() {
//		logger.info(vId + ": is running" + "\t-\n");
		vState = VM_States.Running;
		runningTime++;
	}
	
	@Transitions({
		@Transition(name = "delete", source = "running", target = "off", guard = "failCons")
	})
	public void delete() {
		logger.info(vId + ": is off" + "\t-\n");
		vState = VM_States.OFF;
		runningTime = 0;
	}

	@Transitions({
		@Transition(name = "fail", source = "running", target = "error", guard = "failCons")
	})
	public void fail() {
		logger.info(vId + ": failed [ERROR]" + "\t-\n");
		vState = VM_States.Error;
		runningTime = 0;
	}
	
	@Transitions({
		@Transition(name = "pause", source = "running", target = "stopped", guard = "!canPause")
	})
	public void pause() {
		logger.info(vId + ": paused" + "\t-\n");
		vState = VM_States.Stopped;
		runningTime = 0;
	}
	
	@Data(name = "vId", accessTypePort = AccessType.any)
	public String getvId() {
		return vId + "-" + vState.toString();// + "-" + runningTime;
	}
	
	@Guard(name = "failCons")
	public boolean failCondition() {
		return (runningTime == 10);
	}
	
	@Guard(name = "canPause")
	public boolean canPause() {
		return true;
	}
}

enum VM_States {
	
	Running, Stopped, Error, ON, OFF
}