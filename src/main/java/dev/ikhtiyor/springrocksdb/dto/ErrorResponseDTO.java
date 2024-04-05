package dev.ikhtiyor.springrocksdb.dto;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private String message;
    private Integer httpStatus;
    private String httpStatusText;
    private String endpoint;
}
