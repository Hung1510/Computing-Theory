package Q1;

import java.util.*;

public class TM {

    private static class Transition {
        final String nextState;
        final char writeSymbol;
        final char move;

        Transition(String nextState, char writeSymbol, char move) {
            this.nextState = nextState;
            this.writeSymbol = writeSymbol;
            this.move = move;
        }
    }

    public static final char BLANK = '_';

    private final int tapeSize;
    private final Set<String> states;
    private final Map<String, Map<Character, Transition>> delta;

    private String startState;
    private String acceptState;
    private String rejectState;

    public TM(int tapeSize) {
        this.tapeSize = tapeSize;
        this.states = new LinkedHashSet<>();
        this.delta = new LinkedHashMap<>();
    }

    public void addState(String state) {
        states.add(state);
        delta.putIfAbsent(state, new LinkedHashMap<>());
    }

    public void setStartState(String state) {
        this.startState = state;
    }

    public void setAcceptState(String state) {
        this.acceptState = state;
    }

    public void setRejectState(String state) {
        this.rejectState = state;
    }

    public void addTransition(String state, char readSymbol,
                              String nextState, char writeSymbol, char move) {
        delta.computeIfAbsent(state, k -> new LinkedHashMap<>())
                .put(readSymbol, new Transition(nextState, writeSymbol, move));
    }

    public boolean accept(String input) {
        char[] tape = new char[tapeSize];
        Arrays.fill(tape, BLANK);
        for (int i = 0; i < input.length(); i++) {
            tape[i] = input.charAt(i);
        }

        String currentState = startState;
        int head = 0;

        while (!currentState.equals(acceptState) && !currentState.equals(rejectState)) {
            if (head < 0 || head >= tapeSize) {
                return false;
            }

            char symbol = tape[head];
            Map<Character, Transition> row = delta.get(currentState);

            if (row == null || !row.containsKey(symbol)) {
                return false;
            }

            Transition t = row.get(symbol);
            tape[head] = t.writeSymbol;
            currentState = t.nextState;
            head += (t.move == 'R') ? 1 : -1;
        }

        return currentState.equals(acceptState);
    }

    @Override
    public String toString() {
        return "TM{states=" + states + ", start=" + startState +
                ", accept=" + acceptState + ", reject=" + rejectState + "}";
    }
}
