import kotlin.test.*

internal class TestReduceInputData {
    private var COUNT_MAX_VALUES_OLD = 0
    private var MAX_RATIO_OLD = 0f

    @BeforeTest
    fun getConstants() {
        inputData.clear()
        COUNT_MAX_VALUES_OLD = COUNT_MAX_VALUES
        MAX_RATIO_OLD = MAX_RATIO
    }

    @AfterTest
    fun setConstants() {
        COUNT_MAX_VALUES = COUNT_MAX_VALUES_OLD
        MAX_RATIO = MAX_RATIO_OLD
    }

    @Test
    fun testEmpty() {
        inputData = mutableListOf()
        reduceInputData()
        assertContentEquals(mutableListOf(), inputData)
    }

    @Test
    fun testReduceByCount() {
        for (i in 0..10) {
            inputData.add(Data(i.toFloat(), "$i"))
        }
        inputData.sortBy { -it.value }
        COUNT_MAX_VALUES = 3
        MAX_RATIO = 100f
        reduceInputData()
        assertContentEquals(listOf(Data(10f, "10"), Data(9f, "9"), Data(36f, "others")), inputData)
    }

    @Test
    fun testReduceByRatio() {
        for (i in 0..10) {
            inputData.add(Data(i.toFloat(), "$i"))
        }
        inputData.sortBy { -it.value }
        COUNT_MAX_VALUES = 15
        MAX_RATIO = 1.4f
        reduceInputData()
        println(inputData)
        assertContentEquals(listOf(Data(10f, "10"), Data(9f, "9"), Data(8f, "8"), Data(28f, "others")), inputData)
    }
}
