package deltatwoforce.deltastimetravelmod.items;

import deltatwoforce.deltastimetravelmod.DeltasTimeTravelMod;
import deltatwoforce.deltastimetravelmod.entity.PastPlayerEntity;
import deltatwoforce.deltastimetravelmod.snapshot.ISnapshot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemTimeTravelStick extends Item{
	public ItemTimeTravelStick() {
		this.setRegistryName(DeltasTimeTravelMod.MODID, "time_travel_stick");
		this.setCreativeTab(CreativeTabs.MISC);
		this.setUnlocalizedName("time_travel_stick");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
			for(int i = DeltasTimeTravelMod.timeline.size()-1;i>0;i--) {
				for(ISnapshot snapshot : DeltasTimeTravelMod.timeline.get(i).extra) {
					snapshot.revertTick();
				}
			}
			
			DeltasTimeTravelMod.player = playerIn;
			DeltasTimeTravelMod.inThePast = true;
			DeltasTimeTravelMod.recordTime = false;
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
