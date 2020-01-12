import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.stage.Stage
import tornadofx.*
import tornadofx.Stylesheet.Companion.menu

class HelloWorld : View("USCLEGv8 - An ARMv8 Emulator") {
    override val root = vbox(){
        menubar {
            menu("Archivo") {
                item("Abrir", "ctrl + a")
                item("Nuevo", "ctrl + n")
                item("Guardar", "ctrl + g")
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
