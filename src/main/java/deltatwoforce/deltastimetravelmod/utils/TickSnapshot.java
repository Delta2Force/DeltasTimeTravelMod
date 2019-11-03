package deltatwoforce.deltastimetravelmod.utils;

import java.util.ArrayList;

import deltatwoforce.deltastimetravelmod.snapshot.ISnapshot;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TickSnapshot {
	public Vec3d position;
	public float yaw;
	public float pitch;
	public ItemStack itemInHand;
	public ArrayList<ISnapshot> extra;
}
