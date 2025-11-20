package princ.windfullsc.client;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class WindFullscHandler {
    private static final long getWindow = Minecraft.getInstance().getWindow().handle();
    private static boolean isWindowMaximized;

    public static void activate() {
        isWindowMaximized = GLFW.glfwGetWindowAttrib(getWindow, GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE;

        if (isWindowMaximized) {
            GLFW.glfwSetWindowAttrib(getWindow, GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        } else {
            GLFW.glfwSetWindowAttrib(getWindow, GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
            GLFW.glfwMaximizeWindow(getWindow);
        }
    }

    public static void restore() {
        GLFW.glfwSetWindowAttrib(getWindow, GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
        if (!isWindowMaximized) GLFW.glfwRestoreWindow(getWindow);
    }

    public static void adjust() {
        if (GLFW.glfwGetWindowAttrib(getWindow, GLFW.GLFW_DECORATED) == GLFW.GLFW_TRUE) {
            activate();
        } else if (GLFW.glfwGetWindowAttrib(getWindow, GLFW.GLFW_DECORATED) == GLFW.GLFW_FALSE) {
            restore();
        }
    }
}