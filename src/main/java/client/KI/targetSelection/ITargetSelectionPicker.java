package client.KI.targetSelection;

import java.util.Optional;

import client.game.ClientGameState;

public interface ITargetSelectionPicker {
	/**
	 * Select the next target for path finding, based on the information stored in
	 * the client game state (map, treasure information).
	 *
	 * @param clientGameState The game state of the current round
	 * @return The next target for path finding (if we can find one)
	 */
	Optional<TargetInformation> findTarget(final ClientGameState clientGameState);
}
