import javafx.geometry.Side
import javafx.scene.control.TextArea
import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import javafx.stage.Stage
import tornadofx.*
import java.io.File

var stringInput = mutableListOf<String>()
var textArea = TextArea()

class HelloWorld : View("USCLEGv8 - An ARMv8 Emulator") {
    override val root = vbox(){
        menubar {
            menu("Archivo") {
                item("Abrir", "Ctrl+A").action { openFile() }
           //     item("Nuevo", "Ctrl+N")
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
                button("Ejecutar")
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
                    label("Registros")
                }
            }
        }

    }
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
    val selectedFile = fileChooser.showSaveDialog(null)
}