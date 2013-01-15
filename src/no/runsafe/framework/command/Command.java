package no.runsafe.framework.command;

import com.google.common.collect.ImmutableList;
import no.runsafe.framework.command.prepared.IPreparedCommand;
import no.runsafe.framework.command.prepared.PreparedAsynchronousCallbackCommand;
import no.runsafe.framework.command.prepared.PreparedAsynchronousCommand;
import no.runsafe.framework.command.prepared.PreparedSynchronousCommand;
import no.runsafe.framework.output.ChatColour;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.ICommandExecutor;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

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
		if (!subCommands.isEmpty())
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
			usage.append(String.format(format, ChatColour.YELLOW, cmd, ChatColour.RESET, available.get(cmd)));

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

	protected final void captureTail()
	{
		captureTail = true;
	}

	@Override
	public final IPreparedCommand prepare(ICommandExecutor executor, String[] args)
	{
		if (args != null)
			console.fine("Preparing command %s %s", getName(), StringUtils.join(args, " "));
		else
			console.fine("Preparing command %s", getName());
		return prepare(executor, new HashMap<String, String>(), args, new Stack<Command>());
	}

	@Override
	public void setConsole(IOutput console)
	{
		console.fine("Setting console on command object.");
		this.console = console;
	}

	private IPreparedCommand prepare(ICommandExecutor executor, HashMap<String, String> params, String[] args, Stack<Command> stack)
	{
		stack.add(this);
		HashMap<String, String> myParams = getParameters(args);
		params.putAll(myParams);
		if (myParams.size() > 0)
		{
			if (!captureTail && args.length > myParams.size())
				args = Arrays.copyOfRange(args, myParams.size(), args.length);
			else
				args = new String[0];
		}
		console.fine("Command %s has %d parameters and %d args", getName(), myParams.size(), args.length);
		if (args.length > 0)
		{
			console.fine("Looking for subcommand %s", args[0]);
			Command subCommand = getSubCommand(args[0]);
			if (subCommand != null)
			{
				subCommand.setConsole(console);
				args = Arrays.copyOfRange(args, 1, args.length);
				console.fine("Preparing subcommand %s", executor.getName());
				return subCommand.prepare(executor, params, args, stack);
			}
		}

		if (stack.peek() instanceof AsyncCallbackCommand)
		{
			console.fine("Preparing AsyncCallback command with %d params and %d args", params.size(), args.length);
			return new PreparedAsynchronousCallbackCommand(executor, stack, args, params);
		}
		if (stack.peek() instanceof AsyncCommand)
		{
			console.fine("Preparing Async command with %d params and %d args", params.size(), args.length);
			return new PreparedAsynchronousCommand(executor, stack, args, params);
		}
		console.fine("Preparing Sync command with %d params and %d args", params.size(), args.length);
		return new PreparedSynchronousCommand(executor, stack, args, params);
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
		if (captureTail && args.length > index)
			parameters.put(argumentList.get(index - 1), StringUtils.join(args, " ", index - 1, args.length));
		return parameters;
	}

	private final ImmutableList<String> argumentList;
	private final HashMap<String, Command> subCommands = new HashMap<String, Command>();
	private final String name;
	private final String permission;
	private final String description;
	private IOutput console;
	private boolean captureTail;
}
