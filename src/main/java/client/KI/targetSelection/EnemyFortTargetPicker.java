package client.KI.targetSelection;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.game.ClientGameState;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientFortState;
/**
 * Implementation of the {@link ITargetSelectionPicker} interface that selects
 * the enemy's fort as the target for the game client to aim for.
 * 
 * @author
 */
public class EnemyFortTargetPicker implements ITargetSelectionPicker {
	private final Logger logger = LoggerFactory.getLogger(EnemyFortTargetPicker.class);
	@Override
	/**
	 * Finds the enemy's fort on the given game state map.
	 * @param clientGameState The current game state of the client
	 * @return TargetInformation (coordinate, isPriorityTarget) of the next target.
	 */
	public Optional<TargetInformation> findTarget(final ClientGameState clientGameState) {
	    // Only look for fort if we have not collected treasure
	    if (!clientGameState.isHasCollectedTreasure()) {
	        return Optional.empty();
	    }
	    
	    final Map<Coordinate, ClientTile> map = clientGameState.getMap().getMap();
	    final Optional<Coordinate> fortPosition = map.entrySet().stream()
	            .filter(eachTile -> eachTile.getValue().getClientFortState().equals(EClientFortState.EnemyFortPresent))
	            .map(Map.Entry::getKey)
	            .findFirst();
	    final int isPriorityTarget = 4;
	    
	    if (fortPosition.isPresent()) {
	    	logger.info("Enemy's castle has been spotted!");
	        return Optional.of(new TargetInformation(fortPosition.get(), isPriorityTarget));
	    }

	    return Optional.empty();
	}

}
