package core.mixin;

import core.render.RenderHud;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DownloadingTerrainScreen.class)
public class DownloadingTerrainScreenMixin extends Screen {

    protected DownloadingTerrainScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        BlockPos blockPos = this.client != null ? this.client.player.getBlockPos() : null;
        if (this.client.worldRenderer.isRenderingReady(blockPos)) {
            this.close();
            RenderHud.downloadingTerrain = true;
        }
    }
}
