package no.runsafe.framework.minecraft.event.enchantment;

import no.runsafe.framework.internal.wrapper.ObjectWrapper;
import no.runsafe.framework.minecraft.block.RunsafeBlock;
import no.runsafe.framework.minecraft.enchantment.RunsafeEnchantment;
import no.runsafe.framework.minecraft.event.inventory.RunsafeCancellableInventoryEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.HashMap;
import java.util.Map;

public class RunsafeEnchantItemEvent extends RunsafeCancellableInventoryEvent
{
	public RunsafeEnchantItemEvent(EnchantItemEvent toWrap)
	{
		super(toWrap);
		this.event = toWrap;
	}

	public RunsafePlayer getEnchanter()
	{
		return ObjectWrapper.convert(this.event.getEnchanter());
	}

	public RunsafeBlock getEnchantBlock()
	{
		return ObjectWrapper.convert(this.event.getEnchantBlock());
	}

	public RunsafeMeta getItem()
	{
		return ObjectWrapper.convert(this.event.getItem());
	}

	public int getExpLevelCost()
	{
		return this.event.getExpLevelCost();
	}

	public void setExpLevelCost(int level)
	{
		this.event.setExpLevelCost(level);
	}

	public Map<RunsafeEnchantment, Integer> getEnchantsToAdd()
	{
		Map<RunsafeEnchantment, Integer> enchantments = new HashMap<RunsafeEnchantment, Integer>();

		for (Map.Entry<Enchantment, Integer> enchant : event.getEnchantsToAdd().entrySet())
			enchantments.put(ObjectWrapper.convert(enchant.getKey()), enchant.getValue());

		return enchantments;
	}

	public int whichButton()
	{
		return this.event.whichButton();
	}

	private final EnchantItemEvent event;
}
