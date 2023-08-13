package client.game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
/**
 * We use this class to store the current gameState
 * as our model for the MVC pattern
 * @author
 */
public class GameModel {
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private ClientGameState clientGameState = new ClientGameState();
	
	/**
	 * We add a view to our model to print the changes
	 * @param listener View that gets added
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	/**
	 * We use this to update our gameState when changes apply
	 * @param newClientGameState The new gameState of the current game
	 * which gets send to the view
	 */
	public void setClientGameState(final ClientGameState newClientGameState) {
        this.propertyChangeSupport.firePropertyChange("ClientGameState", this.clientGameState, newClientGameState);
        this.clientGameState = this.clientGameState.update(newClientGameState);
    }
}
