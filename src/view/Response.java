package view;

public class Response<T> {
    private final T data;
    private final boolean success;
    private final String resultMessage;

    public Response(T data, boolean success, String resultMessage) {
        this.data = data;
        this.success = success;
        this.resultMessage = resultMessage;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
