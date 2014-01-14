package no.runsafe.framework.api.command.argument;

import javax.annotation.Nonnull;

public abstract class CommandArgumentSpecification implements IArgument
{
	protected CommandArgumentSpecification(String name)
	{
		this.name = name;
	}

	@Override
	public int length()
	{
		return name.length();
	}

	@Override
	public char charAt(int index)
	{
		return name.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end)
	{
		return name.subSequence(start, end);
	}

	@Nonnull
	@Override
	public String toString()
	{
		return name;
	}

	protected final String name;
}
