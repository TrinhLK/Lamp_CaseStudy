package monitoring1;

import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorSystem;

public class MonitorTestFile_1 {
	private ActorSystem system;
    private EngineFactory engineFactory;
    static final Logger logger = LoggerFactory.getLogger(MonitorTestFile_1.class);
    
    private void initialize() {
        system = ActorSystem.create("MySystem");
        engineFactory = new EngineFactory(system);
    }
    
    private void cleanup() {
        system.shutdown();
    }
    
    public void runningTrackerPeer() {
    	BIPGlue bipGlue = new SystemGlueBuilder().build();
    	
    	BIPEngine engine = engineFactory.create("myEngine", bipGlue);
    	
    	C_SystemsMonitor monitor = new C_SystemsMonitor();
    	C_VirtualMachine vm1 = new C_VirtualMachine("vm1");
    	C_VirtualMachine vm2 = new C_VirtualMachine("vm2");
    	C_MySQL mysql1 = new C_MySQL("mysql1");
    	C_MySQL mysql2 = new C_MySQL("mysql2");
    	C_Tomcat tomcat1 = new C_Tomcat("tomcat1");
    	C_Tomcat tomcat2 = new C_Tomcat("tomcat2");
//    	Apache apache1 = new Apache("apache1");
//    	Apache apache2 = new Apache("apache2");
    	
    	final BIPActor monitor_executor = engine.register(monitor, "monitor", true);
    	final BIPActor vm1_executor = engine.register(vm1, "vm1", true);
		final BIPActor vm2_executor = engine.register(vm2, "vm2", true);
		final BIPActor mysql1_executor = engine.register(mysql1, "mysql1", true);
		final BIPActor mysql2_executor = engine.register(mysql2, "mysql2", true);
		final BIPActor tomcat1_executor = engine.register(tomcat1, "tomcat1", true);
		final BIPActor tomcat2_executor = engine.register(tomcat2, "tomcat2", true);
//		final BIPActor apache1_executor = engine.register(apache1, "apache1", true);
//		final BIPActor apache2_executor = engine.register(apache2, "apache2", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		
		engine.execute();
		
//		int loop = 0;
//		while (loop < 2) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (mysql1_executor.getState().equals("Active")) {
				try {
					logger.info("mysql1_executor is informing to fail");
					
					mysql1_executor.inform("fail");
					
					Thread.sleep (100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else
			if (mysql2_executor.getState().equals("Active")) {
				try {
					logger.info("mysql2_executor is informing to fail");
					mysql2_executor.inform("fail");
					
					Thread.sleep (100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

//			loop++;
//		}
		
		engine.stop();
		engineFactory.destroy(engine);
		
    }
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
    	MonitorTestFile_1 testLamp = new MonitorTestFile_1();
		testLamp.initialize();
		testLamp.runningTrackerPeer();
		testLamp.cleanup();		
	}
}
