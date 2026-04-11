package lap8.Bai3;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom Annotation @Developer
 * Áp dụng cho class để mô tả thông tin developer
 */
@Retention(RetentionPolicy.RUNTIME) // Có thể đọc tại runtime
@Target(ElementType.TYPE) // Áp dụng cho class
public @interface Developer {
    String name();
    String version() default "1.0";
}
