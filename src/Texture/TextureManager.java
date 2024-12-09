package Texture;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class TextureManager {

    public static int loadTexture(String filePath) {
        int textureID = glGenTextures(); // Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID);

        // Texture configuration
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer image = STBImage.stbi_load(filePath, width, height, channels, 4);
            if (image != null) {
                // Upload texture to OpenGL
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
                STBImage.stbi_image_free(image); // Free the image after uploading
            } else {
                throw new RuntimeException("Failed to load texture: " + STBImage.stbi_failure_reason());
            }
        }
        return textureID;
    }

    public static void drawTexture(int textureID, float x, float y, float width, float height) {
        glEnable(GL_TEXTURE_2D); // Enable textures
        glBindTexture(GL_TEXTURE_2D, textureID);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glBegin(GL_QUADS); // Draw a textured quad
        glColor4f(1, 1, 1, 1);
        glTexCoord2f(0, 0); glVertex2f(x, y); // Bottom-left corner
        glTexCoord2f(1, 0); glVertex2f(x + width, y); // Bottom-right corner
        glTexCoord2f(1, 1); glVertex2f(x + width, y + height); // Top-right corner
        glTexCoord2f(0, 1); glVertex2f(x, y + height); // Top-left corner
        glEnd();

        glDisable(GL_TEXTURE_2D); // Disable textures
    }
}
