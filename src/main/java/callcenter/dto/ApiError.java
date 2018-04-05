package callcenter.dto;

import lombok.Data;

@Data
public class ApiError {

    private String statusCode;
    private String InternalStatusCode;

    public ApiError() {}

    public ApiError(String statusCode, String InternalStatusCode) {
        this.statusCode = statusCode;
        this.InternalStatusCode = InternalStatusCode;
    }
}
