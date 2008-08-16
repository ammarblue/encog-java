package org.encog.neural.data.basic;

import org.encog.neural.data.NeuralData;

public class BasicNeuralData implements NeuralData {
	private double[] data;
	
	public BasicNeuralData(int size) {
		data = new double[size];
	}
	
	public BasicNeuralData(double d[])
	{
		this(d.length);
		System.arraycopy(d, 0, data, 0, d.length);
	}

	public void setData(double[] data)
	{
		this.data = data;
	}
	
	public void setData(int index,double d)
	{
		this.data[index] = d;
	}
	
	public double[] getData()
	{
		return data;
	}
	
	public double getData(int index)
	{
		return data[index];
	}

	public int size() {
		return data.length;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder("[BasicNeuralData:");
		for(int i=0;i<this.data.length;i++)
		{
			if( i!=0 )
				builder.append(',');
			builder.append(this.data[i]);
		}
		builder.append("]");
		return builder.toString();
	}
}
