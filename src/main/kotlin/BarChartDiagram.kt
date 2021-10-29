import org.jetbrains.skija.*

private var leftDownY = WINDOW_HEIGHT - 150f
private var widthColumn = WINDOW_WIDTH / 25f
private var heightColumn = (leftDownY) * 0.95f
private var distanceBetweenTwoColumns = widthColumn * 0.67f
private const val cntOfLineOnGrid = 6

private fun recalculateConstants() {
    leftDownY = WINDOW_HEIGHT - 150f
    widthColumn = WINDOW_WIDTH / 25f
    heightColumn = (leftDownY) * 0.95f
    distanceBetweenTwoColumns = widthColumn * 0.67f
}


fun displayBarChartDiagram(canvas: Canvas, paint: Paint, inputData: List<DiagramData>) {
    recalculateConstants()
    logger.info { "display bar chart diagram" }
    font.size = 12f
    displayGrid(canvas, paint, inputData)
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
    canvas.drawRect(Rect(leftDownX, leftDownY, leftDownX + widthColumn, leftDownY - currentHeight), paint)
}

private fun displayGrid(canvas: Canvas, paint: Paint, inputData: List<DiagramData>) {
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