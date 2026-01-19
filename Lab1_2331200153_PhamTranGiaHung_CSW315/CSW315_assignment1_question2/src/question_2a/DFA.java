package question_2a;

import java.util.HashMap;

public class DFA {
    private HashMap<String, State> states;
    private State startState;

    public DFA() {
        this.states = new HashMap<>();
        this.startState = null;
    }
    public void addState(String stateName, boolean isAccept) {
        State newState = new State(stateName, isAccept);
        states.put(stateName, newState);
    }

    public void setStartState(String stateName) {
        State state = states.get(stateName);
        if (state == null) {
            throw new IllegalArgumentException("State " + stateName + " does not exist");
        }
        this.startState = state;
    }

    public void addTransition(String fromStateName, char symbol, String toStateName) {
        State fromState = states.get(fromStateName);
        State toState = states.get(toStateName);
        if (fromState == null) {
            throw new IllegalArgumentException("State " + fromStateName + " does not exist");
        }
        if (toState == null) {
            throw new IllegalArgumentException("State " + toStateName + " does not exist");
        }
        fromState.addTransition(symbol, toState);
    }

    public boolean accept(String str) {
        if (startState == null) {
            return false;
        }
        State currentState = startState;
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            State nextState = currentState.getNextState(symbol);
            if (nextState == null) {
                return false;
            }
            currentState = nextState;
        }
        return currentState.isAccept();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DFA:\n");
        sb.append("Start State: ").append(startState != null ? startState.getName() : "None").append("\n");
        sb.append("States:\n");

        for (State state : states.values()) {
            sb.append("  ").append(state).append("\n");
            for (HashMap.Entry<Character, State> transition : state.getTransitions().entrySet()) {
                sb.append("    ").append(transition.getKey())
                        .append(" -> ").append(transition.getValue().getName()).append("\n");
            }
        }
        return sb.toString();
    }
}
