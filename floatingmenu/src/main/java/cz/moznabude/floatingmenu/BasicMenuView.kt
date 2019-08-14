package cz.moznabude.floatingmenu

import android.content.Context
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.menu.view.*
import kotlin.math.PI
import kotlin.math.atan2

class BasicMenuView: EnumMenuView<BasicMenuView.BasicDirections> {

    constructor(context: Context): super(BasicMenuView.BasicDirections::class.java, context)
    constructor(context: Context, attrs: AttributeSet?): super(BasicMenuView.BasicDirections::class.java, context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(BasicMenuView.BasicDirections::class.java, context, attrs, defStyleAttr)


    override fun setMenuLayout() {
        View.inflate(context, R.layout.menu, this)
        buttons[BasicDirections.Right] = MenuButton(right_button) {}
        buttons[BasicDirections.TopRight] = MenuButton(top_right) {}
        buttons[BasicDirections.Top] = MenuButton(top_button) {}
        buttons[BasicDirections.TopLeft] = MenuButton(top_left) {}
        buttons[BasicDirections.Left] = MenuButton(left_button) {}
        buttons[BasicDirections.BottomLeft] = MenuButton(bottom_left) {}
        buttons[BasicDirections.Bottom] = MenuButton(bottom_button) {}
        buttons[BasicDirections.BottomRight] = MenuButton(bottom_right) {}
    }

    fun example() {
        BasicDirections.values().forEach {
            setText(it, it.toString())
            setFunction(it) { println(it.toString()) }
        }
    }

    enum class BasicDirections(private val angle: ClosedFloatingPointRange<Float>): EnumDirections{
        BottomLeft(-pi1+pi8..-pi2 - pi8),
        Bottom(-pi2-pi8..-pi2+pi8),
        BottomRight(-pi2+pi8..-pi8),
        Right(-pi8..pi8),
        TopRight(pi8..pi2-pi8),
        Top(pi2-pi8..pi2+pi8),
        TopLeft(pi2+pi8..pi1 - pi8),
        Left((1f..1f)) {
            override fun isInCoordinates(coordinates: Coordinates): Boolean {
                val a = atan2(coordinates.y, coordinates.x)
                return (a > pi1 - pi8) || (a < - (pi1 - pi8))
            }
        };

        override fun isInCoordinates(coordinates: Coordinates): Boolean =
            (atan2(-coordinates.y, coordinates.x)) in angle
    }
}

private const val pi8 = PI.toFloat()/8f
private const val pi2 = PI.toFloat()/2f
private const val pi1 = PI.toFloat()