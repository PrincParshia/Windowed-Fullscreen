package princ.windfullsc.client;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class WindFullScHandler {
    private static Window getWindow() {
        return Minecraft.getInstance().getWindow();
    }

    private static int getMonitorSize(boolean bl) {
        if (bl) {
            return getWindow().findBestMonitor().getCurrentMode().getWidth();
        } else {
            return getWindow().findBestMonitor().getCurrentMode().getHeight();
        }
    }

    private static boolean wasWindowMaximized;
    private static int windowWidth;
    private static int windowHeight;
    private static int windowX;
    private static int windowY;

    private static void captureWindowAttributes() {
        wasWindowMaximized = GLFW.glfwGetWindowAttrib(getWindow().handle(), GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE;
        windowWidth = getWindow().getWidth();
        windowHeight = getWindow().getHeight();
        windowX = getWindow().getX();
        windowY = getWindow().getY();
    }

    public static void activateWindowedFullScreen() {
        captureWindowAttributes();
        if (wasWindowMaximized) GLFW.glfwSetWindowAttrib(getWindow().handle(), GLFW.GLFW_MAXIMIZED, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowAttrib(getWindow().handle(), GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowSize(getWindow().handle(), getMonitorSize(true), getMonitorSize(false));
        GLFW.glfwSetWindowPos(getWindow().handle(), 0, 0);
    }

    public static void activateBorderlessWindowed() {
        captureWindowAttributes();
        GLFW.glfwSetWindowAttrib(getWindow().handle(), GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        if (!wasWindowMaximized) GLFW.glfwMaximizeWindow(getWindow().handle());
    }

    public static void restoreWindow() {
        GLFW.glfwSetWindowAttrib(getWindow().handle(), GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
        if (!wasWindowMaximized) {
            GLFW.glfwRestoreWindow(getWindow().handle());
            GLFW.glfwSetWindowSize(getWindow().handle(), windowWidth, windowHeight);
            GLFW.glfwSetWindowPos(getWindow().handle(), windowX, windowY);
            return;
        }
        GLFW.glfwMaximizeWindow(getWindow().handle());
    }

    public static void toggleWindowedFullscreen() {
        if (GLFW.glfwGetWindowAttrib(getWindow().handle(), GLFW.GLFW_DECORATED) == GLFW.GLFW_TRUE) {
            activateWindowedFullScreen();
        } else if (GLFW.glfwGetWindowAttrib(getWindow().handle(), GLFW.GLFW_DECORATED) == GLFW.GLFW_FALSE) {
            restoreWindow();
        }
    }

    public static void toggleBorderlessWindowed() {
        if (GLFW.glfwGetWindowAttrib(getWindow().handle(), GLFW.GLFW_DECORATED) == GLFW.GLFW_TRUE) {
            activateBorderlessWindowed();
        } else if (GLFW.glfwGetWindowAttrib(getWindow().handle(), GLFW.GLFW_DECORATED) == GLFW.GLFW_FALSE) {
            restoreWindow();
        }
    }
}
