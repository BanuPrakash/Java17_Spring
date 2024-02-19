public class SwitchExample1 {
    public static void main(String[] args) {
        System.out.println(getValue("c"));

    }

    private static int getValue(String mode) {
        // java 13 introduced yield instead of using Arrow
        int result = switch (mode) {
            case "a", "b": yield  1;
            case "c": yield 2;
            case "d", "e": {
                System.out.println("d and e");
                yield 3; // instead of return use yield
            }
            default: yield  0;
        };
        return  result;
    }
}
