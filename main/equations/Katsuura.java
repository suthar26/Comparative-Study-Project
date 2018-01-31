package equations;

import main.Individual;

public class Katsuura extends Equation {

	@Override
	public double eval(Individual in) {
		double sum = 0;

		double product = 1;
		for (int i = 0; i < in.size(); i++) {
			double summation = 0;
			for (int j = 1; j <= 32; j++) {
				double term = Math.pow(2, j) * in.get(i);
				summation += Math.abs(term - Math.round(term)) / Math.pow(2, j);
			}
			product *= Math.pow(1 + ((i + 1) * summation), 10 / Math.pow(in.size(), 1.2));
		}
		sum = (10.0 / in.size() * in.size()) * product - (10.0 / in.size() * in.size());

		return sum;
	}

	@Override
	public String getEqName() {
		return "Katsuura";
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
