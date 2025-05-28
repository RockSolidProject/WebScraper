import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import db.DbUtil
import ui.App

fun main() = application {
    if (!DbUtil.prepareDb()){
        exitApplication()
        return@application
    }

    Runtime.getRuntime().addShutdownHook(Thread {
        DbUtil.closeDbConnection()
    })

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
