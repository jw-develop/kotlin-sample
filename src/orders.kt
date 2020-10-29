package orders

fun order(fruits: Array<String>): String {
	var dollars: Double = 0.0
	
	fruits.forEach {
		if (it == "Apple")
			dollars += .6
		else if (it == "Orange")
			dollars += .25
	}

	val toReturn = "\$%.2f".format(dollars)
	return toReturn
}

fun main(args: Array<String>) {
	if (args.contains("test")) {
		runTests()
	} else {
		println(order(args))
	}
}

fun test(name: String,expected: String, result: String): Int {
	if (!expected.equals(result)) {
		println(name + " test failed!")
		println("Expected: " + expected)
		println("Result: " + result + '\n')
		return 0
	}
	return 1
}

fun runTests() {
	var passed = 0
	var fruits: Array<String> = arrayOf()
	passed += test("No Fruit :(","$0.00",order(fruits))

	fruits = arrayOf("Apple")
	passed += test("One Apple","$0.60",order(fruits))

	fruits = arrayOf("Apple","Apple","Orange")
	passed += test("Three Fruit","$1.45",order(fruits))

	if (passed == 3)
		println("All tests passed.")
}
