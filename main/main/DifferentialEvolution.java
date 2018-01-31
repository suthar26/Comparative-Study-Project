package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import equations.Equation;

public class DifferentialEvolution {
	static double cr = 0.9;
	static double F = 0.8;
	static int curFunctionCalls;
	
	public static ResultSet perform(List<Individual> x, Equation e, int maxFunctionCalls){
		//Main.timers.push(Main.getTime());
		curFunctionCalls = 0;
		double bestFitnessThisCall = 0;
		double[] fitnessVals = new double[maxFunctionCalls];
		while(curFunctionCalls < maxFunctionCalls){
			/*if(curFunctionCalls == maxFunctionCalls/10 || curFunctionCalls == maxFunctionCalls*2/10 || curFunctionCalls == maxFunctionCalls*3/10 || curFunctionCalls == maxFunctionCalls*4/10
					|| curFunctionCalls == maxFunctionCalls*5/10 || curFunctionCalls == maxFunctionCalls*6/10 || curFunctionCalls == maxFunctionCalls*7/10
					|| curFunctionCalls == maxFunctionCalls*8/10 || curFunctionCalls == maxFunctionCalls*9/10){
				//System.out.printf("%d%%... ", curFunctionCalls*100/maxFunctionCalls);
				System.out.printf("\t%s-%d-DE: %d%%...\n", e.getEqName(), x.get(0).size(), curFunctionCalls*100/maxFunctionCalls);
			}*/
			for(int i = 0; i < x.size(); i++){
				//Parent selection
				int[] parents = selectParents(0, 99, i);
				//System.out.println(i + ": " + Arrays.toString(parents));
				Individual a, b, c;
				a = x.get(parents[0]);
				b = x.get(parents[1]);
				c = x.get(parents[2]);
				
				//Mutation
				Individual v = a.add(c.subtract(b).multiplyAll(F));
				
				//Crossover
				Individual u = new Individual(x.get(i).size());
				for(int j = 0; j < u.size(); j++){
					if(cr > Math.random() || j == (int)Math.random()*j)
						u.set(j, v.get(j));
					else u.set(j, x.get(i).get(j));
				}
				
				//Selection
				Individual x_prime = new Individual(x.get(i).size());
				double initVal = e.eval(x.get(i));
				double crossVal = e.eval(u);
				//System.out.printf("%s <= %s == %b\n", Main.prettyPrintBigDecimal(crossVal), Main.prettyPrintBigDecimal(initVal), crossVal.compareTo(initVal) <= 0);
				if(crossVal <= initVal){
					x_prime = u;
					bestFitnessThisCall = crossVal;
				} else {
					x_prime = x.get(i);
					bestFitnessThisCall = initVal;
				}
				x.set(i, x_prime);
			}
			//System.out.printf("DE:  %s\n", x.get(0).toString());
			fitnessVals[curFunctionCalls] = bestFitnessThisCall;
			curFunctionCalls++;
		}
		
		//Get the best individual
		Individual best = x.get(0);
		double bestVal = e.eval(best);
		for(Individual i:x){
			double thisVal = e.eval(i);
			if(bestVal < thisVal){
				bestVal = thisVal;
				best = i;
			}		
		} // */
		
		//System.out.printf("100%%. Time taken: %s\n", Main.formatTime(Main.timers.pop()));
		
		ResultSet rs = new ResultSet(best, fitnessVals);
		return rs;
		//return fitnessVals;
		//return best;
	}
	
	private static int[] selectParents(int lowerRange, int upperRange, int doNotInclude){
		List<Integer> list = new ArrayList<Integer>();
		for(int i = lowerRange; i <= upperRange; i++)
			if(i != doNotInclude)
				list.add(i);
		Collections.shuffle(list);
		int[] a = new int[3];
		for(int i = 0; i < 3; i++)
			a[i] = list.get(i);
		return a;
	}
}
