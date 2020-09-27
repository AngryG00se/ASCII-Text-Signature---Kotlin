package signature

import java.io.File
import java.util.LinkedList
import java.util.Scanner
import kotlin.collections.HashMap


fun main() {

    val consoleInput = Scanner(System.`in`)

    print("Enter name and surname: ")
    val nameAndSurname = consoleInput.nextLine()
    print("Enter person's status: ")
    val status =  consoleInput.nextLine()

    val roman = loadFont("D:\\ASCII Text Signature\\ASCII Text Signature\\task\\src\\signature\\roman.txt")
    val medium = loadFont("D:\\ASCII Text Signature\\ASCII Text Signature\\task\\src\\signature\\medium.txt")

    val nameAndSurnameSize = calculateDisplaySize(nameAndSurname, roman)
    val statusSize = calculateDisplaySize(status, medium)

    val signatureSize = calculateSignatureSize(nameAndSurnameSize, statusSize)

    repeatChar('8', signatureSize)
    println()

    if (nameAndSurnameSize > statusSize) {
        printText(nameAndSurname, roman, 0, signatureSize)
        printText(status, medium, (signatureSize - statusSize) / 2 - 4, signatureSize)
    } else {
        printText(nameAndSurname, roman, (signatureSize - nameAndSurnameSize) / 2 - 4, signatureSize)
        printText(status, medium, 0, signatureSize)
    }


    repeatChar('8', signatureSize)

}

fun printText(nameAndSurname: String, font: HashMap<Char, Letter>, spacing: Int, signatureSize: Int) {
    val smallLetterA = font['a']!!
    val height = smallLetterA.height
    val spacesBetween = smallLetterA.width

    var counter = 0

    for (i in 0 until height) {
        printLeftMargin()
        repeatChar(' ', spacing)
        counter += spacing
        for (k in 0..nameAndSurname.lastIndex) {
            if (nameAndSurname[k] == ' ') {
                repeatChar(' ', spacesBetween)
                counter += spacesBetween
                continue;
            }

            val letter = font[nameAndSurname[k]]
            if (letter != null) {
                print(letter.signature[i])
                counter += letter.width
            }
        }

        repeatChar(' ', signatureSize - counter - 8)
        counter = 0

        printRightMargin()
        println()
    }
}


fun loadFont(path: String): HashMap<Char, Letter> {
    val result = HashMap<Char, Letter>()
    val scanner = Scanner(File(path))

    val height = scanner.nextLine().split(" ")[0]

    while (scanner.hasNext()) {
        val letterAndWidth = scanner.nextLine().split(" ")

        val signature = LinkedList<String>()

        for (i in 0 until height.toInt()) {
            signature.add(scanner.nextLine())
        }

        val letter = Letter(letterAndWidth[0], letterAndWidth[1].toInt(), height.toInt(), signature)
        result[letterAndWidth[0][0]] = letter
    }

    return result
}

class Letter(val letter: String, val width: Int, val height: Int, val signature: LinkedList<String>) {
}

fun calculateDisplaySize(input: String, font: HashMap<Char, Letter>): Int {
    var counter = 0;
    for (i in 0..input.lastIndex) {

        if (input[i] == ' ') {
            val smallLetterA = font['a']!!
            counter += smallLetterA.width
        }

        val letter = font[input[i]]

        if (letter != null) {
            counter += letter.width
        }
    }

    return counter
}

fun calculateSignatureSize (nameAndSurnameSize: Int, status: Int): Int {
    return if (nameAndSurnameSize > status) {
        nameAndSurnameSize + 8
    } else {
        status + 8
    }
}

fun repeatChar(char: Char, times: Int) {
    repeat(times) {
        print(char)
    }
}

fun printLeftMargin() {
    print("88  ")
}

fun printRightMargin() {
    print("  88")
}
