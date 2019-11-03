package deltatwoforce.deltastimetravelmod.blocks;

import deltatwoforce.deltastimetravelmod.DeltasTimeTravelMod;
import deltatwoforce.deltastimetravelmod.entity.CarEntity;
import deltatwoforce.deltastimetravelmod.snapshot.SnapshotChangeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCar extends Block{
	
	public BlockCar(Material materialIn) {
		super(materialIn);
		setRegistryName(DeltasTimeTravelMod.MODID, "car");
		setUnlocalizedName("car");
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			worldIn.setBlockState(pos, Blocks.WOOL.getDefaultState());
			DeltasTimeTravelMod.recordTime = false;
			DeltasTimeTravelMod.currentTickSnapshot.extra.add(new SnapshotChangeBlock(pos, state, Blocks.NETHERRACK.getDefaultState(), worldIn));
			DeltasTimeTravelMod.currentTickSnapshot.extra.add(new SnapshotChangeBlock(pos.add(0, 1, 0), worldIn.getBlockState(pos.add(0,1,0)), Blocks.FIRE.getDefaultState(), worldIn));
			DeltasTimeTravelMod.timeline.add(DeltasTimeTravelMod.currentTickSnapshot);
			CarEntity ce = new CarEntity(worldIn, true);
			ce.rotationYaw = -90;
			ce.setPosition(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5);
			worldIn.spawnEntity(ce);
			playerIn.startRiding(ce);
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCar();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}
