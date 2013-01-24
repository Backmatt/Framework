package no.runsafe.framework;

import no.runsafe.framework.command.BukkitCommandExecutor;
import no.runsafe.framework.command.ICommand;
import no.runsafe.framework.command.ICommandHandler;
import no.runsafe.framework.command.RunsafeCommandHandler;
import no.runsafe.framework.event.IPluginDisabled;
import no.runsafe.framework.event.IPluginEnabled;
import no.runsafe.framework.output.IOutput;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public abstract class RunsafePlugin extends InjectionPlugin
{
	static
	{
		Level consoleDebug = null;
		if (new File("runsafe/global.yml").exists())
		{
			try
			{
				YamlConfiguration global = new YamlConfiguration();
				global.load("runsafe/global.yml");
				String debug = (String) global.get("debug");
				if (debug != null)
					consoleDebug = Level.parse(debug);
			}
			catch (Exception e)
			{
			}
		}
		debugLevel = consoleDebug;
	}

//	public static final HashMap<String, RunsafePlugin> Instances = new HashMap<String, RunsafePlugin>();

	public static ICommandHandler getPluginCommand(String name)
	{
		for (String plugin : Instances.keySet())
		{
			PluginCommand command = Instances.get(plugin).getCommand(name);
			if (command != null)
			{
				CommandExecutor executor = command.getExecutor();
				if (executor instanceof BukkitCommandExecutor)
				{
					BukkitCommandExecutor handler = (BukkitCommandExecutor) executor;
					return handler.getHandler();
				}
			}
		}
		return null;
	}

	@Override
	public final void onEnable()
	{
		if (container == null)
			initializePlugin();
		super.onEnable();
		output.fine("Plugin initialized.");

		for (IPluginEnabled impl : getComponents(IPluginEnabled.class))
			impl.OnPluginEnabled();
		output.fine("Plugin enabled event executed.");
	}

	@Override
	public final void onDisable()
	{
		output.outputDebugToConsole(String.format("Disabling plugin %s", this.getName()), Level.FINE);
		for (IPluginDisabled impl : getComponents(IPluginDisabled.class))
			impl.OnPluginDisabled();
	}

	protected abstract void PluginSetup();

	@Deprecated
	protected List<RunsafeCommandHandler> GetLegacyCommands()
	{
		ArrayList<RunsafeCommandHandler> handlers = new ArrayList<RunsafeCommandHandler>();
		for (ICommand command : getComponents(ICommand.class))
		{
			command.setConsole(output);
			handlers.add(new RunsafeCommandHandler(command, output));
		}
		return handlers;
	}

	@Override
	protected void initializePlugin()
	{
		Instances.put(getName(), this);
		super.initializePlugin();

		output = getComponent(IOutput.class);
		if (debugLevel != null)
			output.setDebugLevel(debugLevel);
		output.fine("Standard components added.");

		this.PluginSetup();
		output.fine("Plugin setup performed.");

		RegisterCommands();
		output.outputDebugToConsole("Initiation complete", Level.FINE);
	}

	@Deprecated
	private void RegisterCommands()
	{
		for (RunsafeCommandHandler handler : this.GetLegacyCommands())
		{
			PluginCommand command = getCommand(handler.getName());

			if (command == null)
				output.outputToConsole(String.format("Command not found: %s - does it exist in plugin.yml?", handler.getName()));
			else
			{
				output.fine(String.format("Command handler for %s registered with bukkit.", handler.getName()));
				command.setExecutor(handler);
			}
		}
	}

	private static final Level debugLevel;
}
