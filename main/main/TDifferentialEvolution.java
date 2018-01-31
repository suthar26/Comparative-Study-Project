package main;
import java.util.List;

import equations.Equation;

public class TDifferentialEvolution implements Runnable {
	static double cr = 0.9;
	static double F = 0.8;
	
	//Threading stuff
	public Thread t = new Thread(this, "DE");
	List<Individual> pop;
	Equation eq;
	int nfc;
	ResultSet results;
	static long startTime;
	public boolean isRunning;
	
	public TDifferentialEvolution(List<Individual> x, Equation e, int maxFunctionCalls) {
		pop = x;
		eq = e;
		nfc = maxFunctionCalls;
		isRunning = true;
		DifferentialEvolution.curFunctionCalls = 1;
		t.start();
	}

	@Override
	public void run() {
		startTime = Main.getTime();
		isRunning = true;
		results = DifferentialEvolution.perform(pop, eq, nfc);
		isRunning = false;
		//System.out.printf("%s-%d-DE:  Finished. Took %s.\n", eq.getEqName(), pop.get(0).size(), Main.formatTime(startTime));
	}
	
	public ResultSet getResults(){
		return results;
	}

}
