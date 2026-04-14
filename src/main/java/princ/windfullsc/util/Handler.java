package princ.windfullsc.util;

import com.mojang.blaze3d.platform.Window;
import org.lwjgl.glfw.GLFW;

public class Handler {
    boolean wasMaximized;
    int width;
    int height;
    int x;
    int y;

    void captureWindowAttributes(Window window) {
        this.wasMaximized = GLFW.glfwGetWindowAttrib(window.handle(), GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE;
        this.width = window.getWidth();
        this.height = window.getHeight();
        this.x = window.getX();
        this.y = window.getY();
    }

    public void activateWindowedFullScreen(Window window) {
        this.captureWindowAttributes(window);
        if (this.wasMaximized) GLFW.glfwSetWindowAttrib(window.handle(), GLFW.GLFW_MAXIMIZED, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowAttrib(window.handle(), GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowSize(window.handle(), this.getMonitorSize(window, 0), this.getMonitorSize(window, 1));
        GLFW.glfwSetWindowPos(window.handle(), 0, 0);
    }

    public void activateBorderlessMaximized(Window window) {
        this.captureWindowAttributes(window);
        GLFW.glfwSetWindowAttrib(window.handle(), GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        if (!this.wasMaximized) GLFW.glfwMaximizeWindow(window.handle());
    }

    public void restoreWindow(Window window) {
        GLFW.glfwSetWindowAttrib(window.handle(), GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
        if (!this.wasMaximized) {
            GLFW.glfwRestoreWindow(window.handle());
            GLFW.glfwSetWindowSize(window.handle(), this.width, this.height);
            GLFW.glfwSetWindowPos(window.handle(), this.x, this.y);
            return;
        }
        GLFW.glfwMaximizeWindow(window.handle());
    }

    public void toggleWindowedFullscreen(Window window) {
        if (GLFW.glfwGetWindowAttrib(window.handle(), GLFW.GLFW_DECORATED) == GLFW.GLFW_TRUE) {
            this.activateWindowedFullScreen(window);
        } else if (GLFW.glfwGetWindowAttrib(window.handle(), GLFW.GLFW_DECORATED) == GLFW.GLFW_FALSE) {
            this.restoreWindow(window);
        }
    }

    public void toggleBorderlessWindowed(Window window) {
        if (GLFW.glfwGetWindowAttrib(window.handle(), GLFW.GLFW_DECORATED) == GLFW.GLFW_TRUE) {
            this.activateBorderlessMaximized(window);
        } else if (GLFW.glfwGetWindowAttrib(window.handle(), GLFW.GLFW_DECORATED) == GLFW.GLFW_FALSE) {
            this.restoreWindow(window);
        }
    }

    @SuppressWarnings("ConstantConditions")
    int getMonitorSize(Window window, int i) {
        if (i == 0) return window.findBestMonitor().getCurrentMode().getWidth();
        else return window.findBestMonitor().getCurrentMode().getHeight();
    }
}
