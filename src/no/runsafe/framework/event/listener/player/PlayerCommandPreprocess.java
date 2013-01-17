package no.runsafe.framework.event.listener.player;

import no.runsafe.framework.event.EventEngine;
import no.runsafe.framework.event.IRunsafeEvent;
import no.runsafe.framework.event.listener.EventRouter;
import no.runsafe.framework.event.listener.EventRouterFactory;
import no.runsafe.framework.event.player.IPlayerCommandPreprocessEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.player.RunsafePlayerCommandPreprocessEvent;
import no.runsafe.framework.timer.IScheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocess extends EventRouter<IPlayerCommandPreprocessEvent, PlayerCommandPreprocessEvent>
{
	public PlayerCommandPreprocess(IOutput output, IScheduler scheduler, IPlayerCommandPreprocessEvent handler)
	{
		super(output, scheduler, handler);
	}

	// This one cannot be async, so don't check
	@Override
	@EventHandler
	public void AcceptEvent(PlayerCommandPreprocessEvent event)
	{
		OnEvent(event);
	}

	public boolean OnEvent(PlayerCommandPreprocessEvent event)
	{
		handler.OnBeforePlayerCommand(new RunsafePlayerCommandPreprocessEvent(event));
		return true;
	}

	static
	{
		EventEngine.Register(IPlayerCommandPreprocessEvent.class, new EventRouterFactory()
		{
			@Override
			public Listener getListener(IOutput output, IScheduler scheduler, IRunsafeEvent subscriber)
			{
				return new PlayerCommandPreprocess(output, scheduler, (IPlayerCommandPreprocessEvent) subscriber);
			}
		});
	}
}
