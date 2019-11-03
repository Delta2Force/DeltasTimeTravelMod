package deltatwoforce.deltastimetravelmod.snapshot;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SnapshotChangeBlock implements ISnapshot{
	public BlockPos pos;
	public IBlockState from;
	public IBlockState to;
	public World world;
	
	public SnapshotChangeBlock(BlockPos pos, IBlockState from, IBlockState to, World world) {
		this.pos=pos;
		this.from=from;
		this.to=to;
		this.world=world;
	}
	
	@Override
	public void doTick() {
		world.setBlockState(pos, to);
	}
	
	@Override
	public void revertTick() {
		world.setBlockState(pos, from);
	}
}