package com.example.demo;

/**
 * @author Kisong
 */
public class ServiceException extends RuntimeException {

    private final ServiceError serviceError;

    /**
     * Constructs a new service exception with the service error, {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param serviceError
     */
    public ServiceException(ServiceError serviceError) {
        this(serviceError, serviceError.message());
    }

    /**
     * Constructs a new service exception with the service error, specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param serviceError instance of ServiceError
     * @param message the detail message. The detail message is saved for later retrieved
     * 	by the {@link #getMessage()} method.
     */
    public ServiceException(ServiceError serviceError, String message) {
        super(message);
        this.serviceError = serviceError;
    }

    /**
     * Constructs a new service exception with the service error, specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwable.
     *
     * @param serviceError instance of ServiceError
     * @param cause cause the cause (which is saved for later retrieval by the
     * 	{@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     * 	and indicates that the cause is nonexistent or unknown.)
     */
    public ServiceException(ServiceError serviceError, Throwable cause) {
        this(serviceError, serviceError.message(), cause);
    }

    /**
     * Constructs a new service exception with the service error, specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param serviceError instance of ServiceError
     * @param message the detail message (which is saved for later retrieval
     * 	by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     * 	{@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     * 	and indicates that the cause is nonexistent or unknown.)
     */
    public ServiceException(ServiceError serviceError, String message, Throwable cause) {
        super(message, cause);
        this.serviceError = serviceError;
    }

    /**
     * Gets the service error of this exception.
     *
     * @return instance of ServiceError
     */
    public ServiceError serviceError() {
        return serviceError;
    }

}
