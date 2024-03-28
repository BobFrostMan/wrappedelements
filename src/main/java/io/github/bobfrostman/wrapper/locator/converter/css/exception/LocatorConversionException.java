package io.github.bobfrostman.wrapper.locator.converter.css.exception;

public class LocatorConversionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public LocatorConversionException() {
        super();
    }

    public LocatorConversionException(String errorMessageIn) {
        super(errorMessageIn);
        message = errorMessageIn;
    }

    public LocatorConversionException(String errorMessageIn, Throwable cause) {
        super(errorMessageIn, cause);
        message = errorMessageIn;
    }

    @Override
    public String getMessage() {
        if (message != null) {
            return message;
        } else {
            return super.getMessage();
        }
    }
}
