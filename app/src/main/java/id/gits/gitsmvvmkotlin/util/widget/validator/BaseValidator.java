package id.gits.gitsmvvmkotlin.util.widget.validator;

import android.support.annotation.NonNull;


/**
 * Base Validator class to inherit from for custom validation.
 */
public abstract class BaseValidator implements IValidator {

    /**
     * Error message that will be displayed if validation fails.
     * <p>This field must be set via constructor of the Custom Validation class.</p>
     */
    private String mErrorMessage;

    private ValidationCallback mCallback;

    /**
     * Sole constructor. (For invocation by subclass constructors)
     *
     * @param pErrorMessage error message to display if validation fails
     */
    protected BaseValidator(@NonNull String pErrorMessage) {
        setErrorMessage(pErrorMessage);
    }

    protected BaseValidator(@NonNull String errorMessage, ValidationCallback callback) {
        mErrorMessage = errorMessage;
        mCallback = callback;
    }

    /**
     * Also call the callback method if {@link ValidationCallback} is provided
     *
     * @param pText value associated with the input field
     * @return validity of the field
     */
    public boolean validate(String pText) {
        boolean status = isValid(pText);

        if (mCallback != null)
            mCallback.onValidation(status);

        return status;
    }

    /**
     *
     * @param pText value associated with the input field
     * @return validity of the field
     */
    @Override
    public abstract boolean isValid(String pText);

    /**
     * Sets the error message to display if the validation fails.
     *
     * @param pErrorMessage error message to display if validation fails
     */
    @Override
    public void setErrorMessage(@NonNull String pErrorMessage) {
        mErrorMessage = pErrorMessage;
    }

    /**
     * Get the error message associated with the {@link BaseValidator}
     *
     * @return error message
     */
    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setCallback(ValidationCallback callback) {
        mCallback = callback;
    }
}
