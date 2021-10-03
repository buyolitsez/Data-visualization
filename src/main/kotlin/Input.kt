import java.util.Scanner

data class Data(var value : Float)

val inputScanner = Scanner(System.`in`)
val inputData = ArrayList<Data>()
var diagramName = ""

fun readData() {
    val str = readLine()
    if (str == null) {
        throwError("Input is null")
    }
    diagramName = str!!
    when(str) {
        "circle" -> readDataCircle()
        else -> throwError("Unknown type of diagram")
    }
}

fun readDataCircle() {
    while(inputScanner.hasNextInt()) {
        inputData.add(Data(inputScanner.nextFloat()))
    }
    logger.info {"input data = $inputData"}
    if (inputData.isEmpty()) {
        throwError("input data is empty")
    }
}