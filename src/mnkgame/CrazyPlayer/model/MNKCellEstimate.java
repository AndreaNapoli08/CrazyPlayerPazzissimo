package mnkgame.CrazyPlayer.model;

import mnkgame.MNKCell;

import java.util.Comparator;

public class MNKCellEstimate extends MNKCell {
	double estimate;

	public MNKCellEstimate(int i, int j, double estimate) {
		super(i, j);
		this.estimate = estimate;
	}

	public double getEstimate() {
		return estimate;
	}

	@Override
	public String toString() {
		return super.toString() + " - " + this.estimate;
	}

	public static Comparator<MNKCellEstimate> getCellComparator(boolean firstPlayerTurn) {
		return new Comparator<MNKCellEstimate>() {
			@Override
			public int compare(MNKCellEstimate c1, MNKCellEstimate c2) {
				if (c1.getEstimate() - c2.getEstimate() == 0.0) return 1;    // TreeSet non contiene "duplicati"
				return (int) (firstPlayerTurn ?
						(c1.getEstimate() - c2.getEstimate()) :
						(c2.getEstimate() - c1.getEstimate()));
			}
		};
	}

}
