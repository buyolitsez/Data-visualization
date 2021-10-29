import org.jetbrains.skija.*

private const val cntOfLineOnGridY = 6
private const val cntOfLineOnGridX = 6
private var spaceX = 0f
private var leftDownY = 0f
private var heightChart = 0f
private var weightChart = 0f
private var iterateCordX = 0f
private var iterateCordY = 0f
private var iterateValY = 0f
private var iterateValX = 0f
private var radius = 0f

private fun recalculateConstants(inputData: List<DiagramData>) {
    leftDownY = WINDOW_HEIGHT - 75f
    heightChart = leftDownY * 0.95f
    weightChart = WINDOW_WIDTH * 0.95f
    iterateCordX = weightChart / (cntOfLineOnGridX - 1)
    iterateCordY = heightChart / (cntOfLineOnGridY - 1)
    iterateValX = inputData.maxOf { it.paramName.toFloat() } / (cntOfLineOnGridX - 1)
    iterateValY = inputData.maxOf { it.value } / (cntOfLineOnGridY - 1)
    radius = kotlin.math.min(WINDOW_WIDTH, WINDOW_HEIGHT) / 30f
}

fun displayScatterChartDiagram(canvas: Canvas, paint: Paint, inputData: List<DiagramData>) {
    recalculateConstants(inputData)
    logger.info { "display bar chart diagram" }
    font.size = 12f
    displayGrid(canvas, paint, inputData)
    val maxValueX = inputData.maxOf { it.paramName.toFloat() }
    val maxValueY = inputData.maxOf { it.value }
    for (data in inputData) {
        //value / maxValue * weightChart
        val x = data.paramName.toFloat() / maxValueX * (weightChart - spaceX)
        val y = data.value / maxValueY * heightChart
        canvas.drawCircle(spaceX + x, y, radius, paint)
    }
}

private fun displayGrid(canvas: Canvas, paint: Paint, inputData: List<DiagramData>) {
    paint.color = TEXT_COLOR
    val maxValue = inputData.maxOf { it.value }
    //draw horizontally
    spaceX = 5f + convertFontSizeToPixel(font.size) * formatFloat(maxValue).length
    for (lineNum in 0 until cntOfLineOnGridY) {
        val y = leftDownY - iterateCordY * lineNum
        canvas.drawString(formatFloat(iterateValY * lineNum), 5f, y, font, paint)
        canvas.drawLine(
            spaceX, y, WINDOW_WIDTH.toFloat(), y, paint
        )
    }
    //draw vertically
    for (lineNum in 1 until cntOfLineOnGridX) {
        val x = 5f + iterateCordX * lineNum
        canvas.drawString(formatFloat(iterateValX * lineNum), x, font.size + leftDownY, font, paint)
        canvas.drawLine(x, 0f, x, leftDownY, paint)
    }
}
