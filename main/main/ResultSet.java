package main;

public class ResultSet {

	public Individual best;
	public double[] fitnesses;
	public ResultSet(Individual b, double[] f){
		best = b; fitnesses = f;
	}
}
