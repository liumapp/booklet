package com.liumapp.design.pattern.builder;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class PersonTest {

    @Test
    public void newBuilder() {
        Person.Builder personBuilder = new Person.Builder();
        Person person = personBuilder.create();
        log.info(JSON.toJSONString(person));
    }
}