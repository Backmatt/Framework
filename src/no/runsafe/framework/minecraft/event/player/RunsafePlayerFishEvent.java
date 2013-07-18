package no.runsafe.framework.minecraft.event.player;

import no.runsafe.framework.internal.wrapper.ObjectWrapper;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeFish;
import org.bukkit.event.player.PlayerFishEvent;

public class RunsafePlayerFishEvent extends RunsafeCancellablePlayerEvent
{
	public RunsafePlayerFishEvent(PlayerFishEvent toWrap)
	{
		super(toWrap);
		this.event = toWrap;
	}

	public RunsafeEntity getCaught()
	{
		return ObjectWrapper.convert(this.event.getCaught());
	}

	public RunsafeFish getFish()
	{
		return ObjectWrapper.convert(this.event.getHook());
	}

	public int getExpToDrop()
	{
		return this.event.getExpToDrop();
	}

	public void setExpToDrop(int exp)
	{
		this.event.setExpToDrop(exp);
	}

	public State getState()
	{
		return RunsafePlayerFishEvent.State.valueOf(this.event.getState().name());
	}

	public enum State
	{
		FISHING,
		CAUGHT_FISH,
		CAUGHT_ENTITY,
		IN_GROUND,
		FAILED_ATTEMPT
	}

	private final PlayerFishEvent event;
}
