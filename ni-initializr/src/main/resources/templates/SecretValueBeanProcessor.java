package {{packageName}}.config.secrets;

import com.ni.SecretManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

public class SecretValueBeanProcessor implements BeanPostProcessor {
    private static final String AWS_REGION = "us-east-1";

    @Autowired
    private SecretManager secretManager;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            // make the field accessible if defined private
            ReflectionUtils.makeAccessible(field);
            SecretValue annotation = field.getAnnotation(SecretValue.class);
            if (annotation != null) {
                try {
                    String credentialsKey = annotation.value();
                    secretManager.init(credentialsKey);
                    Map<String, String> credentials = secretManager.getCredentials(credentialsKey);
                    String value = credentials.get(credentialsKey);
                    field.set(bean, value);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return bean;
    }

    @Bean
    SecretManager secretManager(
            @Value("${service_name}") String serviceName,
            @Value("${environment}") String environment
    ) throws Exception {
        return new SecretManager(AWS_REGION, serviceName, environment);
    }
}

