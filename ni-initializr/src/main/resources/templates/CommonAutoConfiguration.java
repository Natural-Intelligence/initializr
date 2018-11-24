package {{packageName}}.config.secrets;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SecretValueBeanProcessor.class})
public class CommonAutoConfiguration {
}
