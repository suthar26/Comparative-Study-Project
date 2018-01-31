package equations;

import main.Individual;

public class Ackley extends Equation {
	
	@Override
	public double eval(Individual in) {
		double sum = 0;

		double sumSquare = 0;
		double sumCos = 0;
		for (int i = 0; i < in.size(); i++) {
			sumSquare += in.get(i) * in.get(i);
			sumCos += Math.cos(2 * Math.PI * in.get(i));
		}

		sum = -20.0 * Math.exp(-0.2 * Math.sqrt(sumSquare / in.size())) - Math.exp(sumCos / in.size()) + 20
				+ Math.E;

		return sum;
	}

	@Override
	public String getEqName() {
		return "Ackley";
		
	}

	@Override
	public double range_minX() {
		return -40;
	}

	@Override
	public double range_maxX() {
		return 40;
	}

	@Override
	public double range_minY() {
		return -40;
	}

	@Override
	public double range_maxY() {
		return 40;
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
