package callcenter.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

public class StatusCodeException extends HttpStatusCodeException {

    public StatusCodeException(HttpStatus statusCode) {
        super(statusCode);
    }

    public StatusCodeException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public StatusCodeException(HttpStatus statusCode, String statusText, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseBody, responseCharset);
    }

    public StatusCodeException(HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }
}
