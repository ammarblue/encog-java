/*
 * Encog(tm) Core Unit Tests v3.0 - Java Version
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
package org.encog.neural.data.buffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.buffer.BufferedNeuralDataSet;
import org.encog.neural.networks.XOR;

public class TestBufferedNeuralDataSet extends TestCase {

	public static final String FILENAME = "xor.bin";
	
	
	public void testBufferData() throws Exception 
	{
		new File(FILENAME).delete();
		BufferedNeuralDataSet set = new BufferedNeuralDataSet(new File(FILENAME));
		set.beginLoad(2, 1);
		for(int i=0;i<XOR.XOR_INPUT.length;i++) {
			BasicMLData input = new BasicMLData(XOR.XOR_INPUT[i]);
			BasicMLData ideal = new BasicMLData(XOR.XOR_IDEAL[i]);
			set.add(input,ideal);
		}
		set.endLoad();
		
		XOR.testXORDataSet(set);
		
	}	
}
