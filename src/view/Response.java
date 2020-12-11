package view;

public class Response<T> {
    private T data;
    private boolean success;
    private String resultMessage;

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
