package dk.frv.eavdam.data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:25
 */
public class FATDMASlotAllocation {

	Set<Integer> allocations;
	
	public FATDMASlotAllocation(){

	}

	public void addAllocation(int allocation){
		if(this.allocations == null) this.allocations = new HashSet<Integer>();
		
		this.allocations.add(new Integer(allocation));
	}
	
	public Set<Integer> getAllocations() {
		return allocations;
	}

	public void setAllocations(Set<Integer> allocations) {
		this.allocations = allocations;
	}

}