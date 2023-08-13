package client.game.views.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import client.KI.KIEnumeration.EClientState;
import client.game.ClientGameState;
import client.game.GameModel;
import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientFortState;
import client.mapData.mapEnumeration.EClientPositionState;
import client.mapData.mapEnumeration.EClientTerrain;
import client.mapData.mapEnumeration.EClientTreasureState;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GuiView implements PropertyChangeListener {
	private final SimpleBooleanProperty hasTreasure = new SimpleBooleanProperty(false);
	private final Label gameMap;
	private final Label endScreen;
	private final Group view;
	private final GameModel model;
	private final static int tileSize = 50;
	private final static int rectangleSize = 20;
	private final static int circleSize = 15;
	private final static int fontSize = 30;

	public GuiView(final GameModel model) {
		this.model = model;
		this.model.addPropertyChangeListener(this);
		
		gameMap = new Label();
		endScreen = new Label();
		view = new Group();
		
		final VBox gameLayout = new VBox();
		gameLayout.setAlignment(Pos.CENTER);
		gameLayout.setSpacing(10);

		gameMap.setFont(Font.font("monospaced", fontSize));
		gameLayout.getChildren().add(gameMap);

		view.getChildren().add(gameLayout);
	}

	@Override
	/**
	 * Checks the send event and prints it if it's a new gameState
	 */
	public void propertyChange(final PropertyChangeEvent evt) {
		Platform.runLater(() -> {
			if (evt.getPropertyName().equals("ClientGameState") && evt.getNewValue() instanceof ClientGameState) {
				final ClientGameState clientGameState = (ClientGameState) evt.getNewValue();
				updateGameMap(clientGameState.getMap());
				updateHasTreasure(clientGameState.isHasCollectedTreasure());
				printEndGame(clientGameState.getClientState());
			}
		});
	}
	
	/**
	 * This method prints who won/lost when the games ends
	 * @param state The final state of the players 
	 */
	private void printEndGame(final EClientState state) {
		if (state == EClientState.WON) {
            endScreen.setTextFill(Color.GREEN);
            endScreen.setText("YOU HAVE WON!");
            endScreen.setFont(Font.font("monospaced", FontWeight.BOLD, fontSize));
            endScreen.relocate(0, 400);
            view.getChildren().add(endScreen);
        } else if (state == EClientState.LOST) {
            endScreen.setTextFill(Color.RED);
            endScreen.setText("YOU HAVE LOST!");
            endScreen.setFont(Font.font("monospaced", FontWeight.BOLD, fontSize));
            endScreen.relocate(0, 400);
            view.getChildren().add(endScreen);
        }
    }

	/**
	 * We use this to update the GUI with the new game state
	 * @param clientGameState The current gameState
	 */
	private void updateGameMap(final ClientMap clientMap) {
		final Coordinate maxCoord = clientMap.getMaxCoord();
		final Map<Coordinate, ClientTile> map = clientMap.getMap();
		final GridPane gridPane = new GridPane();

		for (int yCoord = 0; yCoord <= maxCoord.getY(); yCoord++) {
			for (int xCoord = 0; xCoord <= maxCoord.getX(); xCoord++) {
				final Coordinate coordinate = new Coordinate(xCoord, yCoord);
				final ClientTile tile = map.get(coordinate);
				final Region tileRegion = drawClientTile(tile);
				gridPane.add(tileRegion, xCoord, yCoord); // add the tile region to the grid pane
			}
		}
		view.getChildren().clear();
		view.getChildren().add(gridPane);

		// set the preferred size of each tile in the grid pane
		// set the tile size here
		for (int xCoord = 0; xCoord <= maxCoord.getX(); xCoord++) {
			final ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setPrefWidth(tileSize);
			gridPane.getColumnConstraints().add(colConstraints);
		}
		for (int yCoord = 0; yCoord <= maxCoord.getY(); yCoord++) {
			final RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setPrefHeight(tileSize);
			gridPane.getRowConstraints().add(rowConstraints);
		}
		
		view.getChildren().clear();
		view.getChildren().add(gridPane);
	}

	/**
	 * We use this to update the hasTreasure property
	 * 
	 * @param hasCollectedTreasure The new value for hasTreasure
	 */
	private void updateHasTreasure(final boolean hasCollectedTreasure) {
		hasTreasure.set(hasCollectedTreasure);
	}
	
	/**
	 * This method draws the tile on the GUI depending on the information we have
	 * @param currentTile The ClientTile to be printed
	 * @return
	 */
	private Region drawClientTile(final ClientTile currentTile) {
	    Region region = new Region();
	    region.setPrefSize(rectangleSize, rectangleSize); // set the size of the rectangle
	    
	    if (currentTile == null) {
	        region.setStyle("-fx-background-color: white;"); // set the background color to white
	        return region;
	    }
	    
	    final EClientFortState fort = currentTile.getFortState();
	    final EClientTerrain terrain = currentTile.getTerrain();
	    final EClientTreasureState treasure = currentTile.getClientTreasureState();
	    final EClientPositionState position = currentTile.getClientPositionState();
	    
	    if (fort == EClientFortState.EnemyFortPresent || fort == EClientFortState.MyFortPresent) {
	        region.setStyle("-fx-background-color: goldenrod;");
	    } else if (position == EClientPositionState.BothPlayerPosition) {
	    	region.setStyle("-fx-background-color: green;");
	    	region = addCircleOnPlayerTile(region);
	    } else if(position == EClientPositionState.EnemyPlayerPosition) {
	    	region.setStyle("-fx-background-color: crimson;");
	    	region = addCircleOnPlayerTile(region);
	    } else if(position == EClientPositionState.MyPlayerPosition) {
	    	if (this.hasTreasure.get()) {
		    	region.setStyle("-fx-background-color: yellow;");
	    	} else {
	    		region.setStyle("-fx-background-color: cadetblue;");
	    	}
	    	region = addCircleOnPlayerTile(region);
	    } else if (treasure == EClientTreasureState.MyTreasureIsPresent) {
	        region.setStyle("-fx-background-color: gold;");
	    } else {    
		    switch (terrain) {
	    		case GRASS:
	    			region.setStyle("-fx-background-color: lightgreen;");
	    			break;
	    		case WATER:
	    			region.setStyle("-fx-background-color: aqua;");
	    			break;
	    		case MOUNTAIN:
	    			region.setStyle("-fx-background-color: darkgray;");
	    			break;
		    }
	   }
	      	    
	    return region;
	}

	public Parent asParent() {
		return view;
	}
	
	/**
	 * This method adds a circle on the clientile if the player is present
	 * @param region The tile with the color
	 * @return a StackPane with the new tile and a circle on top
	 */
	private Region addCircleOnPlayerTile(final Region region) {
		// add a circle on the player tile
        final Circle circle = new Circle(circleSize);
        circle.setFill(Color.BLACK);
        circle.setStroke(Color.BLACK);
        
        final StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(region, circle);
        
        return stackPane;
	}
}