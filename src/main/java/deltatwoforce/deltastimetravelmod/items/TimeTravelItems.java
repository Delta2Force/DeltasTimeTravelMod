package deltatwoforce.deltastimetravelmod.items;

import deltatwoforce.deltastimetravelmod.DeltasTimeTravelMod;
import deltatwoforce.deltastimetravelmod.blocks.BlockCar;
import deltatwoforce.deltastimetravelmod.blocks.TimeTravelBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class TimeTravelItems{
	public static final ItemTimeTravelStick TIME_TRAVEL_STICK = new ItemTimeTravelStick();
	public static final Item CAR = new ItemBlock(TimeTravelBlocks.CAR).setRegistryName(DeltasTimeTravelMod.MODID, "car").setCreativeTab(CreativeTabs.MISC);
}
