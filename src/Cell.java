//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Cell {
    public static boolean isNumber(String text) {
        boolean ans = true;
        if (text == null || text.isEmpty()) {
            ans = false;
            return ans;
        }
        try {
            Double.parseDouble(text);
            return ans;
        } catch (NumberFormatException e) {
            ans = false;
        }
        return ans;
    }

    public static boolean IsText(String text) {

        boolean ans = true;
        if (text == null || text.isEmpty()) {
            ans = false;
            return ans;
        }
        if (true) {
            String digits = "0123456789.";
            for (int i = 0; i < text.length(); i++) {
                if (!digits.contains(Character.toString(text.charAt(i)))) {
                    ans = false;
                    return ans;
                }
            }
        }
        return ans;
    }

    public static boolean IsForm(String text) {
        boolean ans = true;
        if (text == null || text.isEmpty()) {
            ans = false;
            return ans;
        }
        if (text.substring(0,1).equals("=")) {
            return ans;
        } else {
            ans = false;
        }
        return ans;
    }

}
