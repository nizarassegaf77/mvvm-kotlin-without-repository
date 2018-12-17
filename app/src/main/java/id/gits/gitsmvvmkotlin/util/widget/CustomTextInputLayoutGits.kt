package id.gits.gitsmvvmkotlin.util.widget

import android.content.Context
import android.content.res.TypedArray
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import java.util.ArrayList
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.util.widget.validator.*
import kotlin.math.max

open class CustomTextInputLayoutGits : TextInputLayout {
    private var mValidators: MutableList<BaseValidator>? = null
    var isSpinner = false
    /**
     * @return if auto-validation is enabled
     */
    var isAutoValidated = false
        private set
    /**
     * @return if auto-trimming input field value is enabled
     */
    var isAutoTrimEnabled = false
        private set
    private var mErrorAlwaysEnabled = true

    /**
     * Return a String value of the input field.
     *
     * This method will remove white spaces if auto-trimming is enabled.
     *
     * @return the value of the input field
     * @see .autoTrimValue
     */
    val value: String
        get() = if (isAutoTrimEnabled)
            editText!!.text.toString().trim { it <= ' ' }
        else
            editText!!.text.toString()

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
        initializeCustomAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
        initializeCustomAttrs(context, attrs)
    }

    private fun initialize() {
        if (!isInEditMode) {
            mValidators = ArrayList<BaseValidator>()
            this.post {
                if (!editText!!.isInEditMode)
                    initializeTextWatcher()
            }
        }
    }

    private fun requestFocusSpinner(view: View) {
        view.isFocusableInTouchMode = true
        if (view.requestFocus()) {
            //scroll.clearFocus()
            view.requestFocus()
        }
        view.isFocusableInTouchMode = false
    }

    private fun initializeCustomAttrs(context: Context, attrs: AttributeSet) {
        if (!isInEditMode) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable
                    .CustomTextInputLayoutGits, 0, 0)

            try {
                isAutoTrimEnabled = typedArray.getBoolean(R.styleable.CustomTextInputLayoutGits_autoTrim,
                        false)
                isAutoValidated = typedArray.getBoolean(R.styleable
                        .CustomTextInputLayoutGits_autoValidate, false)
                mErrorAlwaysEnabled = typedArray.getBoolean(R.styleable
                        .CustomTextInputLayoutGits_errorAlwaysEnabled, true)

                initRequiredValidation(context, typedArray)
                initLengthValidation(context, typedArray)
                initRegexValidation(context, typedArray)
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun initRequiredValidation(context: Context, typedArray: TypedArray) {
        if (typedArray.getBoolean(R.styleable.CustomTextInputLayoutGits_isRequired, false)) {
            var errorMessage = typedArray.getString(R.styleable
                    .CustomTextInputLayoutGits_requiredValidationMessage)
            if (errorMessage == null)
                errorMessage = "This field is required."
            addValidator(RequiredValidator(errorMessage))
        }
    }

    private fun initLengthValidation(context: Context, typedArray: TypedArray) {
        val minLength = typedArray.getInteger(R.styleable.CustomTextInputLayoutGits_minLength,
                LengthValidator.LENGTH_ZERO)
        val maxLength = typedArray.getInteger(R.styleable.CustomTextInputLayoutGits_maxLength,
                LengthValidator.LENGTH_INDEFINITE)

        if (!(minLength == LengthValidator.LENGTH_ZERO && maxLength == LengthValidator
                        .LENGTH_INDEFINITE)) {
            var errorMessage = typedArray.getString(R.styleable
                    .CustomTextInputLayoutGits_lengthValidationMessage)

            if (errorMessage == null) {
                if (minLength == LengthValidator.LENGTH_ZERO) {
                    errorMessage = ("Input length must be less than or\n" +
                            "        equal to" + maxLength)
                } else if (maxLength == LengthValidator.LENGTH_INDEFINITE) {
                    errorMessage = "Input length must be greater than\n" +
                            "        or equal to " + minLength
                } else {
                    errorMessage = "Input must have length between " + minLength + " and " + maxLength
                }
            }
            addValidator(LengthValidator(minLength, maxLength, errorMessage))
        }
    }

    private fun initRegexValidation(context: Context, typedArray: TypedArray) {
        val regex = typedArray.getString(R.styleable.CustomTextInputLayoutGits_regex)
        if (regex != null) {
            var errorMessage = typedArray.getString(R.styleable
                    .CustomTextInputLayoutGits_regexValidationMessage)
            if (errorMessage == null)
                errorMessage = "The field value does not match the\n" +
                        "        required format."
            addValidator(RegexValidator(regex, errorMessage))
        }
    }

    private fun initializeTextWatcher() {
        editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (isAutoValidated)
                    validate()
                else
                    error = null
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    /**
     * Clears all the validators associated with the [ValidatedTextInputLayout].
     */
    fun clearValidators() {
        mValidators!!.clear()
        isErrorEnabled = false
    }

    /**
     * Associates new [IValidator] with the [ValidatedTextInputLayout].
     *
     * @param pValidator new validator to be associated with the input field
     */
    fun addValidator(pValidator: BaseValidator) {
        mValidators!!.add(pValidator)
    }

    /**
     * Enable or disable auto-validation for the [ValidatedTextInputLayout].
     *
     * @param flag flag to enable or disable auto-validation
     */
    fun autoValidate(flag: Boolean) {
        isAutoValidated = flag
    }

    /**
     * Enable or disable auto-trimming of the value of the input field for the
     * [ValidatedTextInputLayout].
     *
     * Enabling will remove any leading and trailing white space from the value of field.
     *
     * Caution: You may not want to enable this in case of password fields.
     *
     * @param flag flag to enable or disable auto-trimming of value
     */
    fun autoTrimValue(flag: Boolean) {
        isAutoTrimEnabled = flag
    }

    /**
     * Flag to always enable error  for the [TextInputLayout]
     *
     * Enabled by default.
     *
     * Disabling will remove the error space below the Field once the validate method returns
     * true.
     * .
     *
     * @param errorAlwaysEnabled flag to set error-space as always enabled
     */
    fun setErrorAlwaysEnabled(errorAlwaysEnabled: Boolean) {
        mErrorAlwaysEnabled = errorAlwaysEnabled
    }

    /**
     * Return a boolean which can be used to check the validity of the field.
     *
     * @return boolean indicating if the field is valid or not.
     */
    fun validate(): Boolean {
        var status = true
        val text = value
        for (validator in mValidators!!) {
            if (!validator.validate(text)) {
                isErrorEnabled = true
                error = validator.errorMessage
                status = false
                if (isSpinner) {
                    requestFocusSpinner(this)
                }
                break
            } else {
                error = null
            }
        }
        if (status && !mErrorAlwaysEnabled) isErrorEnabled = false
        return status
    }
}