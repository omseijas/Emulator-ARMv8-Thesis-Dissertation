package ARMv8_Architecture

import utils.ARMv8Function
import utils.Constants
import utils.Registers
import java.lang.Exception

var memArmv8 = arrayOfNulls<Byte>(100) //Memory is in bytes.
var variables = mutableMapOf<String, ARMv8Function>()
var keyWords = mutableListOf<String>(
    Constants.ADD,
    Constants.ADDI,
    Constants.AND,
    Constants.B,
    Constants.CBNZ,
    Constants.CBZ,
    Constants.EOR,
    Constants.LDUR,
    Constants.ORR,
    Constants.STDUR,
    Constants.SUB,
    Constants.SUBI
)
var numberLines = 0
var registersList: Registers = Registers()

fun getValueFromPosition(name: String, off: String): Byte {
    var position = registersList.getValueRegister(name).toInt()

    var offset = off.removePrefix("#").toInt() / 8
    position += offset
    return memArmv8.get(position)!!
}

class Memory() {

    fun initMemory() {
        for (initializeMemoryAt0 in 0 until memArmv8.size) {
            memArmv8[initializeMemoryAt0] = 0
        }
    }

    fun showStatus() {
        for (initializeMemoryAt0 in 0 until memArmv8.size) {
            if (memArmv8[initializeMemoryAt0]!! != 0.toByte()) {
                println(memArmv8[initializeMemoryAt0].toString() + " in $initializeMemoryAt0")
            }
        }
    }

    fun getStatus(): List<Byte> {
        var memoryList = mutableListOf<Byte>()
        for (i in 0 until memArmv8.size)
                memArmv8[i]?.let { memoryList.add(it) }
        return memoryList
    }
}

open class Instruction() {

    var numberLine: Int = numberLines
    var readAllInput = false

    fun classify(stringToClassify: String, registers: Registers): String {
        var string = stringToClassify.split(' ')
        var resolution = ""
        var newString: MutableList<String> = mutableListOf()
        var i = 0
        registersList = registers
        try {
            when (string[0]) {
                Constants.ADD -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                    }
                    val instructionAdd: InstructionAdd = InstructionAdd(newString)
                    resolution = instructionAdd.runInstruction(newString[1], newString[2], newString[3])
                }
                Constants.ADDI -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                    }
                    val instructionAddI: InstructionAddI = InstructionAddI(newString)
                    resolution = instructionAddI.runInstruction(newString[1], newString[2], newString[3])
                }
                Constants.SUB -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                    }
                    val instructionMult: InstructionSub = InstructionSub(newString)
                    resolution = instructionMult.runInstruction(newString[1], newString[2], newString[3])
                }
                Constants.SUBI -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                    }
                    val onstruction: InstructionSubI = InstructionSubI(newString)
                    resolution = onstruction.runInstruction(newString[1], newString[2], newString[3])
                }
                Constants.LDUR -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                    }
                    val instruction = InstructionLDUR(newString)
                    resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
                }
                Constants.STDUR -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                    }
                    val instruction = InstructionSTUR(newString)
                    resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
                }
                Constants.ORR -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                    }
                    val instruction = InstructionORR(newString)
                    resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
                }

                Constants.EOR -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                    }
                    val instruction = InstructionEOR(newString)
                    resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
                }

                Constants.AND -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                    }
                    val instruction = InstructionAND(newString)
                    resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
                }
                Constants.CBZ -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                    }
                    val instruction = InstructionCBZ(newString)
                    resolution = instruction.runInstruction(newString[1], newString[2])
                }
                Constants.CBNZ -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                    }
                    val instruction = InstructionCBNZ(newString)
                    resolution = instruction.runInstruction(newString[1], newString[2])
                }
                Constants.B -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                    }
                    val instruction = InstructionB(newString)
                    resolution = instruction.runInstruction(newString[1])
                }
                "" -> {

                }
                else -> {
                    for (i in 0..string.size - 1) {
                        newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                    }
                    if (newString.size == 5) {
                        classify(checkForVariable(string), registers)
                        numberLines--
                    } else {
                        throw Exception("Cadena mal formada:" + string)
                    }

                }
            }
            numberLines++
            numberLine = numberLines
        } catch (e: Exception) {
            throw Exception(e.message)
        }
        return resolution
    }

    private fun checkForVariable(string: List<String>): String {
        var newString: MutableList<String> = mutableListOf()
        val possibleVariable = string[0].replace(":".toRegex(), "")
        for (i in 1..string.size - 1) {
            newString.add(i - 1, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
        }
        if (!variables.contains(possibleVariable))
            variables.put(
                possibleVariable,
                ARMv8Function(
                    possibleVariable,
                    numberLines,
                    newString.toString().replace("[^a-zA-Z\\d ]+(?![^{]*})".toRegex(), "")
                )
            )
        return newString.toString().replace("[^a-zA-Z\\d ]+(?![^{]*})".toRegex(), "")
    }


    open fun runInstruction(dest: String, source1: String, source2: String): String {
        return ""
    }

    fun checkForVariables(stringInput: MutableList<String>, numberLine: Int) {
        numberLines = numberLine
        for (i in numberLine until stringInput.size) {
            var string = stringInput[i].split(' ')
            var resolution = ""
            var i = 0
            if (!keyWords.contains(string[0]))
                checkForVariable(string)
            numberLines++

        }
        numberLines = 0
    }

    fun classifyAllInput(stringInput: MutableList<String>, registers: Registers) {
        registersList = registers
        checkForVariables(stringInput, 0)
        numberLines = 0
        while (!readAllInput) {
            if (stringInput.get(numberLines) != "") {
                println(classify(stringInput.get(numberLines), registersList))
            }
            //       numberLines++
            if (numberLines == stringInput.size)
                readAllInput = true
        }
    }

    open fun runInstruction(dest: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    open fun runInstruction(toCompare: String, dest: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun reset() {
        registersList = Registers()
        numberLines = 0
        for (initializeMemoryAt0 in 0 until memArmv8.size) {
            memArmv8[initializeMemoryAt0] = 0
        }
    }
}

class InstructionAdd(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registersList.getValueRegister(source1) //if it is not a register, it should be a number.
        var s2 = registersList.getValueRegister(source2)
        val sum = s1.toInt() + s2.toInt()
        registersList.setValueRegister(dest, true, sum.toString())
        println("NEW VALUE: $sum IN $dest")
        return sum.toString()
    }
}

class InstructionSub(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registersList.getValueRegister(source1) //if it is not a register, it should be a number.
        var s2 = registersList.getValueRegister(source2)
        val substraction = s1.toInt() - s2.toInt()
        registersList.setValueRegister(dest, true, substraction.toString())
        println("NEW VALUE: $substraction IN $dest")
        return substraction.toString()
    }
}

