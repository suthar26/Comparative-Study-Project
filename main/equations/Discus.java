package equations;

import main.Individual;

public class Discus extends Equation {
	
	@Override
	public double eval(Individual in) {
		double sum = 0;
		for (int i = 1; i < in.size(); i++)
			sum += in.get(i) * in.get(i);
		sum += 1e6 * in.get(0) * in.get(0);
		return sum;
	}
	
	@Override
	public String getEqName() {
		return "Discus";
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
