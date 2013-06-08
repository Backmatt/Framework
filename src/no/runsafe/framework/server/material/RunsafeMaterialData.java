package no.runsafe.framework.server.material;

import no.runsafe.framework.wrapper.material.BukkitMaterialData;
import org.bukkit.material.MaterialData;

@SuppressWarnings("deprecation")
public class RunsafeMaterialData extends BukkitMaterialData
{
	public RunsafeMaterialData(MaterialData toWrap)
	{
		super(toWrap);
	}

	public RunsafeMaterialData(int type)
	{
		super(new MaterialData(type));
	}

	public RunsafeMaterialData(int type, byte data)
	{
		super(new MaterialData(type, data));
	}
}