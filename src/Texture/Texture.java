package Texture;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Texture {

    private int textureID;

    public Texture(String filePath) {
        // Cargar la textura desde el archivo
        this.textureID = loadTexture(filePath);
    }

    private int loadTexture(String filePath) {
        // Cargar la imagen con STBImage
        ByteBuffer image;
        int width, height;

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1); // Ancho de la imagen
            IntBuffer h = stack.mallocInt(1); // Alto de la imagen
            IntBuffer comp = stack.mallocInt(1); // Composición de la imagen (colores)

            // Cargar la imagen
            image = stbi_load(filePath, w, h, comp, 4);  // 4 para GL_RGBA
            if (image == null) {
                throw new RuntimeException("No se pudo cargar la imagen: " + filePath);
            }

            width = w.get();
            height = h.get();

            // Crear una textura OpenGL
            int textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            // Especificar los parámetros de la textura
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            // Subir la imagen a OpenGL
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

            // Liberar la memoria de la imagen
            stbi_image_free(image);

            return textureID;
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void enableBlending() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);  // Para transparencia
    }

    public void disableBlending() {
        glDisable(GL_BLEND);
    }

    public int getTextureID() {
        return textureID;
    }
}
