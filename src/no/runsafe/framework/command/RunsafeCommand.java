package no.runsafe.framework.command;

import com.google.common.collect.ImmutableList;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.player.RunsafePlayer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class RunsafeCommand implements ICommand
{
	public RunsafeCommand(String name, String... params)
	{
		commandName = name;
		subCommands = new HashMap<String, ICommand>();
		this.params = new HashMap<String, String>();
		if (params != null)
		{
			paramKeys = new ImmutableList.Builder<String>().add(params).build();
			for (String param : params)
				this.params.put(param, null);
		}
		else
			paramKeys = null;
	}

	@Override
	public String getCommandName()
	{
		return commandName;
	}

	@Override
	public String getDescription()
	{
		return null;
	}

	@Override
	public String getCommandUsage(RunsafePlayer executor)
	{
		HashMap<String, String> available = new HashMap<String, String>();
		if (subCommands != null && !subCommands.isEmpty())
		{
			for (ICommand sub : subCommands.values())
			{
				if (executor == null && (sub instanceof RunsafePlayerCommand || sub instanceof RunsafeAsyncPlayerCommand))
					continue;

				if (executor != null && (sub instanceof RunsafeConsoleCommand || sub instanceof RunsafeAsyncConsoleCommand))
					continue;

				if (sub.CouldExecute(executor))
				{
					String description = sub.getDescription();
					if (description == null)
						available.put(sub.getCommandName(), "");
					else
						available.put(sub.getCommandName(), String.format(" - %s", sub.getDescription()));
				}
			}
		}
		StringBuilder usage = new StringBuilder();
		if (available.isEmpty())
			return String.format("/%s\n", getCommandParams());

		int width = 0;
		for (String cmd : available.keySet())
			if (cmd.length() > width)
				width = cmd.length();
		String format = String.format("  %%1s%%2$-%ds%%3$s%%4$s\n", width);
		for (String cmd : available.keySet())
			usage.append(String.format(format, ChatColor.YELLOW, cmd, ChatColor.RESET, available.get(cmd)));

		return String.format(
			"/%3$s <%1$scommand%2$s>\n%4$s\n Available commands:\n%5$s",
			ChatColor.YELLOW,
			ChatColor.RESET,
			getCommandParams(),
			getDescription() == null ? "" : getDescription(),
			usage
		);
	}

	@Override
	public String getCommandParams()
	{
		String part = ChatColor.BLUE + commandName + ChatColor.RESET;
		if (!params.isEmpty())
			part += " <" +
				ChatColor.YELLOW + StringUtils.join(
				paramKeys,
				ChatColor.RESET + "> <" + ChatColor.YELLOW
			) + ChatColor.RESET + ">";

		if (superCommand != null)
			return superCommand.getCommandParams() + " " + part;

		return part;
	}

	@Override
	public Collection<ICommand> getSubCommands()
	{
		return subCommands.values();
	}

	@Override
	public void addSubCommand(ICommand command)
	{
		command.setSuperCommand(this);
		subCommands.put(command.getCommandName(), command);
	}

	@Override
	public String requiredPermission()
	{
		return null;
	}

	@Override
	public String requiredPermission(String[] args)
	{
		if (args.length < params.size())
			return requiredPermission();
		subArgOffset = 0;
		captureArgs(args);
		ICommand sub = null;
		if (args.length > subArgOffset)
			sub = getSubCommand(args[subArgOffset]);
		if (sub == null)
			return requiredPermission();
		return sub.requiredPermission(getSubArgs(args));
	}

	@Override
	public boolean CanExecute(RunsafePlayer player, String[] args)
	{
		return requiredPermission() == null || player.hasPermission(requiredPermission());
	}

	@Override
	public boolean CouldExecute(RunsafePlayer player)
	{
		if (player == null)
			return true;

		if (requiredPermission() != null)
			return player.hasPermission(requiredPermission());

		if (subCommands != null)
			for (ICommand sub : subCommands.values())
				if (sub.CouldExecute(player))
					return true;

		return false;
	}

	@Override
	public boolean Execute(RunsafePlayer player, String[] args)
	{
		subArgOffset = 0;
		if (!CouldExecute(player) || !CanExecute(player, args))
		{
			Console.outputToConsole(String.format("%s was denied access to command.", player.getName()), Level.WARNING);
			if (requiredPermission(args) == null)
				player.sendColouredMessage("&cPermission denied to that command.");
			else
				player.sendColouredMessage(String.format("&cRequired permission %s missing.", requiredPermission()));
			return true;
		}

		if (args.length < params.size())
		{
			String usage = getCommandUsage(player);
			if (usage != null)
				player.sendMessage(usage);
			return true;
		}
		captureArgs(args);

		ICommand sub = null;
		if (args.length > subArgOffset)
			sub = getSubCommand(args[subArgOffset]);

		if (sub != null)
			subArgOffset++;

		else
		{
			String output = OnExecute(player, args);
			if (output != null)
				player.sendColouredMessage(output);
			return true;
		}

		return sub.Execute(player, getSubArgs(args));
	}

	@Override
	public boolean Execute(String[] args)
	{
		Console.finer(String.format("Console sync Execute: %s, %s", commandName, StringUtils.join(args, ", ")));

		subArgOffset = 0;
		if (args.length < params.size())
		{
			Console.finest(String.format("Missing params (%d < %d)", args.length, params.size()));
			String usage = getCommandUsage(null);
			if (usage != null)
				Console.writeColoured(usage, Level.INFO);
			return true;
		}
		captureArgs(args);

		ICommand sub = null;
		if (args.length > subArgOffset)
			sub = getSubCommand(args[subArgOffset]);

		if (sub != null)
			subArgOffset++;

		else
		{
			Console.finest("Executing command..");
			String output = OnExecute(null, args);
			if (output != null)
				Console.writeColoured(output, Level.INFO);
			return true;
		}

		Console.finest("Passing command off to subcommand..");
		return sub.Execute(getSubArgs(args));
	}

	@Override
	public String OnExecute(RunsafePlayer executor, String[] args)
	{
		return getCommandUsage(executor);
	}

	@Override
	public void setSuperCommand(ICommand command)
	{
		superCommand = command;
	}

	@Override
	public String getArg(String name)
	{
		if (params.containsKey(name))
			return params.get(name);

		if(superCommand != null)
			return superCommand.getArg(name);

		return null;
	}

	public String getArg(String[] args, String name)
	{
		if (paramKeys == null || paramKeys.isEmpty() || !paramKeys.contains(name))
			return null;

		int index = paramKeys.indexOf(name);
		if (args.length >= index)
			return null;
		return args[index];
	}

	@Override
	public void setConsole(IOutput output)
	{
		Console = output;
		for (ICommand sub : subCommands.values())
			sub.setConsole(output);
	}

	@Override
	public void setConsoleLogging(boolean isEnabled)
	{
		logToConsole = isEnabled;
	}

	@Override
	public boolean isConsoleLogEnabled()
	{
		return logToConsole;
	}

	@Override
	public ICommand getTargetCommand(String[] args)
	{
		if (args.length <= params.size())
			return this;

		ICommand sub = getSubCommand(args[params.size()]);
		if(sub == null)
			return this;

		return sub.getTargetCommand(getSubArgs(args));
	}

	@Override
	public String[] getTargetArgs(String[] args)
	{
		ICommand tier = superCommand;
		int index = 0;
		while(tier != null)
		{
			index++;
			index += tier.getArgumentCount();
		}
		return Arrays.copyOfRange(args, index, args.length);
	}

	@Override
	public int getArgumentCount()
	{
		return params.size();
	}

	protected void captureArgs(String[] args)
	{
		if (paramKeys != null && !paramKeys.isEmpty())
			for (String param : paramKeys)
				params.put(param, args[subArgOffset++]);
	}

	protected ICommand getSubCommand(String name)
	{
		Console.finest(String.format("Looking up subcommand %s", name));
		if (subCommands.containsKey(name))
		{
			Console.finest("Found exact match..");
			return subCommands.get(name);
		}
		for (String sub : subCommands.keySet())
			if (sub.startsWith(name))
			{
				Console.finest(String.format("Found partial match in %s", sub));
				return subCommands.get(sub);
			}

		Console.finest("Unknown subcommand");
		return null;
	}

	private String[] getSubArgs(String[] args)
	{
		if (args.length == 1 || args.length == 0 || args.length <= params.size())
			return new String[]{};

		return Arrays.copyOfRange(args, params.size() + 1, args.length);
	}

	protected ICommand superCommand;
	protected final HashMap<String, ICommand> subCommands;
	protected final String commandName;
	protected int subArgOffset;
	protected final List<String> paramKeys;
	protected final HashMap<String, String> params;
	protected IOutput Console;
	protected boolean logToConsole = true;
}
