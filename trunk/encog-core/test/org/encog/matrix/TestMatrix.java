/*
 * Encog(tm) Core v2.5 
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

package org.encog.matrix;

import org.encog.mathutil.matrices.Matrix2D;
import org.encog.mathutil.matrices.MatrixError;
import org.encog.mathutil.matrices.MatrixMath;

import junit.framework.TestCase;

public class TestMatrix extends TestCase {
	
	public void testRowsAndCols() throws Throwable
	{
		Matrix2D matrix = new Matrix2D(6,3);
		TestCase.assertEquals(matrix.getRows(), 6);
		TestCase.assertEquals(matrix.getCols(), 3);
		
		matrix.set(1,2, 1.5);
		TestCase.assertEquals(matrix.get(1,2), 1.5 );
	}
	
	public void testRowAndColRangeUnder() throws Throwable
	{
		Matrix2D matrix = new Matrix2D(6,3);
		
		// make sure set registers error on under-bound row
		try {
			matrix.set(-1, 0, 1);
			TestCase.assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure set registers error on under-bound col
		try {
			matrix.set(0, -1, 1);
			TestCase.assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure get registers error on under-bound row
		try {
			matrix.get(-1, 0 );
			TestCase.assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure set registers error on under-bound col
		try {
			matrix.get(0, -1 );
			TestCase.assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
	}
	
	public void testRowAndColRangeOver() throws Throwable
	{
		Matrix2D matrix = new Matrix2D(6,3);
		
		// make sure set registers error on under-bound row
		try {
			matrix.set(6, 0, 1);
			TestCase.assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure set registers error on under-bound col
		try {
			matrix.set(0, 3, 1);
			TestCase.assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure get registers error on under-bound row
		try {
			matrix.get(6, 0 );
			TestCase.assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure set registers error on under-bound col
		try {
			matrix.get(0, 3 );
			TestCase.assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
	}
	
	public void testMatrixConstruct() throws Throwable
	{
		double m[][] = {
				{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12},
				{13,14,15,16} };
		Matrix2D matrix = new Matrix2D(m);
		TestCase.assertEquals(matrix.getRows(), 4);
		TestCase.assertEquals(matrix.getCols(), 4);
	}
	
	public void testMatrixEquals() throws Throwable
	{
		double m1[][] = {
				{1,2},
				{3,4} };
		
		double m2[][] = {
				{0,2},
				{3,4} };	
	
		Matrix2D matrix1 = new Matrix2D(m1);
		Matrix2D matrix2 = new Matrix2D(m1);
		
		TestCase.assertTrue(matrix1.equals(matrix2));
		
		matrix2 = new Matrix2D(m2);
		
		TestCase.assertFalse(matrix1.equals(matrix2));
	}
	
	public void testMatrixEqualsPrecision() throws Throwable
	{
		double m1[][] = {
				{1.1234,2.123},
				{3.123,4.123} };
		
		double m2[][] = {
				{1.123,2.123},
				{3.123,4.123} };
		
		Matrix2D matrix1 = new Matrix2D(m1);
		Matrix2D matrix2 = new Matrix2D(m2);
		
		TestCase.assertTrue(matrix1.equals(matrix2,3));
		TestCase.assertFalse(matrix1.equals(matrix2,4));
		
		double m3[][] = {
				{1.1,2.1},
				{3.1,4.1} };
		
		double m4[][] = {
				{1.2,2.1},
				{3.1,4.1} };
		
		Matrix2D matrix3 = new Matrix2D(m3);
		Matrix2D matrix4 = new Matrix2D(m4);
		TestCase.assertTrue(matrix3.equals(matrix4,0));
		TestCase.assertFalse(matrix3.equals(matrix4,1));
		
		try
		{
			matrix3.equals(matrix4,-1);
			TestCase.assertTrue( false);
		}
		catch(MatrixError e)
		{
			
		}
		
		try
		{
			matrix3.equals(matrix4,19);
			TestCase.assertTrue( false);
		}
		catch(MatrixError e)
		{
			
		}
		
	}
	
	public void testMatrixMultiply() throws Throwable
	{
		double a[][] = {
				{1,0,2},
				{-1,3,1}
		};
		
		double b[][] = {
				{3,1},
				{2,1},
				{1,0}
		};
		
		double c[][] = {
				{5,1},
				{4,2}
		};
		
		Matrix2D matrixA = new Matrix2D(a);
		Matrix2D matrixB = new Matrix2D(b);
		Matrix2D matrixC = new Matrix2D(c);
		
		Matrix2D result = matrixA.clone();
		result = MatrixMath.multiply(matrixA,matrixB); 

		TestCase.assertTrue(result.equals(matrixC));
		
		double a2[][] = {
				{1,2,3,4},
				{5,6,7,8}
		};
		
		double b2[][] = {
				{1,2,3},
				{4,5,6},
				{7,8,9},
				{10,11,12}
		};
		
		double c2[][] = {
				{70,80,90},
				{158,184,210}
		};
		
		matrixA = new Matrix2D(a2);
		matrixB = new Matrix2D(b2);
		matrixC = new Matrix2D(c2);
		
		result = MatrixMath.multiply(matrixA, matrixB);
		TestCase.assertTrue(result.equals(matrixC));
		
		result = matrixB.clone();
		try
		{
			MatrixMath.multiply(matrixB,matrixA);
			TestCase.assertTrue(false);
		}
		catch(MatrixError e)
		{
			
		}	
	}
	
	public void testBoolean() throws Throwable
	{
		boolean matrixDataBoolean[][] = { 
				{true,false},
				{false,true}
		};
		
		double matrixDataDouble[][] = {
				{1.0,-1.0},
				{-1.0,1.0},
		};
		
		Matrix2D matrixBoolean = new Matrix2D(matrixDataBoolean);
		Matrix2D matrixDouble = new Matrix2D(matrixDataDouble);
		
		TestCase.assertTrue(matrixBoolean.equals(matrixDouble));
	}
	
	public void testGetRow() throws Throwable
	{
		double matrixData1[][] = {
				{1.0,2.0},
				{3.0,4.0}
		};
		double matrixData2[][] = {
				{3.0,4.0}
		};
		
		Matrix2D matrix1 = new Matrix2D(matrixData1);
		Matrix2D matrix2 = new Matrix2D(matrixData2);
		
		Matrix2D matrixRow = matrix1.getRow(1);
		TestCase.assertTrue(matrixRow.equals(matrix2));
		
		try
		{
			matrix1.getRow(3);
			TestCase.assertTrue(false);
		}
		catch(MatrixError e)
		{
			TestCase.assertTrue(true);
		}
	}
	
	public void testGetCol() throws Throwable
	{
		double matrixData1[][] = {
				{1.0,2.0},
				{3.0,4.0}
		};
		double matrixData2[][] = {
				{2.0},
				{4.0}
		};
		
		Matrix2D matrix1 = new Matrix2D(matrixData1);
		Matrix2D matrix2 = new Matrix2D(matrixData2);
		
		Matrix2D matrixCol = matrix1.getCol(1);
		TestCase.assertTrue(matrixCol.equals(matrix2));
		
		try
		{
			matrix1.getCol(3);
			TestCase.assertTrue(false);
		}
		catch(MatrixError e)
		{
			TestCase.assertTrue(true);
		}
	}
	
	public void testZero() throws Throwable
	{
		double doubleData[][] = { {0,0}, {0,0} };
		Matrix2D matrix = new Matrix2D(doubleData);
		TestCase.assertTrue(matrix.isZero());
	}
	
	public void testSum() throws Throwable
	{
		double doubleData[][] = { {1,2}, {3,4} };
		Matrix2D matrix = new Matrix2D(doubleData);
		TestCase.assertEquals((int)matrix.sum(), 1+2+3+4);
	}
	
	public void testRowMatrix() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix2D matrix = Matrix2D.createRowMatrix(matrixData);
		TestCase.assertEquals(matrix.get(0,0), 1.0);
		TestCase.assertEquals(matrix.get(0,1), 2.0);
		TestCase.assertEquals(matrix.get(0,2), 3.0);
		TestCase.assertEquals(matrix.get(0,3), 4.0);
	}
	
	public void testColumnMatrix() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix2D matrix = Matrix2D.createColumnMatrix(matrixData);
		TestCase.assertEquals(matrix.get(0,0), 1.0);
		TestCase.assertEquals(matrix.get(1,0), 2.0);
		TestCase.assertEquals(matrix.get(2,0), 3.0);
		TestCase.assertEquals(matrix.get(3,0), 4.0);
	}
	
	public void testAdd() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix2D matrix = Matrix2D.createColumnMatrix(matrixData);
		matrix.add(0, 0, 1);
		TestCase.assertEquals(matrix.get(0, 0), 2.0);
	}
	
	public void testClear() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix2D matrix = Matrix2D.createColumnMatrix(matrixData);
		matrix.clear();
		TestCase.assertEquals(matrix.get(0, 0), 0.0);
		TestCase.assertEquals(matrix.get(1, 0), 0.0);
		TestCase.assertEquals(matrix.get(2, 0), 0.0);
		TestCase.assertEquals(matrix.get(3, 0), 0.0);
	}
	
	public void testIsVector() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix2D matrixCol = Matrix2D.createColumnMatrix(matrixData);
		Matrix2D matrixRow = Matrix2D.createRowMatrix(matrixData);
		TestCase.assertTrue(matrixCol.isVector());
		TestCase.assertTrue(matrixRow.isVector());
		double matrixData2[][] = {{1.0,2.0},{3.0,4.0}};
		Matrix2D matrix = new Matrix2D(matrixData2);
		TestCase.assertFalse(matrix.isVector());
	}
	
	public void testIsZero() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix2D matrix = Matrix2D.createColumnMatrix(matrixData);
		TestCase.assertFalse(matrix.isZero());
		double matrixData2[] = {0.0,0.0,0.0,0.0};
		Matrix2D matrix2 = Matrix2D.createColumnMatrix(matrixData2);
		TestCase.assertTrue(matrix2.isZero());

	}
	
	public void testPackedArray() throws Throwable
	{
		double matrixData[][] = {{1.0,2.0},{3.0,4.0}};
		Matrix2D matrix = new Matrix2D(matrixData);
		Double matrixData2[] = matrix.toPackedArray();
		TestCase.assertEquals(4, matrixData2.length);
		TestCase.assertEquals(1.0,matrix.get(0, 0));
		TestCase.assertEquals(2.0,matrix.get(0, 1));
		TestCase.assertEquals(3.0,matrix.get(1, 0));
		TestCase.assertEquals(4.0,matrix.get(1, 1));
		
		Matrix2D matrix2 = new Matrix2D(2,2);
		matrix2.fromPackedArray(matrixData2, 0);
		TestCase.assertTrue(matrix.equals(matrix2));
	}
	
	public void testPackedArray2() throws Throwable
	{
		Double data[] = {1.0,2.0,3.0,4.0};
		Matrix2D matrix = new Matrix2D(1,4);
		matrix.fromPackedArray(data, 0);
		TestCase.assertEquals(1.0, matrix.get(0, 0));
		TestCase.assertEquals(2.0, matrix.get(0, 1));
		TestCase.assertEquals(3.0, matrix.get(0, 2));
	}
	
	public void testSize() throws Throwable
	{
		double data[][] = {{1.0,2.0},{3.0,4.0}};
		Matrix2D matrix = new Matrix2D(data);
		TestCase.assertEquals(4, matrix.size());
	}
	
	public void testVectorLength() throws Throwable
	{
		double vectorData[] = {1.0,2.0,3.0,4.0};
		Matrix2D vector = Matrix2D.createRowMatrix(vectorData);
		TestCase.assertEquals(5, (int)MatrixMath.vectorLength(vector));
		
		Matrix2D nonVector = new Matrix2D(2,2);
		try
		{
			MatrixMath.vectorLength(nonVector);
			TestCase.assertTrue(false);
		}
		catch(MatrixError e)
		{
			
		}
	}

}
