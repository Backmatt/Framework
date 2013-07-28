package no.runsafe.framework.internal.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.*;
import no.runsafe.framework.minecraft.RunsafeServer;

import java.util.Map;
import java.util.Stack;

public final class PreparedAsynchronousCallbackCommand extends PreparedCommand
{
	public PreparedAsynchronousCallbackCommand(
		ICommandExecutor executor, Stack<ICommandHandler> definingCommand, String[] args, Map<String, String> parameters)
	{
		super(executor, definingCommand, args, parameters);
	}

	@Override
	public String execute()
	{
		ICommandHandler target = command.peek();
		if (target instanceof CommandScheduler && !parameters.containsValue(null))
		{
			schedule(((CommandScheduler) target).getScheduler());
			return ((ISyncExecute) target).OnExecute(executor, parameters, arguments);
		}

		return usage(target);
	}

	@SuppressWarnings("unchecked")
	public <T> void schedule(final IScheduler scheduler)
	{
		ICommandHandler peek = command.peek();
		if (peek instanceof IAsyncCallbackExecute<?>)
		{
			final IAsyncCallbackExecute<T> target = (IAsyncCallbackExecute<T>) peek;
			scheduler.startAsyncTask(
				new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							final T result = target.OnAsyncExecute(executor, parameters, arguments);
							scheduler.startSyncTask(
								new Runnable()
								{
									@Override
									public void run()
									{
										try
										{
											target.SyncPostExecute(result);
										}
										catch (Exception e)
										{
											RunsafeServer.Instance.getDebugger().logException(e);
										}
									}
								}, 1L
							);
						}
						catch (Exception e)
						{
							RunsafeServer.Instance.getDebugger().logException(e);
						}
					}
				},
				1L
			);
		}
	}
}
