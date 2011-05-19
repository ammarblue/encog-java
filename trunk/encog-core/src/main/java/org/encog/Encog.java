/*
 * Encog(tm) Core v3.0 - Java Version
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
package org.encog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.encog.plugin.EncogPluginBase;
import org.encog.plugin.EncogPluginType1;
import org.encog.plugin.system.SystemCalculationPlugin;
import org.encog.plugin.system.SystemLoggingPlugin;
import org.encog.util.concurrency.EngineConcurrency;

/**
 * Main Encog class, does little more than provide version information. Also
 * used to hold the ORM session that Encog uses to work with Hibernate.
 * 
 * @author jheaton
 */
public final class Encog {

	/**
	 * The current engog version, this should be read from the properties.
	 */
	public static final String VERSION = "3.0.0";

	/**
	 * The current engog file version, this should be read from the properties.
	 */
	private static final String FILE_VERSION = "1";

	/**
	 * The default precision to use for compares.
	 */
	public static final int DEFAULT_PRECISION = 10;

	/**
	 * Default point at which two doubles are equal.
	 */
	public static final double DEFAULT_DOUBLE_EQUAL = 0.0000000000001;

	/**
	 * The version of the Encog JAR we are working with. Given in the form
	 * x.x.x.
	 */
	public static final String ENCOG_VERSION = "encog.version";

	/**
	 * The encog file version. This determines of an encog file can be read.
	 * This is simply an integer, that started with zero and is incremented each
	 * time the format of the encog data file changes.
	 */
	public static final String ENCOG_FILE_VERSION = "encog.file.version";

	/**
	 * The instance.
	 */
	private static Encog instance;

	/**
	 * Get the instance to the singleton.
	 * 
	 * @return The instance.
	 */
	public static Encog getInstance() {
		if (Encog.instance == null) {
			Encog.instance = new Encog();
			Encog.instance.registerPlugin(new SystemCalculationPlugin());
			Encog.instance.registerPlugin(new SystemLoggingPlugin());
		}
		return Encog.instance;
	}

	/**
	 * The current calculation plugin.
	 */
	private EncogPluginType1 calculationPlugin;

	/**
	 * The current logging plugin.
	 */
	private EncogPluginType1 loggingPlugin;

	/**
	 * The plugins.
	 */
	private final List<EncogPluginBase> plugins 
		= new ArrayList<EncogPluginBase>();

	/**
	 * Get the properties as a Map.
	 * 
	 * @return The requested value.
	 */
	private final Map<String, String> properties 
		= new HashMap<String, String>();

	/**
	 * Private constructor.
	 */
	private Encog() {
		this.properties.put(Encog.ENCOG_VERSION, Encog.VERSION);
		this.properties.put(Encog.ENCOG_FILE_VERSION, Encog.FILE_VERSION);
	}

	/**
	 * @return the calculationPlugin
	 */
	public EncogPluginType1 getCalculationPlugin() {
		return this.calculationPlugin;
	}

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return this.properties;
	}

	/**
	 * Register a plugin. If this plugin provides a core service, such as
	 * calculation or logging, this will remove the old plugin.
	 * 
	 * @param plugin
	 *            The plugin to register.
	 */
	public void registerPlugin(final EncogPluginBase plugin) {
		// is it not a general plugin?
		if (plugin.getPluginServiceType() 
				!= EncogPluginType1.SERVICE_TYPE_GENERAL) {
			if (plugin.getPluginServiceType() 
					== EncogPluginType1.SERVICE_TYPE_CALCULATION) {
				// remove the old calc plugin
				if (this.calculationPlugin != null) {
					this.plugins.remove(this.calculationPlugin);
				}
				this.calculationPlugin = (EncogPluginType1) plugin;
			} else if (plugin.getPluginServiceType() 
					== EncogPluginType1.SERVICE_TYPE_LOGGING) {
				// remove the old logging plugin
				if (this.loggingPlugin != null) {
					this.plugins.remove(this.loggingPlugin);
				}
				this.loggingPlugin = (EncogPluginType1) plugin;
			}
		}
		// add to the plugins
		this.plugins.add(plugin);
	}

	/**
	 * Unregister a plugin. If you unregister the current logging or calc
	 * plugin, a new system one will be created. Encog will crash without a
	 * logging or system plugin.
	 * 
	 * @param plugin The plugin.
	 */
	public void unregisterPlugin(final EncogPluginBase plugin) {

		// is it a special plugin?
		// if so, replace with the system, Encog will crash without these
		if (plugin == this.loggingPlugin) {
			this.loggingPlugin = new SystemLoggingPlugin();
		} else if (plugin == this.calculationPlugin) {
			this.calculationPlugin = new SystemCalculationPlugin();
		}

		// remove it
		this.plugins.remove(plugin);
	}

	/**
	 * Provides any shutdown that Encog may need. Currently this shuts down the
	 * thread pool.
	 */
	public void shutdown() {
		EngineConcurrency.getInstance().shutdown(10000);
	}

	/**
	 * @return the loggingPlugin
	 */
	public EncogPluginType1 getLoggingPlugin() {
		return loggingPlugin;
	}
	
	
}