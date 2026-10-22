package com.products.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResposeDTO {

	private String apipath;
	private HttpStatus statusCode;
	private String errorMessage;
	private LocalDateTime errorTime;
}
