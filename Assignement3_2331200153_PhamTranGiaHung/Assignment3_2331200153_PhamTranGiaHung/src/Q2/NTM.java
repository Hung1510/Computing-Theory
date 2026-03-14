package Q2;

import java.util.*;

public class NTM {

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

    private static class Config {
        final String state;
        final char[] tape;
        final int head;
        final int steps;

        Config(String state, char[] tape, int head, int steps) {
            this.state = state;
            this.tape = tape.clone();
            this.head = head;
            this.steps = steps;
        }
    }

    private static final int max_steps = 10_000;

    public static final char blank = '_';

    private final int tapeSize;
    private final Set<String> states;
    private final Map<String, Map<Character, List<Transition>>> delta;

    private String startState;
    private String acceptState;
    private String rejectState;

    public NTM(int tapeSize) {
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
                .computeIfAbsent(readSymbol, k -> new ArrayList<>())
                .add(new Transition(nextState, writeSymbol, move));
    }

    public boolean accept(String input) {
        char[] initTape = new char[tapeSize];
        Arrays.fill(initTape, blank);
        for (int i = 0; i < input.length(); i++) {
            initTape[i] = input.charAt(i);
        }

        Queue<Config> queue = new ArrayDeque<>();
        queue.add(new Config(startState, initTape, 0, 0));

        while (!queue.isEmpty()) {
            Config cfg = queue.poll();

            if (cfg.state.equals(acceptState)) {
                return true;
            }
            if (cfg.state.equals(rejectState)) {
                continue;
            }
            if (cfg.head < 0 || cfg.head >= tapeSize) {
                continue;
            }
            if (cfg.steps >= max_steps) {
                continue;
            }

            char symbol = cfg.tape[cfg.head];
            Map<Character, List<Transition>> row = delta.get(cfg.state);
            if (row == null) continue;

            List<Transition> choices = row.get(symbol);
            if (choices == null || choices.isEmpty()) continue;

            for (Transition t : choices) {
                char[] newTape = cfg.tape.clone();
                newTape[cfg.head] = t.writeSymbol;
                int newHead = cfg.head + (t.move == 'R' ? 1 : -1);
                queue.add(new Config(t.nextState, newTape, newHead, cfg.steps + 1));
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "NTM{states=" + states + ", start=" + startState +
                ", accept=" + acceptState + ", reject=" + rejectState + "}";
    }
}