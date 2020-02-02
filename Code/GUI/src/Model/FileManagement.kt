package Model

import javafx.stage.FileChooser
import stringInput
import textArea
import java.io.File
import java.io.PrintWriter

/*
* En este archivo se encuentra la clase de gestion de ficheros del modelo.
*
* */
class FileManagement{
    fun openFile() {
        val fileChooser = FileChooser()
        val file = fileChooser.showOpenDialog(null)
        if (file != null) {
            File(file.absolutePath).forEachLine {
                stringInput.add(
                    it
                )
            }
            stringInput.forEach { textArea.appendText(it + "\n") }
        }
    }

    fun saveFile() {
        val fileChooser = FileChooser()
        val filter = FileChooser.ExtensionFilter("Archivos TXT (*.txt)", "*.txt")
        fileChooser.extensionFilters.add(filter)
        val file = fileChooser.showSaveDialog(null)
        if (file != null) {
            var writer = PrintWriter(file)
            writer.println(textArea.text)
            writer.close()
        }
    }
}