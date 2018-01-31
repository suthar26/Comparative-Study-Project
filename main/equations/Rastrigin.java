package equations;

import main.Individual;

public class Rastrigin extends Equation {

	@Override
	public double eval(Individual in) {
		double sum = 0;
		for(int i = 0; i < in.size(); i++){
			sum += in.get(i) * in.get(i) - 10.0 * Math.cos(2*Math.PI*in.get(i));
		}
		sum += 10 * in.size();
		return sum;
	}
	
	@Override
	public String getEqName() {
		return "Rastrigin";
	}

	@Override
	public double range_minX() {
		return -5;
	}

	@Override
	public double range_maxX() {
		return 5;
	}

	@Override
	public double range_minY() {
		return -5;
	}

	@Override
	public double range_maxY() {
		return 5;
	}

	@Override
	public double range_stepX() {
		return 0.1;
	}

	@Override
	public double range_stepY() {
		return 0.1;
	}

}
