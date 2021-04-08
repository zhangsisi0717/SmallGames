package ZombiesAttackSimulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;

public class Entity {
	

	/**
	 * @param isZombie if this entity is zombie
	 *                 true if zombie, false otherwise.
	 * @param x        the x-coordinate of this Entity's center.
	 * @param y        the y-coordinate of this Entity's center.
	 */
	private boolean isZombie;
	private double x;
	private double y;
	private double ENTITY_RADIUS = 0.008;
	private static double SPEED = 0.08;
	private static double RADIUS_LIMIT = 0.02;
	private static final Color ZOMBIE_COLOR = new Color(146, 0, 0);
	private static final Color NONZOMBIE_COLOR = new Color(0, 0, 0);

	
	public Entity(boolean isZombie, double x, double y) {
		
		this.isZombie = isZombie;
		this.x = x;
		this.y = y;	
		
	}

	/**
	 * @return true if zombie, false otherwise
	 */
	public boolean isZombie() {	
			return this.isZombie;
	}

	/**
	 * @return the center x-coordinate
	 */
	public double getX() {
			return this.x;
	}

	/**
	 * @return the center y-coordinate
	 */
	public double getY() {
			return this.y;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return this.ENTITY_RADIUS;
	}
	
	public void setRadius(double radius) {
		this.ENTITY_RADIUS = radius;
}

	/**
	 * Draw this Entity.
	 */
	public void draw() {
		  
		if(this.isZombie) {
			StdDraw.setPenColor(ZOMBIE_COLOR);
		}
		else {
			StdDraw.setPenColor(NONZOMBIE_COLOR); 
		}
		StdDraw.filledCircle(this.x, this.y, this.ENTITY_RADIUS);
	}

	/**
	 * @param xOther x-coordinate of the other point.
	 * @param yOther y-coordinate of the other point.
	 * @return distance between this Entity's center and the specified other point.
	 */
	public double distanceCenterToPoint(double xOther, double yOther) {
		
		return Math.sqrt(Math.pow(this.x-xOther,2) + Math.pow(this.y-yOther,2));
		
	}

	/**
	 * @param other the other Entity
	 * @return the distance between this Entity's center and the specified other
	 *         Entity's center.
	 */
	public double distanceCenterToCenter(Entity other) {
		return distanceCenterToPoint(other.getX(), other.getY());
	}

	/**
	 * @param xOther      the x-coordinate of another Entity's center.
	 * @param yOther      the y-coordinate of another Entity's center.
	 * @param radiusOther the radius of another Entity.
	 * @return the distance between this Entity's edge and the specified other
	 *         Entity's edge.
	 */
	public double distanceEdgeToEdge(double xOther, double yOther, double radiusOther) {
		
			return distanceCenterToPoint(xOther, yOther)-(this.ENTITY_RADIUS + radiusOther);
		
	}

	/**
	 * @param other: the other Entity.
	 * @return the distance between this Entity's edge and the specified other
	 *         Entity's edge.
	 */
	public double distanceEdgeToEdge(Entity other) {
		return distanceEdgeToEdge(other.getX(), other.getY(), other.getRadius());
	}

	/**
	 * @param xOther      the x-coordinate of another Entity's center.
	 * @param yOther      the y-coordinate of another Entity's center.
	 * @param radiusOther the radius of another Entity.
	 * @return true if the bounding circle of this Entity overlaps with the bounding
	 *         circle of the specified other Entity, false otherwise.
	 */
	public boolean isTouching(double xOther, double yOther, double radiusOther) {
		if(distanceCenterToPoint(xOther,yOther)<=this.ENTITY_RADIUS+radiusOther) {
			return true;
		}
		else {
			return false;
		}	
	}

	/**
	 * @param other the other Entity
	 * @return true if the bounding circle of this Entity overlaps with the bounding
	 *         circle of the specified other Entity, false otherwise.
	 */
	public boolean isTouching(Entity other) {
		return isTouching(other.getX(), other.getY(), other.getRadius());
	}

	/**
	 * @param xOther x-coordinate of the other point.
	 * @param yOther y-coordinate of the other point.
	 * @param amount the amount to move toward the point.
	 */
	public void moveToward(double xOther, double yOther, double amount) { //this entity move 'amount' toward to the other entity
		double distance = this.distanceCenterToPoint(xOther, yOther);
		double xVector = xOther - getX();
		double yVector = yOther - getY();
		double xAmount = amount * xVector / distance;
		double yAmount = amount * yVector / distance;
			 
		this.x = Math.max(0, Math.min(this.x + xAmount, 1));   //lower bound 0, upper bound 1
		this.y = Math.max(0, Math.min(this.y + yAmount, 1));
	}

