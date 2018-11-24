package com.naturalint.springinitializr;

import io.spring.initializr.generator.ProjectGenerator;
import io.spring.initializr.generator.ProjectRequest;
import io.spring.initializr.metadata.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

public class CustomProjectGenerator extends ProjectGenerator {


    private static final Logger log = LoggerFactory.getLogger(CustomProjectGenerator.class);

    @Override
    protected File generateProjectStructure(ProjectRequest request,
                                            Map<String, Object> model) {
        boolean dockerized = false;
        if (request instanceof CustomProjectRequest && ((CustomProjectRequest) request).isDocker()) {
            dockerized = true;
        }
        Dependency niSecretsDependency = getMetadataProvider().get().getDependencies().get("ni-secrets");
        //add ni-secrets configuration annotation helper
        boolean niSecrets = false;

        if (dockerized) {
            model.put("docker", true);
        }
        File file = super.generateProjectStructure(request, model);
        File baseDir = new File(file, request.getBaseDir());
        if (dockerized) {
            write(new File(baseDir, "Dockerfile"), "Dockerfile", model);
        }
        File appRoot = getProjectApplicationSrcRoot(request, file);
        if (request.getResolvedDependencies().contains(niSecretsDependency)) {
            log.info("its with ni-secrets");
            File configurationFolder = new File(appRoot, "config/secrets");
            configurationFolder.mkdirs();
            write(new File(configurationFolder,"CommonAutoConfiguration.java"), "CommonAutoConfiguration.java", model);
            write(new File(configurationFolder,"SecretValue.java"), "SecretValue.java", model);
            write(new File(configurationFolder,"SecretValueBeanProcessor.java"), "SecretValueBeanProcessor.java", model);
        }
        return file;
    }

    private File getProjectApplicationSrcRoot(ProjectRequest request, File projectRoot) {
        File baseAppFolder = new File(projectRoot, request.getBaseDir());

        String codeLocation = request.getLanguage();
        File src = new File(new File(baseAppFolder, "src/main/" + codeLocation),
                request.getPackageName().replace(".", "/"));
        return src;

    }

}
