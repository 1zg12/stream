package com.example.stream.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@Slf4j
public class RestController {

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
    public String rest(){

        StringBuilder data = new StringBuilder();
        for (int i = 0; i < 1_000_000_000; i++) {
            data.append(LocalDateTime.now().toString());//array size out of VM limite
        }

        return data.toString();
    }

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> stream(final HttpServletResponse response) {

        response.setContentType("application/zip");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename=sample.zip");

        StreamingResponseBody stream = out -> {

            final String home = System.getProperty("user.home");
            String filename = "C:\\Users\\lwpro\\OneDrive\\Documents\\OReilly.Head.First.Servlets.and.JSP.2nd.Edition.Mar.2008.pdf";
            final File directory = new File("C:\\Users\\lwpro\\Projects\\stream\\src");
            final ZipOutputStream zipOut = new ZipOutputStream(out);

            if(directory.exists() && directory.isDirectory()) {
                try {
                    for (final File file : directory.listFiles()) {
                        final InputStream inputStream=new FileInputStream(filename);
                        final ZipEntry zipEntry=new ZipEntry(file.getName());
                        zipOut.putNextEntry(zipEntry);
                        byte[] bytes=new byte[1024];
                        int length;
                        while ((length=inputStream.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                        inputStream.close();
                    }
                    zipOut.close();
                } catch (final IOException e) {
                    log.error("Exception while reading and streaming data {} ", e);
                }
            }
        };
        log.info("steaming response {} ", stream);
        return new ResponseEntity(stream, HttpStatus.OK);
    }
    @GetMapping(value = "/stream2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> stream2() {

        StreamingResponseBody stream = out -> {

            StringBuilder data = new StringBuilder();
            for (int i = 0; i < 1_000_000_000; i++) {
                out.write(LocalDateTime.now().toString().getBytes());
            }
            out.close();
        };
        log.info("steaming response {} ", stream);
        return new ResponseEntity(stream, HttpStatus.OK);
    }

    @GetMapping(value = "/stream3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> stream3() {


        StreamingResponseBody stream = out -> {

            Random randomObj = new Random(1_000_000);
            StringBuilder data = new StringBuilder();
            List<Staff> employees=  new ArrayList<>();
            for (int i = 0; i < 1_000_000; i++) {
                employees.add(new Staff("s"+i, i, "c"+i, randomObj.nextDouble()));
            }
            mapper.writeValue(out, employees);
            out.close();
        };
        log.info("steaming response {} ", stream);
        return new ResponseEntity(stream, HttpStatus.OK);
    }
    @GetMapping(value = "/stream4", produces = MediaType.APPLICATION_JSON_VALUE)
    public StreamingResponseBody stream4() {

        log.info("start streaming4...");
        return out -> {

            Random randomObj = new Random(1_000);
            List<Staff> employees=  new ArrayList<>();
            for (int i = 0; i < 1_000_000; i++) {
                employees.add(new Staff("s"+i, i, "c"+i, randomObj.nextDouble()));
            }
            mapper.writeValue(out, employees);
            out.close();
        };
    }

    record Staff(String name, int age, String country, double salary){//one record is 70B

    }
}
