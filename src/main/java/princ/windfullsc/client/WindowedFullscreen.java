package princ.windfullsc.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

import static princ.windfullsc.client.WindFullScHandler.*;

public class WindowedFullscreen implements ClientModInitializer {
	public static final String NAMESPACE = "windowed-fullscreen";

    private final String CATEGORY = KeyMapping.CATEGORY_MISC;
    private final KeyMapping windowedFullscreenKey = new KeyMapping(
            "key.misc.windowed-fullscreen",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_F12,
            CATEGORY
    );
    private final KeyMapping borderlessWindowedKey = new KeyMapping(
            "key.misc.borderless-windowed",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_F10,
            CATEGORY
    );

    private boolean aBoolean;
    private boolean bBoolean;

	@Override
	public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(windowedFullscreenKey);
        KeyBindingHelper.registerKeyBinding(borderlessWindowedKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            boolean wasWfKeyPressed = InputConstants.isKeyDown(minecraft.getWindow().getWindow(), InputConstants.getKey(windowedFullscreenKey.saveString()).getValue());
            boolean wasBwKeyPressed = InputConstants.isKeyDown(minecraft.getWindow().getWindow(), InputConstants.getKey(borderlessWindowedKey.saveString()).getValue());

            if (wasWfKeyPressed && !aBoolean && !minecraft.getWindow().isFullscreen()) {
                toggleWindowedFullscreen();
            }

            if (wasBwKeyPressed && !bBoolean && !minecraft.getWindow().isFullscreen()) {
                toggleBorderlessWindowed();
            }

            aBoolean = wasWfKeyPressed;
            bBoolean = wasBwKeyPressed;
        });
	}
}