package no.runsafe.framework;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import no.runsafe.framework.command.RunsafeCommandHandler;
import no.runsafe.framework.configuration.RunsafeConfigurationHandler;
import no.runsafe.framework.database.DatabaseHelper;
import no.runsafe.framework.database.RunsafeDatabaseHandler;
import no.runsafe.framework.interfaces.IKernel;
import no.runsafe.framework.interfaces.IOutput;
import no.runsafe.framework.interfaces.IPluginDisabled;
import no.runsafe.framework.interfaces.IPluginEnabled;
import no.runsafe.framework.output.RunsafeOutputHandler;
import no.runsafe.framework.timer.RunsafeTimerHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;

public class RunsafePlugin extends JavaPlugin implements IKernel
{	
	@Override
	public void onEnable()
	{	
		if(container == null)
		{
			container = new DefaultPicoContainer(new Caching());
			addComponent(this);
			addComponent(this.getServer());
			addComponent(this.getLogger());
			addComponent(RunsafeConfigurationHandler.class);
			addComponent(RunsafeOutputHandler.class);
			addComponent(RunsafeDatabaseHandler.class);
			addComponent(RunsafeTimerHandler.class);
			addComponent(DatabaseHelper.class);		
			output = getComponent(IOutput.class);
			this.PluginSetup();
			
			output.outputDebugToConsole(String.format("Initiating plugin %s", this.getName()), Level.FINE);
			RegisterEvents();
			RegisterCommands();
			output.outputDebugToConsole(String.format("Registered %d event listeners", eventListeners.size()), Level.FINE);
			output.outputDebugToConsole(String.format("Initiation completed", this.getName()), Level.FINE);
		}
		for(IPluginEnabled impl : getComponents(IPluginEnabled.class))
		{
			impl.OnPluginEnabled();
		}
		
	}
	
	@Override
	public void onDisable()
	{
		output.outputDebugToConsole(String.format("Deinitiating plugin %s", this.getName()), Level.FINE);
		UnregisterEvents();
		
		for(IPluginDisabled impl : getComponents(IPluginDisabled.class))
		{
			impl.OnPluginDisabled();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		String command = cmd.getName().toLowerCase();
		if(commands.containsKey(command))
			return commands.get(command).onCommand(sender, cmd, label, args);
		
		return false;
	}
	
	@Override
	public void addComponent(Object implOrInstance)
	{
		output.outputDebugToConsole(String.format("Plugin %s added component %s", this.getName(), implOrInstance.getClass().getCanonicalName()), Level.FINE);
		container.addComponent(implOrInstance);
	}
	
	@Override
	public <T> T getComponent(Class<T> type)
	{
		return this.container.getComponent(type);
	}
	
	@Override
	public <T> List<T> getComponents(Class<T> type)
	{
		return this.container.getComponents(type);
	}
	
	protected List<Listener> GetEvents()
	{
		return getComponents(Listener.class);
	}		

	protected List<RunsafeCommandHandler> GetCommands()
	{
		return getComponents(RunsafeCommandHandler.class);
	}
	
	protected void PluginSetup()
	{
	}
	
	private void RegisterEvents()
	{
		eventListeners = GetEvents();
		if(eventListeners != null && !eventListeners.isEmpty())
		{
			PluginManager pluginManager = this.getServer().getPluginManager();
			for (Listener listener : eventListeners) 
			{
				pluginManager.registerEvents(listener, this);
				output.outputDebugToConsole(String.format("Registered event listener %s", listener.getClass().getCanonicalName()), Level.FINER);
			}
		}		
	}
	
	private void RegisterCommands()
	{
		commands = new HashMap<String, RunsafeCommandHandler>();
		List<RunsafeCommandHandler> commandList = this.GetCommands();
		for(RunsafeCommandHandler handler : commandList)
		{
			PluginCommand command = getCommand(handler.getName());
			
			if(command == null)
				output.outputToConsole(String.format("Command not found: %s - does it exist in plugin.yml?", handler.getName()));
			else
				command.setExecutor(handler);
		}
	}
	
	private void UnregisterEvents() 
	{
		// Not supported by bukkit?!
	}
	
	protected DefaultPicoContainer container = null; 
	private List<Listener> eventListeners;
	private IOutput output;
	private HashMap<String, RunsafeCommandHandler> commands;
}
