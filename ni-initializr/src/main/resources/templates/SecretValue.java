package {{packageName}}.config.secrets;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecretValue {
    String value() default "";
}
