import org.jetbrains.skija.*

fun displayBarChartDiagram(canvas: Canvas, paint: Paint) {
    logger.info { "display bar chart diagram" }
    val widthColumn = 30f
    val heightColumn = 400f
    val distanceBetweenTwoColumns = 30f
    val maxValue = inputData.maxOf { it.value }
    var leftDownX = 70f
    val leftDownY = WINDOW_HEIGHT - 150f
    for ((value, name) in inputData) {
        // draw column
        paint.color = BEAUTIFUL_BLUE
        val currentHeight = (value / maxValue) * heightColumn // height of column
        drawRectangle(
            canvas, paint, arrayOf(
                Point(leftDownX, leftDownY), // left down
                Point(leftDownX + widthColumn, leftDownY), // right down
                Point(leftDownX + widthColumn, leftDownY - currentHeight), // right up
                Point(leftDownX, leftDownY - currentHeight) // left up
            )
        )
        // draw name of column
        paint.color = TEXT_COLOR
        font.size = (distanceBetweenTwoColumns + widthColumn) / (name.length).toFloat()
        canvas.drawString(name, leftDownX, leftDownY + font.size, font, paint) // draw name
        leftDownX += widthColumn + distanceBetweenTwoColumns
    }
}