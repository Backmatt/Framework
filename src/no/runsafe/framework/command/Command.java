package no.runsafe.framework.command;

import com.google.common.collect.ImmutableList;
import no.runsafe.framework.output.ChatColour;
import no.runsafe.framework.server.player.RunsafePlayer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.util.*;

public class Command implements ICommandHandler
{
	public Command(String commandName, String description, String permission, String... arguments)
	{
		this.name = commandName;
		this.permission = permission;
		this.description = description;
		if (arguments == null)
			this.argumentList = null;
		else
			this.argumentList = ImmutableList.copyOf(arguments);
	}

	public String getUsage()
	{
		HashMap<String, String> available = new HashMap<String, String>();
		if (subCommands != null && !subCommands.isEmpty())
		{
			for (Command sub : subCommands.values())
			{
					if (sub.description == null)
						available.put(sub.getName(), "");
					else
						available.put(sub.getName(), String.format(" - %s", sub.description));
			}
		}
		StringBuilder usage = new StringBuilder();
		if (available.isEmpty())
			return description == null ? "" : description;

		int width = 0;
		for (String cmd : available.keySet())
			if (cmd.length() > width)
				width = cmd.length();
		String format = String.format("  %%1s%%2$-%ds%%3$s%%4$s\n", width);
		for (String cmd : available.keySet())
			usage.append(String.format(format, ChatColor.YELLOW, cmd, ChatColor.RESET, available.get(cmd)));

		return String.format(
			"<%1$scommand%2$s>\nAvailable commands:\n%3$s",
			ChatColour.YELLOW,
			ChatColour.RESET,
			usage
		);
	}

	public String getUsageCommandParams()
	{
		String part = ChatColour.BLUE + name + ChatColour.RESET;
		if (!argumentList.isEmpty())
			part += " <" +
				ChatColour.YELLOW + StringUtils.join(
				argumentList,
				ChatColour.RESET + "> <" + ChatColour.YELLOW
			) + ChatColour.RESET + ">";
		return part;
	}

	public final String getPermission()
	{
		return permission;
	}

	public final void addSubCommand(Command subCommand)
	{
		subCommands.put(subCommand.getName(), subCommand);
	}

	public final Command getSubCommand(String name)
	{
		if (subCommands.isEmpty())
			return null;

		if (subCommands.containsKey(name))
			return subCommands.get(name);

		for (String sub : subCommands.keySet())
			if (sub.startsWith(name))
				return subCommands.get(sub);

		return null;
	}

	public final String getName()
	{
		return name;
	}

	@Override
	public final PreparedCommand prepare(RunsafePlayer executor, String[] args)
	{
		return prepare(executor, new HashMap<String, String>(), args, new Stack<Command>());
	}

	private PreparedCommand prepare(RunsafePlayer executor, HashMap<String, String> params, String[] args, Stack<Command> stack)
	{
		stack.add(this);
		params.putAll(getParameters(args));
		if (args.length > params.size())
			args = Arrays.copyOfRange(args, params.size(), args.length);
		else
			args = new String[0];

		if (args.length > 0)
		{
			Command subCommand = getSubCommand(args[0]);
			if (subCommand != null)
			{
				args = Arrays.copyOfRange(args, 1, args.length);
				return subCommand.prepare(executor, params, args, stack);
			}
		}
		return new PreparedCommand(executor, stack, args, params);
	}

	private HashMap<String, String> getParameters(String[] args)
	{
		HashMap<String, String> parameters = new HashMap<String, String>();

		int index = 0;
		for (String parameter : argumentList)
		{
			String value = null;
			if (args.length > index)
				value = args[index];
			index++;
			parameters.put(parameter, value);
		}
		return parameters;
	}

	private final ImmutableList<String> argumentList;
	private final HashMap<String, Command> subCommands = new HashMap<String, Command>();
	private final String name;
	private final String permission;
	private final String description;
}