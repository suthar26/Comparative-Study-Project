package equations;

import main.Individual;

public class Rosenbrock extends Equation {

	@Override
	public double eval(Individual in) {
		double sum = 0;
		for (int i = 0; i < in.size() - 1; i++) {
			sum += ((100 * (in.get(i+1) - in.get(i) * in.get(i)) * (in.get(i+1) - in.get(i) * in.get(i)))
					+ ((in.get(i) - 1.0) * (in.get(i) - 1.0)));
		}
		return sum;
	}

	@Override
	public String getEqName() {
		return "Rosenbrock";
	}

	@Override
	public double range_minX() {
		return -1.5;
	}

	@Override
	public double range_maxX() {
		return 2;
	}

	@Override
	public double range_minY() {
		return -0.5;
	}

	@Override
	public double range_maxY() {
		return 3;
	}

	@Override
	public double range_stepX() {
		return 0.05;
	}

	@Override
	public double range_stepY() {
		return 0.05;
	}
}
