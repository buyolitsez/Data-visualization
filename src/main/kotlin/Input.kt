import java.util.*
import kotlin.collections.ArrayList

data class Data(val value: Float, var paramName: String)

enum class DiagramName {
    CIRCLE,
    BAR,
    NONE
}

val inputData = ArrayList<Data>()
var diagramName = DiagramName.NONE

fun readData() {
    println("Diagram types:")
    DiagramName.values().forEach { name ->
        if (name != DiagramName.NONE) {
            println(name.toString().lowercase(Locale.getDefault()))
        }
    }
    outputStringWithColor("Diagram name: ")
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
    outputStringWithColor("data: ")
    var inputString = readLine()
    while (!inputString.isNullOrEmpty()) {
        inputData.add(
            Data(
                inputString.substringBefore(' ').toFloat(),
                inputString.substringAfter(' ')
            )
        )
        if (inputData.last().paramName.length > MAX_NAME_LEN) {
            assert(MAX_NAME_LEN >= 3)
            inputData.last().paramName = inputData.last().paramName.take(MAX_NAME_LEN - 3) + "..."
        }
        outputStringWithColor("data: ")
        inputString = readLine()
    }
    logger.info { "input data = $inputData" }
    if (inputData.isEmpty()) {
        throwError("input data is empty")
    }
}