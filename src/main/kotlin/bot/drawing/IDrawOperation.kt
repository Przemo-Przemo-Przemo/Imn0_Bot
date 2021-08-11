package bot.drawing;

import java.awt.Graphics

public interface IDrawOperation {
    suspend fun draw(graphics: Graphics)
}