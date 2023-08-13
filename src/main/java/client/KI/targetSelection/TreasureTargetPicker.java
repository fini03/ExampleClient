package client.KI.targetSelection;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.game.ClientGameState;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTreasureState;

/**
 * Implementation of the {@link ITargetSelectionPicker} interface that selects
 * the player's own treasure as the target for the game client to aim for.
 * 
 * @author
 */
public class TreasureTargetPicker implements ITargetSelectionPicker {
	private final Logger logger = LoggerFactory.getLogger(TreasureTargetPicker.class);
	@Override
	/**
	 * Finds the player's own treasure on the given game state map.
	 * @param clientGameState The current game state of the client
	 * @return TargetInformation (coordinate, isPriorityTarget) of the next target.
	 */
	public Optional<TargetInformation> findTarget(final ClientGameState clientGameState) {
		if (clientGameState.isHasCollectedTreasure()) {
			logger.info("Treasure has already been collected!");
	        return Optional.empty();
		}
		
        final Map<Coordinate, ClientTile> map = clientGameState.getMap().getMap();
        final Optional<Coordinate> targetCoordinate = map.entrySet().stream().filter(eachTile -> eachTile.getValue().getClientTreasureState()
                .equals(EClientTreasureState.MyTreasureIsPresent)).map(Map.Entry::getKey).findFirst();
        final int isPriorityTarget = 2;
        
        if (targetCoordinate.isPresent()) {
        	logger.info("Treasure has already been spotted!");
            return Optional.of(new TargetInformation(targetCoordinate.get(), isPriorityTarget));
		}
        
		return Optional.empty();
	}
}