	/**
	 * @param other  the other Entity
	 * @param amount the amount to move toward the other Entity.
	 */
	public void moveToward(Entity other, double amount) {
		moveToward(other.getX(), other.getY(), amount);
	}

	/**
	 * @param xOther x-coordinate of the other point.
	 * @param yOther y-coordinate of the other point.
	 * @param amount the amount to move away from the point.
	 */
	public void moveAwayFrom(double xOther, double yOther, double amount) {
		moveToward(xOther, yOther, (-1.) * amount);
	}

	/**
	 * @param other  the other Entity
	 * @param amount the amount to move away from the other Entity.
	 */
	public void moveAwayFrom(Entity other, double amount) {
		moveAwayFrom(other.getX(), other.getY(), amount);
	}

	/**
	 * @param entities          this list of entities to search.
	 * @param includeZombies    whether to include zombies in this search or not.
	 * @param includeNonzombies whether to include nonzombies in this search or not.
	 * @return the closest Entity to this Entity in entities (which is not this).
	 */
	private Entity findClosest(List<Entity> entities, boolean includeZombies, boolean includeNonzombies) {
		Entity closest = null;
		double closestDist = Double.MAX_VALUE;
		for (Entity other : entities) {
			if (this != other) {
				if ((other.isZombie() && includeZombies) || (!other.isZombie() && includeNonzombies)) {
					double dist = distanceEdgeToEdge(other);
					if (dist < closestDist) {
						closest = other;
						closestDist = dist;
					}
				}
			}
		}
		return closest;
	}

	/**
	 * @param entities this list of entities to search.
	 * @return the closest nonzombie to this Entity in entities (which is not this).
	 */
	public Entity findClosestNonzombie(List<Entity> entities) {
		return findClosest(entities, false, true);
	}

	/**
	 * @param entities this list of entities to search.
	 * @return the closest zombie to this Entity in entities (which is not this).
	 */
	public Entity findClosestZombie(List<Entity> entities) {
		return findClosest(entities, true, false);
	}

	/**
	 * @param entities this list of entities to search.
	 * @return the closest Entity to this Entity in entities (which is not this).
	 */
	public Entity findClosestEntity(List<Entity> entities) {
		return findClosest(entities, true, true);
	}

	/**
	 * Updates the appropriate state (instance variables) of this Entity for the
	 * current frame of the simulation.
	 * 
	 * @param entities  list of remaining entities in the simulation. It is likely
	 *                  that this Entity will be one of the entities in the list.
	 *                  Care should be taken to not overreact to oneself.
	 * @param deltaTime the change in time since the previous frame
	 * 
	 * @return true if this Entity remains in existence past the current frame,
	 *         false if consumed
	 * @implNote update strategy: 1. if zombie: find the cloest non-zombie and move toward it,
	 *                  2. if nonzombie and no touching zombie: move away from zombie or move away from all other entities
	 *                  3. if nonzombie and touching zombie: 20%:consumed by zombie, 80%:turning into zombie
	 */
	 
	public boolean update(List<Entity> entities, double deltaTime) {
		if(this.isZombie()) { //if is zombie, move toward to cloest nonzombie
			Entity nonZombie = this.findClosestNonzombie(entities);
			if(nonZombie != null) {
			this.moveToward(nonZombie,deltaTime*SPEED);
			}
		}
		else { //if this entity is nonzombie, them find closest zombie
			Entity zombie = this.findClosestZombie(entities);
			if(!this.isTouching(zombie)) { //if this nonzombie doesnt not touch closest zombie, then move away from zombie, and update the position and return true
				if(Math.random()<=0.2) {
				this.moveAwayFrom(zombie, deltaTime*SPEED);
				}
				else {
					Entity closestEntity = this.findClosestEntity(entities);
					this.moveAwayFrom(closestEntity, deltaTime*SPEED);
				}
			}
			else { //if this nonzombie touch closest zombie
				this.isZombie = true;
				if(Math.random()<=0.2) { //<0.2: be consumed
					if(zombie.getRadius()*1.2>=RADIUS_LIMIT) {
						zombie.ENTITY_RADIUS = RADIUS_LIMIT;
					}
					else {
						zombie.ENTITY_RADIUS = zombie.getRadius()*1.2;
					}
					return false;
				}
			}
		}
	return true;
	}
}
