package org.payroll.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponseDto {
    private boolean success;
    private String message;
    private Object data;
}