package main;
import java.util.Arrays;
import java.util.Random;

public class Individual {

	double[] data;

	public Individual(int size) {
		data = new double[size];
	}

	public static Individual generateNew(int size, double minVal, double maxVal) {
		Individual in = new Individual(size);
		Random r = new Random();
		for (int i = 0; i < in.size(); i++)
			in.set(i, r.nextDouble() * (maxVal - minVal) + minVal);
		return in;
	}

	public int size() {
		return data.length;
	}

	public double get(int index) {
		return data[index];
	}
	
	public double[] getData(){
		return data;
	}
	
	public void setData(double[] data){
		this.data = data;
	}

	public void set(int index, double newVal) {
		data[index] = newVal;
	}
	
	/**
	 * Adds this individual with another given individual.
	 * @param in The individual to add each value with.
	 * @return The result of adding each individual's data together, as its own individual.
	 */
	public Individual add(Individual in){
		if(size() == in.size()){
			Individual newInd = new Individual(size());
			for(int i = 0; i < size(); i++){
				newInd.set(i, get(i) + in.get(i));
			}
			return newInd;
		} else {
			System.out.println("Individual add error: Mismatched dimensions.");
			return null;
		}
	}
	
	/**
	 * Subtracts this individual with another given individual.
	 * @param in The individual to subtract each value with.
	 * @return The result of subtracting each individual's data together, as its own individual.
	 */
	public Individual subtract(Individual in){
		if(size() == in.size()){
			Individual newInd = new Individual(size());
			for(int i = 0; i < size(); i++){
				newInd.set(i, get(i) - in.get(i));
			}
			return newInd;
		} else {
			System.out.println("Individual add error: Mismatched dimensions.");
			return null;
		}
	}
	
	/**
	 * Multiplies all of this Individual's values with a given constant.
	 * @param constant The constant to multiply all this Individual's information by.
	 * @return The result of multiplying a constant to this Individual, as its own Individual.
	 */
	public Individual multiplyAll(double constant){
		Individual newInd = new Individual(size());
		for(int i = 0; i < size(); i++){
			newInd.set(i, get(i) * constant);
		}
		return newInd;
	}
	
	@Override
	public String toString(){
		return Arrays.toString(data);
	}
}
