package no.runsafe.framework.api.player;

import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.Universe;

import javax.annotation.Nullable;

public interface IPlayerUniverse
{
	@Nullable
	Universe getUniverse();
	boolean isInUniverse(String universeName);
	RunsafeWorld getWorld();
	String getWorldName();
}