package equations;

import main.Individual;

public class Weierstrass extends Equation {

	public double a = 0.5;
	public double b = 3;
	public double k_max = 20;

	@Override
	public double eval(Individual in) {
		double sum = 0;

		//Left side of equation
		for (int i = 0; i < in.size(); i++) {
			for (int k = 0; k <= k_max; k++) {
				sum += Math.pow(a, k) * Math.cos(2 * Math.PI * Math.pow(b, k) * (in.get(i) + 0.5));
			}
		}
		
		//Summation of the right side of equation
		double constant = 0;
		for (int k = 0; k <= k_max; k++)
			constant += Math.pow(a, k) * Math.cos(2 * Math.PI * Math.pow(b, k) * 0.5);
		
		sum -= in.size() * constant;
		return sum;
	}

	@Override
	public String getEqName() {
		return "Weierstraß";
	}

	@Override
	public double range_minX() {
		return -100;
	}

	@Override
	public double range_maxX() {
		return 100;
	}

	@Override
	public double range_minY() {
		return -100;
	}

	@Override
	public double range_maxY() {
		return 100;
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
