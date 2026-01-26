package question_2a;

public class Main {
    public static void main(String[] args) {
        DFA dfa = new DFA();

        dfa.addState("s", false);
        dfa.addState("q1", true);
        dfa.addState("q2", false);
        dfa.addState("r1", true);
        dfa.addState("r2", false);

        dfa.setStartState("s");

        dfa.addTransition("s", 'a', "q1");
        dfa.addTransition("s", 'b', "r1");
        dfa.addTransition("q1", 'a', "q1");
        dfa.addTransition("q1", 'b', "q2");
        dfa.addTransition("q2", 'a', "q1");
        dfa.addTransition("q2", 'b', "q2");
        dfa.addTransition("r1", 'a', "r2");
        dfa.addTransition("r1", 'b', "r1");
        dfa.addTransition("r2", 'a', "r2");
        dfa.addTransition("r2", 'b', "r1");

        System.out.println(dfa);

        String[] testInputs = { "aa", "aabba", "bbb", "abababab" };
        System.out.println("Test Results:");
        for (String input : testInputs) {
            boolean result = dfa.accept(input);
            System.out.println(input + " -> " + (result ? "Accept" : "Reject"));
        }
    }
}
