import ARMv8_Architecture.Instruction
import ARMv8_Architecture.Memory
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
import javafx.stage.FileChooser
import javafx.stage.Screen
import javafx.stage.Stage
import tornadofx.*
import utils.Registers
import java.io.File
import java.io.PrintWriter


var registers: Registers = Registers()
var memory: Memory = Memory()
var stringInput = mutableListOf<String>()
var textArea = TextArea()
var checked = false
var instructionExecuted = SimpleStringProperty()

class ARMv8View : View("USCLEGv8 - An ARMv8 Emulator") {

    val controller: USCLEGController by inject()
    override val root = vbox() {
        // addClass(ARMv8Style.tackyButton)
        //   children.asSequence().forEach { it.addClass(ARMv8Style.tackyButton)}
        menubar {
            addClass(ARMv8Style.tackyButton)
            menu("Archivo") {
                // addClass(ARMv8Style.tackyButton)
                item("Abrir", "Ctrl+A").action { openFile() }
                item("Guardar", "Ctrl+G").action { saveFile() }
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
            addClass(ARMv8Style.tackyButton)
            gridpaneConstraints {
                alignment = Pos.CENTER_RIGHT
            }
            row {
                button("Abrir").action { openFile() }
                button("Guardar").action { saveFile() }
                button("Ejecutar").action { controller.execute() }
                button("Paso a paso").action { controller.executeStepByStep() }
                button("Reiniciar").action { controller.stop() }

            }

        }
        splitpane(Orientation.VERTICAL) {
            gridpane() {
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
            gridpane(){
                gridpaneConstraints {
                    alignment = Pos.CENTER
                }
                addClass(ARMv8Style.tackyButton)
                row() {
                    label(instructionExecuted)
                }
            }
            hbox {
                gridpane() {
                    addClass(ARMv8Style.tackyButton)
                    gridpaneConstraints {
                        paddingLeft = 10.0
                    }

                    row() {
                        label("Memoria utilizada") {
                            setHalignment(this, HPos.CENTER)
                        }
                    }
                    row {
                        addClass(ARMv8Style.tackyButton)
                        listview(controller.memarmv8) {
                            setPrefSize(2000.0, 2000.0)
                            addClass(ARMv8Style.tackyButton)
                        }
                    }
                }
                gridpane{
                    addClass(ARMv8Style.tackyButton)
                    gridpaneConstraints {
                        paddingLeft = 10.0
                    }
                    row() {
                        label("Registros") {
                            setHalignment(this, HPos.CENTER)
                        }
                    }
                    row {

                        addClass(ARMv8Style.tackyButton)
                        listview(controller.values) {
                            setPrefSize(2000.0, 2000.0)
                            addClass(ARMv8Style.tackyButton)
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
    var memarmv8=  FXCollections.observableArrayList(memory.getStatus())
    var values = FXCollections.observableArrayList(registers.getValues())

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
            if (numberLine >= stringInput.size)
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
        val file = File("C:\\Users\\Zezão\\Desktop\\Manu\\Trabajo-de-fin-de-grado\\Code\\textEmulator\\src\\test.pdf")
        val hostServices = hostServices
        hostServices.showDocument(file.absolutePath)
    }



}

class HelloWorldApp : App(ARMv8View::class, ARMv8Style::class) {
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
        val tackyButton by cssclass()
        private val topColor = Color.RED
        private val rightColor = Color.DARKGREEN
        private val leftColor = Color.ORANGE
        private val bottomColor = Color.PURPLE
    }

    init {
        s(root, button, menu, content, contextMenu, cell, tackyButton) {
            backgroundColor += c("#121212")
        }
        s(label, button) {
            textFill = c("#FFFFFF")
            opacity = 0.87
            fontWeight = FontWeight.EXTRA_BOLD
            fontSize = 16.px
        }
        s(tackyButton, button) {
            borderColor += box(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
        }
        s(textArea, cell) {
            textFill = c("#FFF")
        }

    }
}

fun openFile() {
    val fileChooser = FileChooser()
    val selectedFile = fileChooser.showOpenDialog(null)
    if (selectedFile != null) {

        File(selectedFile.absolutePath).forEachLine {
            stringInput.add(
                it
            )
        }
        stringInput.forEach { textArea.appendText(it + "\n") }

    } else {
        println("File selection cancelled.");
    }

}

fun saveFile() {
    val fileChooser = FileChooser()
    val extensionFilterArmv8 = FileChooser.ExtensionFilter("Archivos TXT (*.txt)", "*.txt")
    fileChooser.extensionFilters.add(extensionFilterArmv8)
    val selectedFile = fileChooser.showSaveDialog(null)
    if (selectedFile != null) {
        var writer = PrintWriter(selectedFile)
        writer.println(textArea.text)
        writer.close()
    } else {
        println("File selection cancelled.");
    }
}