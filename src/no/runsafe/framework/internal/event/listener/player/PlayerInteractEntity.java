package no.runsafe.framework.internal.event.listener.player;

import no.runsafe.framework.api.event.IRunsafeEvent;
import no.runsafe.framework.internal.event.listener.EventRouterBase;
import no.runsafe.framework.api.EventRouterFactory;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;
import no.runsafe.framework.api.IScheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public final class PlayerInteractEntity extends EventRouterBase<IPlayerInteractEntityEvent, PlayerInteractEntityEvent>
{
	public PlayerInteractEntity(IOutput output, IScheduler scheduler, IPlayerInteractEntityEvent handler)
	{
		super(output, scheduler, handler);
	}

	@EventHandler
	@Override
	public void AcceptEvent(PlayerInteractEntityEvent event)
	{
		super.AcceptEvent(event);
	}

	@Override
	public boolean OnEvent(PlayerInteractEntityEvent event)
	{
		handler.OnPlayerInteractEntityEvent(new RunsafePlayerInteractEntityEvent(event));
		return true;
	}

	public static EventRouterFactory Factory()
	{
		return new EventRouterFactory()
		{
			@Override
			public Class<? extends IRunsafeEvent> getInterface()
			{
				return IPlayerInteractEntityEvent.class;
			}

			@Override
			public Listener getListener(IOutput output, IScheduler scheduler, IRunsafeEvent subscriber)
			{
				return new PlayerInteractEntity(output, scheduler, (IPlayerInteractEntityEvent) subscriber);
			}
		};
	}
}