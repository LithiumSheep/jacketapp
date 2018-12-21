package com.lithiumsheep.jacketapp.arch;

import android.support.annotation.NonNull;

/**
 * A simple wrapper around {@param T} meant for view models
 * <p>
 * static factory methods {@link #loading()}, {@link #success(Object)}, and {@link #error(String)}
 * create new Resource wrappers so they do not interfere with the state of the data wrapped.
 * Observers in ui contexts can then observe one Resource rather than listen to multiple
 * LiveData objects, one for data and another for error message
 * <p>
 * Many thanks to the following links that helped inspire this pattern.  This class is nearly directly
 * ripped from the Kotlin implementation in google code samples
 *
 * @param <T> The Type of the data that this Resource is wrapping
 * @see <a href="https://old.reddit.com/r/androiddev/comments/8u2d12/how_do_you_propagate_errors_back_up_the/">Reddit discussion</a>
 * @see <a href="https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt">Github sample</a>
 */
public class Resource<T> {

    enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    private Status status;
    private T data;
    private String message;

    private Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }

    public boolean isLoading() {
        return status == Status.LOADING;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(@NonNull String message) {
        return new Resource<>(Status.ERROR, null, message);
    }

    public static <T> Resource<T> error(@NonNull Throwable throwable) {
        return new Resource<>(Status.ERROR, null, throwable.getMessage());
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING, null, null);
    }
}
