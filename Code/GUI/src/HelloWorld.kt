import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import javafx.stage.Stage
import tornadofx.*


class HelloWorld : View("USCLEGv8 - An ARMv8 Emulator") {
    override val root = vbox(){
        menubar {
            menu("Archivo") {
                item("Abrir", "ctrl + a").action { openFile() }
                item("Nuevo", "ctrl + n")
                item("Guardar", "ctrl + g").action { saveFile() }
            }
            menu("Ejecutar") {
                item("Paso a paso")
                item("Detener")
            }
            menu("Ayuda"){
                item("Manuel de usuario")
            }
        }
        gridpane {
            row {
                button("Abrir")
                button("Guardar")
                button("Ejecutar")
                button("Paso a paso")
                button("Detener")
            }

        }
        squeezebox {
            fold("Código", expanded = true) {
                textarea {
                    prefRowCount = 25
                    vgrow = Priority.ALWAYS
                }
            }
            fold("Registros", expanded = true) {
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

}

fun saveFile(){
    val fileChooser = FileChooser()
    val selectedFile = fileChooser.showSaveDialog(null)
}