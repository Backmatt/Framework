package no.runsafe.framework.event.listener.block;

import no.runsafe.framework.event.EventEngine;
import no.runsafe.framework.event.IRunsafeEvent;
import no.runsafe.framework.event.block.IBlockRedstone;
import no.runsafe.framework.event.listener.EventRouterBase;
import no.runsafe.framework.event.listener.EventRouterFactory;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.timer.IScheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockRedstone extends EventRouterBase<IBlockRedstone, BlockRedstoneEvent>
{
	public BlockRedstone(IOutput output, IScheduler scheduler, IBlockRedstone handler)
	{
		super(output, scheduler, handler);
	}

	@EventHandler
	@Override
	public void AcceptEvent(BlockRedstoneEvent event)
	{
		super.AcceptEvent(event);
	}

	@Override
	public boolean OnEvent(BlockRedstoneEvent event)
	{
		int current = handler.OnBlockRedstone(event.getOldCurrent(), event.getNewCurrent());
		if (current >= 0)
			event.setNewCurrent(current);
		return true;
	}

	static class Factory implements EventRouterFactory
	{
		@Override
		public Class<? extends IRunsafeEvent> getInterface()
		{
			return IBlockRedstone.class;
		}

		@Override
		public Listener getListener(IOutput output, IScheduler scheduler, IRunsafeEvent subscriber)
		{
			return new BlockRedstone(output, scheduler, (IBlockRedstone) subscriber);
		}
	}
}

