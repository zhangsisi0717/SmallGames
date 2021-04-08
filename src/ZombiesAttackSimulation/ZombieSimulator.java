package ZombiesAttackSimulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;
import support.cse131.ArgsProcessor;
import support.cse131.Timing;
import zombies.ZombieSimulationFiles;


public class ZombieSimulator {
	private static final String ZOMBIE_TOKEN_VALUE = "Zombie";
	private List<Entity> allEntities;
	private List<Entity> activeEntites;
	private static double SPEED = 0.08 ;

	
	/**
	 * Constructs a ZombieSimulator with an empty list of Entities.
	 */
	public ZombieSimulator() {
		this.allEntities = new LinkedList<Entity>();
		
	}

	/**
	 * @return the current list of active entities (that is: those which have not
	 *         been consumed).
	 */
	public List<Entity> getEntities() {
		return this.allEntities;
	}

	/**
	 * Reads an entire zombie simulation file from a specified ArgsProcessor, adding
	 * each Entity to the list of entities.
	 * 
	 * @param ap ArgsProcessor to read the complete zombie simulation file format.
	 */
	public void readEntities(ArgsProcessor ap) {
		int N = ap.nextInt();
		int cur = 0;
		List<Entity> allEntities = new LinkedList<Entity>();
		while(cur<N) { //reading all the data
			boolean isZombie = ap.nextString().equals(this.ZOMBIE_TOKEN_VALUE);
			double x = ap.nextDouble();
			double y = ap.nextDouble();
			allEntities.add(new Entity(isZombie,x,y));
			cur +=1;
		}
		this.allEntities = allEntities;
		this.activeEntites = this.allEntities;
	}

	/**
	 * @return the number of zombies in entities.
	 */
	public int getZombieCount() {
		int numZombie = 0;
		for(Entity i: allEntities) {
			if (i.isZombie()) {
				numZombie +=1;
			}
		}
		return numZombie;
	}

	/**
	 * @return the number of nonzombies in entities.
	 */
	public int getNonzombieCount() {
		int numNonZombie = 0;
		for(Entity i: allEntities) {
			if (!i.isZombie()) {
				numNonZombie +=1;
			}
		}
		return numNonZombie;

		
	}

	/**
	 * Draws a frame of the simulation.
	 */
	public void draw() {
		StdDraw.clear();
		StdDraw.enableDoubleBuffering();
		for (Entity entity : this.activeEntites) {
			entity.draw();
		}

		StdDraw.show(); 
	}

	/**
	 * Updates the state of current entity including position, if it is a zombie based on passed deltatime 
	 * 
	 * 
	 * Note: some entities may be consumed and will not remain for future frames of
	 * the simulation.
	 * 
	 * @param deltaTime the amount of time since the previous frame of the simulation.
	 *
	 */
	public void update(double deltaTime) {
		List<Entity> newActiveEntities = new LinkedList<Entity>();
		for(Entity i: this.activeEntites) {
			if(i.update(this.activeEntites, deltaTime)){
				newActiveEntities.add(i);
			}
			
		}
		this.activeEntites = newActiveEntities;
			
	}

	/**
	 * Runs the zombie attack simulation
	 */
	public static void main(String[] args) {
		StdDraw.setXscale(0, 1);
		StdDraw.setYscale(0, 1);
		StdDraw.enableDoubleBuffering(); 

		ArgsProcessor ap = ZombieSimulationFiles.createArgsProcessorFromFile(args);
		ZombieSimulator zombieSimulator = new ZombieSimulator();
		zombieSimulator.readEntities(ap);
		zombieSimulator.draw();
		StdDraw.pause(500);

		double prevTime = Timing.getCurrentTimeInSeconds();
		while (zombieSimulator.getNonzombieCount() > 0) {
			double currTime = Timing.getCurrentTimeInSeconds();
			double deltaTime = currTime - prevTime;
			if (deltaTime > 0.0) {
				zombieSimulator.update(deltaTime);
				zombieSimulator.draw();
			}
			StdDraw.pause(10);
			prevTime = currTime;
		}
	}
}
