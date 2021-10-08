import org.jetbrains.skija.Canvas
import org.jetbrains.skija.Paint
import kotlin.math.PI

fun displayCircleDiagram(canvas: Canvas, paint: Paint) {
    logger.info {"display circle diagram"}
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
    for (it in inputData) {
        sum += it.value
    }
    var angle = kotlin.math.atan2((y - CIRCLE_DIAGRAM_Y0).toDouble(), (x - CIRCLE_DIAGRAM_X0).toDouble())
    if (angle < 0) {
        angle += PI * 2F
    }
    for (i in inputData.indices) {
        val currentAngle = inputData[i].value / sum * PI * 2F
        if (angle <= currentAngle) {
            setColor(paint, i)
            break
        } else {
            angle -= currentAngle
        }
    }
    canvas.drawPoint(x, y, paint)
}
