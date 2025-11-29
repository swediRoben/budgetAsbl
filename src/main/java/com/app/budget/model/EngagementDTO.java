package com.app.budget.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EngagementDTO {

    private Long id;

    @Size(max = 255)
    private String bonEngagement;

    private Long idExercice;

    @Size(max = 255)
    private String ligneBudgetaire;

    private Long idPlanFondActivite;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "40.08")
    private BigDecimal montantPrevision;

    private Boolean validation;

    @Size(max = 255)
    private String motif;

    @Size(max = 255)
    private String status;

}
