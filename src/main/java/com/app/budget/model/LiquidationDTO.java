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
public class LiquidationDTO {

    private Long id;

    private Long idEngagement;

    @Size(max = 255)
    private String bonEngagment;

    private Long idExercice;

    @Size(max = 255)
    private String ligneBudgetaire;

    private Long idPlanFondActivite;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "80.08")
    private BigDecimal montantEngage;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "18.08")
    private BigDecimal montantLiquide;

    @Size(max = 255)
    private String status;

    private Long engagementId;

}
