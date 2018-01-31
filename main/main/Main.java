package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import equations.Ackley;
import equations.BentCigar;
import equations.Discus;
import equations.Equation;
import equations.Griewank;
import equations.HighConditionedElliptic;
import equations.Katsuura;
import equations.Rastrigin;
import equations.Rosenbrock;
import equations.Weierstrass;

public class Main {

	public static final int runsPerFunction = 5;
	public static final int maxFunctionCallsMultiplier = 3000;
	public static final int populationSize = 100;
	public static final int individual_lowerLimit = -10;
	public static final int individual_upperLimit = 10;
	public static final int[] testDimensions = {10};
	public static final int fileStepSize = populationSize;
	public static final Equation[] eqs = {
			
//			new Katsuura(),
//			new Ackley()
//			new BentCigar(),
//			new Discus(),
//			new Griewank(),
//			new HighConditionedElliptic(),
//			new Rastrigin(),
			new Rosenbrock()
//			new Weierstrass()
		};
	
	static Stack<Long> timers = new Stack<Long>();
	
	public static long getTime() {
		return System.nanoTime();
	}
	
	public static String formatTime(long origStartTime){
		return String.format("%.2fs (%.0f:%02.0f)", (getTime()-origStartTime)*1.0e-9, Math.floor((getTime()-origStartTime)*1.0e-9/60), (getTime()-origStartTime)*1.0e-9%60);
	}
	
	public static void main(String[] args) {
		timers.push(getTime());
		
		
		timers.push(getTime());
		System.out.println("Generating plots.");
		generatePlots();
		System.out.printf("Finished generating plots. Took %s.\n\n", formatTime(timers.pop()));
		
		timers.push(getTime());
		System.out.println("Starting comparisons.");
		//doAlgorithmComparison();
		doAlgorithmComparison_Threaded();
		System.out.printf("All comparisons finished. Took %s.\n", formatTime(timers.pop()));
		
		
		System.out.printf("Program finished. Total running time: %s.\n", formatTime(timers.pop()));
	}
	
