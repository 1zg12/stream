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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@Slf4j
public class StressController {

    ObjectMapper mapper = new ObjectMapper();

    Random randomObj = new Random(1_000_000);
    List<Staff> employees = Collections.synchronizedList(new ArrayList<>());

    {
        IntStream.range(0, 10_000_000)
                .parallel()
                .forEach(i ->
                        employees.add(new Staff("s" + i, i, "c" + i, randomObj.nextDouble()))
                );
    }

    @GetMapping(value = "/stress", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> stress() {


        StreamingResponseBody stream = out -> {
            log.info("start writing the employees");
            mapper.writeValue(out, employees);
            out.close();
        };
        log.info("steaming response {} ", stream);
        return new ResponseEntity(stream, HttpStatus.OK);
    }


}
