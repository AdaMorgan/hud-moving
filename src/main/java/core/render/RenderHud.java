package core.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;

public class RenderHud {
    public static float alpha = 0;
    public static boolean state = false;
    public static Identifier identifier;
    public static boolean downloadingTerrain = false;

    public static void render() {
        renderServerWorldEvent();
        renderHudRenderCallback();
    }

    private static void renderServerWorldEvent() {
        ServerWorldEvents.LOAD.register((server, world) -> {
            RenderHud.alpha = 0f;
            RenderHud.identifier = new Identifier("textures/hud/minecraft.png");
            RenderHud.state = true;
        });
    }

    private static void renderHudRenderCallback() {
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            Window window = MinecraftClient.getInstance().getWindow();

            int textureWidth = 256;
            int textureHeight = 69;
            int x = (window.getWidth() / 8) - (textureWidth / 2);
            int y = 30;

            if (identifier != null && downloadingTerrain) {
                System.out.println(alpha);
                float beta;
                if (state) {
                    if (alpha >= 15) state = false;
                    alpha += 0.075;
                    beta = alpha / 10;
                } else {
                    if (alpha <= 0) {
                        identifier = null;
                        return;
                    }
                    alpha -= 0.05;
                    beta = Math.min(1, alpha);
                }

                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1F, 1F, 1F, beta);
                RenderSystem.setShaderTexture(0, identifier);
                DrawableHelper.drawTexture(matrixStack, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
            }
        });
    }
}
