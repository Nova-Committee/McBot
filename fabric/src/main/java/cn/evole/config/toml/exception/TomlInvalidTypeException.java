package cn.evole.config.toml.exception;

public class TomlInvalidTypeException extends RuntimeException {
    public TomlInvalidTypeException() {
    }

    public TomlInvalidTypeException(String message) {
        super(message);
    }

    public TomlInvalidTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TomlInvalidTypeException(Throwable cause) {
        super(cause);
    }
}
