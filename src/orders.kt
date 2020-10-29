package orders

fun order(fruits: Array<String>): String {	
	var dollars: Double = 0.0

	// Count apples and oranges
	var apples = 0
	var oranges = 0
	fruits.forEach {
		if (it == "Apple")
			apples += 1
		else if (it == "Orange")
			oranges += 1
	}

	dollars += ((apples / 2) + apples % 2) * .6
	dollars += (((oranges / 3) * 2) + oranges % 3) * .25
	
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
	passed += test("Three Fruit","$0.85",order(fruits))

	fruits = arrayOf("Apple","Apple","Apple","Orange","Orange","Orange","Orange")
	passed += test("Basket of 3 apples, 4 oranges","$1.95",order(fruits))

	if (passed == 4)
		println("All tests passed.")
}
