package com.naturalint.springinitializr;

import io.spring.initializr.generator.ProjectRequest;

public class CustomProjectRequest extends ProjectRequest {

    public boolean isDocker() {
        return docker;
    }

    public void setDocker(boolean docker) {
        this.docker = docker;
    }

    private boolean docker;


}
