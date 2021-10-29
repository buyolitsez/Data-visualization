import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.swing.Swing
import org.jetbrains.skija.*
import org.jetbrains.skija.Data
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaRenderer
import org.jetbrains.skiko.SkiaWindow
import java.awt.Dimension
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.ByteChannel
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import javax.swing.WindowConstants


var surface: Surface = Surface.makeRasterN32Premul(WINDOW_WIDTH, WINDOW_HEIGHT)
var surfaceCanvas: Canvas = surface.canvas
var wasSavedToPng = false

fun saveToPng(outFileName: String) {
    val image: Image = surface.makeImageSnapshot()
    val pngData: Data? = image.encodeToData(EncodedImageFormat.PNG)
    check(pngData != null) { println("Something went wrong, cant save to png"); return }
    val pngBytes: ByteBuffer = pngData.toByteBuffer()

    try {
        val path = Paths.get("$outFileName.png")
        val channel: ByteChannel = Files.newByteChannel(
            path,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE
        )
        channel.write(pngBytes)
        channel.close()
    } catch (e: IOException) {
        println(e)
    }

}

fun createWindow(title: String, inputData: List<DiagramData>) = runBlocking(Dispatchers.Swing) {
    logger.info { "Create window" }
    val window = SkiaWindow()
    window.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
    window.title = title

    window.layer.renderer = Renderer(window.layer, inputData)

    window.preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
    window.minimumSize = Dimension(100, 100)
    window.pack()
    window.layer.awaitRedraw()
    window.isVisible = true
}

class Renderer(private val layer: SkiaLayer, private val inputData: List<DiagramData>) : SkiaRenderer {
    private val paint = Paint().apply {
        color = COLORS_HEX[(COLORS_HEX.indices).random()]
        mode = PaintMode.FILL
        strokeWidth = 1f
    }

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        WINDOW_WIDTH = layer.width
        WINDOW_HEIGHT = layer.height
        if (!wasSavedToPng) {
            paint.color = 0xffFFFFFF.toInt() // White color
            surfaceCanvas.drawRect(
                Rect(0f, 0f, WINDOW_WIDTH.toFloat(), WINDOW_HEIGHT.toFloat()),
                paint
            ) // fill a background white
            wasSavedToPng = true
            onRender(surfaceCanvas, width, height, nanoTime) // draw diagram on surface canvas
            saveToPng("output")
        }
        val contentScale = layer.contentScale
        canvas.scale(contentScale, contentScale)
        when (diagramName) {
            DiagramName.CIRCLE -> displayCircleDiagram(canvas, paint, inputData)
            DiagramName.BAR -> displayBarChartDiagram(canvas, paint, inputData)
            DiagramName.SCATTER -> displayScatterChartDiagram(canvas, paint, inputData)
            else -> throwError("Forgot to add diagram type")
        }
        layer.needRedraw()
    }
}

