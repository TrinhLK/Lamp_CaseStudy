package monitoring1;

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

@Ports({ @Port(name = "VMready", type = PortType.enforceable),
//	@Port(name = "VMrunning", type = PortType.enforceable),
//	@Port(name = "moveReq", type = PortType.enforceable),
	@Port(name = "VMdown", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "monitor.C_SystemsMonitor")
public class C_SystemsMonitor {

	Logger logger = LoggerFactory.getLogger(C_SystemsMonitor.class);

	String vmInfo = "";
	String mysqlList = "";
	String tomcatList = "";
	String apacheList = "";
	
	public C_SystemsMonitor() {
		// TODO Auto-generated constructor stub
	}
	
	@Transitions({
		@Transition(name = "VMready", source = "0", target = "1", guard = "canConnect"),
		@Transition(name = "VMready", source = "1", target = "1", guard = ""),
	})
	public void vmReady() {
//		String[] vmInfo1 = vmInfo.split("-");
//		vmInfo = _vId;
		logger.info("Monitor: {" + vmInfo + "} is {ready}.\n");
	}
	
	@Transitions({
		@Transition(name = "VMdown", source = "1", target = "0", guard = ""),
	})
	public void vmDown(@Data(name = "vId") String _vId) {
//		String[] vmInfo1 = _vId.split("-");
		vmInfo = _vId;
//		vmList.put(vmInfo[0], vmInfo[1]);
		logger.info("Monitor: {" + vmInfo + "} is {down}.\n");
	}
	
	@Guard(name = "canConnect")
	public boolean canConnect(@Data(name = "vId") String _vId) {
		vmInfo = _vId;
		return (!vmInfo.equals(null));
	}
}
