import org.jetbrains.skija.Paint
import kotlin.random.Random

fun getRandomChar(index: Int) : Int {
    return Random(index).nextInt() % 256
}


fun distanceSq(x1: Float, y1: Float, x2: Float, y2: Float) = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)

fun setColor(paint: Paint, index: Int) {
    if (index < COLORS_HEX.size) {
        paint.color = COLORS_HEX[index]
    } else {
        paint.setARGB(255, getRandomChar(index * 3), getRandomChar(index * 3 + 1), getRandomChar(index * 3 + 2))
    }
}
