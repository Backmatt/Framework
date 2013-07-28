package no.runsafe.framework.api.command.player;

import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public interface IPlayerExecute
{
	/**
	 * If you use optional arguments, you must still override this command but you can leave it blank.
	 *
	 * @param executor   The player executing the command
	 * @param parameters The arguments you defined in the constructor and their values as supplied by the user
	 * @return Message to show to the user running the command
	 */
	String OnExecute(RunsafePlayer executor, Map<String, String> parameters);
}