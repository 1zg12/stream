package com.example.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
public class ClassCast {

    @Test
    public void test(){
        assertThatThrownBy(()-> {
            String result = TestCast.convertInstatnceOfObject(123);
            log.info("check result {}", result);
        }).isInstanceOf(ClassCastException.class)
                .hasMessageContaining("cannot be cast to class");
    }
}

class TestCast{
    public static <T> T convertInstatnceOfObject(Object o){
        try{
            return (T)o;
        }catch (ClassCastException e){
            e.printStackTrace();
            return null;
        }

    }
}
