package no.runsafe.framework.command;

import no.runsafe.framework.output.ChatColour;
import no.runsafe.framework.server.ICommandExecutor;
import no.runsafe.framework.server.ObjectWrapper;
import org.bukkit.entity.Player;

public final class BukkitCommandExecutor implements org.bukkit.command.CommandExecutor
{
	public BukkitCommandExecutor(ICommandHandler command, ICommandExecutor console)
	{
		this.command = command;
		this.console = console;
	}

	public String getName()
	{
		return command.getName();
	}

	@Override
	public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args)
	{
		PreparedCommand preparedCommand;
		if (sender instanceof Player)
			preparedCommand = this.command.prepare(ObjectWrapper.convert((Player) sender), args);
		else
			preparedCommand = this.command.prepare(console, args);

		String permission = preparedCommand.getRequiredPermission();
		if (permission == null || sender.hasPermission(permission))
			sender.sendMessage(ChatColour.ToMinecraft(preparedCommand.execute()));
		else
		{
			sender.sendMessage(
				ChatColour.ToMinecraft(
					String.format("&4You do not have the required permission &c%s&4!", permission)
				)
			);
		}
		return true;
	}

	private final ICommandHandler command;
	private final ICommandExecutor console;
}
