/*import ARMv8_Architecture.Instruction
import ARMv8_Architecture.Memory
import utils.Registers
import java.io.File
import java.lang.Exception

var registers: Registers = Registers()

var stackPointerArmv8 : Int = 0




fun main(args: Array<String>) {
    var stringInput = mutableListOf<String>()
    var initializeMemoryAt0=0
    val m = Memory();
    m.initMemory()
    val i = Instruction()
    var j =0
    try {
        i.classifyAllInput(stringInput,registers)
    }
        catch(e:Exception){
            print(e.message)
        }
    val fileName = "resultado.txt"
    val fileCode = File(fileName)
    registers.listofRegisters.forEach { print("${it?.nameRegisters}:${it?.valueRegister} \n") }
    println("MEMORY")
    m.showStatus()
    //   fileCode.writeText(stringInput[0])

}
*/