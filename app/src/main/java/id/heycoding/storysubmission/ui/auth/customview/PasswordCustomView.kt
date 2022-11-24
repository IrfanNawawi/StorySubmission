package id.heycoding.storysubmission.ui.auth.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged

class PasswordCustomView : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        doOnTextChanged { text, _, _, _ ->
            if (text?.length!! < 6) {
                error = "Password harus 6 karakter"
            }
        }

        //Show Hide Password
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    transformationMethod = HideReturnsTransformationMethod.getInstance()
                    return@setOnTouchListener false
                }
                MotionEvent.ACTION_UP -> {
                    transformationMethod = PasswordTransformationMethod.getInstance()
                    return@setOnTouchListener false
                }
            }
            false
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}