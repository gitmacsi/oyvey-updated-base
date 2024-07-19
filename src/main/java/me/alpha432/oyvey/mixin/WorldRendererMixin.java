package me.alpha432.oyvey.mixin;
import com.mojang.blaze3d.systems.RenderSystem;
import me.alpha432.oyvey.event.impl.Render3DEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.alpha432.oyvey.util.traits.Util.EVENT_BUS;

@Mixin( WorldRenderer.class )
public class WorldRendererMixin {
    @Inject(method = "render", at = @At("RETURN"))
    private void render(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2 , CallbackInfo ci) {
        MinecraftClient.getInstance().getProfiler().push("oyvey-render-3d");
        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);
        MatrixStack stack = new MatrixStack();

        //matrix4f is relative but matrix4f2 is locked to the screen
        stack.peek().getPositionMatrix().mul(matrix4f);

        Render3DEvent event = new Render3DEvent(stack,tickCounter.getTickDelta(false));
        EVENT_BUS.post(event);
        MinecraftClient.getInstance().getProfiler().pop();
    }
}