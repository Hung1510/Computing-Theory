package question_2a;

import java.util.HashMap;

public class State {
    private String name;
    private boolean isAccept;
    private HashMap<Character, State> transitions;

    public State(String name, boolean isAccept) {
        this.name = name;
        this.isAccept = isAccept;
        this.transitions = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void addTransition(char symbol, State toState) {
        transitions.put(symbol, toState);
    }

    public State getNextState(char symbol) {
        return transitions.get(symbol);
    }

    public HashMap<Character, State> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        return name + (isAccept ? " (accept)" : "");
    }
}
