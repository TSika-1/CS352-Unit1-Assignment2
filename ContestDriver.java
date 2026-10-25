import java.util.ArrayList;
import java.util.Random;

public class ContestDriver {

	private static final Random RAND = new Random();

	// Step 3: coin flip, true about 50% of the time
	private static boolean flipCoin() {
		return RAND.nextBoolean();
	}

	// Step 2: build n uniquely named Contestants in ONE ArrayList
	private static ArrayList<Contestant> createContestants(int n) {
		ArrayList<Contestant> line = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			line.add(new Contestant("Contestant" + i, 18 + (i % 50)));
		}
		return line;
	}

	// Step 4: one round. Contestants are processed in their starting order.
	// 'next' is the index of the next unprocessed Contestant.
	//  - correct:   remove and insert at front; everything before 'next' shifts
	//               right, so the next unprocessed one is at next+1.
	//  - incorrect: remove and append; the next unprocessed one slides into 'next'.
	private static void playRound(ArrayList<Contestant> line, boolean verbose) {
		int n = line.size();
		int next = 0;
		for (int count = 0; count < n; count++) {
			Contestant c = line.get(next);
			line.remove(next);
			boolean correct = flipCoin();
			if (correct) {
				line.add(0, c);
				next++;
			} else {
				line.add(c);
			}
			if (verbose) {
				System.out.println(c.getName() + (correct ? " correct -> front : " : " incorrect -> end : ") + names(line));
			}
		}
	}

	private static String names(ArrayList<Contestant> line) {
		StringBuilder sb = new StringBuilder();
		for (Contestant c : line) sb.append(c.getName()).append(' ');
		return sb.toString();
	}

	// Step 5: record each Contestant's position at the end of a round
	private static void recordPositions(ArrayList<Contestant> line) {
		for (int i = 0; i < line.size(); i++) {
			line.get(i).recordPosition(i);
		}
	}

	// Steps 6/7: time the rounds only (fairness recording is NOT timed)
	private static void timedRun(int n, int rounds) {
		ArrayList<Contestant> line = createContestants(n);
		long start = System.nanoTime();
		for (int r = 0; r < rounds; r++) {
			playRound(line, false);
		}
		long ms = (System.nanoTime() - start) / 1_000_000;
		System.out.println(n + " contestants, " + rounds + " rounds: " + ms + " ms");
	}

	public static void main(String[] args) {
		System.out.println("Starting Simulation");

		// Step 0
		Contestant ac = new Contestant("Billy", 22);
		System.out.println(ac);

		// Step 2: small list
		ArrayList<Contestant> small = createContestants(10);
		System.out.println("Created " + small.size() + " contestants: " + names(small));

		// Steps 3-4: one verbose round on 5 contestants to verify by hand
		ArrayList<Contestant> five = createContestants(5);
		System.out.println("Start: " + names(five));
		playRound(five, true);
		System.out.println("End:   " + names(five));

		// Step 5: 8 contestants, 12 rounds, averages
		ArrayList<Contestant> eight = createContestants(8);
		for (int r = 0; r < 12; r++) {
			playRound(eight, false);
			recordPositions(eight);
		}
		for (Contestant c : eight) System.out.println(c);

		// Step 6: 43 contestants
		int[] roundCounts = {10, 100, 1000, 10000};
		for (int rc : roundCounts) timedRun(43, rc);

		// Step 7: 45,000 contestants (10000 rounds can take a long time; see notes)
		for (int rc : roundCounts) timedRun(45000, rc);

		System.out.println("Finished");
	}
}
