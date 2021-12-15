package com.daangn;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Collections;

public class PersonHandler {

    @NonNull
    public ServerResponse listPeople(ServerRequest request) {
        // request processing...
        return ServerResponse.ok().body(Collections.emptyList());
    }

    @NonNull
    public ServerResponse createPerson(ServerRequest request) {
        // request processing...
        return ServerResponse.ok().body(Collections.emptyMap());
    }

    @NonNull
    public ServerResponse getPerson(ServerRequest request) {
        // request processing...
        return ServerResponse.ok().body(Collections.emptyMap());
    }

}
