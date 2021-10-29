import org.jetbrains.skija.Paint
import kotlin.random.Random

// get random int modulo 256
fun getRandomChar(index: Int): Int {
    return Random(index).nextInt() % 256
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
    var result = String.format("%.${countOfDigitsAfterComma}f", value)
    while(result.length > 1 && (result.last() == '.' || result.last() == '0')) {
        result = result.dropLast(1)
    }
    return result
}

fun convertFontSizeToPixel(fontSize : Float): Float {
    return fontSize * 0.6f
}

fun outputStringWithColor(string : String) {
    val color = "\u001B[36m"
    val reset = "\u001B[0m"
    print(color + string + reset)
}

fun isEqualsFloat(a : Float, b : Float) : Boolean = kotlin.math.abs(a - b) <= EPS_EQUALS

fun checkInputParamNamesOnlyNumbers(inputData: List<DiagramData>) {
    val wasNumbers = mutableSetOf<Float>()
    for (data in inputData) {
        val floatValue = data.paramName.toFloatOrNull()
        if (floatValue == null) {
            throwError("Parameter name can be only number")
        } else if (wasNumbers.contains(floatValue)) {
            throwError("Two parameters can't be equal")
        } else {
            wasNumbers.add(floatValue)
        }
    }
}