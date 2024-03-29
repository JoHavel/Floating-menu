package cz.moznabude.floatingmenu

import android.content.Context
import android.util.AttributeSet
import java.util.*

abstract class EnumMenuView<T>: MenuView<T> where T: Enum<T>, T: EnumMenuView.EnumDirections{
    final override val buttons: EnumMap<T, MenuButton>

    private val clazz: Class<T>

    override fun getDirection(coordinates: Coordinates): T? = clazz.enumConstants!!.first { it.isInCoordinates(coordinates) }

    constructor(clazz: Class<T>, context: Context): super(context) {
        this.clazz = clazz
        buttons = EnumMap(clazz)
        this.setMenuLayout()
    }
    constructor(clazz: Class<T>, context: Context, attrs: AttributeSet?): super(context, attrs)  {
        this.clazz = clazz
        buttons = EnumMap(clazz)
        this.setMenuLayout()
    }
    constructor(clazz: Class<T>, context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)  {
        this.clazz = clazz
        buttons = EnumMap(clazz)
        this.setMenuLayout()
    }

    interface EnumDirections: Directions {
        fun isInCoordinates(coordinates: Coordinates): Boolean
    }
}