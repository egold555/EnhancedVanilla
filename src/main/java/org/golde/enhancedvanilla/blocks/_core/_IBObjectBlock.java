package org.golde.enhancedvanilla.blocks._core;

import org.golde.enhancedvanilla.init._core.shared._IBObject;

public interface _IBObjectBlock extends _IBObject {

	public default boolean shouldRegisterItem() {
		return true;
	}
	
}
