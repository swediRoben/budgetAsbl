package com.app.budget.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.app.budget.service.ExerciceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the dateDebut value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ExerciceDateDebutUnique.ExerciceDateDebutUniqueValidator.class
)
public @interface ExerciceDateDebutUnique {

    String message() default "{Exists.exercice.dateDebut}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ExerciceDateDebutUniqueValidator implements ConstraintValidator<ExerciceDateDebutUnique, LocalDate> {

        private final ExerciceService exerciceService;
        private final HttpServletRequest request;

        public ExerciceDateDebutUniqueValidator(final ExerciceService exerciceService,
                final HttpServletRequest request) {
            this.exerciceService = exerciceService;
            this.request = request;
        }

        @Override
        public boolean isValid(final LocalDate value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(exerciceService.get(Long.parseLong(currentId)).getDateDebut())) {
                // value hasn't changed
                return true;
            }
            return !exerciceService.dateDebutExists(value);
        }

    }

}
