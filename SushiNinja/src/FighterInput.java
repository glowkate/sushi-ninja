import java.util.ArrayList;

/*
    An interface to be used by classes with the job of giving fighters instructions.
    Examples of where it might be used would be for player turn input or cpu players.
 */

public interface FighterInput {
    void doTurn(final Fighter FIGHTER, final Map MAP, final ArrayList<Fighter> ACTIVE_FIGHTERS);
}