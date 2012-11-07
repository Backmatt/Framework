package no.runsafe.framework.server.player;

import org.bukkit.OfflinePlayer;

import java.util.List;

public class RunsafeAmbiguousPlayer extends RunsafePlayer
{
	public RunsafeAmbiguousPlayer(OfflinePlayer toWrap, List<String> ambiguous)
	{
		super(toWrap);
	}

	public List<String> getAmbiguity()
	{
		return ambiguity;
	}

	private List<String> ambiguity;
}
