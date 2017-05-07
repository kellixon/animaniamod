package com.animania.client.render.pigs;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.animania.client.models.ModelHog;
import com.animania.common.entities.pigs.EntityHogLargeWhite;
import com.animania.common.handler.BlockHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHogLargeWhite extends RenderLiving {
	private static final ResourceLocation PIG_TEXTURES = new ResourceLocation(
			"animania:textures/entity/pigs/hog_large_white.png");
	private static final ResourceLocation PIG_TEXTURES_BLINK = new ResourceLocation(
			"animania:textures/entity/pigs/hog_large_white_blink.png");

	public RenderHogLargeWhite(RenderManager rm) {
		super(rm, new ModelHog(), 0.5F);
		this.addLayer(new LayerMudHogLargeWhite(this));
	}

	protected void preRenderScale(EntityHogLargeWhite entity, float f) {
		GL11.glScalef(1.16F, 1.16F, 1.16F);

		double x = entity.posX;
		double y = entity.posY;
		double z = entity.posZ;

		BlockPos pos = new BlockPos(x, y, z);
		Random rand = new Random();

		Block blockchk = entity.world.getBlockState(pos).getBlock();

		if (blockchk == BlockHandler.blockMud && !entity.getMuddy()) {
			GlStateManager.translate(0.0F, entity.height - 1.45F, 0.0F);
			GlStateManager.rotate(86.0F, 0.0F, 0.0F, 1.0F);
			entity.setMuddy(true);
			entity.setMudTimer(1.0F);
			entity.setSplashTimer(1.0F);
		} else if (entity.isWet() && entity.getMuddy()) {
			entity.setMuddy(false);
			entity.setMudTimer(0.0F);
			entity.setSplashTimer(0.0F);
		} else if (blockchk == BlockHandler.blockMud) {
			Float splashTimer = entity.getSplashTimer();
			GlStateManager.translate(0.0F, entity.height - 1.45F, 0.0F);
			GlStateManager.rotate(86.0F, 0.0F, 0.0F, 1.0F);

			splashTimer = splashTimer - 0.045F;
			entity.setSplashTimer(splashTimer);
			if (splashTimer <= 0.0F) {
				entity.setMuddy(true);
				entity.setMudTimer(1.0F);
			}

		} else if (entity.getMudTimer() > 0) {
			entity.setMuddy(false);
			float mudTimer = entity.getMudTimer();
			if (rand.nextInt(3) < 1) {
				mudTimer = mudTimer - 0.0025F;
				entity.setMudTimer(mudTimer);
			}
		}

	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		preRenderScale((EntityHogLargeWhite) entityliving, f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		EntityHogLargeWhite entity = (EntityHogLargeWhite) par1Entity;

		int blinkTimer = entity.blinkTimer;

		if (blinkTimer < 7 && blinkTimer >= 0) {
			return PIG_TEXTURES_BLINK;
		} else {
			return PIG_TEXTURES;
		}
	}

}