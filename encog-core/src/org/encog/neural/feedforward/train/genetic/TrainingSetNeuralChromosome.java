/*
  * Encog Neural Network and Bot Library for Java v0.5
  * http://www.heatonresearch.com/encog/
  * http://code.google.com/p/encog-java/
  * 
  * Copyright 2008, Heaton Research Inc., and individual contributors.
  * See the copyright.txt in the distribution for a full listing of 
  * individual contributors.
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
  */
package org.encog.neural.feedforward.train.genetic;

import org.encog.neural.NeuralNetworkError;
import org.encog.neural.feedforward.FeedforwardNetwork;



/**
 * TrainingSetNeuralChromosome: Implements a chromosome that 
 * allows a feedforward neural network to be trained using a 
 * genetic algorithm.  The network is trained using training
 * sets.
 * 
 * The chromosome for a feed forward neural network
 * is the weight and threshold matrix.  
 */
public class TrainingSetNeuralChromosome extends
		NeuralChromosome<TrainingSetNeuralGeneticAlgorithm> {

	/**
	 * The constructor, takes a list of cities to set the initial "genes" to.
	 * 
	 * @param genetic The genetic algorithm used with this chromosome.
	 * @param network The neural network to train.
	 * @throws NeuralNetworkException Error setting up the training.
	 */
	public TrainingSetNeuralChromosome(
			final TrainingSetNeuralGeneticAlgorithm genetic,
			final FeedforwardNetwork network) throws NeuralNetworkError {
		this.setGeneticAlgorithm(genetic);
		this.setNetwork(network);

		initGenes(network.getWeightMatrixSize());
		updateGenes();
	}

	@Override
	public void calculateCost() throws NeuralNetworkError {
		// update the network with the new gene values
		this.updateNetwork();

		// update the cost with the new genes
		setCost(getNetwork().calculateError(this.getGeneticAlgorithm().getTraining()));

	}

	/**
	 * Set all genes.
	 * 
	 * @param list
	 *            A list of genes.
	 * @throws NeuralNetworkException
	 */
	@Override
	public void setGenes(final Double[] list) throws NeuralNetworkError {

		// copy the new genes
		super.setGenes(list);

		calculateCost();

	}
}
