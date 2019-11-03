package deltatwoforce.deltastimetravelmod.entity;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PastPlayerEntity extends EntityLiving{
	public final GameProfile profile;
	
    public float renderOffsetX;
    
    @SideOnly(Side.CLIENT)
    public float renderOffsetY;
    
    public float renderOffsetZ;
	
	public PastPlayerEntity(World worldIn) {
		super(worldIn);
		this.profile = null;
	}
	
	public PastPlayerEntity(World worldIn, GameProfile profile) {
		super(worldIn);
		this.profile = profile;
	}
	
	public ResourceLocation getLocationSkin() {
	    NetworkPlayerInfo networkplayerinfo = new NetworkPlayerInfo(profile);
	    return profile == null ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()) : networkplayerinfo.getLocationSkin();
	}
}
