import utils.Registers
import java.io.File
import java.lang.Exception

var registers: Registers = Registers()
val memArmv8 = List(4096){""}
var stackPointerArmv8 : Int = 0

fun getPositionRegister(name:String, offset:String):Int{
    var value = name.removePrefix("X").toInt()
    return value * 8 + offset.removePrefix("#").toInt() * 8
}

fun getValueFromPosition(name:String, offset: String):String{
    var value = getPositionRegister(name,offset)/8
    return registers.getValueRegister("X"+value)
}

fun main(args: Array<String>) {
    var stringInput = mutableListOf<String>()
    File("C:\\Users\\Zezão\\Desktop\\Manu\\Trabajo-de-fin-de-grado\\Code\\textEmulator\\src\\test.txt").forEachLine {
        stringInput.add(
            it
        )
    }
    println("Has introducido: $stringInput")
    val i = Instruction()
    var j =0
    try {
        for(input in stringInput){
            println(i.classify(input))
        }
    }
        catch(e:Exception){
            print(e.message)
        }
    val fileName = "resultado.txt"
    val fileCode = File(fileName)
    registers.listofRegisters.forEach { print("""${it?.nameRegisters}:${it?.valueRegister} ||| """) }
 //   fileCode.writeText(stringInput[0])

}

open class Instruction() {

    fun classify(stringToClassify: String): String {
        var string = stringToClassify.split(' ')
        var resolution = ""
        var newString: MutableList<String> = mutableListOf()
        var i = 0


        when (string[0]) {
            "ADD" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                }
                val instructionAdd: InstructionAdd = InstructionAdd(newString)
                resolution = instructionAdd.runInstruction(newString[1], newString[2], newString[3])
            }
            "ADDI" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                }
                val instructionAddI: InstructionAddI = InstructionAddI(newString)
                resolution = instructionAddI.runInstruction(newString[1], newString[2], newString[3])
            }
            "SUB" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                }
                val instructionMult: InstructionSub = InstructionSub(newString)
                resolution = instructionMult.runInstruction(newString[1], newString[2], newString[3])
            }
            "SUBI" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                }
                val onstruction: InstructionSubI = InstructionSubI(newString)
                resolution = onstruction.runInstruction(newString[1], newString[2], newString[3])
            }
            "SUBS" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *".toRegex(), "")))
                }
                val onstruction = InstructionSubS(newString)
                resolution = onstruction.runInstruction(newString[1], newString[2], newString[3])
            }

            /*TO DO*/
            "LDUR" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionLDUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }
            "STDUR" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionSTUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }
            "ORR" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionORR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }

            "EOR" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionSTUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }

            "AND" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionAND(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }

            "NOT" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionSTUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }
            "CBZ" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionSTUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }
            "CBNZ" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionSTUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }
            "B.cond" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionSTUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }
            "B" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionSTUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }
            "BR" -> {
                for (i in 0..string.size - 1) {
                    newString.add(i, (string[i].replace(",* *\\[?\\]?".toRegex(), "")))
                }
                val instruction = InstructionSTUR(newString)
                resolution = instruction.runInstruction(newString[1], newString[2], newString[3])
            }
        }
        return resolution
    }


    open fun runInstruction(dest: String, source1: String, source2: String): String {
        return ""
    }


    open fun part() {}

}

class InstructionAdd(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1) //if it is not a register, it should be a number.
        var s2 = registers.getValueRegister(source2)
        val sum = s1.toInt() + s2.toInt()
        registers.setValueRegister(dest,true,sum.toString())
        println("NEW VALUE: $sum IN $dest")
        return sum.toString()
    }
}

class InstructionSub(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1) //if it is not a register, it should be a number.
        var s2 = registers.getValueRegister(source2)
        val substraction = s1.toInt() - s2.toInt()
        registers.setValueRegister(dest,true,substraction.toString())
        println("NEW VALUE: $substraction IN $dest")
        return substraction.toString()
    }
}

class InstructionAddI(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        val sum = s1.toInt() + source2.toInt()
        registers.setValueRegister(dest,true,sum.toString())

        println("NEW VALUE: $sum IN $dest")
        return sum.toString()
    }
}

class InstructionSubI(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        val sub = s1.toInt() - source2.toInt()
        registers.setValueRegister(dest,true,sub.toString())
        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}

class InstructionSubS(instruction: List<String>) : Instruction() {
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        var s2 = registers.getValueRegister(source2)
        val sub = s1.toInt() - source2.toInt()

        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}
//LDUR/STUR allow accessing 32/64-bit values when they are not aligned to the size of the operand.
// For example, a 32-bit value stored at address 0x52.
class InstructionLDUR(instruction: List<String>) : Instruction(){
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        val value = getValueFromPosition(source1,source2)
        registers.setValueRegister(dest,true,value)
        return value +"in "+dest
    }
}
//As of now, I wont touch the offset.
class InstructionSTUR(instruction: List<String>) : Instruction(){
    override fun runInstruction(source: String, dest1: String, dest2: String): String {
        val value = registers.getValueRegister(source)

        registers.setValueRegister(dest1,true,value)
        return value +" in "+dest1
    }
}

class InstructionCBZ(instruction: List<String>):Instruction(){}
class InstructionCBNZ(instruction: List<String>):Instruction(){}
class InstructionBCond(instruction: List<String>):Instruction(){}
class InstructionAND(instruction: List<String>):Instruction(){
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        var s2 = registers.getValueRegister(source2)
        val sub = s1.toInt() and s2.toInt()

        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}

class InstructionORR(instruction: List<String>):Instruction(){
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        var s2 = registers.getValueRegister(source2)
        val sub = s1.toInt() or s2.toInt()

        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}
class InstructionEOR(instruction: List<String>):Instruction(){}
class InstructionNOT(instruction: List<String>):Instruction(){}
class InstructionB(instruction: List<String>):Instruction(){}
class InstructionBR(instruction: List<String>):Instruction(){}


class DecimalRepresentation(instruction: String){

}
class BecimalRepresentation(instruction: String){

}