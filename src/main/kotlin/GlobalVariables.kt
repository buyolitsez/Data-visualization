import org.jetbrains.skija.Font
import org.jetbrains.skija.Typeface

const val WINDOW_WIDTH = 800
const val WINDOW_HEIGHT = 600
val COLORS_HEX = listOf(
    0xffFFFF00.toInt(), // Yellow
    0xff008080.toInt(), // Teal
    0xffC0C0C0.toInt(), // Silver
    0xffFF0000.toInt(), // Red
    0xff800080.toInt(), // Purple
    0xff808000.toInt(), // Olive
    0xff000080.toInt(), // Navy
    0xff800000.toInt(), // Maroon
    0xff00FF00.toInt(), // Lime
    0xff008000.toInt(), // Green
    0xff808080.toInt(), // Gray
    0xffFF00FF.toInt(), // Fuchsia
    0xff0000FF.toInt(), // Blue
    0xff00FFFF.toInt() // Aqua
)
const val TEXT_COLOR = 0xff2C2828.toInt()
const val BEAUTIFUL_BLUE = 0xff0E81EA.toInt()



val typeface = Typeface.makeFromFile("fonts/JetBrainsMono-Regular.ttf")
val font = Font(typeface, 30f)

const val countOfDigitsAfterComma = 2
