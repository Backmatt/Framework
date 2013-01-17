package no.runsafe.framework.event.listener.player;

import no.runsafe.framework.event.EventEngine;
import no.runsafe.framework.event.IRunsafeEvent;
import no.runsafe.framework.event.listener.EventRouter;
import no.runsafe.framework.event.listener.EventRouterFactory;
import no.runsafe.framework.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.player.RunsafePlayerChangedWorldEvent;
import no.runsafe.framework.timer.IScheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangedWorld extends EventRouter<IPlayerChangedWorldEvent, PlayerChangedWorldEvent>
{
	public PlayerChangedWorld(IOutput output, IScheduler scheduler, IPlayerChangedWorldEvent handler)
	{
		super(output, scheduler, handler);
	}

	@EventHandler
	@Override
	public void AcceptEvent(PlayerChangedWorldEvent event)
	{
		super.AcceptEvent(event);
	}

	@Override
	public boolean OnEvent(PlayerChangedWorldEvent event)
	{
		handler.OnPlayerChangedWorld(new RunsafePlayerChangedWorldEvent(event));
		return true;
	}

	public static void Register()
	{
		EventEngine.Register(IPlayerChangedWorldEvent.class, new EventRouterFactory()
		{
			@Override
			public Listener getListener(IOutput output, IScheduler scheduler, IRunsafeEvent subscriber)
			{
				return new PlayerChangedWorld(output, scheduler, (IPlayerChangedWorldEvent) subscriber);
			}
		});
	}
}
