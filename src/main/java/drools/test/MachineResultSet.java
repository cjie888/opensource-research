package drools.test;

import drools.Machine;

public interface MachineResultSet {
	
	boolean next();
	
	Machine getMachine();

}
