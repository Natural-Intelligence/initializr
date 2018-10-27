package com.naturalint.springinitializr;

import io.spring.initializr.generator.ProjectGenerator;
import io.spring.initializr.generator.ProjectRequest;

import java.io.File;
import java.util.Map;

public class CustomProjectGenerator extends ProjectGenerator {

    @Override
    protected File generateProjectStructure(ProjectRequest request,
                                            Map<String, Object> model) {
        boolean dockerized = false;
        if (request instanceof CustomProjectRequest && ((CustomProjectRequest) request).isDocker()) {
            dockerized = true;
        }
        if (dockerized) {
            model.put("docker", true);
        }
        File file = super.generateProjectStructure(request, model);
        if (dockerized) {
            File baseDir = new File(file, request.getBaseDir());
            write(new File(baseDir, "Dockerfile"), "Dockerfile", model);

        }

        return file;
    }

}
