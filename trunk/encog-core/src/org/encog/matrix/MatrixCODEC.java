/*
  * Encog Neural Network and Bot Library for Java
  * http://www.heatonresearch.com/encog/
  * http://code.google.com/p/encog-java/
  * Copyright 2008, Heaton Research Inc., and individual contributors as indicated
  * by the @authors tag. See the copyright.txt in the distribution for a
  * full listing of individual contributors.
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
package org.encog.matrix;

import org.encog.neural.feedforward.FeedforwardLayer;
import org.encog.neural.feedforward.FeedforwardNetwork;



/**
 * MatrixCODEC: The matrix CODEC can encode or decode a matrix
 * to/from an array of doubles.  This is very useful when the 
 * neural network must be looked at as an array of doubles for
 * genetic algorithms and simulated annealing.
 * 
 * @author Jeff Heaton
 * @version 2.1
 */
public class MatrixCODEC {

	/**
	 * Use an array to populate the memory of the neural network.
	 * 
	 * @param array
	 *            An array of doubles.
	 */
	public static void arrayToNetwork(final Double array[],
			final FeedforwardNetwork network) {

		// copy data to array
		int index = 0;

		for (final FeedforwardLayer layer : network.getLayers()) {

			// now the weight matrix(if it exists)
			if (layer.getNext() != null) {
				index = layer.getMatrix().fromPackedArray(array, index);
			}
		}
	}

	/**
	 * Convert to an array. This is used with some training algorithms that
	 * require that the "memory" of the neuron(the weight and threshold values)
	 * be expressed as a linear array.
	 * 
	 * @return The memory of the neuron.
	 */
	public static Double[] networkToArray(final FeedforwardNetwork network) {
		int size = 0;

		// first determine size
		for (final FeedforwardLayer layer : network.getLayers()) {
			// count the size of the weight matrix
			if (layer.hasMatrix()) {
				size += layer.getMatrixSize();
			}
		}

		// allocate an array to hold
		final Double result[] = new Double[size];

		// copy data to array
		int index = 0;

		for (final FeedforwardLayer layer : network.getLayers()) {

			// now the weight matrix(if it exists)
			if (layer.getNext() != null) {

				final Double matrix[] = layer.getMatrix().toPackedArray();
				for (int i = 0; i < matrix.length; i++) {
					result[index++] = matrix[i];
				}
			}
		}

		return result;
	}

}
