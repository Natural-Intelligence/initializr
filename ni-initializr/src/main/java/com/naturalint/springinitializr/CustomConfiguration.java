package com.naturalint.springinitializr;

import io.spring.initializr.generator.ProjectGenerator;
import io.spring.initializr.metadata.DependencyMetadataProvider;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.util.TemplateRenderer;
import io.spring.initializr.web.project.MainController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@Configuration
public class CustomConfiguration {

    @Bean
    public MainController initializrMainController(
            InitializrMetadataProvider metadataProvider,
            TemplateRenderer templateRenderer,
            ResourceUrlProvider resourceUrlProvider,
            ProjectGenerator projectGenerator,
            DependencyMetadataProvider dependencyMetadataProvider) {
        return new CustomMainController(metadataProvider, templateRenderer,
                resourceUrlProvider, projectGenerator, dependencyMetadataProvider);
    }


    @Bean
    public ProjectGenerator projectGenerator() {
        return new CustomProjectGenerator();
    }
}
