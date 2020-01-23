import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Side
import javafx.scene.control.TextArea
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.paint.Color.*
import javafx.scene.text.FontWeight
import javafx.scene.text.FontWeight.*
import javafx.stage.FileChooser
import javafx.stage.Stage
import tornadofx.*
import java.io.File
import java.io.PrintWriter
import ARMv8_Architecture.Instruction
import utils.Registers
var registers: Registers = Registers()

var stringInput = mutableListOf<String>()
var textArea = TextArea()
val contador = SimpleStringProperty()

class HelloWorld : View("USCLEGv8 - An ARMv8 Emulator") {
    val controller : USCLEGController by inject()
    override val root = vbox(){
        importStylesheet("C:\\Users\\Zezão\\Desktop\\Manu\\Trabajo-de-fin-de-grado\\Code\\GUI\\tyle.css")
        menubar {
            menu("Archivo") {
                item("Abrir", "Ctrl+A").action { openFile() }
                item("Guardar", "Ctrl+G").action { saveFile() }
            }
            menu("Ejecutar") {
                item("Ejecutar","Ctrl+E")
                item("Paso a paso")
                item("Detener")
            }
            menu("Ayuda"){
                item("Manuel de usuario")
            }
        }
        gridpane {
            row {
                button("Abrir").action { openFile() }
                button("Guardar").action { saveFile() }
                button("Ejecutar").action{controller.increment()}
                button("Paso a paso")
                button("Detener")
            }

        }
        drawer(Side.LEFT, true) {
            item("Código", expanded = true) {
                textarea {
                    textArea = this
                    prefRowCount = 25
                    vgrow = Priority.ALWAYS
  //                 bind(observableListOf(stringInput))
 //                  textProperty().bind(.textPropecty())

                }
            }
            item("Registros", expanded = true) {
                stackpane {
                    listview(controller.values)
                    label(){
                        bind(contador)

                    }
                }
            }
        }

    }
}

class USCLEGController:Controller(){
    fun increment(){
        contador
//        values.set(0,values.get(0)+"x")
        println(registers.getValues())
    }

    val values = FXCollections.observableArrayList(registers.getValues()   )

}

class HelloWorldApp : App(HelloWorld::class) {
    override fun start(stage: Stage) {
        stage.minHeight = 720.0
        stage.minWidth = 1080.0

        super.start(stage)
    }
}

fun openFile(){
    val fileChooser = FileChooser()
    val selectedFile = fileChooser.showOpenDialog(null)
    if (selectedFile != null) {

        File(selectedFile.absolutePath).forEachLine {
            stringInput.add(
                it
            )
        }
        stringInput.forEach {textArea.appendText(it+"\n")  }

    }
    else {
        println("File selection cancelled.");
    }

}

fun saveFile(){
    val fileChooser = FileChooser()
    val extensionFilterArmv8 = FileChooser.ExtensionFilter("Archivos TXT (*.txt)", "*.txt")
    fileChooser.extensionFilters.add(extensionFilterArmv8)
    val selectedFile = fileChooser.showSaveDialog(null)
    if (selectedFile != null) {
        var writer = PrintWriter(selectedFile)
        writer.println(textArea.text)
        writer.close()
    }
    else {
        println("File selection cancelled.");
    }
}