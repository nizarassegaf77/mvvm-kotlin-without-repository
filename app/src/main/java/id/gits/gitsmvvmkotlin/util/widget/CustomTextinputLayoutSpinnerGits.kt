package id.gits.gitsmvvmkotlin.util.widget

import android.app.AlertDialog
import android.content.Context
import android.support.design.widget.TextInputLayout
import android.util.AttributeSet
import android.support.v4.content.res.TypedArrayUtils.getTextArray
import android.content.res.TypedArray
import id.gits.gitsmvvmkotlin.R
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import android.R.id.button1
import android.support.design.widget.TextInputEditText
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu


class CustomTextinputLayoutSpinnerGits : TextInputEditText {

    var items: MutableList<String> = ArrayList<String>()
    var positionSelected = 0

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    fun init(context: Context, attrs: AttributeSet) {
        //init this textinputlayout is spinnser
        //this.isSpinner = true
        setFocusable(false)
        setLongClickable(false)
        setFocusableInTouchMode(false)

        setOnClickListener {
            val popup = PopupMenu(context, this)
            var position = 0

            //Inflating the Popup using xml file
            for (e in items) {
                popup.menu.add(1, position, position, e).title = e
                position++
            }

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(
                    object : PopupMenu.OnMenuItemClickListener {
                        override fun onMenuItemClick(item: MenuItem): Boolean {
                            setText(item.getTitle().toString())
                            positionSelected = item.itemId
                            return true
                        }
                    })

            popup.show() //showing popup menu
        }

        /*val a = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTextInputLayoutSpinnerGits, 0, 0)
        try {
            val entries = a.getTextArray(R.styleable.CustomTextInputLayoutSpinnerGits_android_itemSpinners)
            if (entries != null) {

            }
        } finally {
            a.recycle()
        }*/
    }

}