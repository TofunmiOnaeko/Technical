package application.service;

import application.mapper.ContentMapper;
import application.mapper.OutputMapper;
import application.model.Content;
import application.model.Output;
import application.repository.ContentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
public class FileService {

    @Autowired
    ContentMapper contentMapper;
    @Autowired
    OutputMapper outputMapper;
    @Autowired
    ContentRepository contentRepository;
    ReadWriteLock lock = new ReentrantReadWriteLock();

    public String mapFileToContent(String file) {
        ArrayList<Content> contentList = new ArrayList<>();
        ArrayList<Output> outputList = new ArrayList<>();

        if (file != null && !file.isEmpty()) {
            try {
                Stream<String> lines = file.lines();
                lines.forEach(line -> {
                    String[] values = line.split("\\|");
                    contentList.add(contentMapper.mapArrayToContent(values));
                });
            } catch (Exception e) {
                log.error("Exception caught when mapping input file to Content class, exception: " + e);
            }
            saveContentToDB(contentList);
            outputList = outputMapper.mapToOutput(contentList);
        } else {
            log.error("Error mapping file to content, file is null or empty");
            return "Error mapping file to content";
        }

        return returnJson(outputList);
    }

    private String returnJson(ArrayList<Output> outputList) {
        String json = "";

        try {
            ObjectWriter ow = new ObjectMapper().writer();
            json = ow.writeValueAsString(outputList);
        } catch (Exception e) {
            log.error("Exception occurred when formatting output object as json, exception: " + e);
        }

        return json;
    }

    private void saveContentToDB(ArrayList<Content> contentList) {
        contentList.stream().forEach(content -> {
            try {
                lock.writeLock().lock();
                contentRepository.save(content);
            } catch (RuntimeException e) {
                log.error("unable to save content to the database");
            } finally {
                lock.writeLock().unlock();
            }
        });
    }
}
