package Q3;

import java.util.*;

public class MTM {

    private static class Transition {
        final String nextState;
        final char[] writeSymbols;
        final char[] moves;

        Transition(String nextState, char[] writeSymbols, char[] moves) {
            this.nextState = nextState;
            this.writeSymbols = writeSymbols.clone();
            this.moves = moves.clone();
        }
    }

    public static final char blank = '_';

    private final int tapeNum;
    private final int tapeSize;
    private final Set<String> states;
    private final Map<String, Map<String, Transition>> delta;

    private String startState;
    private String acceptState;
    private String rejectState;

    public MTM(int tapeNum, int tapeSize) {
        this.tapeNum = tapeNum;
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

    public void addTransition(String state, char[] readSymbols,
                              String nextState, char[] writeSymbols, char[] moves) {
        String key = Arrays.toString(readSymbols);
        delta.computeIfAbsent(state, k -> new LinkedHashMap<>())
                .put(key, new Transition(nextState, writeSymbols, moves));
    }

    private static final int MAX_STEPS = 10_000;

    public boolean accept(String[] inputs) {
        char[][] tapes = new char[tapeNum][tapeSize];
        int[] heads = new int[tapeNum];

        for (int t = 0; t < tapeNum; t++) {
            Arrays.fill(tapes[t], blank);
            if (t < inputs.length && inputs[t] != null) {
                for (int c = 0; c < inputs[t].length(); c++) {
                    tapes[t][c] = inputs[t].charAt(c);
                }
            }
        }

        String currentState = startState;
        int steps = 0;

        while (!currentState.equals(acceptState) && !currentState.equals(rejectState)) {
            if (steps++ >= MAX_STEPS) return false;
            char[] reads = new char[tapeNum];
            for (int t = 0; t < tapeNum; t++) {
                if (heads[t] < 0 || heads[t] >= tapeSize) {
                    return false;
                }
                reads[t] = tapes[t][heads[t]];
            }

            Map<String, Transition> row = delta.get(currentState);
            String key = Arrays.toString(reads);

            if (row == null || !row.containsKey(key)) {
                return false;
            }

            Transition tr = row.get(key);

            for (int t = 0; t < tapeNum; t++) {
                tapes[t][heads[t]] = tr.writeSymbols[t];
                heads[t] += (tr.moves[t] == 'R') ? 1 : -1;
            }

            currentState = tr.nextState;
        }

        return currentState.equals(acceptState);
    }

    @Override
    public String toString() {
        return "MTM{tapes=" + tapeNum + ", states=" + states +
                ", start=" + startState + ", accept=" + acceptState +
                ", reject=" + rejectState + "}";
    }
}