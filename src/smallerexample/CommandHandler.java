package smallerexample;

import java.util.HashMap;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.BIPActor;
import org.javabip.api.PortType;

@Ports({ @Port(name = "handleCommand", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "monitor.CommandHandler")
public class CommandHandler {

	private BIPActor monitor;
	
	public CommandHandler(BIPActor _monitor) {
		// TODO Auto-generated constructor stub
		this.monitor = _monitor;
	}
	
	@Transition(name = "handleCommand", source = "0", target = "0")
	public void handleCommand(@Data(name = "command") Command command) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("command", command);
		monitor.inform("executedCommand", data);
	}
}
