import ARMv8_Architecture.Instruction
import ARMv8_Architecture.Memory
import ARMv8_Architecture.MemoryManagement
import Model.FileManagement
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.HPos
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane.setHalignment
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.stage.Screen
import javafx.stage.Stage
import tornadofx.*
import utils.Registers
import java.io.File
import java.nio.file.Paths


var registers: Registers = Registers()
var memory: Memory = Memory()
var stringInput = mutableListOf<String>()
var textArea = TextArea()
var checked = false
var instructionExecuted = SimpleStringProperty()

//**En este fichero se encuentran las clases asociadas a la vista:
// -ARMv8View: en la que se encuentra la estructura de la GUI
// -ARMv8App: donde se inicializa la aplicaciºon
// -ARMv8Style: donde se encuentran los distintos estilos aplicados a la GUI
// En esta clase tambien se encuentra el controlador: USCLEGController, que redirige la logica al modelo.
// *//

class ARMv8View : View("USCLEGv8 - An ARMv8 Emulator") {

    val controller: USCLEGController by inject()
    override val root = vbox {
        menubar {
            addClass(ARMv8Style.custom)
            menu("Archivo") {
                // addClass(ARMv8Style.tackyButton)
                item("Abrir", "Ctrl+A").action { controller.openFile() }
                item("Guardar", "Ctrl+G").action { controller.saveFile() }
            }
            menu("Ejecutar") {
                item("Ejecutar", "Ctrl+E").action { controller.execute() }
                item("Paso a paso", "Ctrl+P").action { controller.executeStepByStep() }
                item("Reiniciar").action { controller.stop() }
            }
            menu("Ayuda") {
                item("Manual de usuario").action { controller.openHelp() }
            }
        }

        gridpane {
            addClass(ARMv8Style.custom)
            gridpaneConstraints {
                alignment = Pos.CENTER_RIGHT
            }
            row {

                button("Abrir").action { controller.openFile() }
                button("Guardar").action { controller.saveFile() }
                button("Ejecutar").action { controller.execute() }
                button("Paso a paso").action { controller.executeStepByStep() }
                button("Reiniciar").action { controller.stop() }

            }

        }
        splitpane(Orientation.VERTICAL) {
            gridpane{
                vgap = 20.0
                row() {
                    textarea {
                        textArea = this
                        prefRowCount = 200
                        setPrefSize(2000.0, 2000.0)
                        vgrow = Priority.ALWAYS
                    }
                }
            }
            gridpane{
                gridpaneConstraints {
                    alignment = Pos.CENTER
                }
                addClass(ARMv8Style.custom)
                row() {
                    label(instructionExecuted)
                }
            }
            hbox {
                gridpane {
                    addClass(ARMv8Style.custom)
                    gridpaneConstraints {
                        paddingLeft = 10.0
                    }

                    row {
                        label("Memoria utilizada") {
                            setHalignment(this, HPos.CENTER)
                        }
                    }
                    row {
                        addClass(ARMv8Style.custom)
                        listview(controller.memarmv8) {
                            setPrefSize(2000.0, 2000.0)
                            addClass(ARMv8Style.custom)
                        }
                    }
                }
                gridpane{
                    addClass(ARMv8Style.custom)
                    gridpaneConstraints {
                        paddingLeft = 10.0
                    }
                    row {
                        label("Registros") {
                            setHalignment(this, HPos.CENTER)
                        }
                    }
                    row {

                        addClass(ARMv8Style.custom)
                        listview(controller.values) {
                            setPrefSize(2000.0, 2000.0)
                            addClass(ARMv8Style.custom)
                        }
                    }
                }


            }
        }


    }


}

class USCLEGController : Controller() {
    var numberLine = 0
    var instructions = Instruction()
    var memoryManager = MemoryManagement()
    var memarmv8=  FXCollections.observableArrayList(memory.getStatus())
    var values = FXCollections.observableArrayList(registers.getValues())
    var fileManagement = FileManagement()
    fun openFile(){
        fileManagement.openFile()
    }
    fun saveFile(){
        fileManagement.saveFile()
    }
    fun execute() {
        stringInput = textArea.text.split("\n").toMutableList()
        if (stringInput.size == 0)
            alert(
                Alert.AlertType.WARNING,
                "Código vacío",
                "No es posible ejecutar código, ya que no hay código que ejecutar. Por favor, introduce el código que deseas ejecutar."
            )
        else {
            try {
                stringInput.remove("")
                instructions.classifyAllInput(stringInput, registers)
                values.setAll(registers.getValues())
                registers.getValues().forEach { println(it) }
                memarmv8.setAll(memory.getStatus())
            } catch (e: Exception) {
                alert(Alert.AlertType.ERROR, "Se ha producido un error inesperado", e.message)
            }
        }
    }

    fun executeStepByStep() {
        stringInput = textArea.text.split("\n").toMutableList()
        if (stringInput.size == 0)
            alert(
                Alert.AlertType.WARNING,
                "Código vacío",
                "No es posible ejecutar código, ya que no hay código que ejecutar. Por favor, introduce el código que deseas ejecutar."
            )
        else {
            if (numberLine == stringInput.size)
                alert(
                    Alert.AlertType.INFORMATION,
                    "Código ejecutado completamente",
                    "No es posible seguir avanzando, reinicia para volver a ejecutar el código."
                )
            else {
                if (!checked) {
                    instructions.checkForVariables(stringInput, numberLine)
                    checked = true
                    instructions.numberLine = 0
                }
                instructions.classify(stringInput[numberLine], registers)
                instructionExecuted.setValue(("Instrucción ejecutada: "+stringInput[numberLine]))
                if (numberLine == instructions.numberLine)
                    numberLine++
                else
                    numberLine = instructions.numberLine
            }
        }
        values.setAll(registers.getValues())
        memarmv8.setAll(memory.getStatus())
    }

    fun stop() {
        numberLine = 0
        registers = Registers()
        instructions = Instruction()
        instructions.reset()
        values.setAll(registers.getValues())
        memory.initMemory()
        memarmv8.setAll(memory.getStatus())

    }

    fun openHelp() {
        var file = File(Paths.get("").toAbsolutePath().toString()+"\\TFG_Orquidea_Manuela_Seijas_Salinas.pdf")
        val hostServices = hostServices
        hostServices.showDocument(file.path)
    }



}

class ARMv8App : App(ARMv8View::class, ARMv8Style::class) {
    init {
        reloadStylesheetsOnFocus()
    }

    override fun start(stage: Stage) {
        super.start(stage)
        memory.initMemory()
        stage.width = Screen.getPrimary().visualBounds.width
        stage.height = Screen.getPrimary().visualBounds.height
    }
}

class ARMv8Style : Stylesheet() {
    companion object {
        val custom by cssclass()

    }

    init {
        s(root, button, menu, content, contextMenu, cell, custom) {
            backgroundColor += c("#121212")
        }
        s(label, button) {
            textFill = c("#FFFFFF")
            opacity = 0.87
            fontWeight = FontWeight.EXTRA_BOLD
            fontSize = 16.px
        }
        s(custom, button) {
            borderColor += box(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
        }
        s(textArea, cell) {
            textFill = c("#FFF")
        }

    }
}

