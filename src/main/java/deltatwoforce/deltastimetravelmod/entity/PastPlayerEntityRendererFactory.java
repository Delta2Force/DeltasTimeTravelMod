package deltatwoforce.deltastimetravelmod.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class PastPlayerEntityRendererFactory implements IRenderFactory<PastPlayerEntity>{
	public static final PastPlayerEntityRendererFactory INSTANCE = new PastPlayerEntityRendererFactory();
	
	@Override
	public Render<? super PastPlayerEntity> createRenderFor(RenderManager manager) {
		return new PastPlayerEntityRenderer(manager);
	}

}
