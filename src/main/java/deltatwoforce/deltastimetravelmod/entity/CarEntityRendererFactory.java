package deltatwoforce.deltastimetravelmod.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class CarEntityRendererFactory implements IRenderFactory<CarEntity>{
	public static final CarEntityRendererFactory INSTANCE = new CarEntityRendererFactory();

	@Override
	public Render<? super CarEntity> createRenderFor(RenderManager manager) {
		return new CarEntityRenderer(manager);
	}

}
