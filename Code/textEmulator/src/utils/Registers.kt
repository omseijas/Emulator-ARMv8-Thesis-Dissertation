package utils

import java.lang.Exception

//Registers are size 64 bits

/*En esta clase crearemos los registros a utilizar. En primer lugar crearemos una lista de 32 registros,
* en la que introduciremos el nombre del registro, el estado (por defecto inactivo) y el valor (por defecto
* vacío. */
class Registers {
    val listofRegisters = arrayOfNulls<Register>(32)

    constructor() {

        for(i in 0 until  listofRegisters.size-1){ //We only set the 31 first, as the register 32 has a different form
            var registerToAdd = Register("X" +i,false,"0")
            listofRegisters.set(i,registerToAdd)
        }
        var registerToAdd = Register("XZR",false,"0")
        listofRegisters.set(listofRegisters.size-1,registerToAdd)
    }
    fun setValueRegister(name: String, status: Boolean, value: String){
        var iterationValue = 0
        for ( i in 0 until  listofRegisters.size){
            if(listofRegisters.get(i)!!.getNameRegister() == name){
                iterationValue = i
                    break
                }
            }

        if(iterationValue == listofRegisters.size-1){
            throw Exception("No se puede introducir un valor distinto de cero en " +
                    "XZR\n")
        }
        listofRegisters.set(iterationValue, Register(name, status, value))
    }

    fun getValueRegister(name: String): String {
        val value = listofRegisters.filter {it!!.getNameRegister() == name}.get(0)?.valueRegister
        return value!!
    }


}

class Register() {


    var statusRegister = false
    var valueRegister = ""
    var nameRegisters = ""

    constructor(name: String, status: Boolean, value: String) : this() {
        statusRegister = status
        valueRegister = value
        nameRegisters = name
    }
    fun getNameRegister(): String {
        return nameRegisters
    }

    fun setNameRegister(s: String) {
        nameRegisters = s
    }

}