package cz.moznabude.floatingcontrol

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout

class MenuView: GridLayout {

    init {
        View.inflate(context, R.layout.menu, this)
    }

    fun disappear() { this.visibility = View.GONE }
    fun appear() { this.visibility = View.VISIBLE }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)
}