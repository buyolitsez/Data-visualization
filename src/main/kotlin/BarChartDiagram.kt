import org.jetbrains.skija.*

fun displayBarChartDiagram(canvas: Canvas, paint: Paint) {
    logger.info { "display bar chart diagram" }
    val widthColumn = 30f
    val heightColumn = 400f
    val distanceBetweenTwoColumns = 30f
    val maxValue = inputData.maxOf { it.value }
    var leftDownX = 5f
    val leftDownY = WINDOW_HEIGHT - 150f
    for ((value, name) in inputData) {
        paint.color = BEAUTIFUL_BLUE
        val currentHeight = (value / maxValue) * heightColumn
        drawSquare(
            canvas, paint, arrayOf(
                Point(leftDownX, leftDownY),
                Point(leftDownX + widthColumn, leftDownY),
                Point(leftDownX + widthColumn, leftDownY - currentHeight),
                Point(leftDownX, leftDownY - currentHeight)
            )
        )
        paint.color = TEXT_COLOR
        font.size = (distanceBetweenTwoColumns + widthColumn) / (name.length).toFloat()
        canvas.drawString(name, leftDownX, leftDownY + font.size, font, paint)
        leftDownX += widthColumn + distanceBetweenTwoColumns
    }
}