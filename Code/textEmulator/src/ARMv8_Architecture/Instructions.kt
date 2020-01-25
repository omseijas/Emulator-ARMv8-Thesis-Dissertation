package ARMv8_Architecture

import registers
import utils.ARMv8Function
import utils.Constants
import java.lang.Exception

var memArmv8 = arrayOfNulls<Byte>(4096) //Memory is in bytes.
var variables = mutableMapOf<String, ARMv8Function>()
var keyWords = mutableListOf<String>(
    Constants.ADD,
    Constants.ADDI,
    Constants.AND,
    Constants.B,
    Constants.BCOND,
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
fun getValueFromPosition(name: String, off: String): Byte {
    var position = registers.getValueRegister(name)

    var offset = off.removePrefix("#").toInt() / 8
    position += offset
    return memArmv8.get(position.toInt())!!
}

open class Memory() {

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
}

open class Instruction() {
    var readAllInput = false


    fun classify(stringToClassify: String): String {
        var string = stringToClassify.split(' ')
        var resolution = ""
        var newString: MutableList<String> = mutableListOf()
        var i = 0


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
            Constants.BCOND -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionSTUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }
            Constants.B -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionB(newString)
                resolution = instruction.runInstruction(newString[1])
            }

            else -> {
                classify(checkForVariable(string))

            }
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


    fun classifyAllInput(stringInput: MutableList<String>) {
        for (input in stringInput) {
            if (input != "") {
                var string = input.split(' ')
                var resolution = ""
                var i = 0
                if (!keyWords.contains(string[0]))
                    checkForVariable(string)
            }
            numberLines++

        }
        numberLines = 0
        while (!readAllInput) {
            if (stringInput.get(numberLines) != "") {
                println(classify(stringInput.get(numberLines)))
            }
                numberLines++
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
}

class InstructionAdd(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1) //if it is not a register, it should be a number.
        var s2 = registers.getValueRegister(source2)
        val sum = s1.toInt() + s2.toInt()
        registers.setValueRegister(dest, true, sum.toString())
        println("NEW VALUE: $sum IN $dest")
        return sum.toString()
    }
}

class InstructionSub(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1) //if it is not a register, it should be a number.
        var s2 = registers.getValueRegister(source2)
        val substraction = s1.toInt() - s2.toInt()
        registers.setValueRegister(dest, true, substraction.toString())
        println("NEW VALUE: $substraction IN $dest")
        return substraction.toString()
    }
}

class InstructionAddI(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        val sum = s1.toInt() + source2.toInt()
        registers.setValueRegister(dest, true, sum.toString())

        println("NEW VALUE: $sum IN $dest")
        return sum.toString()
    }
}

class InstructionSubI(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        val sub = s1.toInt() - source2.toInt()
        registers.setValueRegister(dest, true, sub.toString())
        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}


class InstructionAND(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        var s2 = registers.getValueRegister(source2)
        val sub = s1.toInt() and s2.toInt()
        registers.setValueRegister(dest,true,sub.toString())
        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}

class InstructionORR(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        var s2 = registers.getValueRegister(source2)
        val sub = s1.toInt() or s2.toInt()
        registers.setValueRegister(dest,true,sub.toString())
        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}

class InstructionEOR(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        var s2 = registers.getValueRegister(source2)
        val sub = s1.toInt() xor s2.toInt()
        registers.setValueRegister(dest,true,sub.toString())
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
        val valueToCompare = registers.getValueRegister(toCompare).toInt()
        if (valueToCompare == 0)
            numberLines = variables.get(dest)!!.numberLines - 1
        return ""
    }
}

class InstructionCBNZ(instruction: List<String>) : Instruction() {
    override fun runInstruction(toCompare: String, dest: String): String {
        val valueToCompare = registers.getValueRegister(toCompare).toInt()
        if (valueToCompare != 0)
            numberLines = variables.get(dest)!!.numberLines - 1
        return ""
    }
}

class InstructionBCond(instruction: List<String>) : Instruction() {}
//LDUR/STUR allow accessing 32/64-bit values when they are not aligned to the size of the operand.
// For example, a 32-bit value stored at address 0x52.
class InstructionLDUR(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        val value = getValueFromPosition(source1, source2)

        registers.setValueRegister(dest, true, value.toString())
        println("NEW VALUE: $value IN $dest")
        return value.toString()
    }
}

//As of now, I wont touch the offset.
class InstructionSTUR(instruction: List<String>) : Instruction() {
    override fun runInstruction(source: String, dest: String, offset: String): String {
        var position = registers.getValueRegister(source).toInt()
        position += offset.removePrefix("#").toInt() / 8
        memArmv8[position] = registers.getValueRegister(source).toByte()
        return source + " in " + dest
    }
}

class DecimalRepresentation(instruction: String) {

}

class BecimalRepresentation(instruction: String) {

}