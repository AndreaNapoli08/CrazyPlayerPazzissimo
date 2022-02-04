package mnkgame.CrazyPlayer;

import mnkgame.MNKCell;
import mnkgame.MNKPlayer;

import java.util.TreeSet;

public class CrazyPlayer implements MNKPlayer {
	private AIHelper ai;
	private int M, N, K;
	private AIHelper.MNKBoardEstimate board;

	@Override
	public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
		this.M = M;
		this.N = N;
		this.K = K;
		ai = new AIHelper(M, N, K, first, timeout_in_secs);
		board = new AIHelper.MNKBoardEstimate(M, N, K);
	}

	@Override
	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
		// TODO: valutare aggiunta di try-catch

		long start = System.currentTimeMillis();
		ai.setStart(start);

		if (MC.length > 0) {
			// Recover the last move
			MNKCell c = MC[MC.length - 1];
			board.markCell(c.i, c.j);
			ai.updateBounds(c);
		} else {
			// First to play
			MNKCell middleCell = new MNKCell(M / 2, N / 2);
			board.markCell(middleCell.i, middleCell.j);
			ai.updateBounds(middleCell);
			return middleCell;
		}

		// Only one possible move
		if (FC.length == 1)
			return FC[0];

		// ALPHABETA
		MNKCell bestCell = null;
		double bestEval = Double.NEGATIVE_INFINITY;
		int depth = 0;
		TreeSet<AIHelper.MNKCellEstimate> cells = ai.getBestMoves(FC, board, true);
		for (AIHelper.MNKCellEstimate cell : cells) {
			if (ai.isTimeEnded()) break;

			board.markCell(cell.i, cell.j);
			double moveEval = ai.alphabeta(board, cell.estimate, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depth);
			if (moveEval >= bestEval) {
				bestEval = moveEval;
				bestCell = cell;
			}
			board.unmarkCell();
		}
		board.markCell(bestCell.i, bestCell.j);
		ai.updateBounds(bestCell);

		System.out.println(System.currentTimeMillis() - start);
		ai.printBounds();

		return bestCell;
	}

	@Override
	public String playerName() {
		return "CrazyPlayerPazzissimo";
	}
}
