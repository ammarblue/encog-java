/*
  * Encog Neural Network and Bot Library for Java v1.x
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

package org.encog.neural.persist.persistors;

import javax.xml.transform.sax.TransformerHandler;

import org.encog.matrix.Matrix;
import org.encog.neural.NeuralNetworkError;
import org.encog.neural.networks.layers.SOMLayer;
import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.neural.persist.Persistor;
import org.encog.util.XMLUtil;
import org.encog.util.NormalizeInput.NormalizationType;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class SOMLayerPersistor implements Persistor {
	
	public static String NORM_TYPE_MULTIPLICATIVE = "MULTIPLICATIVE";
	public static String NORM_TYPE_Z_AXIS = "Z_AXIS";
	
	public EncogPersistedObject load(Element layerNode)
			{
		String str = layerNode.getAttribute("neuronCount");
		String normType = layerNode.getAttribute("normalization");
		int neuronCount = Integer.parseInt(str);

		SOMLayer layer;
		
		if( normType.equals(SOMLayerPersistor.NORM_TYPE_MULTIPLICATIVE))
			layer = new SOMLayer(neuronCount, NormalizationType.MULTIPLICATIVE);
		else if( normType.equals(SOMLayerPersistor.NORM_TYPE_Z_AXIS))
			layer = new SOMLayer(neuronCount, NormalizationType.Z_AXIS);
		else
			layer = null;
		
		Element matrixElement = XMLUtil.findElement(layerNode, "weightMatrix");
		if (matrixElement != null) {
			Element e = XMLUtil.findElement(matrixElement, "Matrix");
			Persistor persistor = EncogPersistedCollection
					.createPersistor("Matrix");
			Matrix matrix = (Matrix) persistor.load(e);
			layer.setMatrix(matrix);
		}
		return layer;
	}

	public void save(EncogPersistedObject object, TransformerHandler hd)
	{

		try {
			SOMLayer layer = (SOMLayer) object;

			AttributesImpl atts = new AttributesImpl();
			atts.addAttribute("", "", "neuronCount", "CDATA", ""
					+ layer.getNeuronCount());
			
			String normType = null;
			
			if( layer.getNormalizationType()== NormalizationType.MULTIPLICATIVE )
				normType = SOMLayerPersistor.NORM_TYPE_MULTIPLICATIVE;
			else if( layer.getNormalizationType()== NormalizationType.Z_AXIS )
				normType = SOMLayerPersistor.NORM_TYPE_Z_AXIS;
			
			if( normType==null)
			{
				throw new NeuralNetworkError("Unknown normalization type");
			}
			
			atts.addAttribute("", "", "normalization", "CDATA", ""
					+ normType );
			
			hd.startElement("", "", layer.getClass().getSimpleName(), atts);
			

			atts.clear();

			if (layer.hasMatrix()) {
				if (layer.getMatrix() instanceof EncogPersistedObject) {
					Persistor persistor = EncogPersistedCollection
							.createPersistor(layer.getMatrix().getClass()
									.getSimpleName());
					atts.clear();
					hd.startElement("", "", "weightMatrix", atts);
					persistor.save(layer.getMatrix(), hd);
					hd.endElement("", "", "weightMatrix");
				}
			}

			hd.endElement("", "", layer.getClass().getSimpleName());
		} catch (SAXException e) {
			throw new NeuralNetworkError(e);
		}
	}

}
