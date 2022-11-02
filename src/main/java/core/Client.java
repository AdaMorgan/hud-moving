package core;

import core.render.RenderHud;
import net.fabricmc.api.ClientModInitializer;

public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RenderHud.render();
    }
}
