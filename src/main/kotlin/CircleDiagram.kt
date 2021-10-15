import org.jetbrains.skija.*
import kotlin.math.min

var CIRCLE_DIAGRAM_X0 = WINDOW_WIDTH / 3f
var CIRCLE_DIAGRAM_Y0 = WINDOW_HEIGHT / 3f
var CIRCLE_DIAGRAM_RADIUS = min(CIRCLE_DIAGRAM_X0, CIRCLE_DIAGRAM_Y0) - 5f

var DISTANCE_BETWEEN_TWO_NAMES = WINDOW_HEIGHT / 12f // by Y
var SQUARE_SIZE = min(WINDOW_WIDTH / 40f, WINDOW_HEIGHT / 30f) // size of color example square
var BORDER_X = CIRCLE_DIAGRAM_X0 + CIRCLE_DIAGRAM_RADIUS + 30f

private fun recalculateConstants() {
    CIRCLE_DIAGRAM_X0 = WINDOW_WIDTH / 3f
    CIRCLE_DIAGRAM_Y0 = WINDOW_HEIGHT / 3f
    CIRCLE_DIAGRAM_RADIUS = min(CIRCLE_DIAGRAM_X0, CIRCLE_DIAGRAM_Y0) - 5f
    DISTANCE_BETWEEN_TWO_NAMES = WINDOW_HEIGHT / 12f
    SQUARE_SIZE = min(WINDOW_WIDTH / 40f, WINDOW_HEIGHT / 30f)
    BORDER_X = CIRCLE_DIAGRAM_X0 + CIRCLE_DIAGRAM_RADIUS + 30f
}

fun displayCircleDiagram(canvas: Canvas, paint: Paint) {
    recalculateConstants()
    logger.info { "display circle diagram" }
    drawCircle(canvas, paint)
    //draw a description of colors
    drawExampleSquares(canvas, paint)
    drawNameForValues(canvas, paint)
}

private fun drawNameForValues(canvas: Canvas, paint: Paint) {
    val maxNameLen = inputData.maxOf { "${it.paramName}(${formatFloat(it.value)})".length }
    font.size =
        listOf(
            (WINDOW_WIDTH - BORDER_X - SQUARE_SIZE) / maxNameLen,
            WINDOW_HEIGHT / ((inputData.size - 1) * (DISTANCE_BETWEEN_TWO_NAMES)) * 3f,
            1.5f * SQUARE_SIZE
        ).minOf { it } * 1.6f
    for (index in inputData.indices) {
        paint.color = TEXT_COLOR
        val y = (index + 1) * DISTANCE_BETWEEN_TWO_NAMES
        canvas.drawString(
            "${inputData[index].paramName}(${formatFloat(inputData[index].value)})",
            BORDER_X + SQUARE_SIZE,
            y,
            font,
            paint
        )
    }
}

private fun drawExampleSquares(canvas: Canvas, paint: Paint) {
    for (index in inputData.indices) {
        setColor(paint, index)
        val y = (index + 1) * DISTANCE_BETWEEN_TWO_NAMES
        canvas.drawRect(Rect(BORDER_X, y, BORDER_X + SQUARE_SIZE, y - SQUARE_SIZE), paint)
    }
}

private fun drawCircle(canvas: Canvas, paint: Paint) {
    var sum = 0F
    inputData.forEach {
        sum += it.value
    }
    var startAngle = 0f
    inputData.indices.forEach { index ->
        val currentAngle = inputData[index].value / sum * 360f
        setColor(paint, index)
        canvas.drawArc(
            0f,
            0f,
            CIRCLE_DIAGRAM_RADIUS * 2f,
            CIRCLE_DIAGRAM_RADIUS * 2f,
            startAngle,
            currentAngle,
            true,
            paint
        )
        startAngle += currentAngle
    }
}
