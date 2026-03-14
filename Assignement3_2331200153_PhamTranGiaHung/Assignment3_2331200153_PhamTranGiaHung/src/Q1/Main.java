package Q1;

public class Main {
    public static void main(String[] args) {
        TM tm = new TM(50);

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

        tm.addTransition("q1", '0', "q2", '_', 'R');
        tm.addTransition("q1", '_', "qreject", '_', 'R');

        tm.addTransition("q2", '0', "q3", 'x', 'R');
        tm.addTransition("q2", 'x', "q2", 'x', 'R');
        tm.addTransition("q2", '_', "qaccept", '_', 'R');

        tm.addTransition("q3", '0', "q4", '0', 'R');
        tm.addTransition("q3", 'x', "q3", 'x', 'R');
        tm.addTransition("q3", '_', "q5", '_', 'L');

        tm.addTransition("q4", '0', "q3", 'x', 'R');
        tm.addTransition("q4", 'x', "q4", 'x', 'R');
        tm.addTransition("q4", '_', "qreject", '_', 'R');

        tm.addTransition("q5", '0', "q5", '0', 'L');
        tm.addTransition("q5", 'x', "q5", 'x', 'L');
        tm.addTransition("q5", '_', "q2", '_', 'R');

        String[] tests = {"", "0", "00", "000", "0000", "00000", "000000", "0000000", "00000000"};

        for (String s : tests) {
            System.out.println("\"" + s + "\" -> " + (tm.accept(s) ? "Accept" : "Reject"));
        }
    }
}
