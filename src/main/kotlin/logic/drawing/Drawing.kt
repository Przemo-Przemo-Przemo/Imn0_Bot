package logic.drawing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Drawing(private val sourceImagePath: String) {
    suspend fun getImage(drawOperation: IDrawOperation): File {
        val source = withContext(Dispatchers.IO) {
            ImageIO.read(File(sourceImagePath))
        }

        val renderedImage = getRenderedImage(source, drawOperation)
        val image = File("image.png")

        withContext(Dispatchers.IO) {
            ImageIO.write(renderedImage, "png", image)
        }

        return image
    }

    private suspend fun getRenderedImage(source: BufferedImage, drawOperation: IDrawOperation): BufferedImage {
        val width = source.width
        val height = source.height

        val renderedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = getGraphics(renderedImage)

        graphics.drawImage(source, 0, 0, width, height, null)
        drawOperation.draw(graphics) //TODO: IEnumerable

        graphics.dispose()

        return renderedImage
    }

    private fun getGraphics(bitmap: BufferedImage): Graphics {
        val graphics = bitmap.createGraphics()

        graphics.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_GASP)

        return graphics
    }
}