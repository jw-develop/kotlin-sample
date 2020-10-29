package orders

fun order(fruits: Array<String>): String {
	var dollars: Double = 0.0
	
	fruits.forEach {
		if (it == "Apple")
			dollars += .25
		else if (it == "Orange")
			dollars += .6
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

fun test(name: String,expected: String, result: String) {
	if (!expected.equals(result)) {
		println(name + " test failed!")
		println("Expected: " + expected)
		println("Result: " + result + '\n')
	}
}

fun runTests() {
	var fruits: Array<String> = arrayOf()
	test("No Fruit :(","$0.00",order(fruits))

	fruits = arrayOf("Apple")
	test("One Apple","$0.25",order(fruits))

	fruits = arrayOf("Apple","Apple","Orange")
	test("Three Fruit","$1.10",order(fruits))
}
