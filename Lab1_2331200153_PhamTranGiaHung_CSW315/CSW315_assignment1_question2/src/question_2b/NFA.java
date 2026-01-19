package question_2b;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class NFA {
    private HashMap<String, State> states;
    private State startState;

    public NFA() {
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

    public void addEpsilonTransition(String fromStateName, String toStateName) {
        State fromState = states.get(fromStateName);
        State toState = states.get(toStateName);

        if (fromState == null) {
            throw new IllegalArgumentException("State " + fromStateName + " does not exist");
        }
        if (toState == null) {
            throw new IllegalArgumentException("State " + toStateName + " does not exist");
        }

        fromState.addEpsilonTransition(toState);
    }

    private HashSet<State> epsilonClosure(HashSet<State> states) {
        HashSet<State> closure = new HashSet<>(states);
        Stack<State> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            State state = stack.pop();
            for (State epsilonState : state.getEpsilonTransitions()) {
                if (!closure.contains(epsilonState)) {
                    closure.add(epsilonState);
                    stack.push(epsilonState);
                }
            }
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

    public boolean accept(String str) {
        if (startState == null) {
            return false;
        }
        HashSet<State> currentStates = new HashSet<>();
        currentStates.add(startState);
        currentStates = epsilonClosure(currentStates);

        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
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
        sb.append("DFA:\n");
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

            // Epsilon
            for (State epsilonState : state.getEpsilonTransitions()) {
                sb.append("    ε -> ").append(epsilonState.getName()).append("\n");
            }
        }
        return sb.toString();
    }
}
