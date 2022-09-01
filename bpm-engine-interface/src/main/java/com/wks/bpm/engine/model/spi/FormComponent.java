package com.wks.bpm.engine.model.spi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author victor.franca
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormComponent {
	
	private String id;
	private String key;
	private String type;
	
	private String text;
	private String label;

}