package equations;

import main.Individual;

public class Griewank extends Equation {
	
	@Override
	public double eval(Individual in) {
		double sum = 1;
		
		double innerSum = 0;
		double innerProduct = 1;
		
		for(int i = 0; i < in.size(); i++){
			innerSum += in.get(i)*in.get(i);
			innerProduct *= Math.cos(in.get(i)/Math.sqrt(i+1));
		}
		
		sum += innerSum * (1.0 / 4000.0) - innerProduct;
		
		return sum;
	}
	
	@Override
	public String getEqName() {
		return "Griewank";
	}

	@Override
	public double range_minX() {
		return -50;
	}

	@Override
	public double range_maxX() {
		return 50;
	}

	@Override
	public double range_minY() {
		return -50;
	}

	@Override
	public double range_maxY() {
		return 50;
	}

	@Override
	public double range_stepX() {
		return 1;
	}

	@Override
	public double range_stepY() {
		return 1;
	}

}
