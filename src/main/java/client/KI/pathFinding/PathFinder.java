package client.KI.pathFinding;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.KI.KIEnumeration.EClientMove;
import client.KI.pathFinding.support.PathToMoves;
import client.KI.pathFinding.support.PlayerFinder;
import client.KI.targetSelection.TargetInformation;
import client.KI.targetSelection.TargetSelector;
import client.exceptions.MovesQueueEmptyException;
import client.exceptions.PathNotFoundException;
import client.game.ClientGameState;
import client.mapData.ClientTile;
import client.mapData.Coordinate;

public class PathFinder {
	private final Logger logger = LoggerFactory.getLogger(PathFinder.class);
	private final Queue<EClientMove> moves = new ArrayDeque<>();
	private int lastTargetPriority = -1;
	private TargetInformation lastTarget;

	public EClientMove getNextMove(final ClientGameState clientGameState) {
        final Optional<TargetInformation> target = TargetSelector.findTarget(clientGameState);
        final Map<Coordinate, ClientTile> map = clientGameState.getMap().getMap();
        if (target.isPresent()) {
            if (lastTargetPriority < target.get().isPriorityTarget()) {
                logger.info("We found an important target at {}, clearing move queue...", target.get().getTargetCoordinate().toString());
                moves.clear();
                lastTargetPriority = target.get().isPriorityTarget();
            }

            if (!moves.isEmpty() && lastTargetAlreadyUncovered(map)) {
                moves.clear();
            }

            if (moves.isEmpty()) {
                lastTarget = target.get();
                generateNewMoves(clientGameState, target.get());
            }
        }
        
        try {
            if (moves.isEmpty()) {
                throw new MovesQueueEmptyException("Our moving queue is empty!");
            }    
        } catch (MovesQueueEmptyException emptyQueueExc) {
            logger.error("ERROR: There is moves to perform ", emptyQueueExc.getMessage());
        }
        
        final EClientMove move = moves.remove();
        logger.info(move.name());
        
        return move;
    }

	private boolean lastTargetAlreadyUncovered(final Map<Coordinate, ClientTile> map) {
		if(!map.get(lastTarget.getTargetCoordinate()).isCovered()) {
			return true;
		}
		return false;
	}

	private void generateNewMoves(final ClientGameState clientGameState, final TargetInformation target) {
		logger.trace("Generating moves...");
		final List<Coordinate> path = constructPath(clientGameState, target.getTargetCoordinate());
		moves.addAll(PathToMoves.transformPathToMoves(path));
	}

	private List<Coordinate> constructPath(final ClientGameState clientGameState, final Coordinate target) {
		logger.trace("Constructing path...");

		final Map<Coordinate, ClientTile> map = clientGameState.getMap().getMap();
		final Optional<Coordinate> start = PlayerFinder.findPlayerPosition(map);
		final List<Coordinate> path = DijkstraAlgorithm.findPath(map, start.get(), target);

		logger.debug("Our current player is at {}", start.get().toString());
		logger.info(path.toString());

		try {
			if (path.size() <= 1) {
				throw new PathNotFoundException("Our path we want to calculate is empty!");
			}
		} catch (PathNotFoundException pathNotFoundExc) {
			logger.error("ERROR: There is no path available ", pathNotFoundExc.getMessage());
		}

		return path;
	}
}
