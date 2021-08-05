package jp.kps8x9.m2.magicmod.entities.meteorite;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MeteoriteModel extends EntityModel<Entity> {
	private final ModelRenderer bone;

	public MeteoriteModel() {
		texWidth = 64;
		texHeight = 64;

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 22.0F, 0.0F);
		bone.texOffs(3, 26).addBox(-7.0F, -14.0F, -7.0F, 14.0F, 12.0F, 14.0F, 0.0F, false);
		bone.texOffs(0, 0).addBox(-5.0F, -16.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
		bone.texOffs(0, 0).addBox(-4.0F, -17.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
		bone.texOffs(0, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
		bone.texOffs(0, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bone.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {

	}
}