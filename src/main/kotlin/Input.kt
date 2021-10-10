import java.util.*
import kotlin.collections.ArrayList

data class Data(var value: Float)

enum class DiagramName {
    CIRCLE,
    NONE
}

val inputScanner = Scanner(System.`in`)
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
        "circle" -> readDataCircle()
        else -> throwError("Unknown type of diagram")
    }
}

fun readDataCircle() {
    diagramName = DiagramName.CIRCLE
    println("Write data")
    while (inputScanner.hasNextFloat()) {
        inputData.add(Data(inputScanner.nextFloat()))
    }
    logger.info { "input data = $inputData" }
    if (inputData.isEmpty()) {
        throwError("input data is empty")
    }
}