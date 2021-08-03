package jp.kps8x9.m2.magicmod.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

@Mod.EventBusSubscriber
public class RenderEvent {
    public static List<Beam> Beams = new ArrayList<Beam>();

    @SubscribeEvent
    public static void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        Iterator<Beam> Iterator = Beams.iterator();
        while (Iterator.hasNext()) {
            Beam beam = Iterator.next();
            renderLine(event, beam);
            if (beam.decreaseSize()){
                Iterator.remove();
            }
        }
    }

    public static void renderLine(RenderWorldLastEvent event, Beam beam) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        MatrixStack matrixStack = event.getMatrixStack();
        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        matrixStack.pushPose();
        matrixStack.translate(-projectedView.x,-projectedView.y,-projectedView.z);
        RenderSystem.lineWidth(beam.lineWidth);

        Matrix4f matrix = matrixStack.last().pose();

        if (!buffer.building()) buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);;
        drawLine(matrix, buffer,beam.vecStart,beam.vecEnd,beam.color);

        matrixStack.popPose();
        tessellator.end();

    }

    private static void drawLine(Matrix4f matrix, BufferBuilder buffer, Vector3d vecStart, Vector3d vecEnd, Color color) {
        buffer.vertex(matrix, (float)vecStart.x + 0.5f, (float)vecStart.y + 1.0f,(float)vecStart.z + 0.5f)
                .color(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha())
                .endVertex();
        buffer.vertex(matrix, (float)vecEnd.x + 0.5f, (float)vecEnd.y, (float)vecEnd.z + 0.5f)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .endVertex();
    }

    public static class Beam {
        public float lineWidth;
        public Vector3d vecStart;
        public Vector3d vecEnd;
        public Color color;
        public int count;
        public int lifeTime;

        public Beam(float lineWidth, Vector3d vecStart, Vector3d vecEnd, Color color) {
            this.lineWidth = lineWidth;
            this.vecStart = vecStart;
            this.vecEnd = vecEnd;
            this.color = color;
            this.count = 0;
            RenderEvent.Beams.add(this);
        }

        public boolean decreaseSize() {
            lineWidth -= 0.5;
            return lineWidth < 0.5;
        }

    }
}
