package main;

import java.util.List;

import equations.Equation;

public class ParticleSwarmOptimization {
	static double c1 = 2.05;
	static double c2 = 2.05;
	static double w; //0.9 - 0.4, linearly decreasing
	static double w_start = 0.9;
	static double w_end = 0.4;
	static int curFunctionCalls;
	
	public static ResultSet perform(List<Individual> population, Equation e, int maxFunctionCalls) {
		//Main.timers.push(Main.getTime());
		curFunctionCalls = 0;
		int dimensions = population.get(0).size();
		double[][] velocities = new double[population.size()][dimensions];
		double[] p_gbest = new double[dimensions];
		double[][] p_pbest = new double[population.size()][dimensions];
		double[] gvals = new double[maxFunctionCalls];
		double gbestVal;
		

		for(int i = 0; i < p_gbest.length; i++)
			p_gbest[i] = Double.MAX_VALUE;
		for(int i = 0; i < p_pbest.length; i++)
			for(int j = 0; j < p_pbest[i].length; j++)
				p_pbest[i][j] = Double.MAX_VALUE;
		
		//Initialize
		for(int i = 0; i < population.size(); i++){
			Individual in = population.get(i);
			p_pbest[i] = in.getData().clone();
			
			if(i != 0){
				if(e.eval(in) <= e.eval(p_gbest))
					p_gbest = p_pbest[i].clone();
			} else {
				p_gbest = p_pbest[i].clone();
			}
		}
		gbestVal = e.eval(p_gbest);

		//Initialize velocities with zero velocity to start
		for(int i = 0; i < velocities.length; i++)
			for(int j = 0; j < velocities[i].length; j++)
				velocities[i][j] = 0;
		
		while(curFunctionCalls++ < maxFunctionCalls){
			/*if(curFunctionCalls == maxFunctionCalls/10 || curFunctionCalls == maxFunctionCalls*2/10 || curFunctionCalls == maxFunctionCalls*3/10 || curFunctionCalls == maxFunctionCalls*4/10
					|| curFunctionCalls == maxFunctionCalls*5/10 || curFunctionCalls == maxFunctionCalls*6/10 || curFunctionCalls == maxFunctionCalls*7/10
					|| curFunctionCalls == maxFunctionCalls*8/10 || curFunctionCalls == maxFunctionCalls*9/10){
				//System.out.printf("%d%%... ", curFunctionCalls*100/maxFunctionCalls);
				System.out.printf("\t%s-%d-PSO: %d%%...\n", e.getEqName(), population.get(0).size(), curFunctionCalls*100/maxFunctionCalls);
			}*/
			
			w = w_start - ((double)curFunctionCalls/maxFunctionCalls) * (w_start - w_end);
			for(int i = 0; i < population.size(); i++){
				Individual in = population.get(i);
				//Update velocity and position
				for(int j = 0; j < in.size(); j++){
					double r1 = Math.random();
					double r2 = Math.random();
					velocities[i][j] = w*velocities[i][j] + r1*c1 * (p_pbest[i][j] - in.get(j)) + r2*c2 * (p_gbest[j] - in.get(j));
					in.set(j, in.get(j) + velocities[i][j]);
				}
				//Update swarm
				double pbestVal = e.eval(p_pbest[i]);
				double curVal = e.eval(in);
				if(curVal < pbestVal){
					p_pbest[i] = in.getData().clone();
					if(pbestVal < gbestVal){
						p_gbest = p_pbest[i].clone();
						gbestVal = pbestVal;
					}
				}
			}
			//System.out.println(gbestVal);
			//System.out.println(Arrays.toString(population.get(1).getData()));
			//System.out.printf("%d/%d: %s\n", curFunctionCalls, maxFunctionCalls, Arrays.toString(p_gbest));
			//System.out.printf("PSO: %s\n", population.get(0).toString());
			gvals[curFunctionCalls-1] = gbestVal;
		}

		//System.out.printf("100%%. Time taken: %s\n", Main.formatTime(Main.timers.pop()));
		
		Individual g = new Individual(0);
		g.data = p_gbest;
		ResultSet rs = new ResultSet(g, gvals);
		return rs;
		//return gvals;
		//return p_gbest;
	}
}
