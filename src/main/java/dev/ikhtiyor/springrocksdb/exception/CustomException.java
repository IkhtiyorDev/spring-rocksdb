package dev.ikhtiyor.springrocksdb.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
