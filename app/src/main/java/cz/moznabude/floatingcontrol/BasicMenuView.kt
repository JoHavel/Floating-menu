package cz.moznabude.floatingcontrol

import android.content.Context
import android.util.AttributeSet
import android.view.View

class BasicMenuView: EnumMenuView<BasicMenuView.BasicDirections> {

    constructor(context: Context): super(BasicMenuView.BasicDirections::class.java, context)
    constructor(context: Context, attrs: AttributeSet?): super(BasicMenuView.BasicDirections::class.java, context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(BasicMenuView.BasicDirections::class.java, context, attrs, defStyleAttr)


    override fun setMenuLayout() {
        View.inflate(context, R.layout.menu, this)
    }

    enum class BasicDirections: EnumDirections{
        Right, Left, Top, Bottom,
        TopRight, TopLeft, BottomRight, BottomLeft;

        override fun isInCoordinates(coordinates: Coordinates): Boolean {
            return true
        }
    }
}