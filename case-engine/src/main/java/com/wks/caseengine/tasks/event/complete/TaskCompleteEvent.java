/*
 * WKS Platform - Open-Source Project
 * 
 * This file is part of the WKS Platform, an open-source project developed by WKS Power.
 * 
 * WKS Platform is licensed under the MIT License.
 * 
 * © 2021 WKS Power. All rights reserved.
 * 
 * For licensing information, see the LICENSE file in the root directory of the project.
 */
package com.wks.caseengine.tasks.event.complete;

import org.springframework.context.ApplicationEvent;

public class TaskCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public TaskCompleteEvent(final TaskCompleteEventObject source) {
		super(source);
	}

}