package Q3;

public class Main {
    public static void main(String[] args) {
        MTM tm = new MTM(2, 100);

        tm.addState("q1");
        tm.addState("q2");
        tm.addState("q3");
        tm.addState("q4");
        tm.addState("q5");
        tm.addState("qaccept");
        tm.addState("qreject");

        tm.setStartState("q1");
        tm.setAcceptState("qaccept");
        tm.setRejectState("qreject");

        char B = MTM.blank;

        tm.addTransition("q1", new char[]{'0', B}, "q2", new char[]{'_', B}, new char[]{'R', 'R'});
        tm.addTransition("q1", new char[]{B, B}, "qreject", new char[]{B, B}, new char[]{'R', 'R'});

        tm.addTransition("q2", new char[]{'0', B}, "q3", new char[]{'x', B}, new char[]{'R', 'R'});
        tm.addTransition("q2", new char[]{'x', B}, "q2", new char[]{'x', B}, new char[]{'R', 'R'});
        tm.addTransition("q2", new char[]{B, B}, "qaccept", new char[]{B, B}, new char[]{'R', 'R'});

        tm.addTransition("q3", new char[]{'0', B}, "q4", new char[]{'0', B}, new char[]{'R', 'R'});
        tm.addTransition("q3", new char[]{'x', B}, "q3", new char[]{'x', B}, new char[]{'R', 'R'});
        tm.addTransition("q3", new char[]{B, B}, "q5", new char[]{B, B}, new char[]{'L', 'L'});

        tm.addTransition("q4", new char[]{'0', B}, "q3", new char[]{'x', B}, new char[]{'R', 'R'});
        tm.addTransition("q4", new char[]{'x', B}, "q4", new char[]{'x', B}, new char[]{'R', 'R'});
        tm.addTransition("q4", new char[]{B, B}, "qreject", new char[]{B, B}, new char[]{'R', 'R'});

        tm.addTransition("q5", new char[]{'0', B}, "q5", new char[]{'0', B}, new char[]{'L', 'L'});
        tm.addTransition("q5", new char[]{'x', B}, "q5", new char[]{'x', B}, new char[]{'L', 'L'});
        tm.addTransition("q5", new char[]{'_', B}, "q2", new char[]{'_', B}, new char[]{'R', 'R'});

        String[] tests = {"", "0", "00", "000", "0000", "00000", "000000", "0000000", "00000000"};

        for (String s : tests) {
            System.out.println("\"" + s + "\" -> " + (tm.accept(new String[]{s}) ? "Accept" : "Reject"));
        }
    }
}
