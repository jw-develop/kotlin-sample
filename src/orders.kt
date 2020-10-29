package orders

import java.io.File
import java.io.FileWriter
import java.util.Scanner

// -----------------------------------------------------------------------------
// Ordering Service
// -----------------------------------------------------------------------------
fun order(fruits: Array<String>): String {

	// Getting current stock of apples and oranges.
	val stockScanner = Scanner(File("stock.txt"))
	var appleStock = 0
	var orangeStock = 0
	while (stockScanner.hasNextLine()) {
		val line = stockScanner.nextLine()
		val words = line.split(":")
		if (words.size > 1) {
			if (words[0] == "apples")
				appleStock = words[1].toInt()
			if (words[0] == "oranges")
				orangeStock = words[1].toInt()
		}
	}

	// Getting # of lines in log.txt
	val writer = FileWriter("log.txt",true)
	val scanner = Scanner(File("log.txt"))
	val scannerLines = ArrayList<String>()
	while (scanner.hasNextLine()) {
		scannerLines.add(scanner.nextLine())
	}
	val id = scannerLines.size
	
	// Count apples and oranges
	var apples = 0
	var oranges = 0
	fruits.forEach {
		if (it == "Apple") {
			apples += 1
			appleStock--
		}
		else if (it == "Orange") {
			oranges += 1
			orangeStock--
		}
	}
	
	// Calculate cost
	var dollars: Double = 0.0
	dollars += ((apples / 2) + apples % 2) * .6
	dollars += (((oranges / 3) * 2) + oranges % 3) * .25
	var toReturn = "\$%.2f".format(dollars)

	// Check if out of stock
	if (orangeStock < 0 || appleStock < 0) {
		toReturn = "Order " + id + " failed. Fruit Shortage."
		writer.write(toReturn)
	} else {
		writer.write("Order " + id + " completed. Price: " + toReturn + ". Delivery time: 15 minutes" + "\n")
		val stockWriter = FileWriter("stock.txt",false)
		stockWriter.write("apples:" + appleStock + "\noranges:" + orangeStock)
		stockWriter.close()
	}
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
	while(true) { // Always checking for new messages.
		scanner = Scanner(File("log.txt"))
		var newOffset = 0
		while (scanner.hasNextLine()) {
			scanner.nextLine()
			newOffset++
		}
		scanner.close()

		// If the file is of a different length, print out the latest messages.
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