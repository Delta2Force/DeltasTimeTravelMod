package deltatwoforce.deltastimetravelmod.model;

//Made with Blockbench
//Paste this code into your mod.

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CarModel extends ModelBase {
	public final ModelRenderer chassy;
	public final ModelRenderer wheelsleft;
	public final ModelRenderer wheelsright;

	public CarModel() {
		textureWidth = 128;
		textureHeight = 128;

		chassy = new ModelRenderer(this);
		chassy.setRotationPoint(0.0F, 24.0F, 0.0F);
		chassy.cubeList.add(new ModelBox(chassy, 0, 0, -8.0F, -13.0F, -19.0F, 16, 8, 14, 0.0F, false));
		chassy.cubeList.add(new ModelBox(chassy, 48, 48, -8.0F, -13.0F, 9.0F, 16, 8, 8, 0.0F, false));
		chassy.cubeList.add(new ModelBox(chassy, 0, 54, -8.0F, -13.0F, -5.0F, 1, 8, 14, 0.0F, false));
		chassy.cubeList.add(new ModelBox(chassy, 48, 8, 7.0F, -13.0F, -5.0F, 1, 8, 14, 0.0F, false));
		chassy.cubeList.add(new ModelBox(chassy, 0, 22, -8.0F, -22.0F, -6.0F, 16, 1, 16, 0.0F, false));
		chassy.cubeList.add(new ModelBox(chassy, 0, 39, -7.0F, -6.0F, -5.0F, 14, 1, 14, 0.0F, false));
		chassy.cubeList.add(new ModelBox(chassy, 4, 54, -8.0F, -21.0F, 9.0F, 1, 8, 1, 0.0F, false));
		chassy.cubeList.add(new ModelBox(chassy, 0, 54, 7.0F, -21.0F, 9.0F, 1, 8, 1, 0.0F, false));
		chassy.cubeList.add(new ModelBox(chassy, 50, 0, 7.0F, -21.0F, -6.0F, 1, 8, 1, 0.0F, false));
		chassy.cubeList.add(new ModelBox(chassy, 46, 0, -8.0F, -21.0F, -6.0F, 1, 8, 1, 0.0F, false));

		wheelsleft = new ModelRenderer(this);
		wheelsleft.setRotationPoint(0.0F, 24.0F, 0.0F);
		wheelsleft.cubeList.add(new ModelBox(wheelsleft, 42, 42, 6.0F, -5.0F, 10.0F, 2, 5, 5, 0.0F, false));
		wheelsleft.cubeList.add(new ModelBox(wheelsleft, 0, 22, 6.0F, -5.0F, -17.0F, 2, 5, 5, 0.0F, false));

		wheelsright = new ModelRenderer(this);
		wheelsright.setRotationPoint(0.0F, 24.0F, 0.0F);
		wheelsright.cubeList.add(new ModelBox(wheelsright, 0, 0, -8.0F, -5.0F, 10.0F, 2, 5, 5, 0.0F, false));
		wheelsright.cubeList.add(new ModelBox(wheelsright, 0, 39, -8.0F, -5.0F, -17.0F, 2, 5, 5, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		chassy.render(f5);
		wheelsleft.render(f5);
		wheelsright.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}