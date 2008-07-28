package org.encog.neural.feedforward.train;

import org.encog.neural.XOR;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.feedforward.FeedforwardNetwork;
import org.encog.neural.networks.feedforward.train.anneal.NeuralSimulatedAnnealing;

import junit.framework.TestCase;

public class TestAnneal extends TestCase {

	public void testAnneal() throws Throwable
	{
		NeuralDataSet trainingData = new BasicNeuralDataSet(XOR.XOR_INPUT,XOR.XOR_IDEAL);
		FeedforwardNetwork network = XOR.createThreeLayerNet();
		NeuralSimulatedAnnealing train = new NeuralSimulatedAnnealing(network,trainingData,10,2,100);	

		for (int i = 0; i < 100; i++) 
		{
			train.iteration();
			network = train.getNetwork();
		}
		
		TestCase.assertTrue("Error too high for simulated annealing",train.getError()<0.1);
		TestCase.assertTrue("XOR outputs not correct",XOR.verifyXOR(network, 0.1));

	}
	
}
