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

public class SnapshotEntityDespawn implements ISnapshot{
	private UUID uuid;
	private World world;
	private Entity entityType;
	
	public SnapshotEntityDespawn(Entity entityType) {
		this.entityType = entityType;
		this.world = entityType.world;
		this.uuid = entityType.getUniqueID();
	}

	@Override
	public void doTick() {
		for(Entity e : world.loadedEntityList) {
			if(e.getUniqueID() == uuid) {
				world.removeEntity(e);
				return;
			}
		}
	}

	@Override
	public void revertTick() {
		Entity e = entityType;
		e.setUniqueId(uuid);
		e.isAddedToWorld();
		e.forceSpawn = true;
		world.spawnEntity(e);
	}
}
