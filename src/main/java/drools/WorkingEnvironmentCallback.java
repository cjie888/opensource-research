package drools;

import org.drools.WorkingMemory;

public interface WorkingEnvironmentCallback {

	void initEnvironment(WorkingMemory workingMemory);
}
