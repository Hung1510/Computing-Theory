package question_2a;

import java.util.ArrayList;
import java.util.HashMap;

public class DFA {
    public ArrayList<State> states;
    public HashMap<String, State> stateMap;
    public State startState;

    public DFA() {
        this.states = new ArrayList<>();
        this.stateMap = new HashMap<>();
        this.startState = null;
    }

    public void addState(String state, boolean accepted) {
        State newState = new State(state, accepted);
        states.add(newState);
        stateMap.put(state, newState);
    }

    public void setStartState(String state) {
        State stateObj = stateMap.get(state);
        if (stateObj == null) {
            throw new IllegalArgumentException("State " + state + " does not exist");
        }
        this.startState = stateObj;
    }

    public void addTransition(String fromState, char symbol, String toState) {
        State fromStateObj = stateMap.get(fromState);
        State toStateObj = stateMap.get(toState);
        if (fromStateObj == null) {
            throw new IllegalArgumentException("State " + fromState + " does not exist");
        }
        if (toStateObj == null) {
            throw new IllegalArgumentException("State " + toState + " does not exist");
        }
        fromStateObj.addTransition(symbol, toStateObj);
    }

    public boolean accept(String input) {
        if (startState == null) {
            return false;
        }
        State currentState = startState;
        for (char symbol : input.toCharArray()) {
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

        for (State state : states) {
            sb.append("  ").append(state).append("\n");
            for (HashMap.Entry<Character, State> transition : state.getTransitions().entrySet()) {
                sb.append("    ").append(transition.getKey())
                        .append(" -> ").append(transition.getValue().getName()).append("\n");
            }
        }
        return sb.toString();
    }
}
