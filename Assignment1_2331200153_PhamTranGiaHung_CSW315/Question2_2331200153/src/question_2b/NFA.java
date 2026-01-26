package question_2b;

import java.util.HashMap;
import java.util.HashSet;

public class NFA {
    private HashMap<String, State> states;
    private State startState;

    public NFA() {
        this.states = new HashMap<>();
        this.startState = null;
    }

    public void addState(String state, boolean accepted) {
        State newState = new State(state, accepted);
        states.put(state, newState);
    }

    public void setStartState(String state) {
        State stateObj = states.get(state);
        if (stateObj == null) {
            throw new IllegalArgumentException("State " + state + " does not exist");
        }
        this.startState = stateObj;
    }

    public void addTransition(String fromState, char symbol, String toState) {
        State fromStateObj = states.get(fromState);
        State toStateObj = states.get(toState);
        if (fromStateObj == null) {
            throw new IllegalArgumentException("State " + fromState + " does not exist");
        }
        if (toStateObj == null) {
            throw new IllegalArgumentException("State " + toState + " does not exist");
        }
        fromStateObj.addTransition(symbol, toStateObj);
    }

    public void addEpsilonTransition(String fromState, String toState) {
        State fromStateObj = states.get(fromState);
        State toStateObj = states.get(toState);

        if (fromStateObj == null) {
            throw new IllegalArgumentException("State " + fromState + " does not exist");
        }
        if (toStateObj == null) {
            throw new IllegalArgumentException("State " + toState + " does not exist");
        }

        fromStateObj.addEpsilonTransition(toStateObj);
    }

    private void computeEpsilonClosure(State state, HashSet<State> closure) {
        if (closure.contains(state)) {
            return;
        }
        closure.add(state);
        for (State epsilonState : state.getEpsilonTransitions()) {
            computeEpsilonClosure(epsilonState, closure);
        }
    }

    private HashSet<State> epsilonClosure(HashSet<State> states) {
        HashSet<State> closure = new HashSet<>();
        for (State state : states) {
            computeEpsilonClosure(state, closure);
        }
        return closure;
    }

    private HashSet<State> move(HashSet<State> currentStates, char symbol) {
        HashSet<State> nextStates = new HashSet<>();
        for (State state : currentStates) {
            nextStates.addAll(state.getNextState(symbol));
        }
        return nextStates;
    }

    public boolean accept(String input) {
        if (startState == null) {
            return false;
        }
        HashSet<State> currentStates = new HashSet<>();
        currentStates.add(startState);
        currentStates = epsilonClosure(currentStates);

        for (char symbol : input.toCharArray()) {
            currentStates = move(currentStates, symbol);
            currentStates = epsilonClosure(currentStates);

            if (currentStates.isEmpty()) {
                return false;
            }
        }

        for (State state : currentStates) {
            if (state.isAccept()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NFA:\n");
        sb.append("Start State: ").append(startState != null ? startState.getName() : "None").append("\n");
        sb.append("States:\n");

        for (State state : states.values()) {
            sb.append("  ").append(state).append("\n");

            // Regular transitions
            for (HashMap.Entry<Character, HashSet<State>> transition : state.getTransitions().entrySet()) {
                for (State toState : transition.getValue()) {
                    sb.append("    ").append(transition.getKey())
                            .append(" -> ").append(toState.getName()).append("\n");
                }
            }

            // Epsilon transitions
            for (State epsilonState : state.getEpsilonTransitions()) {
                sb.append("    ε -> ").append(epsilonState.getName()).append("\n");
            }
        }
        return sb.toString();
    }
}