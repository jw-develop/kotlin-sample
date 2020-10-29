package orders

import java.io.File
import java.io.FileWriter
import java.util.Scanner

// -----------------------------------------------------------------------------
// Ordering Service
// -----------------------------------------------------------------------------
fun order(fruits: Array<String>): String {	
	val writer = FileWriter("log.txt",true)
	val scanner = Scanner(File("log.txt"))
	val scannerLines = ArrayList<String>()
	while (scanner.hasNextLine()) {
		scannerLines.add(scanner.nextLine())
	}
	val id = scannerLines.size
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
	writer.write("Order " + id + " completed. Price: " + toReturn + " Delivery time: 15 minutes" + "\n")
	writer.close()
	scanner.close()
	return toReturn
}

// -----------------------------------------------------------------------------
// Mail Service
// -----------------------------------------------------------------------------
fun mail() {
	var scanner = Scanner(File("log.txt"))
	var offset = 0
	while (scanner.hasNextLine()) {
		scanner.nextLine()
		offset++
	}
	println("Listening for new orders to complete.")
	while(true) {
		scanner = Scanner(File("log.txt"))
		var newOffset = 0
		while (scanner.hasNextLine()) {
			scanner.nextLine()
			newOffset++
		}
		scanner.close()
		if (offset < newOffset) {
			scanner = Scanner(File("log.txt"))
			var current = 0
			while (current < offset && scanner.hasNextLine()) scanner.nextLine()
			while (offset < newOffset && scanner.hasNextLine()) {
				println(scanner.nextLine())
			}
			scanner.close()
		}
	}
}


// -----------------------------------------------------------------------------
// Testing
// -----------------------------------------------------------------------------
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

fun main(args: Array<String>) {
	if (args.contains("test")) {
		runTests()
	} else if (args.contains("mail")) {
		mail()
	} else {
		println(order(args))
	}
}