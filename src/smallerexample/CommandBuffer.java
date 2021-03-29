package smallerexample;

import java.util.LinkedList;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;

@Ports({ @Port(name = "putCommand", type = PortType.enforceable),
	@Port(name = "getCommand", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "monitor.CommandBuffer")
public class CommandBuffer {

	private LinkedList<Command> commandList;
	private int bufferSize;
	
	public CommandBuffer(int _bufferSize) {
		// TODO Auto-generated constructor stub
		this.bufferSize = _bufferSize;
		this.commandList = new LinkedList<Command>();
	}
	
	@Transition(name = "getCommand", source = "0", target = "0", guard = "bufferIsNotEmpty")
	public void getCommandToHandle() {
		commandList.remove();
	}
	
	@Transition(name = "putCommand", source = "0", target = "0", guard = "bufferIsNotFull")
	public void putCommandFromReader(@Data(name = "input") Command command) {
		commandList.add(command);
	}
	
	@Guard(name = "bufferIsNotEmpty")
	public boolean bufferIsNotEmpty() {
		return !commandList.isEmpty();
	}
	
	@Guard(name = "bufferIsNotFull")
	public boolean bufferIsNotFull() {
		return commandList.size() != bufferSize;
	}
	
	@Data(name = "command")
	public Command getNextCommand() {
		return commandList.get(0);
	}
}
