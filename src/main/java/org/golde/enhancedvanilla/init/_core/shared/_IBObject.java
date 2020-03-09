package org.golde.enhancedvanilla.init._core.shared;

public interface _IBObject {

	public default boolean shouldBeInCreatveTab() {
		return true;
	}
	
	public void initModel();
	
}
