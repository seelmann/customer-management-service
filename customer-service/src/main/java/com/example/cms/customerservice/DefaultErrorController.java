
package com.example.cms.customerservice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultErrorController implements ErrorController {

    @Value("${error.path:/error}")
    private String errorPath;

    @Override
    public String getErrorPath() {
        return this.errorPath;
    }

    @RequestMapping(value = "${error.path:/error}")
    public ResponseEntity<String> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
