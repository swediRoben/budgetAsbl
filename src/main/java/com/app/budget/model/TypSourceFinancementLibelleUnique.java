package com.app.budget.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.app.budget.service.TypSourceFinancementService;
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
 * Validate that the libelle value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = TypSourceFinancementLibelleUnique.TypSourceFinancementLibelleUniqueValidator.class
)
public @interface TypSourceFinancementLibelleUnique {

    String message() default "{Exists.typSourceFinancement.libelle}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class TypSourceFinancementLibelleUniqueValidator implements ConstraintValidator<TypSourceFinancementLibelleUnique, String> {

        private final TypSourceFinancementService typSourceFinancementService;
        private final HttpServletRequest request;

        public TypSourceFinancementLibelleUniqueValidator(
                final TypSourceFinancementService typSourceFinancementService,
                final HttpServletRequest request) {
            this.typSourceFinancementService = typSourceFinancementService;
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
            if (currentId != null && value.equalsIgnoreCase(typSourceFinancementService.get(Long.parseLong(currentId)).getLibelle())) {
                // value hasn't changed
                return true;
            }
            return !typSourceFinancementService.libelleExists(value);
        }

    }

}
