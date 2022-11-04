package core.mixin;

import core.render.RenderHud;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "respawnPlayer", at = @At(value = "HEAD"))
    private void respawnPlayer(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        RenderHud.alpha = 0f;

        if (player.getSpawnPointDimension() == World.OVERWORLD)
            RenderHud.identifier = new Identifier("textures/hud/overworld.png");

        if (player.getSpawnPointDimension() == World.END)
            RenderHud.identifier = new Identifier("textures/hud/end.png");

        if (player.getSpawnPointDimension() == World.NETHER)
            RenderHud.identifier = new Identifier("textures/hud/nether.png");

        RenderHud.state = true;
    }
}
