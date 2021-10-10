import org.jetbrains.skija.*
import kotlin.math.PI

fun displayCircleDiagram(canvas: Canvas, paint: Paint) {
    logger.info { "display circle diagram" }
    var x = 0F
    while (x < WINDOW_WIDTH) {
        var y = 0F
        while (y < WINDOW_HEIGHT) {
            circleDiagramDrawPoint(x, y, canvas, paint)
            y += STEP
        }
        x += STEP
    }
    font.size = 30f
    for (index in inputData.indices) {
        setColor(paint, index)
        val y = (index + 1) * 50f
        drawSquare(canvas, paint, arrayOf(Point(500f, y), Point(520f, y), Point(520f, y - 20f), Point(500f, y - 20f)))
        paint.setARGB(255, 0, 0, 0)
        canvas.drawString("${inputData[index].paramName}(${inputData[index].value})", 530f, y, font, paint)
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