class InstructionAddI(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registersList.getValueRegister(source1)
        val sum = s1.toInt() + source2.toInt()
        registersList.setValueRegister(dest, true, sum.toString())

        println("NEW VALUE: $sum IN $dest")
        return sum.toString()
    }
}

class InstructionSubI(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registersList.getValueRegister(source1)
        val sub = s1.toInt() - source2.toInt()
        registersList.setValueRegister(dest, true, sub.toString())
        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}


class InstructionAND(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registersList.getValueRegister(source1)
        var s2 = registersList.getValueRegister(source2)
        val sub = s1.toInt() and s2.toInt()
        registersList.setValueRegister(dest, true, sub.toString())
        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}

class InstructionORR(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registersList.getValueRegister(source1)
        var s2 = registersList.getValueRegister(source2)
        val sub = s1.toInt() or s2.toInt()
        registersList.setValueRegister(dest, true, sub.toString())
        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}

class InstructionEOR(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registersList.getValueRegister(source1)
        var s2 = registersList.getValueRegister(source2)
        val sub = s1.toInt() xor s2.toInt()
        registersList.setValueRegister(dest, true, sub.toString())
        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}

class InstructionB(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String): String {
        if (variables.containsKey(dest)) {
            numberLines = variables.get(dest)!!.numberLines - 1
        } else
            throw Exception("Parece que esa nombre no existe! Revisa que sea correcto.")
        return ""
    }
}

class InstructionCBZ(instruction: List<String>) : Instruction() {
    override fun runInstruction(toCompare: String, dest: String): String {
        val valueToCompare = registersList.getValueRegister(toCompare).toInt()
        if (valueToCompare == 0)
            numberLines = variables.get(dest)!!.numberLines - 1
        return ""
    }
}

class InstructionCBNZ(instruction: List<String>) : Instruction() {
    override fun runInstruction(toCompare: String, dest: String): String {
        val valueToCompare = registersList.getValueRegister(toCompare).toInt()
        if (valueToCompare != 0)
            numberLines = variables.get(dest)!!.numberLines - 1
        return ""
    }
}


class InstructionLDUR(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        val value = getValueFromPosition(source1, source2)

        registersList.setValueRegister(dest, true, value.toString())
        println("NEW VALUE: $value IN $dest")
        return value.toString()
    }
}

class InstructionSTUR(instruction: List<String>) : Instruction() {
    override fun runInstruction(source: String, dest: String, offset: String): String {
        try {
            var position = registersList.getValueRegister(source).toInt()
            position += offset.removePrefix("#").toInt() / 8
            memArmv8[position] = registersList.getValueRegister(source).toByte()
            return source + " in " + dest
        } catch (e: Exception) {
            throw  Exception("Parece que estás intentando acceder a una posición de memoria que no existe. Revisa tus instrucciones" +
                    "de acceso y comprueba que estás dentro de los límites establecidos.")
        }
        return  ""
    }
}

///TODO FLAGS
class InstructionBCond(instruction: List<String>) : Instruction() {}

class DecimalRepresentation(instruction: String) {

}

class BecimalRepresentation(instruction: String) {

}