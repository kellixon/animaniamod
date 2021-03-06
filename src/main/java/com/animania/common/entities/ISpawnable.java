package com.animania.common.entities;

import net.minecraft.item.Item;

public interface ISpawnable
{

	public Item getSpawnEgg();
	
	public int getPrimaryEggColor();
	
	public int getSecondaryEggColor();
	
	public EntityGender getEntityGender();
	
	default boolean usesEggColor()
	{
		return true;
	}
	
}
