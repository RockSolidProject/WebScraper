import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import db.DbUtil
import ui.App

fun main() = application {
    if (!DbUtil.prepareDb()){
        exitApplication()
        return@application
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "AdminPanel",
        icon = painterResource("icons/admin_panel.png")
    ) {
        App()
    }
}
