public class RecursiveTextTools {

    public static String reverse(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        return reverse(str.substring(1)) + str.charAt(0);
    }

    public static boolean isPalindrome(String str) {
        if (str == null) return false;
        String clean = str.replaceAll("\\s+", "").toLowerCase();
        return isPalindromeHelper(clean);
    }

    private static boolean isPalindromeHelper(String str) {
        if (str.length() <= 1) {
            return true;
        }
        if (str.charAt(0) != str.charAt(str.length() - 1)) {
            return false;
        }
        return isPalindromeHelper(str.substring(1, str.length() - 1));
    }

    public static int countCharacter(String str, char ch) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        int count = (str.charAt(0) == ch) ? 1 : 0;
        return count + countCharacter(str.substring(1), ch);
    }

    public static void main(String[] args) {
        System.out.println("--- 測試 reverse ---");
        System.out.println("reverse(\"hello\"): " + reverse("hello"));

        System.out.println("\n--- 測試 isPalindrome ---");
        System.out.println("isPalindrome(\"\"): " + isPalindrome(""));
        System.out.println("isPalindrome(\"a\"): " + isPalindrome("a"));
        System.out.println("isPalindrome(\"Level\"): " + isPalindrome("Level"));
        System.out.println("isPalindrome(\"A man a plan a canal Panama\"): " + isPalindrome("A man a plan a canal Panama"));
        System.out.println("isPalindrome(\"hello\"): " + isPalindrome("hello"));

        System.out.println("\n--- 測試 countCharacter ---");
        System.out.println("countCharacter(\"banana\", 'a'): " + countCharacter("banana", 'a'));
    }
}