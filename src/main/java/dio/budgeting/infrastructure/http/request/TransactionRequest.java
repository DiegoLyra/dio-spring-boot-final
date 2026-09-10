package dio.budgeting.infrastructure.http.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransactionRequest(
    @NotBlank(message = "A descrição não pode ser vazia")
    String description,

    @NotNull(message = "O valor é obrigatório")
    BigDecimal amount,

    @NotBlank(message = "A categoria é obrigatória")
    String category
) {}