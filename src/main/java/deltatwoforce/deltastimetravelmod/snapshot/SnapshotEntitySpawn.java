package deltatwoforce.deltastimetravelmod.snapshot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import ibxm.Player;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SnapshotEntitySpawn implements ISnapshot{
	private UUID uuid;
	private World world;
	private Entity entityType;
	
	public SnapshotEntitySpawn(Entity entityType) {
		this.entityType = entityType;
		this.world = entityType.world;
		this.uuid = entityType.getUniqueID();
	}

	@Override
	public void doTick() {
		Entity e = entityType;
		e.isDead = false;
		e.setUniqueId(uuid);
		world.spawnEntity(e);
	}

	@Override
	public void revertTick() {
		for(Entity e : world.loadedEntityList) {
			if(e.getUniqueID() == uuid) {
				world.removeEntity(e);
				return;
			}
		}
	}
}
