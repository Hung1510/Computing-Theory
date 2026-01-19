package question_2b;

import java.util.HashMap;
import java.util.HashSet;

public class State {
    private String name;
    private boolean isAccept;
    private HashMap<Character, HashSet<State>> transitions;
    private HashSet<State> epsilonTransitions;

    public State(String name, boolean isAccept) {
        this.name = name;
        this.isAccept = isAccept;
        this.transitions = new HashMap<>();
        this.epsilonTransitions = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean isAccept) {
        this.isAccept = isAccept;
    }

    public void addTransition(char symbol, State toState) {
        transitions.computeIfAbsent(symbol, k -> new HashSet<>()).add(toState);
    }

    public HashSet<State> getNextState(char symbol) {
        return transitions.getOrDefault(symbol, new HashSet<>());
    }
    public void addEpsilonTransition(State toState) {
        epsilonTransitions.add(toState);
    }

    public HashSet<State> getEpsilonTransitions() {
        return epsilonTransitions;
    }
    public HashMap<Character, HashSet<State>> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        return name + (isAccept ? " (accept)" : "");
    }
}
