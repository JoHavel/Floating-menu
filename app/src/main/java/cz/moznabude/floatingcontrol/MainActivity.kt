package cz.moznabude.floatingcontrol

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs
import kotlin.math.sign

class MainActivity : AppCompatActivity() {

    private val handlerOfLongTouch = Handler()
    private var isActive = false
    private var isOpened = false
    private lateinit var menu: MenuView
    private val runnable = Runnable {
        Toast.makeText(applicationContext, "pressed once", Toast.LENGTH_SHORT).show()
        isActive = false
        println(c.x - f.x)
        println(c.y - f.y)
        menu.appear()
        menu.x = c.x - menu.width/2
        menu.y = c.y - menu.height/2
        isOpened = true
    }

    private val f = Coordinates(0f, 0f)
    private val c = Coordinates(0f, 0f)
    //private lateinit var screenSize: Coordinates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu = floating_menu
        menu.x = -Float.MAX_VALUE
        menu.y = -Float.MAX_VALUE

        //screenSize = Coordinates(screen.width.toFloat(), screen.height.toFloat())

        screen.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                isActive = true
                handlerOfLongTouch.postDelayed(runnable, 1000)
                f.x = event.x
                f.y = event.y
            }

            if (isActive) {
                fun stop() {
                    handlerOfLongTouch.removeCallbacks(runnable)
                    isActive = false
                }
                if (event.actionMasked == MotionEvent.ACTION_MOVE) {
                    c.x = event.x
                    c.y = event.y
                    if (abs(c.x - f.x) > 100 || abs(c.y - f.y) > 100) {
                        stop()
                    }
                }
                if (event.actionMasked == MotionEvent.ACTION_UP || event.actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
                    stop()
                }
            }

            if (event.actionMasked == MotionEvent.ACTION_UP && isOpened) {
                menu.disappear()
                isOpened = false
                c.x = event.x
                c.y = event.y
                val direction = (c.y - f.y) / (c.x - f.x)
                if(sign(c.x-f.x) > 0) {
                    when(direction) {
                        in (-0.5f).rangeTo(0.5f) -> println("next")
                        else -> println("Error")
                    }
                } else {

                }
            }
            true
        }
    }


}

private data class Coordinates(var x: Float, var y: Float)
