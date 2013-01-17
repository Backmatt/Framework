package no.runsafe.framework.event.listener.player;

import no.runsafe.framework.event.EventEngine;
import no.runsafe.framework.event.IRunsafeEvent;
import no.runsafe.framework.event.listener.EventRouter;
import no.runsafe.framework.event.listener.EventRouterFactory;
import no.runsafe.framework.event.player.IPlayerJoinEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.player.RunsafePlayerJoinEvent;
import no.runsafe.framework.timer.IScheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin extends EventRouter<IPlayerJoinEvent, PlayerJoinEvent>
{
	public PlayerJoin(IOutput output, IScheduler scheduler, IPlayerJoinEvent subscriber)
	{
		super(output, scheduler, subscriber);
	}

	// We have to put this here to get the annotation onto the method.
	@EventHandler
	@Override
	public void AcceptEvent(PlayerJoinEvent event)
	{
		super.AcceptEvent(event);
	}

	@Override
	public boolean OnEvent(PlayerJoinEvent event)
	{
		handler.OnPlayerJoinEvent(new RunsafePlayerJoinEvent(event));
		return true;
	}

	public static void Register()
	{
		EventEngine.Register(IPlayerJoinEvent.class, new EventRouterFactory()
		{
			@Override
			public Listener getListener(IOutput output, IScheduler scheduler, IRunsafeEvent subscriber)
			{
				return new PlayerJoin(output, scheduler, (IPlayerJoinEvent) subscriber);
			}
		});
	}
}
