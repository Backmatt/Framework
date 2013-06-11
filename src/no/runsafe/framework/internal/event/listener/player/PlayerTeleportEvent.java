package no.runsafe.framework.internal.event.listener.player;

import no.runsafe.framework.api.event.IRunsafeEvent;
import no.runsafe.framework.internal.event.listener.EventRouterBase;
import no.runsafe.framework.api.EventRouterFactory;
import no.runsafe.framework.api.event.player.IPlayerTeleport;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.internal.wrapper.ObjectWrapper;
import no.runsafe.framework.api.IScheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class PlayerTeleportEvent extends EventRouterBase<IPlayerTeleport, org.bukkit.event.player.PlayerTeleportEvent>
{
	protected PlayerTeleportEvent(IOutput output, IScheduler scheduler, IPlayerTeleport handler)
	{
		super(output, scheduler, handler);
	}

	@EventHandler
	@Override
	public void AcceptEvent(org.bukkit.event.player.PlayerTeleportEvent event)
	{
		super.AcceptEvent(event);
	}

	@Override
	public boolean OnEvent(org.bukkit.event.player.PlayerTeleportEvent event)
	{
		return handler.OnPlayerTeleport(
			ObjectWrapper.convert(event.getPlayer()),
			ObjectWrapper.convert(event.getFrom()),
			ObjectWrapper.convert(event.getTo())
		);
	}

	public static EventRouterFactory Factory()
	{
		return new EventRouterFactory()
		{
			@Override
			public Class<? extends IRunsafeEvent> getInterface()
			{
				return IPlayerTeleport.class;
			}

			@Override
			public Listener getListener(IOutput output, IScheduler scheduler, IRunsafeEvent subscriber)
			{
				return new PlayerTeleportEvent(output, scheduler, (IPlayerTeleport) subscriber);
			}
		};
	}
}