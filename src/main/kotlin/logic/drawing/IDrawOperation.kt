package logic.drawing;

import java.awt.Graphics
import java.awt.image.BufferedImage;

public interface IDrawOperation {
    suspend fun draw(graphics: Graphics)
}