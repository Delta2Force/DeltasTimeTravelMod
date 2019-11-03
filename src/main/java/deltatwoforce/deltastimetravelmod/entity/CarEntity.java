package deltatwoforce.deltastimetravelmod.entity;

import deltatwoforce.deltastimetravelmod.DeltasTimeTravelMod;
import deltatwoforce.deltastimetravelmod.snapshot.ISnapshot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CarEntity extends Entity{
	public boolean forward;
	public boolean back;
	public boolean left;
	public boolean right;
	public boolean up;
	public boolean down;
	public float acceleration;
	public float verticalAcceleration;
	public float rotationAcceleration;
	
	public boolean timeTravel;
	public boolean travelFlag;
	
	public CarEntity(World worldIn) {
		super(worldIn);
		this.timeTravel = false;
	}
	
	public CarEntity(World worldIn, boolean timeTravel) {
		super(worldIn);
		this.timeTravel = timeTravel;
	}
	
	@Override
	public boolean hasNoGravity() {
		return false;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
	
	@Override
	public AxisAlignedBB getEntityBoundingBox() {
		return super.getEntityBoundingBox().grow(0,0.25,0).offset(0, -0.3, 0);
	}
	
	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return true;
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		player.startRiding(this);
		return true;
	}
	
	@Override
	public void onUpdate() {
		updateInputs();
		this.posX += acceleration * Math.cos(this.rotationYaw * Math.PI / 180);
		this.posZ -= acceleration * Math.sin(this.rotationYaw * Math.PI / 180);
		this.rotationYaw += rotationAcceleration;
		if(this.isBeingRidden() && !timeTravel) {
			if(forward) {	
				acceleration = acceleration + (0.4f - acceleration) * .1f;
			}else if(back){
				acceleration = acceleration + (-0.4f - acceleration) * .1f;
			}else {
				acceleration = acceleration + (0f - acceleration) * .1f;
			}
			if(left) {
				rotationAcceleration = rotationAcceleration + (10f - rotationAcceleration) * .1f;
			}else if(right) {
				rotationAcceleration = rotationAcceleration + (-10f - rotationAcceleration) * .1f;
			}else {
				rotationAcceleration = rotationAcceleration + (0f - rotationAcceleration) * .1f;
			}
			if(up) {
				this.posY += 0.2;
			}
			if(down) {
				this.posY -= 0.2;
			}
		}else {
			if(timeTravel) {
				if(travelFlag) {
					acceleration = acceleration + (0f - acceleration) * .1f;
					if(!this.world.checkBlockCollision(getEntityBoundingBox())) {
						this.posY -= 0.2;
					}
				}else {
					acceleration = acceleration + (0.8f - acceleration) * .1f;
				}
				if(acceleration > 0.79f) {
					if(!this.world.isRemote) {
						for(int i = DeltasTimeTravelMod.timeline.size()-1;i>0;i--) {
							for(ISnapshot snapshot : DeltasTimeTravelMod.timeline.get(i).extra) {
								snapshot.revertTick();
							}
						}
						
						DeltasTimeTravelMod.inThePast = true;
						DeltasTimeTravelMod.recordTime = false;
					}
					timeTravel = false;
				}
			}else {
				acceleration = acceleration + (0f - acceleration) * .1f;
				rotationAcceleration = rotationAcceleration + (0f - rotationAcceleration) * .1f;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateInputs() {
		forward = Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown();
		back = Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown();
		left = Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown();
		right = Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown();
		up = Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
		down = Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown();
	}
	
	@Override
	public double getMountedYOffset() {
		return 0;
	}
	
	@Override
	protected void entityInit() {}
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}
}
