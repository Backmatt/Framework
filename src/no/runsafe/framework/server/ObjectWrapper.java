package no.runsafe.framework.server;

import no.runsafe.framework.server.block.*;
import no.runsafe.framework.server.chunk.RunsafeChunk;
import no.runsafe.framework.server.entity.RunsafeEntity;
import no.runsafe.framework.server.entity.RunsafeLivingEntity;
import no.runsafe.framework.server.entity.RunsafePainting;
import no.runsafe.framework.server.entity.RunsafeProjectile;
import no.runsafe.framework.server.inventory.RunsafeInventory;
import no.runsafe.framework.server.item.RunsafeItem;
import no.runsafe.framework.server.item.RunsafeItemStack;
import no.runsafe.framework.server.material.RunsafeMaterial;
import no.runsafe.framework.server.material.RunsafeMaterialData;
import no.runsafe.framework.server.metadata.RunsafeMetadata;
import no.runsafe.framework.server.player.RunsafePlayer;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.Metadatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectWrapper
{
	@SuppressWarnings("unchecked")
	public static <Wrapper> List<Wrapper> convert(List<?> toWrap)
	{
		if(toWrap == null)
			return null;

		ArrayList<Wrapper> results = new ArrayList<Wrapper>();
		for(Object item : toWrap)
		{
			if(item instanceof Metadatable)
				results.add((Wrapper) convert((Metadatable)item));
			else if(item instanceof ItemStack)
				results.add((Wrapper) convert((ItemStack)item));
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public static <Wrapper extends RunsafeMetadata, Raw extends Metadatable> List<Wrapper> convert(Raw[] toWrap)
	{
		if(toWrap == null)
			return null;

		ArrayList<Wrapper> results = new ArrayList<Wrapper>();
		for(Raw item : toWrap)
		{
			results.add((Wrapper) convert(item));
		}
		return results;
	}

	public static RunsafeInventory convert(Inventory toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeInventory(toWrap);
	}

	public static RunsafeMaterial convert(Material toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeMaterial(toWrap);
	}

	public static RunsafeChunk convert(Chunk toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeChunk(toWrap);
	}

	public static RunsafeMaterialData convert(MaterialData toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeMaterialData(toWrap);
	}

	public static List<RunsafePlayer> convert(OfflinePlayer[] toWrap)
	{
		if(toWrap == null)
			return null;

		ArrayList<RunsafePlayer> results = new ArrayList<RunsafePlayer>();
		for(OfflinePlayer player : toWrap)
			results.add(new RunsafePlayer(player));
		return results;
	}

	public static List<RunsafePlayer> convert(Set<? extends OfflinePlayer> toWrap)
	{
		if(toWrap == null)
			return null;

		ArrayList<RunsafePlayer> results = new ArrayList<RunsafePlayer>();
		for(OfflinePlayer player : toWrap)
			results.add(new RunsafePlayer(player));
		return results;
	}

	public static RunsafeMetadata convert(Metadatable toWrap)
	{
		if(toWrap == null)
			return null;

		if(toWrap instanceof Block)
			return convert((Block) toWrap);

		if(toWrap instanceof BlockState)
			return convert((BlockState) toWrap);

		if(toWrap instanceof Entity)
			return convert((Entity) toWrap);

		if(toWrap instanceof World)
			return convert((World) toWrap);

		return new RunsafeMetadata(toWrap);
	}

	public static RunsafeBlock convert(Block toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeBlock(toWrap);
	}

	public static RunsafeBlockState convert(BlockState toWrap)
	{
		if(toWrap == null)
			return null;

		if(toWrap instanceof BrewingStand)
			return convert((BrewingStand) toWrap);

		if(toWrap instanceof Chest)
			return convert((Chest) toWrap);

		if(toWrap instanceof CreatureSpawner)
			return convert((CreatureSpawner) toWrap);

		if(toWrap instanceof Dispenser)
			return convert((Dispenser) toWrap);

		if(toWrap instanceof Furnace)
			return convert((Furnace) toWrap);

		if(toWrap instanceof Jukebox)
			return convert((Jukebox) toWrap);

		if(toWrap instanceof NoteBlock)
			return convert((NoteBlock) toWrap);

		if(toWrap instanceof Sign)
			return convert((Sign) toWrap);

		return new RunsafeBlockState(toWrap);
	}

	public static RunsafeBrewingStand convert(BrewingStand toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeBrewingStand(toWrap);
	}

	public static RunsafeChest convert(Chest toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeChest(toWrap);
	}

	public static RunsafeCreatureSpawner convert(CreatureSpawner toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeCreatureSpawner(toWrap);
	}

	public static RunsafeDispenser convert(Dispenser toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeDispenser(toWrap);
	}

	public static RunsafeFurnace convert(Furnace toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeFurnace(toWrap);
	}

	public static RunsafeJukebox convert(Jukebox toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeJukebox(toWrap);
	}

	public static RunsafeNoteBlock convert(NoteBlock toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeNoteBlock(toWrap);
	}

	public static RunsafeSign convert(Sign toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeSign(toWrap);
	}

	public static RunsafeEntity convert(Entity toWrap)
	{
		if(toWrap == null)
			return null;

		if(toWrap instanceof Player)
			return convert((Player) toWrap);

		if(toWrap instanceof Painting)
			return convert((Painting) toWrap);

		if(toWrap instanceof LivingEntity)
			return convert((LivingEntity) toWrap);

		if(toWrap instanceof Projectile)
			return convert((Projectile) toWrap);

		return new RunsafeEntity(toWrap);
	}

	public static RunsafePlayer convert(Player toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafePlayer(toWrap);
	}

	public static RunsafePainting convert(Painting toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafePainting(toWrap);
	}

	public static RunsafeLivingEntity convert(LivingEntity toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeLivingEntity(toWrap);
	}

	public static RunsafeProjectile convert(Projectile toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeProjectile(toWrap);
	}

	public static RunsafeWorld convert(World toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeWorld(toWrap);
	}

	public static RunsafeLocation convert(Location toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeLocation(toWrap);
	}

	public static RunsafeItem convert(Item toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeItem(toWrap);
	}

	public static RunsafeItemStack convert(ItemStack toWrap)
	{
		if(toWrap == null)
			return null;
		return new RunsafeItemStack(toWrap);
	}
}
