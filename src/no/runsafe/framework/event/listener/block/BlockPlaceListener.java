package no.runsafe.framework.event.listener.block;

import no.runsafe.framework.event.block.IBlockPlaceEvent;
import no.runsafe.framework.event.listener.EventRouter;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.block.RunsafeBlockPlaceEvent;
import no.runsafe.framework.timer.IScheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

@SuppressWarnings("deprecation")
@Deprecated
public class BlockPlaceListener extends EventRouter<IBlockPlaceEvent, BlockPlaceEvent>
{
	public BlockPlaceListener(IOutput output, IScheduler scheduler, IBlockPlaceEvent handler)
	{
		super(output, scheduler, handler);
	}

	@EventHandler
	@Override
	public void AcceptEvent(BlockPlaceEvent event)
	{
		super.AcceptEvent(event);
	}

	@EventHandler
	public boolean OnEvent(BlockPlaceEvent event)
	{
		handler.OnBlockPlaceEvent(new RunsafeBlockPlaceEvent(event));
		return true;
	}
}