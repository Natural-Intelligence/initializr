package com.naturalint.springinitializr;

import io.spring.initializr.generator.BasicProjectRequest;
import io.spring.initializr.generator.ProjectGenerator;
import io.spring.initializr.generator.ProjectRequest;
import io.spring.initializr.metadata.DependencyMetadataProvider;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.util.TemplateRenderer;
import io.spring.initializr.web.project.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.io.IOException;
import java.util.Map;

public class CustomMainController extends MainController {
    private static final Logger log = LoggerFactory.getLogger(CustomMainController.class);

    public CustomMainController(InitializrMetadataProvider metadataProvider, TemplateRenderer templateRenderer, ResourceUrlProvider resourceUrlProvider, ProjectGenerator projectGenerator, DependencyMetadataProvider dependencyMetadataProvider) {
        super(metadataProvider, templateRenderer, resourceUrlProvider, projectGenerator, dependencyMetadataProvider);
    }


    @Override
    protected void renderHome(Map<String, Object> model) {
        super.renderHome(model);
//        model.put("")
    }

    @RequestMapping("/starter_1.zip")
    @ResponseBody
    public ResponseEntity<byte[]> springZip(CustomProjectRequest basicRequest)
            throws IOException {
        ResponseEntity<byte[]> responseEntity = super.springZip(basicRequest);
        return responseEntity;
    }


    @ModelAttribute
    public CustomProjectRequest projectRequest(
            @RequestHeader Map<String, String> headers) {
        CustomProjectRequest request = new CustomProjectRequest();
        request.getParameters().putAll(headers);
        request.initialize(this.metadataProvider.get());
        return request;
    }
}
