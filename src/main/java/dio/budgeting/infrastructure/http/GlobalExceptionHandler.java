package dio.budgeting.infrastructure.http;

import dio.budgeting.infrastructure.http.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação nos Parâmetros de Entrada",
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ErrorResponseDTO> handleAiApiException(RestClientResponseException ex) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        String message = "Falha na comunicação com o serviço de IA. Verifique as credenciais ou a disponibilidade da API.";

        if (ex.getStatusCode().value() == 401) {
            message = "Chave da API de IA não configurada ou inválida (Unauthorized).";
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex.getStatusCode().value() == 429) {
            message = "Limite de requisições excedido no provedor de IA (Cota esgotada).";
            status = HttpStatus.TOO_MANY_REQUESTS;
        }

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                status.value(),
                "Erro na Integração com IA",
                message
        );

        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno no Servidor",
                "Ocorreu um erro ao processar sua solicitação: " + ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}