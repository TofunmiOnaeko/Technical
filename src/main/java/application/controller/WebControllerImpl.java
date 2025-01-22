package application.controller;

import lombok.extern.slf4j.Slf4j;
import application.service.FileService;
import application.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
@Controller
@RequestMapping("Processing")
public class WebControllerImpl {

    @Autowired
    FileService fileService;

    @PostMapping("/ImportFile")
    public ResponseEntity<Response> importFile(@RequestBody String file) {
        String mappedFile = fileService.mapFileToContent(file);

        if (("Error mapping file to content").equals(mappedFile)) {
            return new ResponseEntity<>(new Response("Error mapping file to content, file is null or empty"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new Response(mappedFile), HttpStatus.OK);
        }
    }

}
