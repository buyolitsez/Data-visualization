import java.util.*
import kotlin.collections.ArrayList

data class Data(val value: Float, val paramName: String)

enum class DiagramName {
    CIRCLE,
    BAR,
    NONE
}

val inputData = ArrayList<Data>()
var diagramName = DiagramName.NONE

fun readData() {
    println("Write a diagram name")
    println("Diagram types:")
    DiagramName.values().forEach { name ->
        if (name != DiagramName.NONE) {
            println(name.toString().lowercase(Locale.getDefault()))
        }
    }

    when (readLine()) {
        null -> throwError("Input is null")
        "circle" -> diagramName = DiagramName.CIRCLE
        "bar" -> diagramName = DiagramName.BAR
        else -> throwError("Unknown type of diagram")
    }
    readDataArray()
}

fun readDataArray() {
    println("Write data")
    var inputString = readLine()
    while (!inputString.isNullOrEmpty()) {
        inputData.add(
            Data(
                inputString.substringBefore(' ').toFloat(),
                inputString.substringAfter(' ')
            )
        )
        inputString = readLine()
    }
    logger.info { "input data = $inputData" }
    if (inputData.isEmpty()) {
        throwError("input data is empty")
    }
}