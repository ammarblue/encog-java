/*
 * Encog Artificial Intelligence Framework v2.x
 * Java Version
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
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
package org.encog.persist.persistors;

import java.util.Map;

import org.encog.neural.activation.ActivationCompetitive;
import org.encog.parse.tags.read.ReadXML;
import org.encog.parse.tags.write.WriteXML;
import org.encog.persist.EncogPersistedObject;
import org.encog.persist.Persistor;

/**
 * The Encog persistor used to persist the ActivationCompetitivePersistor class.
 */
public class ActivationCompetitivePersistor  implements Persistor {

	/**
	 * The winners attribute.
	 */
	public static final String ATTRIBUTE_WINNERS = "winners";	
	
	/**
	 * Load the specified Encog object from an XML reader.
	 * 
	 * @param in
	 *            The XML reader to use.
	 * @return The loaded object.
	 */
	public EncogPersistedObject load(ReadXML in) {
		
		final Map<String, String> map = in.readPropertyBlock();
		final int winners = Integer.parseInt(map
				.get(ActivationCompetitivePersistor.ATTRIBUTE_WINNERS));
		return new ActivationCompetitive(winners);
	}

	/**
	 * Save the specified Encog object to an XML writer.
	 * 
	 * @param obj
	 *            The object to save.
	 * @param out
	 *            The XML writer to save to.
	 */
	public void save(EncogPersistedObject obj, WriteXML out) {
		final ActivationCompetitive c = (ActivationCompetitive) obj;
		out.beginTag(c.getClass().getSimpleName());
		out.addProperty(ActivationCompetitivePersistor.ATTRIBUTE_WINNERS, c
				.getMaxWinners());
		out.endTag();
		
	}

}