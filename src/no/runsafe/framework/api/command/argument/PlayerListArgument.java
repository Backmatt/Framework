package no.runsafe.framework.api.command.argument;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.InjectionPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PlayerListArgument extends PlayerArgument
{
	public PlayerListArgument(boolean required)
	{
		super("players", required);
	}

	@Override
	public boolean isWhitespaceInclusive()
	{
		return true;
	}

	public List<IPlayer> getValue(IPlayer context, Map<String, String> params)
	{
		String[] names = LISTSEPARATOR.split(params.get("name"));
		List<IPlayer> players = new ArrayList<IPlayer>(names.length);
		IServer server = InjectionPlugin.getGlobalComponent(IServer.class);
		for (String playerName : names)
		{
			IPlayer player = server.getPlayer(playerName);
			if (player != null)
				players.add(player);
		}
		return players;
	}

	static final Pattern LISTSEPARATOR = Pattern.compile("\\s+");
}
