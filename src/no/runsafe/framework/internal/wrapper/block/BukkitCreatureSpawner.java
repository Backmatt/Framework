package no.runsafe.framework.internal.wrapper.block;

import no.runsafe.framework.minecraft.block.RunsafeBlockState;
import no.runsafe.framework.api.minecraft.RunsafeEntityType;
import no.runsafe.framework.minecraft.entity.EntityType;
import org.bukkit.block.CreatureSpawner;

public abstract class BukkitCreatureSpawner extends RunsafeBlockState
{
	protected BukkitCreatureSpawner(CreatureSpawner toWrap)
	{
		super(toWrap);
		spawner = toWrap;
	}

	public int getDelay()
	{
		return spawner.getDelay();
	}

	public void setDelay(int i)
	{
		spawner.setDelay(i);
	}

	public void setCreature(RunsafeEntityType type)
	{
		spawner.setSpawnedType(type.getRaw());
	}

	public RunsafeEntityType getCreature()
	{
		return EntityType.convert(spawner.getSpawnedType());
	}

	protected final CreatureSpawner spawner;
}
