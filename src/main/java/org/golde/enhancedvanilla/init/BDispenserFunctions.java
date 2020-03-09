package org.golde.enhancedvanilla.init;

import org.golde.enhancedvanilla.dispenser.DyeBehaviour;
import org.golde.enhancedvanilla.dispenser.JukeboxBehaviour;
import org.golde.enhancedvanilla.dispenser.NametagBehaviour;
import org.golde.enhancedvanilla.dispenser.ShearsBehaviour;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BDispenserFunctions {

	public static void postInit() {
		add(Items.SHEARS, ShearsBehaviour.getInstance());
		add(Items.DYE, DyeBehaviour.getInstance());
		add(Items.NAME_TAG, NametagBehaviour.getInstance());
		
		add(Items.RECORD_11, JukeboxBehaviour.getInstance());
        add(Items.RECORD_13, JukeboxBehaviour.getInstance());
        add(Items.RECORD_BLOCKS, JukeboxBehaviour.getInstance());
        add(Items.RECORD_CAT, JukeboxBehaviour.getInstance());
        add(Items.RECORD_CHIRP, JukeboxBehaviour.getInstance());
        add(Items.RECORD_FAR, JukeboxBehaviour.getInstance());
        add(Items.RECORD_MALL, JukeboxBehaviour.getInstance());
        add(Items.RECORD_STAL, JukeboxBehaviour.getInstance());
        add(Items.RECORD_MELLOHI, JukeboxBehaviour.getInstance());
        add(Items.RECORD_STRAD, JukeboxBehaviour.getInstance());
        add(Items.RECORD_WAIT, JukeboxBehaviour.getInstance());
        add(Items.RECORD_WARD, JukeboxBehaviour.getInstance());
	}
	
	public static void add(Item i, BehaviorDefaultDispenseItem b) {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(i, b);
    }
}
