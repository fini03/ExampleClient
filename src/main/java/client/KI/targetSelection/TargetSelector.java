package client.KI.targetSelection;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import client.game.ClientGameState;
import client.mapData.ClientTile;
import client.mapData.Coordinate;

/**
 * We use the target selector as a manager for selecting the next goal that
 * should be visited by our path finding.
 *
 * @author
 */
public class TargetSelector {
	private static final ITargetSelectionPicker[] targetSelectionPickers = new ITargetSelectionPicker[] {
			new TreasureTargetPicker(), new EnemyFortTargetPicker(), new TreasureDiscoveryTargetPicker(),
			new EnemyFortDiscoveryTarget() };

	/**
	 * Select the next target for path finding, based on the information stored in
	 * the client game state (map, treasure information).
	 *
	 * @param clientGameState The game state of the current round
	 * @return The next target for path finding (if we can find one)
	 */
	public static Optional<TargetInformation> findTarget(final ClientGameState clientGameState) {
		return Arrays.stream(targetSelectionPickers)
				.map(targetSelectionPickers -> targetSelectionPickers.findTarget(clientGameState))
				.filter(Optional::isPresent).findFirst().orElse(findNextCoveredTile(clientGameState));
	}

	/**
	 * Selects the next uncovered tile as a target
	 * 
	 * @param clientGameState The game state of the current round
	 * @return The next uncovered tile
	 */
	private static Optional<TargetInformation> findNextCoveredTile(final ClientGameState clientGameState) {
		final Map<Coordinate, ClientTile> map = clientGameState.getMap().getMap();
	    final int isPriorityTarget = 0;
	    
		return map.entrySet().stream().filter(eachTile -> eachTile.getValue().isCovered())
				.map(entry -> new TargetInformation(entry.getKey(), isPriorityTarget)).findFirst();
	}
}
