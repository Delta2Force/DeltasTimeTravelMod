package deltatwoforce.deltastimetravelmod.snapshot;

import net.minecraft.world.World;

public class SnapshotTime implements ISnapshot{
	private World world;
	private long time;
	
	public SnapshotTime(World w) {
		this.world = w;
		this.time = w.getWorldTime();
	}
	
	@Override
	public void doTick() {
		world.setWorldTime(time);
	}

	@Override
	public void revertTick() {
		world.setWorldTime(time);
	}
}
