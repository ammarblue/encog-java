/*
 * Encog(tm) Workbench v3.0
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2011 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.dialogs.training.methods;

import java.awt.Frame;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.training.DialogMaxError;

/**
 * A dialog box that inputs for the parameters to use with
 * the backpropagation training method.
 * @author jheaton
 *
 */
public class InputBackpropagation extends DialogMaxError {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private DoubleField learningRate;
	private DoubleField momentum;


	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputBackpropagation() {
		super(true);
		setTitle("Train Backpropagation");
		addProperty(this.learningRate = new DoubleField("learning rate","Learning Rate",true,-1,-1));
		addProperty(this.momentum = new DoubleField("momentum","Momentum",true,-1,-1));
		render();
		this.learningRate.setValue(0.7);
		this.momentum.setValue(0.0);

		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());
	}


	public DoubleField getLearningRate() {
		return learningRate;
	}


	public DoubleField getMomentum() {
		return momentum;
	}

	

}
