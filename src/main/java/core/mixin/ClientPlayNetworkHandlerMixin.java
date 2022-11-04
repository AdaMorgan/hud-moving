package core.mixin;

import core.render.RenderHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow public abstract ClientWorld getWorld();

    @Inject(method = "onWorldEvent", at = @At(value = "HEAD"))
    public void onWorldEvent(WorldEventS2CPacket packet, CallbackInfo ci) {
        if (this.getWorld().isClient() && packet.getEventId() == WorldEvents.TRAVEL_THROUGH_PORTAL) {
            RenderHud.alpha = 0f;

            if (this.getWorld().getRegistryKey() == World.OVERWORLD)
                RenderHud.identifier = new Identifier("textures/hud/overworld.png");

            if (this.getWorld().getRegistryKey() == World.END)
                RenderHud.identifier = new Identifier("textures/hud/end.png");

            if (this.getWorld().getRegistryKey() == World.NETHER)
                RenderHud.identifier = new Identifier("textures/hud/nether.png");

            RenderHud.state = true;
        }
    }
}
