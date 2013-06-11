package no.runsafe.framework.internal;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.IPluginUpdate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.picocontainer.Startable;

import java.io.IOException;
import java.util.logging.Level;

public class VersionEngine implements Startable
{
	public VersionEngine(RunsafePlugin plugin, IOutput output)
	{
		this(plugin, output, null);
	}

	public VersionEngine(RunsafePlugin plugin, IOutput output, IPluginUpdate updater)
	{
		this.plugin = plugin;
		this.updater = updater;
		this.output = output;
	}

	@Override
	public void start()
	{
		String lastVersion = getLastVersion();
		if (!plugin.getDescription().getVersion().equals(lastVersion))
		{
			if (updater == null || updater.UpdateFrom(lastVersion))
				saveCurrentVersion();
		}
		output.fine("Plugin version logged.");
	}

	@Override
	public void stop()
	{
	}

	public String getLastVersion()
	{
		YamlConfiguration config = new YamlConfiguration();
		try
		{
			config.load("runsafe/plugins.yml");
		}
		catch (IOException e)
		{
			return null;
		}
		catch (InvalidConfigurationException e)
		{
			output.outputToConsole(String.format("Invalid yml in runsafe/plugins.yml! - %s", e.getMessage()), Level.WARNING);
			return null;
		}
		return config.getString(plugin.getName());
	}

	public void saveCurrentVersion()
	{
		YamlConfiguration config = new YamlConfiguration();
		try
		{
			config.load("runsafe/plugins.yml");
		}
		catch (IOException e)
		{
			output.outputToConsole(String.format("Problem loading runsafe/plugins.yml! - %s", e.getMessage()), Level.WARNING);
		}
		catch (InvalidConfigurationException e)
		{
			output.outputToConsole(String.format("Invalid yml in runsafe/plugins.yml! - %s", e.getMessage()), Level.WARNING);
		}
		config.set(plugin.getName(), plugin.getDescription().getVersion());
		try
		{
			config.save("runsafe/plugins.yml");
		}
		catch (IOException e)
		{
			output.outputToConsole(String.format("Unable to save runsafe/plugins.yml! - %s", e.getMessage()), Level.SEVERE);
		}
	}

	private final RunsafePlugin plugin;
	private final IPluginUpdate updater;
	private final IOutput output;
}
