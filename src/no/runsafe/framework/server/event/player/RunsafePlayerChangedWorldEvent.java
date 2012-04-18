package no.runsafe.framework.server.event.player;

import no.runsafe.framework.server.RunsafeWorld;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class RunsafePlayerChangedWorldEvent extends RunsafePlayerEvent
{
	public RunsafePlayerChangedWorldEvent(PlayerChangedWorldEvent toWrap)
	{
		super(toWrap);
		event = toWrap;
	}

	public RunsafeWorld getSourceWorld()
	{
		return new RunsafeWorld(event.getFrom());
	}

	private PlayerChangedWorldEvent event;
}