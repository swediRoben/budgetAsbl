package com.app.budget.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.app.budget.service.DeviseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the symbole value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = DeviseSymboleUnique.DeviseSymboleUniqueValidator.class
)
public @interface DeviseSymboleUnique {

    String message() default "{Exists.devise.symbole}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class DeviseSymboleUniqueValidator implements ConstraintValidator<DeviseSymboleUnique, String> {

        private final DeviseService deviseService;
        private final HttpServletRequest request;

        public DeviseSymboleUniqueValidator(final DeviseService deviseService,
                final HttpServletRequest request) {
            this.deviseService = deviseService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(deviseService.get(Long.parseLong(currentId)).getSymbole())) {
                // value hasn't changed
                return true;
            }
            return !deviseService.symboleExists(value);
        }

    }

}
