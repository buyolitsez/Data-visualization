import org.jetbrains.skija.*

private const val leftDownY = WINDOW_HEIGHT - 150f
private const val widthColumn = 30f
private const val heightColumn = (leftDownY) * 0.95f
private const val distanceBetweenTwoColumns = 30f
private const val cntOfLineOnGrid = 6

fun displayBarChartDiagram(canvas: Canvas, paint: Paint) {
    logger.info { "display bar chart diagram" }
    font.size = 12f
    displayGrid(canvas, paint)
    val maxValue = inputData.maxOf { it.value }
    var leftDownX = 5f + convertFontSizeToPixel(font.size) * formatFloat(maxValue).length + 30f
    for ((value, name) in inputData) {
        drawColumn(canvas, paint, value, maxValue, leftDownX)
        drawNameColumn(canvas, paint, name, leftDownX)
        leftDownX += widthColumn + distanceBetweenTwoColumns
    }
}

private fun drawNameColumn(
    canvas: Canvas,
    paint: Paint,
    name: String,
    leftDownX: Float
) {
    paint.color = TEXT_COLOR
    font.size = (distanceBetweenTwoColumns + widthColumn) / (name.length).toFloat()
    canvas.drawString(name, leftDownX, leftDownY + font.size, font, paint) // draw name
}

private fun drawColumn(
    canvas: Canvas,
    paint: Paint,
    value: Float,
    maxValue: Float,
    leftDownX: Float,
) {
    paint.color = BEAUTIFUL_BLUE
    val currentHeight = (value / maxValue) * heightColumn // height of current column
    drawRectangle(
        canvas, paint, arrayOf(
            Point(leftDownX, leftDownY), // left down
            Point(leftDownX + widthColumn, leftDownY), // right down
            Point(leftDownX + widthColumn, leftDownY - currentHeight), // right up
            Point(leftDownX, leftDownY - currentHeight) // left up
        )
    )
}

private fun displayGrid(canvas: Canvas, paint: Paint) {
    paint.color = TEXT_COLOR
    val maxValue = inputData.maxOf { it.value }
    val iterateStepValue = maxValue / (cntOfLineOnGrid - 1)
    val iterateStepY = heightColumn / (cntOfLineOnGrid - 1)
    for (lineNum in 0 until cntOfLineOnGrid) {
        val y = leftDownY - iterateStepY * lineNum
        canvas.drawString(formatFloat(iterateStepValue * lineNum), 5f, y, font, paint)
        canvas.drawLine(
            5f + convertFontSizeToPixel(font.size) * formatFloat(maxValue).length, y, WINDOW_WIDTH.toFloat(), y, paint
        )
    }
}