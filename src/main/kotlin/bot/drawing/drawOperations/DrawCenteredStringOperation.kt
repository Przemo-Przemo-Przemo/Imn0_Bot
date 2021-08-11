package bot.drawing.drawOperations

import bot.drawing.IDrawOperation
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Rectangle

class DrawCenteredStringOperation(private val text: String, private val font: Font, private val color: Color, private val textArea: Rectangle): IDrawOperation {
    override suspend fun draw(graphics: Graphics) {
        val metrics = graphics.getFontMetrics(font)

        val x = textArea.x + (textArea.width - metrics.stringWidth(text)) / 2
        val y = textArea.y + ((textArea.height - metrics.height) / 2) + metrics.ascent

        graphics.color = color
        graphics.font = font
        graphics.drawString(text, x, y)
    }
}