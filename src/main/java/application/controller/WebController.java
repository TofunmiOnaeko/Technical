package application.controller;

import application.model.Content;
import application.model.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public interface WebController {

    public ResponseEntity<Response> importFile(@RequestBody Content content);

}
