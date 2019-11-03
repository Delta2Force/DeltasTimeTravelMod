package deltatwoforce.deltastimetravelmod.entity;

import deltatwoforce.deltastimetravelmod.DeltasTimeTravelMod;
import deltatwoforce.deltastimetravelmod.model.CarModel;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderItemFrame;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CarEntityRenderer extends Render<CarEntity>{
	private CarModel model = new CarModel();
	private ResourceLocation texture = new ResourceLocation(DeltasTimeTravelMod.MODID, "textures/entities/car.png");
	
	protected CarEntityRenderer(RenderManager renderManager) {
		super(renderManager);
	}
	@Override
	protected ResourceLocation getEntityTexture(CarEntity entity) {
		return new ResourceLocation(DeltasTimeTravelMod.MODID, "textures/entites/car.png");
	}
	@Override
	public void doRender(CarEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y+1.8, z);
		GlStateManager.scale(0.075, 0.075, 0.075);
		GlStateManager.rotate(180, 1, 0, 0);
		GlStateManager.rotate(entityYaw-90, 0, -1, 0);
		GlStateManager.rotate(180, 0, 1, 0);
		this.bindTexture(texture);
		if(!entity.world.checkBlockCollision(entity.getEntityBoundingBox())) {
			model.wheelsleft.rotateAngleZ = -1.57f;
			model.wheelsleft.offsetX = 13f;
			model.wheelsleft.offsetY = 1f;
			
			model.wheelsright.rotateAngleZ = 1.57f;
			model.wheelsright.offsetX = -13f;
			model.wheelsright.offsetY = 1f;
		}else {
			model.wheelsleft.rotateAngleZ = 0;
			model.wheelsleft.offsetX = 0;
			model.wheelsleft.offsetY = 0;
			
			model.wheelsright.rotateAngleZ = 0;
			model.wheelsright.offsetX = 0;
			model.wheelsright.offsetY = 0;
		}
		model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1);
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	public float lerp(float a, float b, float t) {
		return a + (b-a)*t;
	}
}
