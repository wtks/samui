package work.wtks.samui

import io.nats.client.Connection
import io.nats.client.Nats
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import work.wtks.samui.AirconState.*
import java.awt.*
import java.util.logging.LogManager
import kotlin.system.exitProcess

object Main : NativeKeyListener {

    lateinit var nc: Connection

    var toggle = false

    @JvmStatic
    fun main(args: Array<String>) {
        LogManager.getLogManager().reset()

        if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.registerNativeHook()
            } catch (e: NativeHookException) {
                e.printStackTrace()
                exitProcess(1)
            }
        }

        GlobalScreen.addNativeKeyListener(this)

        nc = Nats.connect("nats://192.168.100.2:4222")

        try {
            SystemTray.getSystemTray().add(TrayIcon(
                Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource("1f601.png")),
                "samui",
                PopupMenu().apply {
                    add(MenuItem("Exit").apply {
                        addActionListener {
                            exitProcess(0)
                        }
                    })
                }
            ).apply {
                isImageAutoSize = true
            })
        } catch (e: AWTException) {
            e.printStackTrace()
            exitProcess(2)
        }
    }

    override fun nativeKeyTyped(p0: NativeKeyEvent) = Unit
    override fun nativeKeyPressed(p0: NativeKeyEvent) = Unit

    override fun nativeKeyReleased(p0: NativeKeyEvent) {
        if (p0.keyCode == NativeKeyEvent.VC_F23) {
            if (toggle) {
                nc.publish(
                    "work.wtks.home.aircon", AirconState(
                        mode = Mode.OFF
                    ).toBody()
                )
            } else {
                nc.publish(
                    "work.wtks.home.aircon", AirconState(
                        mode = Mode.HEATER,
                        temp = 26
                    ).toBody()
                )
            }
            toggle = !toggle
        }
    }

}