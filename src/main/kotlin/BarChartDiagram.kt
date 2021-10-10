import org.jetbrains.skija.*

fun displayBarChartDiagram(canvas: Canvas, paint: Paint) {
    logger.info { "display bar chart diagram" }
    val fontSize = 20f
    val widthColumn = 30f
    val heightColumn = 400f
    val distanceBetweenTwoColumns = 30f
    font.size = fontSize
    val maxValue = inputData.maxOf { it.value }
    var leftDownX = 5f
    val leftDownY = WINDOW_HEIGHT - 150f
    for ((value, name) in inputData) {
        paint.setARGB(255, 14, 129, 234)
        val currentHeight = (value / maxValue) * heightColumn
        drawSquare(
            canvas, paint, arrayOf(
                Point(leftDownX, leftDownY),
                Point(leftDownX + widthColumn, leftDownY),
                Point(leftDownX + widthColumn, leftDownY - currentHeight),
                Point(leftDownX, leftDownY - currentHeight)
            )
        )
        paint.color = 0xff2C2828.toInt()
        canvas.drawString(name, leftDownX, leftDownY + fontSize, font, paint)
        leftDownX += widthColumn + distanceBetweenTwoColumns

    }
}