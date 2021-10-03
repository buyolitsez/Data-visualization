import org.jetbrains.skija.Paint
import kotlin.random.Random

class myRandom {
    val count = mutableMapOf<Int, Int>()
    fun rnd(x : Int) : Int {
//        if (!count.containsKey(x)) {
//            count.set(x, 0)
//        }
        return Random(x).nextInt()
    }
}

val random = myRandom()

fun distanceSq(x1: Float, y1: Float, x2: Float, y2: Float) = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)

fun setColor(paint: Paint, index: Int) {
    if (index < COLORS_HEX.size) {
        paint.color = COLORS_HEX[index]
    } else {
        paint.setARGB(random.rnd(index), random.rnd(index), random.rnd(index), random.rnd(index))
    }
}
