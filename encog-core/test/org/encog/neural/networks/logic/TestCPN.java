/*
 * Encog(tm) Artificial Intelligence Framework v2.3
 * Java Version, Unit Tests
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

package org.encog.neural.networks.logic;

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.cpn.TrainInstar;
import org.encog.neural.networks.training.cpn.TrainOutstar;
import org.encog.neural.pattern.CPNPattern;
import org.junit.Assert;

import junit.framework.TestCase;

public class TestCPN extends TestCase {
	
	public static final int WIDTH = 11;
	public static final int HEIGHT = 11;

	public static final String[][] PATTERN1 =  { { 
		"           ",
        "           ",
        "     O     ",
        "     O     ",
        "    OOO    ",
        "    OOO    ",
        "    OOO    ",
        "   OOOOO   ",
        "   OOOOO   ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "        O  ",
        "       O   ",
        "     OOO   ",
        "    OOO    ",
        "   OOO     ",
        " OOOOO     ",
        "OOOOO      ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "           ",
        "  OO       ",
        "  OOOOO    ",
        "  OOOOOOO  ",
        "  OOOOO    ",
        "  OO       ",
        "           ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "OOOOO      ",
        " OOOOO     ",
        "   OOO     ",
        "    OOO    ",
        "     OOO   ",
        "       O   ",
        "        O  ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "   OOOOO   ",
        "   OOOOO   ",
        "    OOO    ",
        "    OOO    ",
        "    OOO    ",
        "     O     ",
        "     O     ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "      OOOOO",
        "     OOOOO ",
        "     OOO   ",
        "    OOO    ",
        "   OOO     ",
        "   O       ",
        "  O        ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "           ",
        "       OO  ",
        "    OOOOO  ",
        "  OOOOOOO  ",
        "    OOOOO  ",
        "       OO  ",
        "           ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "  O        ",
        "   O       ",
        "   OOO     ",
        "    OOO    ",
        "     OOO   ",
        "     OOOOO ",
        "      OOOOO",
        "           ",
        "           "  } };

	String[][] PATTERN2 = { { 
		"           ",
        "           ",
        "     O     ",
        "     O     ",
        "     O     ",
        "    OOO    ",
        "    OOO    ",
        "    OOO    ",
        "   OOOOO   ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "     O     ",
        "     O     ",
        "    O O    ",
        "    O O    ",
        "    O O    ",
        "   O   O   ",
        "   O   O   ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "           ",
        "     O     ",
        "    OOO    ",
        "    OOO    ",
        "    OOO    ",
        "   OOOOO   ",
        "           ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "           ",
        "           ",
        "     O     ",
        "     O     ",
        "     O     ",
        "    OOO    ",
        "           ",
        "           ",
        "           "  },

      { "           ",
        "  O        ",
        "     O     ",
        "     O     ",
        "    OOO    ",
        "    OO     ",
        "    OOO   O",
        "    OOOO   ",
        "   OOOOO   ",
        "           ",
        "       O   "  },

      { "           ",
        "           ",
        "     O     ",
        "     O     ",
        "    OOO    ",
        "    OOO    ",
        "    OOO    ",
        "   OOOOO   ",
        "   OOOOO   ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "       O   ",
        "      O    ",
        "    OOO    ",
        "    OOO    ",
        "   OOO     ",
        "  OOOOO    ",
        " OOOOO     ",
        "           ",
        "           "  },

      { "           ",
        "           ",
        "        O  ",
        "       O   ",
        "     OOO   ",
        "    OOO    ",
        "   OOO     ",
        " OOOOO     ",
        "OOOOO      ",
        "           ",
        "           "  } };
	public static final double HI = 1;
	public static final double LO = 0;
	
	
	private double[][] input1;
	private double[][] input2;
	private double[][] ideal1;
	
	private int inputNeurons;
	private int instarNeurons;
	private int outstarNeurons;

	public void prepareInput()
	{
		int n,i,j;
		
		this.inputNeurons = WIDTH * HEIGHT;
		this.instarNeurons = PATTERN1.length;
		this.outstarNeurons = 2;
		
		this.input1 = new double[PATTERN1.length][this.inputNeurons];
		this.input2 = new double[PATTERN2.length][this.inputNeurons];
		this.ideal1 = new double[PATTERN1.length][this.instarNeurons];

		  for (n=0; n<PATTERN1.length; n++) {
		    for (i=0; i<HEIGHT; i++) {
		      for (j=0; j<WIDTH; j++) {
		    	input1[n][i*WIDTH+j] = (PATTERN1[n][i].charAt(j) == 'O') ? HI : LO;
		    	input2[n][i*WIDTH+j] = (PATTERN2[n][i].charAt(j) == 'O') ? HI : LO;
		      }
		    }
		  }
		  normalizeInput();
		  for (n=0; n<PATTERN1.length; n++) {
		    this.ideal1[n][0] = Math.sin(n * 0.25 * Math.PI);
		    this.ideal1[n][1] = Math.cos(n * 0.25 * Math.PI);
		  }

	}
	
	public double sqr(double x)
	{
		return x*x;
	}
	
	
	void normalizeInput()
	{
	  int  n,i;
	  double length1, length2;

	  for (n=0; n<PATTERN1.length; n++) {
	    length1 = 0;
	    length2 = 0;
	    for (i=0; i<this.inputNeurons; i++) {
	      length1  += sqr(this.input1[n][i]);
	      length2 += sqr(this.input2[n][i]);
	    }
	    length1  = Math.sqrt(length1);
	    length2 = Math.sqrt(length2);
	    
	    for (i=0; i<this.inputNeurons; i++) {
	      input1[n][i] /= length1;
	      input2[n][i] /= length2;
	    }
	  }
	}

	public BasicNetwork createNetwork()
	{		
        CPNPattern pattern = new CPNPattern();
        pattern.setInputNeurons( this.inputNeurons );
        pattern.setInstarCount( this.instarNeurons );
        pattern.setOutstarCount( this.outstarNeurons );

        BasicNetwork network = pattern.generate();
        network.reset();

        return network;
	}
	
	public void trainInstar(BasicNetwork network,NeuralDataSet training)
	{
		Train train = new TrainInstar(network,training,0.1);
		for(int i=0;i<2;i++) {
			train.iteration();
		} 
	}
	
	public void trainOutstar(BasicNetwork network,NeuralDataSet training)
	{
		Train train = new TrainOutstar(network,training,0.1);
		for(int i=0;i<2;i++) {
			train.iteration();
		} 
	}
	
	public NeuralDataSet generateTraining(double[][] input,double[][] ideal)
	{
		NeuralDataSet result = new BasicNeuralDataSet(input,ideal);
		return result;
	}
	
	public double determineAngle(NeuralData angle)
	{
		double result;

		  result = ( Math.atan2(angle.getData(0), angle.getData(1)) / Math.PI) * 180;
		  if (result < 0)
		    result += 360;

		  return result;
	}
	
	public void test(BasicNetwork network,String[][] pattern,double[][] input)
	{
		int[] require = {
				0,
				45,
				90,
				135,
				180,
				225,
				270,
				315 };
		
		for(int i=0;i<pattern.length;i++)
		{
			NeuralData inputData = new BasicNeuralData(input[i]);
			NeuralData outputData = network.compute(inputData);
			int angle = (int)determineAngle(outputData);
			Assert.assertEquals(require[i],angle);
		}
	}
	
	public void testCPNNetwork()
	{
		prepareInput();
		normalizeInput();
		BasicNetwork network = createNetwork();
		NeuralDataSet training = generateTraining(this.input1,this.ideal1);
		trainInstar(network,training);
		trainOutstar(network,training);
		test(network,PATTERN1,this.input1);
		
	}
}
