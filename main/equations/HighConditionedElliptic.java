package equations;

import main.Individual;

public class HighConditionedElliptic extends Equation{

	@Override
	public double eval(Individual in) {
		double sum = 0;
		for(int i = 0; i < in.size(); i++)
			sum += Math.pow(1e6, (i/(in.size()-1))) * in.get(i) * in.get(i);
		return sum;
	}
	
	@Override
	public String getEqName() {
		return "SRHCE";
		//return "Shifted Rotated High Conditioned Elliptic";
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
