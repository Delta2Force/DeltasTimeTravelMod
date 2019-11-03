package deltatwoforce.deltastimetravelmod.blocks;

import deltatwoforce.deltastimetravelmod.DeltasTimeTravelMod;
import deltatwoforce.deltastimetravelmod.entity.CarEntity;
import deltatwoforce.deltastimetravelmod.model.CarModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityCarSpecialRenderer extends TileEntitySpecialRenderer<TileEntityCar>{
	private CarModel carModel;
	private ResourceLocation texture = new ResourceLocation(DeltasTimeTravelMod.MODID, "textures/entities/car.png");
	
	public TileEntityCarSpecialRenderer() {
		carModel = new CarModel();
	}
	
	@Override
	public void render(TileEntityCar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		RenderHelper.disableStandardItemLighting();
        setLightmapDisabled(true);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x+0.5, y+2.8d, z+0.5);
		GlStateManager.scale(0.075, 0.075, 0.075);
		GlStateManager.rotate(180, 1, 0, 0);
		this.bindTexture(texture);
		carModel.render(new CarEntity(Minecraft.getMinecraft().world), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1);
		GlStateManager.popMatrix();
		
		RenderHelper.enableStandardItemLighting();
        setLightmapDisabled(false);
	}
}
