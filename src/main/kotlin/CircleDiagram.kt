import org.jetbrains.skija.*
import kotlin.math.PI

private const val distanceBetweenNames = 50f // by Y
private const val fontSize = 30f
private const val squareSize = 20f // size of color example square
private const val borderX = CIRCLE_DIAGRAM_X0 + CIRCLE_DIAGRAM_RADIUS + fontSize


fun displayCircleDiagram(canvas: Canvas, paint: Paint) {
    logger.info { "display circle diagram" }
    drawCircle(canvas, paint)
    //draw a description of colors
    font.size = fontSize
    for (index in inputData.indices) {
        //draw example square
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
        //draw name of value
        paint.color = TEXT_COLOR
        canvas.drawString(
            "${inputData[index].paramName}(${inputData[index].value})",
            borderX + fontSize,
            y,
            font,
            paint
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
