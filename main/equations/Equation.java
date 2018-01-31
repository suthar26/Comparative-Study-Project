package equations;


import main.Individual;

public abstract class Equation {
	

	//These functions are used for plotting the function
	public abstract double range_minX();
	public abstract double range_maxX();
	public abstract double range_minY();
	public abstract double range_maxY();
	public abstract double range_stepX();
	public abstract double range_stepY();
	
	public abstract double eval(Individual in);
	public abstract String getEqName();
	
	public double eval(double[] data){
		Individual i = new Individual(data.length);
		i.setData(data);
		return eval(i);
	}
}
