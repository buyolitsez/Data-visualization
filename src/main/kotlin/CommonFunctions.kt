import org.jetbrains.skija.Canvas
import org.jetbrains.skija.Paint
import org.jetbrains.skija.Path
import org.jetbrains.skija.Point
import kotlin.random.Random

// get random int modulo 256
fun getRandomChar(index: Int): Int {
    return Random(index).nextInt() % 256
}

fun drawRectangle(canvas: Canvas, paint: Paint, points: Array<Point>) {
    check(points.size == 4)
    val polygonPath = Path()
    polygonPath.reset()
    polygonPath.moveTo(points[0])
    polygonPath.lineTo(points[1])
    polygonPath.lineTo(points[2])
    polygonPath.lineTo(points[3])
    polygonPath.lineTo(points[0])
    canvas.drawPath(polygonPath, paint)
}

// distance between two points, squared
fun distanceSq(x1: Float, y1: Float, x2: Float, y2: Float) = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)

fun setColor(paint: Paint, index: Int) {
    if (index < COLORS_HEX.size) {
        paint.color = COLORS_HEX[index]
    } else {
        paint.setARGB(255, getRandomChar(index * 3), getRandomChar(index * 3 + 1), getRandomChar(index * 3 + 2))
    }
}

fun formatFloat(value: Float): String {
    return String.format("%.${countOfDigitsAfterComma}f", value)
}

fun convertFontSizeToPixel(fontSize : Float): Float {
    return fontSize * 0.6f
}