	public static void doAlgorithmComparison_Threaded(){
		for(int dimensions : testDimensions){
			for(Equation e : eqs){
				timers.push(getTime());
				try {
					BufferedWriter file = new BufferedWriter(new FileWriter(String.format("TESTComp-%s-%d.csv", e.getEqName(), dimensions)));
					file.write("sep=;\r\n");
					
					StringBuilder sb_de = new StringBuilder("sep=;\r\n");
					StringBuilder sb_pso = new StringBuilder("sep=;\r\n");

					sb_de.append(";Solution found;Solution value;");
					sb_pso.append(";Solution found;Solution value;");
					
					int i;
					for(i = 0; i < maxFunctionCallsMultiplier*dimensions; i += fileStepSize){
						sb_de.append(String.format(";%d", i));
						sb_pso.append(String.format(";%d", i));
					}
					//Guarantee the last run is recorded, no matter the step size
					if(i != maxFunctionCallsMultiplier*dimensions - 1){
						sb_de.append(String.format(";%d", maxFunctionCallsMultiplier*dimensions));
						sb_pso.append(String.format(";%d", maxFunctionCallsMultiplier*dimensions));
					}
					sb_de.append("\r\n");
					sb_pso.append("\r\n");
					
					
					System.out.printf("Starting comparison for %s (%d dimensions).\n", e.getEqName(), dimensions);
					for(int r = 0; r < runsPerFunction; r++){
						List<Individual> curSet = getNewPopulation(dimensions);
						
						System.out.printf("%s-%d: Run %d of %d: ", e.getEqName(), dimensions, r+1, runsPerFunction);
						
						System.out.printf("Initializing threads... ");
						TDifferentialEvolution de = new TDifferentialEvolution(cloneList(curSet), e, maxFunctionCallsMultiplier*dimensions);
						TParticleSwarmOptimization pso = new TParticleSwarmOptimization(cloneList(curSet), e, maxFunctionCallsMultiplier*dimensions);
						System.out.printf("Now running...\r");
						
						try {

							while(de.isRunning || pso.isRunning){
								Thread.sleep(750);
								System.out.printf("%s-%d: Run %d of %d: DE: %6d/%6d (%3d%%); PSO: %6d/%6d (%3d%%)\r", e.getEqName(), dimensions, r+1, runsPerFunction,
										DifferentialEvolution.curFunctionCalls-1, de.nfc, DifferentialEvolution.curFunctionCalls*100/(maxFunctionCallsMultiplier*dimensions),
										ParticleSwarmOptimization.curFunctionCalls-1, pso.nfc, ParticleSwarmOptimization.curFunctionCalls*100/(maxFunctionCallsMultiplier*dimensions));
							}
							
							de.t.join();
							pso.t.join();
						} catch (InterruptedException e1) {
							System.err.println("\tThe threads were interrupted. Skipping to next run.");
							System.err.println(e1.getMessage());
							continue;
						}
						System.out.printf("%s-%d: Run %d of %d: DE: %6d/%6d (%3d%%); PSO: %6d/%6d (%3d%%); Time took: %s\n", e.getEqName(), dimensions, r+1, runsPerFunction,
								de.nfc, de.nfc, 100, pso.nfc, pso.nfc, 100, formatTime(TDifferentialEvolution.startTime));
						
						ResultSet deResults = de.getResults();
						ResultSet psoResults = pso.getResults();
						
						double[] deFitnesses = deResults.fitnesses;
						double[] psoFitnesses = psoResults.fitnesses;

						sb_de.append(String.format("Run %d", r+1));
						sb_pso.append(String.format("Run %d", r+1));

						sb_de.append(";" + deResults.best.toString());
						sb_pso.append(";" + psoResults.best.toString());
						
						sb_de.append(String.format(";%G;", e.eval(deResults.best)));
						System.out.println(";" + e.eval(deResults.best) + ";");
						System.out.println(";" + e.eval(psoResults.best) + ";");
						sb_pso.append(String.format(";%G;", e.eval(psoResults.best)));
						
						int d;
						for(d = 0; d < maxFunctionCallsMultiplier*dimensions; d += fileStepSize){
							sb_de.append(String.format(";%G", deFitnesses[d]));
							sb_pso.append(String.format(";%G", psoFitnesses[d]));
							System.out.println(";" + deFitnesses[d] + ";");
							System.out.println(";" + psoFitnesses[d] + ";");
						}
						if(d != maxFunctionCallsMultiplier*dimensions - 1){
							sb_de.append(String.format(";%G", deFitnesses[maxFunctionCallsMultiplier*dimensions - 1]));
							sb_pso.append(String.format(";%G", psoFitnesses[maxFunctionCallsMultiplier*dimensions - 1]));
						}
						
						sb_de.append("\r\n");
						sb_pso.append("\r\n");
					}
					
					System.out.printf("%s-%d: Writing to \"Comp-%s-%d.csv\"...\n", e.getEqName(), dimensions, e.getEqName(), dimensions);
					file.write("Differential evolution:\r\n" + sb_de.toString().substring(7) + "\r\n\r\nParticle swarm optimization:\r\n" + sb_pso.toString().substring(7));
					file.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.printf("%s-%d: Finished comparison. Running time: %s.\n", e.getEqName(), dimensions, formatTime(timers.pop()));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Individual> cloneList(List<Individual> list){
		return ((List<Individual>) ((ArrayList<Individual>) list).clone());
	}

	public static void generatePlots() {
		StringBuilder sb = new StringBuilder();
		for (Equation e : eqs) {
			timers.push(getTime());
			try {
				System.out.printf("Writing to \"Plot-%s.csv\"... ", e.getEqName());
				sb = new StringBuilder("sep=;\r\n");
				BufferedWriter w = new BufferedWriter(new FileWriter("Plot-" + e.getEqName() + ".csv"));
				for (double d = e.range_minY(); d <= e.range_maxY(); d += e.range_stepY())
					sb.append(String.format(";%f", d));
				sb.append("\r\n");
				for (double x = e.range_minX(); x <= e.range_maxX(); x += e.range_stepX()) {
					sb.append(String.format("%f;", x));
					for (double y = e.range_minY(); y <= e.range_maxY(); y += e.range_stepY()) {
						Individual in = new Individual(2);
						in.data[0] = x;
						in.data[1] = y;
						sb.append(String.format("%20.20G;", e.eval(in)));
					}
					sb.append("\r\n");
				}
				sb.append("\r\n");

				w.write(sb.toString());
				w.close();
			} catch (IOException ex) {
				System.out.println("Can't write for some reason. Dumping data instead.");
				System.out.println(sb.toString()); // Just in case
			}
			System.out.printf("Done. Took %s.\n", formatTime(timers.pop()));
		}
	}

	public static List<Individual> getNewPopulation(int dimensions) {
		List<Individual> list = new ArrayList<Individual>();
		Random r = new Random();
		for(int i = 0; i < populationSize; i++){
			Individual in = new Individual(dimensions);
			for(int j = 0; j < dimensions; j++)
				in.set(j, r.nextDouble()*(individual_upperLimit-individual_lowerLimit) + individual_lowerLimit);
			list.add(in);
		}
		return list;
	}
	
	public static String prettyPrintBigDecimal(BigDecimal num){
		return String.format("%20.20G", num);
	}

}
