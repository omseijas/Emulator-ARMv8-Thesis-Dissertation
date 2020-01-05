import utils.Registers

fun main(args: Array<String>) {
    val stringInput = readLine()!!
    println("You entered: $stringInput")
    val i: Instruction = Instruction()
    i.classify(stringInput)
}

open class Instruction() {

    fun classify(stringToClassify: String) {
        var string = stringToClassify.split(' ')
        var newString: MutableList<String> = mutableListOf()
        var i = 0

        when (string[0]) {
            "ADD" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                }
                val instructionAdd: InstructionAdd = InstructionAdd(newString)
                instructionAdd.runInstruction(newString[1], newString[2], newString[3])
            }
            "ADDI" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                }
                val instructionAddI: InstructionAddI = InstructionAddI(newString)
                instructionAddI.runInstruction(newString[1], newString[2], newString[3])
            }
            "SUB" -> {
            for (i in 0..string.size - 1) {
                newString.add(i, (string[i].replace(",* *".toRegex(), "")))
            }
            val instructionMult: InstructionSub= InstructionSub(newString)
                instructionMult.runInstruction(newString[1], newString[2], newString[3])
            }

        }

    }



    open fun runInstruction(dest: String, source1: String, source2: String): String {
        return ""
    }

    //Thirty-one 64-bit general-purpose registers X0-X30
    fun checkIfRegister(possibleRegister: String): String {
        if (possibleRegister[0].equals("X")) {
            if (possibleRegister[1].isDigit()) {
                if (possibleRegister.length > 2) {
                    if (possibleRegister[2].isDigit() && (possibleRegister[1] == '1' || possibleRegister[1] == '2' ||
                                possibleRegister[1] == '3')) {
                        val reg: Registers = Registers(possibleRegister)
                        return reg.getValueRegister()
                    }
                }
            }
        }
        return possibleRegister
    }

    open fun part() {}

}

class InstructionAdd(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = checkIfRegister(source1) //if it is not a register, it should be a number.
        var s2 = checkIfRegister(source2)
        val sum = s1.toInt() + s2.toInt()

        println("NEW VALUE: $sum IN $dest")
        return sum.toString()
    }
}

class InstructionSub(instruction: List<String>): Instruction(){
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = checkIfRegister(source1) //if it is not a register, it should be a number.
        var s2 = checkIfRegister(source2)
        val substraction = s1.toInt() - s2.toInt()

        println("NEW VALUE: $substraction IN $dest")
        return substraction.toString()
    }
}

class InstructionAddI(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = checkIfRegister(source1)
        val sum = s1.toInt() + source2.toInt()

        println("NEW VALUE: $sum IN $dest")
        return sum.toString()
    }
}

class InstructionSubI(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = checkIfRegister(source1)
        val sub = s1.toInt() - source2.toInt()

        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}
