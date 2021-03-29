package smallerexample;

import org.javabip.api.BIPActor;

public class Command {

	private String id;
	//private String id;
	private String topic;
	private String message;
	private BIPActor component;	//BIP Actor representing client issing the command.
	
	public Command(BIPActor _component, String _id, String _topic, String _msg) {
		// TODO Auto-generated constructor stub
		this.component = _component;
		this.id = _id;
		this.topic = _topic;
		this.message = _msg;
	}
	
	public BIPActor getComponent() {
		return this.component;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public String getMessage() {
		return this.message;
	}
}
