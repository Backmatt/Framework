package no.runsafe.framework.minecraft.event;

import no.runsafe.framework.api.event.CancellableEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class RunsafeCancellableEvent extends RunsafeEvent implements CancellableEvent
{

	protected RunsafeCancellableEvent(Event toWrap)
	{
		super(toWrap);
		event = (Cancellable) toWrap;
	}

	@Override
	@Deprecated
	public boolean getCancelled()
	{
		return this.event.isCancelled();
	}

	@Override
	@Deprecated
	public void setCancelled(boolean cancel)
	{
		this.event.setCancelled(cancel);
	}

	@Override
	public boolean isCancelled()
	{
		return event.isCancelled();
	}

	@Override
	public void cancel()
	{
		event.setCancelled(true);
		for (Runnable callback : cancellationCallbacks)
			callback.run();
	}

	@Override
	public void addCancellationHandle(Runnable callback)
	{
		cancellationCallbacks.add(callback);
	}

	private final List<Runnable> cancellationCallbacks = new ArrayList<Runnable>();
	private final Cancellable event;
}