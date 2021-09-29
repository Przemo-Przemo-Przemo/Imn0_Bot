package bot.drawing;

import java.awt.Graphics

interface IDrawOperation { // C# (C hashtag)
    suspend fun draw(graphics: Graphics)
}