import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.swing.Swing
import org.jetbrains.skija.Canvas
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaRenderer
import org.jetbrains.skiko.SkiaWindow
import java.awt.Dimension
import javax.swing.WindowConstants

fun createWindow(title: String) = runBlocking(Dispatchers.Swing) {
    logger.info { "Create window" }
    val window = SkiaWindow()
    window.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
    window.title = title

    window.layer.renderer = Renderer(window.layer)

    window.preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
    window.minimumSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
    window.pack()
    window.layer.awaitRedraw()
    window.isVisible = true
}

class Renderer(private val layer: SkiaLayer) : SkiaRenderer {
    private val paint = Paint().apply {
        color = COLORS_HEX[(COLORS_HEX.indices).random()]
        mode = PaintMode.FILL
        strokeWidth = 1f
    }

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val contentScale = layer.contentScale
        canvas.scale(contentScale, contentScale)

        when (diagramName) {
            DiagramName.CIRCLE -> displayCircleDiagram(canvas, paint)
            DiagramName.BAR -> displayBarChartDiagram(canvas, paint)
            else -> throwError("Forgot to add diagram type")
        }
        layer.needRedraw()
    }
}

