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
package org.encog.bot.spider.logging;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

/**
 * The Heaton Research Spider 
 * Copyright 2007 by Heaton Research, Inc.
 * 
 * HTTP Programming Recipes for Java ISBN: 0-9773206-6-9
 * http://www.heatonresearch.com/articles/series/16/
 * 
 * SpiderFormatter: A simple formatter to display JDK
 * logging.
 * 
 * This class is released under the:
 * GNU Lesser General Public License (LGPL)
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * @author Jeff Heaton
 * @version 1.1
 */
public class SpiderFormatter extends Formatter {

  /**
   * Called by the JDK Logging System to format log entries.
   * 
   * @param log
   *          The LogRecord to log.
   * @return The line to be logged.
   */  
  public String format(LogRecord log) {
    StringBuilder str = new StringBuilder();
    str.append(log.getLevel());
    str.append(':');
    str.append(new Date(log.getMillis()));
    str.append(':');
    str.append(log.getThreadID());
    str.append(':');
    str.append(log.getMessage());
    str.append('\n');
    if (log.getThrown() != null) {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(os);
      log.getThrown().printStackTrace(ps);
      str.append(os.toString());
      ps.close();
      try {
        os.close();
      } catch (IOException e) {
      }
    }

    return str.toString();
  }

}
