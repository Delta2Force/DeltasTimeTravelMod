package deltatwoforce.deltastimetravelmod.snapshot;

import java.util.UUID;

import ibxm.Player;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SnapshotEntityMove implements ISnapshot{
	public Vec3d position;
	public float yaw;
	public float pitch;
	public Entity e;
	
	public SnapshotEntityMove(Entity e) {
		position = e.getPositionVector();
		yaw = e.rotationYaw;
		pitch = e.rotationPitch;
		this.e = e;
	}
	
	
	@Override
	public void doTick() {
		tick();
	}

	@Override
	public void revertTick() {
		tick();
	}
	
	public void tick() {
		if(e.isDead) {
			e.isDead = false;
		}
		e.setPositionAndRotation(position.x, position.y, position.z, yaw, pitch);
	}
}
