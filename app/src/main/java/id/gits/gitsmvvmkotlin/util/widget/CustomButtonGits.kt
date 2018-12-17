package id.gits.gitsmvvmkotlin.util.widget

import id.gits.gitsmvvmkotlin.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.text.Layout
import android.text.StaticLayout
import android.databinding.adapters.TextViewBindingAdapter.setTextSize
import android.databinding.adapters.TextViewBindingAdapter.setTextSize
import android.text.TextPaint
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth


class CustomButtonGits : RelativeLayout {

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var array: TypedArray
    private lateinit var customButton: CustomButton
    var mProgressBarSize = 0
    var isShowLoading = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {

        array = context.obtainStyledAttributes(attrs, R.styleable.CustomButtonGits)

        isClickable = true

        customButton = CustomButton(context, attrs)

        val paramsContainer = RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        )

        customButton.layoutParams = paramsContainer

        paramsContainer.addRule(RelativeLayout.CENTER_IN_PARENT)

        addView(customButton)

        initLoader()

        addView(progressBar)

        initTextview()

        addView(textView)

    }

    fun initTextview() {
        val paramTextView = RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        )

        paramTextView.addRule(RelativeLayout.CENTER_IN_PARENT)

        textView = TextView(customButton.context, null)
        textView.setTextColor(array.getColor(R.styleable.CustomButtonGits_progressColor, resources.getColor(android.R.color.white)))
        textView.setText(array.getString(R.styleable.CustomButtonGits_text))
        textView.layoutParams = paramTextView
        textView.gravity = Gravity.CENTER
        textView.visibility = View.VISIBLE
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { customButton.onTouchEvent(it) }
        return super.onTouchEvent(event)
    }

    fun initLoader() {
        progressBar = ProgressBar(customButton.context, null)

        mProgressBarSize = array.getInt(R.styleable.CustomButtonGits_progressBarSize, 1)

        progressBar.apply {

            //setBackgroundColor(array.getColor(R.styleable.CustomButtonGits_progressColor, resources.getColor(android.R.color.white)))

            customButton.measure(0, 0)

            var pbSize = 0

            if (mProgressBarSize == 0) {
                pbSize = customButton.measuredHeight - 16
            } else {
                pbSize = customButton.measuredHeight / 2
            }

            indeterminateDrawable?.setColorFilter(
                    array.getColor(R.styleable.CustomButtonGits_progressColor, resources.getColor(android.R.color.white)),
                    PorterDuff.Mode.SRC_ATOP
            )

            val params = RelativeLayout.LayoutParams(
                    pbSize,
                    pbSize
            )

            params.addRule(RelativeLayout.CENTER_IN_PARENT)

            progressBar.layoutParams = params
        }

        progressBar.visibility = View.GONE
    }

    /**
     * Hides loader and shows text on button
     */
    fun hideLoading() {
        isShowLoading = false
        //customButton.hideLoading()
        textView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    /**
     * Shows loading animation
     */
    fun showLoading() {
        isShowLoading = true
        //customButton.showLoading()
        textView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    class CustomButton : TextView {

        private var mWidth: Int = 0
        private var mHeight: Int = 0
        private var mPressedColor: Int = 0
        private lateinit var mTextColor: ColorStateList
        private var mUnPressedColor: Int = 0
        private var mRoundRadius: Int = 0
        private var mBtnRadius: Int = 0
        private var mShapeType: Int = 0
        private var mPaint: Paint? = null
        private var mRectF: RectF? = null
        var isLoading = false

        constructor(context: Context) : super(context) {}

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
            init(context, attrs)
        }

        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
            init(context, attrs)
        }

        @SuppressLint("CustomViewStyleable", "PrivateResource")
        private fun init(context: Context, attrs: AttributeSet) {
            super.setTextAppearance(context, android.R.style.Widget_DeviceDefault_Light_TextView)

            this.isClickable = false

            if (isInEditMode) {
                return
            }

            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButtonGits)

            mPressedColor = typedArray.getColor(
                    R.styleable.CustomButtonGits_pressedColor,
                    resources.getColor(android.R.color.holo_blue_bright)
            )

            mUnPressedColor = typedArray.getColor(
                    R.styleable.CustomButtonGits_unpressedColor,
                    resources.getColor(android.R.color.holo_blue_dark)
            )

            mShapeType = typedArray.getInt(R.styleable.CustomButtonGits_shapeType, 1)

            mRoundRadius = typedArray.getDimensionPixelSize(
                    R.styleable.CustomButtonGits_roundRadius, 0
            )

            /*mTextColor = typedArray.getColorStateList(
                    R.styleable.CustomButtonGits_textColor).withAlpha(255)*/

            typedArray.recycle()

            mPaint = Paint()
            mPaint!!.color = mUnPressedColor
            mPaint!!.style = Paint.Style.FILL
            mPaint!!.isAntiAlias = true
            this.setWillNotDraw(false)
            this.isDrawingCacheEnabled = true
            this.setBackgroundColor(resources.getColor(android.R.color.transparent))
            //this.setTextColor(resources.getColor(android.R.color.transparent))

            gravity = Gravity.CENTER

            mRectF = RectF()

        }

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)
            mWidth = w
            mHeight = h
            mBtnRadius = mWidth / 2
        }

        /**
         * Hides loader and shows text on button
         */
        fun hideLoading() {
            //setText(text)
            isLoading = false
            //this.setTextColor(mTextColor)
            this.invalidate()
        }

        /**
         * Shows loading animation and hide text on button
         */
        fun showLoading() {
            //setText("")
            isLoading = true
            this.setTextColor(resources.getColor(android.R.color.transparent))
            this.invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            if (mPaint == null) {
                return
            }

            //mPaint?.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mPaint!!.alpha = 255
            //mPaint!!.alpha = HALF_ALPHA

            /*if (mShapeType == 0) {// draw cirle button
                canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), mBtnRadius.toFloat(), mPaint!!)
            }else*/
            if (mShapeType == 1) {
                mRectF!!.set(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
                canvas.drawRoundRect(mRectF!!, mRoundRadius.toFloat(), mRoundRadius.toFloat(), mPaint!!)
            } else {// draw rectangle button
                mRectF!!.set(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
                canvas.drawRoundRect(mRectF!!, mHeight.toFloat(), mHeight.toFloat(), mPaint!!)
            }

        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mPaint!!.color = mPressedColor
                    invalidate()
                }

                MotionEvent.ACTION_UP -> {
                    mPaint!!.color = mUnPressedColor
                    invalidate()
                }
            }
            return super.onTouchEvent(event)
        }

        companion object {

            private val HALF_ALPHA = 127
        }

    }


}
