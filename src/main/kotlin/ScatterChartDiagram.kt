import org.jetbrains.skija.*

private const val cntOfLineOnGridY = 6
private const val cntOfLineOnGridX = 9
private var leftDownY = WINDOW_HEIGHT - 75f
private var heightChart = (leftDownY) * 0.95f
private var weightChart = WINDOW_WIDTH * 0.95f

private fun recalculateConstants() {
    leftDownY = WINDOW_HEIGHT - 75f
    heightChart = (leftDownY) * 0.95f
    weightChart = WINDOW_WIDTH * 0.95f
}

fun displayScatterChartDiagram(canvas: Canvas, paint: Paint, inputData: List<DiagramData>) {
    recalculateConstants()
    logger.info { "display bar chart diagram" }
    font.size = 12f
    displayGrid(canvas, paint, inputData)
}

private fun displayGrid(canvas: Canvas, paint: Paint, inputData: List<DiagramData>) {
    paint.color = TEXT_COLOR
    val maxValue = inputData.maxOf { it.value }
    val iterateCordX = weightChart / (cntOfLineOnGridX - 1)
    val iterateCordY = heightChart / (cntOfLineOnGridY - 1)
    val iterateValY = inputData.maxOf { it.value } / (cntOfLineOnGridY - 1)
    val iterateValX = inputData.maxOf { it.paramName.toFloat() } / (cntOfLineOnGridX - 1)
    //draw horizontally
    for (lineNum in 0 until cntOfLineOnGridY) {
        val y = leftDownY - iterateCordY * lineNum
        canvas.drawString(formatFloat(iterateValY * lineNum), 5f, y, font, paint)
        canvas.drawLine(
            5f + convertFontSizeToPixel(font.size) * formatFloat(maxValue).length, y, WINDOW_WIDTH.toFloat(), y, paint
        )
    }
    //draw vertically
    for (lineNum in 1 until cntOfLineOnGridX) {
        val x = 5f + iterateCordX * lineNum
        canvas.drawString(formatFloat(iterateValX * lineNum), x, font.size + leftDownY, font, paint)
        canvas.drawLine(x, 0f, x, leftDownY, paint)
    }
}
