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
package org.encog.workbench.util;

import java.awt.Font;

/**
 * Define the fonts used by Encog.
 * @author jheaton
 */
public class EncogFonts {
	
	private static EncogFonts instance;
	private Font codeFont;
	private Font bodyFont;
	private Font headFont;
	private Font titleFont;
	
	private EncogFonts()
	{		
		this.codeFont = new Font("monospaced", 0, 12);
		this.bodyFont = new Font("serif", 0, 12);
		this.headFont = new Font("serif", Font.BOLD, 12);
		this.titleFont = new Font("serif", Font.BOLD, 16);
	}
	
	public static EncogFonts getInstance()
	{
		if( instance==null )
			instance = new EncogFonts();
		return instance;
	}

	/**
	 * @return the codeFont
	 */
	public Font getCodeFont() {
		return codeFont;
	}

	/**
	 * @return the bodyFont
	 */
	public Font getBodyFont() {
		return bodyFont;
	}

	/**
	 * @return the headFont
	 */
	public Font getHeadFont() {
		return headFont;
	}

	/**
	 * @return the titleFont
	 */
	public Font getTitleFont() {
		return titleFont;
	}
	
	
	
}
