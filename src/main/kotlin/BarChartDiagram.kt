import org.jetbrains.skija.*

private const val leftDownY = WINDOW_HEIGHT - 150f
private const val widthColumn = 30f
private const val heightColumn = 400f
private const val distanceBetweenTwoColumns = 30f

fun displayBarChartDiagram(canvas: Canvas, paint: Paint) {
    logger.info { "display bar chart diagram" }
    val maxValue = inputData.maxOf { it.value }
    var leftDownX = 70f
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