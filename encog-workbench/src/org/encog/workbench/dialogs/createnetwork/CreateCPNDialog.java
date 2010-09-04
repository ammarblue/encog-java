/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class CreateCPNDialog extends EncogPropertiesDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1916684369370397010L;
	
	private IntegerField inputCount;
	private IntegerField instarCount;
	private IntegerField outstarCount;
	
	public CreateCPNDialog(Frame owner) {
		super(owner);
		setTitle("Create CPN Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.inputCount = new IntegerField("input neurons","Input Neuron Count",true,1,100000));
		addProperty(this.instarCount = new IntegerField("instar neurons","Instar Neuron Count",true,1,100000));
		addProperty(this.outstarCount = new IntegerField("outstar neurons","Outstar Neuron Count",true,1,100000));
		render();
	}

	public IntegerField getInputCount() {
		return inputCount;
	}

	public IntegerField getInstarCount() {
		return instarCount;
	}

	public IntegerField getOutstarCount() {
		return outstarCount;
	}
	
	



}