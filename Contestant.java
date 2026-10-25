/* Contestant.java:
 * This class extends Person while adding attributes and methods
 * related to recording performance in the specified contest.
 *
 * Author: Name and Student ID
 * Last updated:
 */
public class Contestant extends Person {
	// A Contestant IS-A Person that also keeps track of game data.

	// Step 1: constructor
	public Contestant(String name, int age) {
		super(name, age);
	}

	// Step 5: statistics. Store only a running total and a count
	// (not every position) so 1,000,000 Contestants stay cheap in memory.
	private long positionSum = 0;
	private int roundsRecorded = 0;

	// Call once per round with the Contestant's index at the end of the round.
	public void recordPosition(int position) {
		positionSum += position;
		roundsRecorded++;
	}

	public double getAveragePosition() {
		if (roundsRecorded == 0) return 0.0;
		return (double) positionSum / roundsRecorded;
	}

	public int getRoundsRecorded() {
		return roundsRecorded;
	}

	@Override
	public String toString() {
		return super.toString() + " avg position: " + String.format("%.2f", getAveragePosition());
	}
}