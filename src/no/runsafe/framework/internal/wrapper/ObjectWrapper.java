package no.runsafe.framework.internal.wrapper;

import no.runsafe.framework.api.minecraft.IInventoryHolder;
import no.runsafe.framework.api.minecraft.RunsafeEntityType;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeTravelAgent;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.block.*;
import no.runsafe.framework.minecraft.enchantment.RunsafeEnchantment;
import no.runsafe.framework.minecraft.entity.*;
import no.runsafe.framework.minecraft.item.meta.*;
import no.runsafe.framework.minecraft.material.RunsafeMaterialData;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.framework.internal.wrapper.item.BukkitItemStack;
import no.runsafe.framework.internal.wrapper.metadata.BukkitMetadata;
import no.runsafe.framework.minecraft.inventory.*;
import no.runsafe.framework.minecraft.chunk.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.Metadatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ObjectWrapper
{
	@SuppressWarnings("unchecked")
	public static <Wrapper> List<Wrapper> convert(List<?> toWrap)
	{
		if (toWrap == null)
			return null;

		ArrayList<Wrapper> results = new ArrayList<Wrapper>();
		for (Object item : toWrap)
		{
			if (item instanceof Metadatable)
				results.add((Wrapper) convert((Metadatable) item));
			else if (item instanceof ItemStack)
				results.add((Wrapper) convert((ItemStack) item));
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public static <Wrapper extends BukkitMetadata, Raw extends Metadatable> List<Wrapper> convert(Raw[] toWrap)
	{
		if (toWrap == null)
			return null;

		ArrayList<Wrapper> results = new ArrayList<Wrapper>();
		for (Raw item : toWrap)
			results.add((Wrapper) convert(item));
		return results;
	}

	@SuppressWarnings("unchecked")
	public static <Wrapper extends BukkitItemStack, Raw extends ItemStack> List<Wrapper> convert(Raw[] toWrap)
	{
		if (toWrap == null)
			return null;

		ArrayList<Wrapper> results = new ArrayList<Wrapper>();
		for (Raw item : toWrap)
			results.add((Wrapper) convert(item));
		return results;
	}

	public static RunsafeInventory convert(Inventory toWrap)
	{
		if (toWrap == null)
			return null;

		if (toWrap instanceof AnvilInventory)
			return new RunsafeAnvilInventory((AnvilInventory) toWrap);

		return new RunsafeInventory(toWrap);
	}

	public static RunsafeEntityEquipment convert(EntityEquipment toWrap)
	{
		if (toWrap == null)
			return null;

		return new RunsafeEntityEquipment(toWrap);
	}

	public static no.runsafe.framework.minecraft.Item convert(Material toWrap)
	{
		if (toWrap == null)
			return null;
		return no.runsafe.framework.minecraft.Item.get(toWrap, (byte) 0);
	}

	public static RunsafeChunk convert(Chunk toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeChunk(toWrap);
	}

	public static RunsafeMaterialData convert(MaterialData toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeMaterialData(toWrap);
	}

	public static List<RunsafePlayer> convert(OfflinePlayer[] toWrap)
	{
		if (toWrap == null)
			return null;

		ArrayList<RunsafePlayer> results = new ArrayList<RunsafePlayer>();
		for (OfflinePlayer player : toWrap)
			results.add(new RunsafePlayer(player));
		return results;
	}

	public static List<RunsafePlayer> convert(Set<? extends OfflinePlayer> toWrap)
	{
		if (toWrap == null)
			return null;

		ArrayList<RunsafePlayer> results = new ArrayList<RunsafePlayer>();
		for (OfflinePlayer player : toWrap)
			results.add(new RunsafePlayer(player));
		return results;
	}

	public static BukkitMetadata convert(Metadatable toWrap)
	{
		if (toWrap == null)
			return null;

		if (toWrap instanceof Block)
			return convert((Block) toWrap);

		if (toWrap instanceof BlockState)
			return convert((BlockState) toWrap);

		if (toWrap instanceof Entity)
			return convert((Entity) toWrap);

		if (toWrap instanceof World)
			return convert((World) toWrap);

		return new BukkitMetadata(toWrap);
	}

	public static RunsafeBlock convert(Block toWrap)
	{
		if (toWrap == null)
			return null;
		if (toWrap.getState() != null && toWrap.getState() instanceof CreatureSpawner)
			return new RunsafeSpawner(toWrap);
		return new RunsafeBlock(toWrap);
	}

	public static RunsafeBlockState convert(BlockState toWrap)
	{
		if (toWrap == null)
			return null;

		if (toWrap instanceof BrewingStand)
			return convert((BrewingStand) toWrap);

		if (toWrap instanceof Chest)
			return convert((Chest) toWrap);

		if (toWrap instanceof CreatureSpawner)
			return convert((CreatureSpawner) toWrap);

		if (toWrap instanceof Dispenser)
			return convert((Dispenser) toWrap);

		if (toWrap instanceof Furnace)
			return convert((Furnace) toWrap);

		if (toWrap instanceof Jukebox)
			return convert((Jukebox) toWrap);

		if (toWrap instanceof NoteBlock)
			return convert((NoteBlock) toWrap);

		if (toWrap instanceof Sign)
			return convert((Sign) toWrap);

		if (toWrap instanceof Dropper)
			return convert((Dropper) toWrap);

		return new RunsafeBlockState(toWrap);
	}

	public static RunsafeBrewingStand convert(BrewingStand toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeBrewingStand(toWrap);
	}

	public static RunsafeChest convert(Chest toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeChest(toWrap);
	}

	public static RunsafeDoubleChest convert(DoubleChest toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeDoubleChest(toWrap);
	}

	public static RunsafeCreatureSpawner convert(CreatureSpawner toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeCreatureSpawner(toWrap);
	}

	public static RunsafeDispenser convert(Dispenser toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeDispenser(toWrap);
	}

	public static RunsafePlayerInventory convert(PlayerInventory toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafePlayerInventory(toWrap);
	}

	public static RunsafeFurnace convert(Furnace toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeFurnace(toWrap);
	}

	public static RunsafeJukebox convert(Jukebox toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeJukebox(toWrap);
	}

	public static RunsafeFallingBlock convert(FallingBlock toWrap)
	{
		if (toWrap == null)
			return null;

		return new RunsafeFallingBlock(toWrap);
	}

	public static RunsafeNoteBlock convert(NoteBlock toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeNoteBlock(toWrap);
	}

	public static RunsafeSign convert(Sign toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeSign(toWrap);
	}

	public static RunsafeEntity convert(Entity toWrap)
	{
		if (toWrap == null)
			return null;

		if (toWrap instanceof Player)
			return convert((Player) toWrap);

		if (toWrap instanceof Painting)
			return convert((Painting) toWrap);

		if (toWrap instanceof ItemFrame)
			return convert((ItemFrame) toWrap);

		if (toWrap instanceof org.bukkit.entity.LivingEntity)
			return convert((org.bukkit.entity.LivingEntity) toWrap);

		if (toWrap instanceof Projectile)
			return convert((Projectile) toWrap);

		if (toWrap instanceof Item)
			return convert((Item) toWrap);

		return new RunsafeEntity(toWrap);
	}

	public static RunsafePlayer convert(Player toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafePlayer(toWrap);
	}

	public static RunsafePlayer convert(HumanEntity toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafePlayer((Player) toWrap);
	}

	public static RunsafePainting convert(Painting toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafePainting(toWrap);
	}

	public static RunsafeItemFrame convert(ItemFrame toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeItemFrame(toWrap);
	}

	public static RunsafeLivingEntity convert(org.bukkit.entity.LivingEntity toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeLivingEntity(toWrap);
	}

	public static RunsafeProjectile convert(Projectile toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeProjectile(toWrap);
	}

	public static RunsafeFish convert(Fish toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeFish(toWrap);
	}

	public static RunsafeWorld convert(World toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeWorld(toWrap);
	}

	public static RunsafeLocation convert(Location toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeLocation(toWrap);
	}

	public static RunsafeItem convert(Item toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeItem(toWrap);
	}

	public static RunsafeMeta convert(ItemStack toWrap)
	{
		if (toWrap == null)
			return null;
		ItemMeta meta = toWrap.getItemMeta();
		if (meta instanceof BookMeta)
			return new RunsafeBook(toWrap);
		if (meta instanceof EnchantmentStorageMeta)
			return new RunsafeEnchantmentStorage(toWrap);
		if (meta instanceof FireworkMeta)
			return new RunsafeFirework(toWrap);
		if (meta instanceof LeatherArmorMeta)
			return new RunsafeLeatherArmor(toWrap);
		if (meta instanceof MapMeta)
			return new RunsafeMap(toWrap);
		if (meta instanceof PotionMeta)
			return new RunsafePotion(toWrap);
		if (meta instanceof SkullMeta)
			return new RunsafeSkull(toWrap);
		return new RunsafeMeta(toWrap);
	}

	public static RunsafeEnchantment convert(Enchantment toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeEnchantment(toWrap);
	}

	public static RunsafeInventoryType convert(InventoryType toWrap)
	{
		return RunsafeInventoryType.valueOf(toWrap.name());
	}

	public static RunsafeInventoryView convert(InventoryView toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeInventoryView(toWrap);
	}

	public static RunsafeTravelAgent convert(TravelAgent toWrap)
	{
		if (toWrap == null)
			return null;
		return new RunsafeTravelAgent(toWrap);
	}

	public static RunsafeHopper convert(Hopper toWrap)
	{
		if (toWrap == null)
			return null;

		return new RunsafeHopper(toWrap);
	}

	public static RunsafeDropper convert(Dropper toWrap)
	{
		if (toWrap == null)
			return null;

		return new RunsafeDropper(toWrap);
	}

	public static IInventoryHolder convert(InventoryHolder toWrap)
	{
		if (toWrap == null)
			return null;
		if (toWrap instanceof Hopper)
			return convert((Hopper) toWrap);
		if (toWrap instanceof BrewingStand)
			return convert((BrewingStand) toWrap);
		if (toWrap instanceof Chest)
			return convert((Chest) toWrap);
		if (toWrap instanceof Dispenser)
			return convert((Dispenser) toWrap);
		if (toWrap instanceof Furnace)
			return convert((Furnace) toWrap);
		if (toWrap instanceof DoubleChest)
			return convert((DoubleChest) toWrap);
		if (toWrap instanceof Player)
			return convert((Player) toWrap);
		if (toWrap instanceof Dropper)
			return convert((Dropper) toWrap);
		return null;
	}

	public static RunsafeEntityType convert(org.bukkit.entity.EntityType type)
	{
		if (type == null)
			return null;
		return no.runsafe.framework.minecraft.entity.EntityType.convert(type);
	}
}
