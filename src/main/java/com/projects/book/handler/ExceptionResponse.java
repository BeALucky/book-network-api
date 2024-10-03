package com.projects.book.handler;

import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
	private Integer businessErrorCode;
	private String businessErrorDescription;
	private String error;
	private Set<String> validationErrors;
	private Map<String, String> errors;
	
}
