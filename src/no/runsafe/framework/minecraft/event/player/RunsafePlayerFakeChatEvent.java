package no.runsafe.framework.minecraft.event.player;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.RunsafeServer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RunsafePlayerFakeChatEvent extends RunsafePlayerChatEvent
{
	@SuppressWarnings("LocalVariableOfConcreteClass")
	@Nullable
	public static String Run(IPlayer player, String message)
	{
		RunsafePlayerFakeChatEvent event = new RunsafePlayerFakeChatEvent(player, message);
		if (!event.Fire())
			return null;

		return String.format(event.format, event.player.getName(), event.message);
	}

	public static void Broadcast(IPlayer player, String message)
	{
		String result = Run(player, message);
		if (result != null)
			RunsafeServer.InternalAPI.broadcastMessage(result);
	}

	public RunsafePlayerFakeChatEvent(IPlayer player, String message)
	{
		super(null);
		this.player = player;
		this.message = message;
	}

	@Override
	public String getFormat()
	{
		return format;
	}

	@Override
	public void setFormat(String format)
	{
		this.format = format;
	}

	@Override
	public String getMessage()
	{
		return message;
	}

	@Override
	public void setMessage(String message)
	{
		this.message = message;
	}

	@Override
	public IPlayer getPlayer()
	{
		return player;
	}

	@Override
	@Nullable
	public List<IPlayer> getRecipients()
	{
		return null;
	}

	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}

	@Override
	public void cancel()
	{
		cancelled = true;
		if (cancellationCallbacks != null)
			for (Runnable callback : cancellationCallbacks)
				if (callback != null)
					callback.run();
	}

	@Override
	public void addCancellationHandle(Runnable callback)
	{
		if (cancellationCallbacks == null)
			cancellationCallbacks = new ArrayList<Runnable>(1);
		cancellationCallbacks.add(callback);
	}

	private Collection<Runnable> cancellationCallbacks;
	private final IPlayer player;
	private String message;
	private String format = "<%1$s> %2$s";
	private boolean cancelled;
}
