package com.daangn;

import org.springframework.context.support.GenericApplicationContext;

public class FunctionalBeanDefinitions {

    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean(MyRepository.class);
        applicationContext.registerBean(MyService.class, () -> {
            return new MyService(applicationContext.getBean(MyRepository.class));
        });
    }

    static class MyRepository {

    }

    static class MyService {
        final MyRepository repository;
        public MyService(MyRepository repository) {
            this.repository = repository;
        }
    }

}
