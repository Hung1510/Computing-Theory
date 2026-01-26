package question_2b;

public class Main {
    public static void main(String[] args) {
        NFA nfa = new NFA();

        nfa.addState("q1", false);
        nfa.addState("q2", false);
        nfa.addState("q3", false);
        nfa.addState("q4", true);

        nfa.setStartState("q1");

        nfa.addTransition("q1", '0', "q1");
        nfa.addTransition("q1", '1', "q1");
        nfa.addTransition("q1", '1', "q2");
        nfa.addTransition("q2", '0', "q3");
        nfa.addEpsilonTransition("q2", "q3");
        nfa.addTransition("q3", '1', "q4");
        nfa.addTransition("q4", '0', "q4");
        nfa.addTransition("q4", '1', "q4");

        System.out.println(nfa);

        String[] testInputs = { "11", "01", "101", "100" };

        System.out.println("Test Results:");
        for (String input : testInputs) {
            boolean result = nfa.accept(input);
            System.out.println(input + " -> " + (result ? "Accept" : "Reject"));
        }
    }
}