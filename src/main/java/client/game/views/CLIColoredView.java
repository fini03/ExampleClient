package client.game.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import client.KI.KIEnumeration.EClientState;
import client.game.ClientGameState;
import client.game.color.ColorManager;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientFortState;
import client.mapData.mapEnumeration.EClientPositionState;
import client.mapData.mapEnumeration.EClientTerrain;
import client.mapData.mapEnumeration.EClientTreasureState;

public class CLIColoredView implements PropertyChangeListener {
	@Override
	/**
	 * Checks the send event and prints it if its a a new gameState 
	 */
	public void propertyChange(final PropertyChangeEvent evt) {
		if ((evt.getPropertyName().equals("ClientGameState")) && (evt.getNewValue() instanceof ClientGameState)) {
			System.out.println(this.printEndGame(((ClientGameState) evt.getNewValue())));
		}
	}
	
	/**
	 * We use this to print the final state of the player
	 * @param clientGameState The current gameState
	 * @return The map with the message (if the game ended) or just the map 
	 */
    private String printEndGame(final ClientGameState clientGameState) {
        if(clientGameState.getClientState().equals(EClientState.WON)) {
            return drawGameMap(clientGameState) + ColorManager.greenFont("YOU HAVE WON!") + " \n\n";
        }
        if(clientGameState.getClientState().equals(EClientState.LOST)) {
            return drawGameMap(clientGameState) + ColorManager.redFont("YOU HAVE LOST!") + " \n\n";
        }
        
        return drawGameMap(clientGameState);
    }
	
    /**
     * We use this to print our current map
     * @param clientGameState The current gameState
     * @return mapTiles as string
     */
	private String drawGameMap(final ClientGameState clientGameState) {
		final Coordinate maxCoord = clientGameState.getMap().getMaxCoord();
		final Map<Coordinate, ClientTile> map = clientGameState.getMap().getMap();
		final boolean hasTreasure = clientGameState.isHasCollectedTreasure();
		final StringBuilder builder = new StringBuilder();

		for (int yCoord = 0; yCoord <= maxCoord.getY(); yCoord++) {
			for (int xCoord = 0; xCoord <= maxCoord.getX(); xCoord++) {
				final Coordinate coordinate = new Coordinate(xCoord, yCoord);
				final ClientTile tile = map.get(coordinate);
				builder.append(drawClientTile(tile, hasTreasure));
			}
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * We use this to print the data of our tiles
	 * @param currentTile The tile that should be printed
	 * @return The tile information as symbol-strings.
	 */
	private String drawClientTile(final ClientTile currentTile, final boolean hasTreasure) {
		if (currentTile == null) {
			return " ";
		}
		final EClientFortState fort = currentTile.getFortState();
		final EClientTerrain terrain = currentTile.getTerrain();
		final EClientTreasureState treasure = currentTile.getClientTreasureState();
		final EClientPositionState position = currentTile.getClientPositionState();

		if (fort == EClientFortState.EnemyFortPresent) {
			return ColorManager.yellowFont("| X | ");
		} else if (fort == EClientFortState.MyFortPresent) {
			return ColorManager.yellowFont("| # | ");
		}
		
		if (position == EClientPositionState.BothPlayerPosition) {
			return ColorManager.redFont("[ = ] ");
		} else if (position == EClientPositionState.EnemyPlayerPosition) {
			return ColorManager.redFont("[ 8 ] ");
		} else if (position == EClientPositionState.MyPlayerPosition) {
			if(hasTreasure) {
				return ColorManager.magentaFont("[ 0 ] ");
			}
			return ColorManager.blueFont("[ 0 ] ");
		}
		
		if (treasure == EClientTreasureState.MyTreasureIsPresent) {
			return ColorManager.yellowFont("| $ | ");
		}

		return printTerrain(terrain);
	}
	
	/**
	 * Returns a colored string representation of the terrain
	 * @return A colored string representation of the terrain
    */
	private String printTerrain(final EClientTerrain terrain) {
		switch (terrain) {
			case GRASS:
				return ColorManager.greenFont("| . | ");
			case WATER:
				return ColorManager.cyanFont("( ~ ) ");
			case MOUNTAIN:
				return ColorManager.whiteFont("[ ^ ] ");
			default: 
				return ColorManager.redFont("| ? | ");
		}
	}
}
