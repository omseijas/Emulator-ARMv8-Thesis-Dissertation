package ARMv8_Architecture

import registers
var memArmv8 = arrayOfNulls<Byte>(4096) //Memory is in bytes.

fun getValueFromPosition(name:String, off: String):Byte{
    var position = registers.getValueRegister(name)

    var offset = off.removePrefix("#").toInt() / 8
    position += offset
    return memArmv8.get(position.toInt())!!
}
open class Memory(){

    fun initMemory(){
        for (initializeMemoryAt0 in 0 until memArmv8.size){
            memArmv8[initializeMemoryAt0]=0
        }
    }

    fun showStatus(){
        for (initializeMemoryAt0 in 0 until memArmv8.size){
            if(memArmv8[initializeMemoryAt0]!! != 0.toByte()){
                println(memArmv8[initializeMemoryAt0].toString() + " in $initializeMemoryAt0")
            }
        }
    }
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
                val instruction = InstructionEOR(newString)
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
class InstructionEOR(instruction: List<String>):Instruction(){
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        var s1 = registers.getValueRegister(source1)
        var s2 = registers.getValueRegister(source2)
        val sub = s1.toInt() xor s2.toInt()

        println("NEW VALUE: $sub IN $dest")
        return sub.toString()
    }
}

class InstructionB(instruction: List<String>):Instruction(){}
class InstructionBR(instruction: List<String>):Instruction(){}
class InstructionCBZ(instruction: List<String>):Instruction(){}
class InstructionCBNZ(instruction: List<String>):Instruction(){}
class InstructionBCond(instruction: List<String>):Instruction(){}
//LDUR/STUR allow accessing 32/64-bit values when they are not aligned to the size of the operand.
// For example, a 32-bit value stored at address 0x52.
class InstructionLDUR(instruction: List<String>) : Instruction(){
    override fun runInstruction(dest: String, source1: String, source2: String): String {
        val value = getValueFromPosition(source1,source2)

        registers.setValueRegister(dest,true,value.toString())
        println("NEW VALUE: $value IN $dest")
        return value.toString()
    }
}
//As of now, I wont touch the offset.
class InstructionSTUR(instruction: List<String>) : Instruction(){
    override fun runInstruction(source: String, dest: String, offset: String): String {
        var position= registers.getValueRegister(source).toInt()
        position += offset.removePrefix("#").toInt() / 8
        memArmv8[position] = registers.getValueRegister(source).toByte()
        return source +" in "+dest
    }
}

class DecimalRepresentation(instruction: String){

}
class BecimalRepresentation(instruction: String){

}