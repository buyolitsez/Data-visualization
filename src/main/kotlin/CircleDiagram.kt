import org.jetbrains.skija.*
import kotlin.math.PI
import kotlin.math.min

private const val CIRCLE_DIAGRAM_X0 = WINDOW_WIDTH / 3f
private const val CIRCLE_DIAGRAM_Y0 = WINDOW_HEIGHT / 3f
private val CIRCLE_DIAGRAM_RADIUS = min(CIRCLE_DIAGRAM_X0, CIRCLE_DIAGRAM_Y0) - 5f
private const val STEP = 1F // step in iterating over coordinates

private const val distanceBetweenNames = 50f // by Y
private const val squareSize = 20f // size of color example square
private val borderX = CIRCLE_DIAGRAM_X0 + CIRCLE_DIAGRAM_RADIUS + 30f


fun displayCircleDiagram(canvas: Canvas, paint: Paint) {
    logger.info { "display circle diagram" }
    drawCircle(canvas, paint)
    //draw a description of colors
    drawExampleSquares(canvas, paint)
    drawNameForValues(canvas, paint)
}

private fun drawNameForValues(canvas: Canvas, paint: Paint) {
    val maxNameLen = inputData.maxOf { "${it.paramName}(${formatFloat(it.value)})".length }
    font.size = min((WINDOW_WIDTH - borderX - squareSize) / maxNameLen, 1.5f * squareSize) * 1.6f
    for (index in inputData.indices) {
        paint.color = TEXT_COLOR
        val y = (index + 1) * distanceBetweenNames
        canvas.drawString(
            "${inputData[index].paramName}(${formatFloat(inputData[index].value)})",
            borderX + squareSize,
            y,
            font,
            paint
        )
    }
}

private fun drawExampleSquares(canvas: Canvas, paint: Paint) {
    for (index in inputData.indices) {
        setColor(paint, index)
        val y = (index + 1) * distanceBetweenNames
        drawRectangle(
            canvas,
            paint,
            arrayOf(
                Point(borderX, y),
                Point(borderX + squareSize, y),
                Point(borderX + squareSize, y - squareSize),
                Point(borderX, y - squareSize)
            )
        )
    }
}

private fun drawCircle(canvas: Canvas, paint: Paint) {
    var x = 0F
    while (x < WINDOW_WIDTH) {
        var y = 0F
        while (y < WINDOW_HEIGHT) {
            circleDiagramDrawPoint(x, y, canvas, paint)
            y += STEP
        }
        x += STEP
    }
}

fun circleDiagramDrawPoint(x: Float, y: Float, canvas: Canvas, paint: Paint) {
    if (distanceSq(x, y, CIRCLE_DIAGRAM_X0, CIRCLE_DIAGRAM_Y0) > CIRCLE_DIAGRAM_RADIUS * CIRCLE_DIAGRAM_RADIUS) {
        return
    }
    var sum = 0F
    inputData.forEach {
        sum += it.value
    }
    // get angle in radians, where point is
    var angle = kotlin.math.atan2((y - CIRCLE_DIAGRAM_Y0).toDouble(), (x - CIRCLE_DIAGRAM_X0).toDouble())
    if (angle < 0) angle += PI * 2F
    for (index in inputData.indices) {
        // convert data.value to radians(divide circle into parts)
        val currentAngle = inputData[index].value / sum * PI * 2F
        if (angle <= currentAngle) {
            setColor(paint, index)
            break
        } else {
            angle -= currentAngle
        }
    }
    canvas.drawPoint(x, y, paint)
}
