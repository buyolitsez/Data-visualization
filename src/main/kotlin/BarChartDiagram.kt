import org.jetbrains.skija.*

fun displayBarChartDiagram(canvas: Canvas, paint: Paint) {
    logger.info { "display bar chart diagram" }
    paint.setARGB(255, 14, 129, 234)
    val widthColumn = 30f
    val heightColumn = 400f
    val distanceBetweenTwoColumns = 30f
    val maxValue = inputData.maxOf { it.value }
    var leftDownX = 5f
    val leftDownY = WINDOW_HEIGHT - 150f
    for ((value, _) in inputData) {
        val currentHeight = (value / maxValue) * heightColumn
        drawSquare(
            canvas, paint, arrayOf(
                Point(leftDownX, leftDownY),
                Point(leftDownX + widthColumn, leftDownY),
                Point(leftDownX + widthColumn, leftDownY - currentHeight),
                Point(leftDownX, leftDownY - currentHeight)
            )
        )
        leftDownX += widthColumn + distanceBetweenTwoColumns
    }
}