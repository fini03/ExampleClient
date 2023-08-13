package client.KI.targetSelection;

import client.mapData.Coordinate;

/**
 * This class represents a target location on the map and whether it is a
 * priority target or not.
 * 
 * @author
 */
public class TargetInformation {
	private final Coordinate targetCoordinate;
	private final int isPriorityTarget;

	public TargetInformation(final Coordinate targetCoordinate, final int isPriorityTarget) {
		this.targetCoordinate = targetCoordinate;
		this.isPriorityTarget = isPriorityTarget;
	}

	public Coordinate getTargetCoordinate() {
		return this.targetCoordinate;
	}

	public int isPriorityTarget() {
		return this.isPriorityTarget;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		TargetInformation targetInformation = (TargetInformation) o;
		return this.targetCoordinate.equals(targetInformation.targetCoordinate)
				&& this.isPriorityTarget == targetInformation.isPriorityTarget;
	}
}
