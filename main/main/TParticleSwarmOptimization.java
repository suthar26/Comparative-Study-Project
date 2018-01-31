package main;

import java.util.List;

import equations.Equation;

public class TParticleSwarmOptimization implements Runnable {
	static double c1 = 2.05;
	static double c2 = 2.05;
	static double w; //0.9 - 0.4, linearly decreasing
	static double w_start = 0.9;
	static double w_end = 0.4;
	
	//Threading stuff
	public Thread t = new Thread(this, "PSO");
	List<Individual> pop;
	Equation eq;
	int nfc;
	ResultSet results;
	static long startTime;
	public boolean isRunning;
	
	
	public TParticleSwarmOptimization(List<Individual> x, Equation e, int maxFunctionCalls) {
		pop = x;
		eq = e;
		nfc = maxFunctionCalls;
		isRunning = true;
		ParticleSwarmOptimization.curFunctionCalls = 1;
		t.start();
	}

	@Override
	public void run() {
		startTime = Main.getTime();
		isRunning = true;
		results = ParticleSwarmOptimization.perform(pop, eq, nfc);
		isRunning = false;
		//System.out.printf("%s-%d-PSO: Finished. Took %s.\n", eq.getEqName(), pop.get(0).size(), Main.formatTime(startTime));
	}
	
	public ResultSet getResults(){
		return results;
	}

}
