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
    val window = SkiaWindow()
    window.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
    window.title = title

    window.layer.renderer = Renderer(window.layer)

    window.preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
    window.minimumSize = Dimension(WINDOW_WIDTH,WINDOW_HEIGHT)
    window.pack()
    window.layer.awaitRedraw()
    window.isVisible = true
}

class Renderer(private val layer: SkiaLayer): SkiaRenderer {
    private val paint = Paint().apply {
        color = COLORS_HEX[(COLORS_HEX.indices).random()]
        mode = PaintMode.FILL
        strokeWidth = 1f
    }

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val contentScale = layer.contentScale
        canvas.scale(contentScale, contentScale)

        displayTestCircle(canvas)

        layer.needRedraw()
    }

    private fun displayTestCircle(canvas: Canvas) {
        var x = 0F
        val r = 250F
        val x0 = 300F
        val y0 = 300F
        val step = 1F
        while (x < WINDOW_WIDTH) {
            var y = 0F
            while (y < WINDOW_HEIGHT) {
                y += step
                if (distanceSq(x, y, x0, y0) <= r * r) {
                    canvas.drawPoint(x, y, paint)
                }
            }
            x += step
        }
    }
}

fun distanceSq(x1: Float, y1: Float, x2: Float, y2: Float) = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)