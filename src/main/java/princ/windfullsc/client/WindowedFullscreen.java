package princ.windfullsc.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import princ.windfullsc.util.Handler;

public class WindowedFullscreen implements ClientModInitializer {
	public static final String NAMESPACE = "windowed-fullscreen";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);
	public Handler handler;

	static final KeyMapping.Category CATEGORY;
	static final KeyMapping KEY_WINDOWED_FULLSCREEN;
	static final KeyMapping KEY_BORDERLESS_WINDOWED;

	public WindowedFullscreen() {
		this.handler = new Handler();
	}

	@Override
	public void onInitializeClient() {
		this.registerKeyMappings();
	}

	void registerKeyMappings() {
		KeyMappingHelper.registerKeyMapping(KEY_WINDOWED_FULLSCREEN);
		ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
			if (KEY_WINDOWED_FULLSCREEN.consumeClick()) {
				this.handler.toggleWindowedFullscreen(minecraft.getWindow());
			}
		});

		KeyMappingHelper.registerKeyMapping(KEY_BORDERLESS_WINDOWED);
		ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
			if (KEY_BORDERLESS_WINDOWED.consumeClick()) {
				this.handler.toggleBorderlessWindowed(minecraft.getWindow());
			}
		});
	}

	public static Identifier withDefaultNamespace(final String string) {
		return Identifier.fromNamespaceAndPath(NAMESPACE, string);
	}

	public static String withKeyMappingPrefix(String string) {
		return "key." + NAMESPACE + "." + string;
	}

	static {
		CATEGORY = KeyMapping.Category.register(withDefaultNamespace("default"));
		KEY_WINDOWED_FULLSCREEN = new KeyMapping(withKeyMappingPrefix("windowed_fullscreen"), InputConstants.Type.KEYSYM, InputConstants.KEY_F12, CATEGORY);
		KEY_BORDERLESS_WINDOWED = new KeyMapping(withKeyMappingPrefix("borderless_windowed"), InputConstants.Type.KEYSYM, InputConstants.KEY_F10, CATEGORY);
	}
}