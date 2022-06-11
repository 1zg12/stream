package com.example.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@Slf4j
public class TestDirectory {
    @Test
    public void test(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
    }
    @Test
    public void test2(){
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("application.yml");
        File file = new File(resource.getFile());
        log.info(file.toString());
        assertThat(file).exists();
    }
}
