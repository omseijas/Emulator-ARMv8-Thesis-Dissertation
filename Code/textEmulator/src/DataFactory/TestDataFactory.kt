package DataFactory

import ARMv8_Architecture.Memory
import utils.ARMv8Function
import utils.Constants
import utils.Registers


class TestDataFactory {
    /* Data creation to use in the Unitary tests. */
    companion object{
        const val instructionADD = "ADDI X4, X4, X1"
        const val instructionADDI = "ADDI X4, X4, 1"
        const val instructionSUBI = "SUBI X4, X4, 1"
        const val instructionSUB = "SUB X4, X4, X1"
        const val instructionLDUR = "LDUR X1, [X4, #0]"
        const val instructionSTDUR = "STDUR X1, [X4, #8]"
        const val instructionORR = "ORR X4, X4, X1"
        const val instructionEOR = "EOR X4, X4, X1"
        const val instructionAND = "AND X4, X4, X1"
        const val instructionCBZ = "CBZ X4, test"
        const val instructionCBNZ = "CBNZ X4, test"
        const val instructionB = "B test"
        const val instructionVariable = "test: SUB X4, X4, X1"
    }
    val registers = Registers()
    val memory = Memory()
    val variables = mutableMapOf<String, ARMv8Function>()
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
}