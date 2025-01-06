package ChangedClasses;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.ArrayList;
import java.util.Objects;

public class CellFuntions {
    public static void main(String args []) {
        System.out.println(Calculate("()"));
    }
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
        if (text.substring(0, 1).equals("=")) {
            return ans;
        } else {
            ans = false;
        }
        return ans;
    }

    public static double ComputeForm(String text) throws Exception {
        //How formula can look like
        // 1+1,1+1*2/3, ((2*2+3)/2)*2
        double ans = 0;
        if (text != null && !text.isEmpty() && !text.contains(" ")) {
            //Check if there are parenthasies
            try {
                ans = Double.parseDouble(Calculate(text));
            } catch (ArithmeticException e) {
                System.out.println("You cant divide by 0");
            }
        } else throw new IllegalArgumentException("Invalid formula format");
        return ans;
    }

    //The function does this: "1*1/((875*2)+8-9*12)" -> 334 recursively untily gets rid of all the parenthasies
    public static String Calculate(String text) {
        if (text.equals("") || text==null) {
            return "";
        }
        //remove the = sign
        String log = text;
        double ans = 0;
        ArrayList divided = DividerbyAction(text);
        //remove the = sign
        if (divided.contains("=")) {divided.remove(0);}
        ArrayList<Integer> indexbox = new ArrayList<>();
        boolean torun = false;
        //In case there are parenthasies - find the last parenthasies and calculate the expression inside them
        for (int i = 0; i<divided.size(); i+=1) {
            //In case while looping through the expression ( is found
            if ((divided.get(i)).equals("(")) {torun = true;}
            while (torun) {
                //Add the found ( index to the indexbox
                indexbox.add(i);
                //This loop will run unil it finds the closing ) or finds one more opening (
                i+=1;
                //Move one step further
                //Check the option with nested expressions
                if ((divided.get(i)).equals("(")) {
                    //In case after the opening one we eventually find one more opening one
                    //Just remove the part of the indexbox, so the first index would be the index with the last found (
                    indexbox.clear();
                }
                //Check the option without nested expressions
                if ((divided.get(i)).equals(")")) {
                    int left_bound = indexbox.get(1);
                    int right_bound = i;
                    //At this stage this is found (1*2) , the indexes are pointing to 1*2
                    //Calculate this expression and turn it to double
                    String expression = String.join("", divided.subList(left_bound,right_bound));
                    double result = Double.parseDouble(String.valueOf(Calculate(expression)));
                    //Add the expression that we've calculated to a string
                    divided.set(left_bound-1,Double.toString(result));
                    //Remove the initial expression from the string
                    divided.subList(left_bound,right_bound+1).clear();
                    text = String.join("",divided);
                    //Clean the indexbox
                    System.out.println("Current indexbox: " + indexbox);
                    indexbox.clear();
                    torun = false;
                    //Pass further into the recursion loop
                    double res = Double.parseDouble(Calculate(text));
                    System.out.println("The following string extracted --> " + log.substring(left_bound,right_bound+1) + " from " + divided );
                    return String.valueOf(res);
                }
        }
        }
        //Return the result of a looper (array) turned into string and parse Double'ed after.
        ans = Double.parseDouble(looper(divided).get(0).toString());

        return String.valueOf(ans);
    }
    //The Function that does: "1*1/875*2+8-9*12" -> [1, *, 1, /, 875, *, 2, +, 8, -, 9, *, 12]
    private static ArrayList<String> DividerbyAction(String text) {
        //Scan for the math operators
        ArrayList<String> symbols = new ArrayList();
        //Add all the chars in a string to a list
        for (int i = 0; i < text.length(); i += 1) {
            symbols.add(Character.toString(text.charAt(i)));
        }
        //Add one ~ at a last index for further logic
        symbols.add("~");
        //System.out.println("Before consolidation"+symbols);
        //Consolidate the digits in the list [2,5] -> [25]
        String digits = "0123456789.";
        StringBuilder str = new StringBuilder();
        //Create an arraylist of indexes
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < symbols.size(); i += 1) {
            if (digits.contains(symbols.get(i))) {
                //Add the digit to a stringbuilder
                str.append(symbols.get(i));
                //System.out.println(str);
                //Add it to the indexes list
                indexes.add(i);
                //Replace the digit with null
                if (indexes.size() > 1) {
                    symbols.set(i, null);
                }
            } else {
                //System.out.println(indexes);
                //In case we are not a digit index in symbols - we should check whether the digit containing operation has happened in the past - Indexes list shouldn't be empty
                if (!indexes.isEmpty()) {
                    //Append the collected digits to symbols
                    String collected = str.toString();
                    //Place it to the index where initial digit was found
                    int initial_index = indexes.get(0);
                    symbols.set(initial_index,collected);
                    //Clean the traces
                    indexes.clear();
                    str = new StringBuilder();
                }
            }
        }
        //System.out.println("After consolidation"+symbols);
        //Remove nulls and tildas
        symbols.removeIf(Objects::isNull);
        symbols.remove("~");
        //System.out.println("Return" + symbols);
        return symbols;



    }
    //The function does this "[2, *, 8, /, 875, *, 2]" -> "[367]" Without : ")","(".
    public static ArrayList<String> looper(ArrayList<String> text) {
        //System.out.println(text);
        while (text.contains("/")) {
            //In case text contains / we want to loop through it, calculate the first couple of numbers and calculating, then one more time in case text has /
            for (int i = 0; i < text.size(); i += 1) {
                if (text.get(i).toString().equals("/")) {
                    String newvalue = "";
                    double number_before = Double.parseDouble(text.get(i - 1));
                    double number_after = Double.parseDouble(text.get(i + 1));
                    if (number_after==0) {throw new ArithmeticException("You cand divide by 0");}
                    newvalue = Double.toString(number_before / number_after);
                    int first_index = i - 1;
                    text.set(first_index, newvalue);
                    text.set(i, null);
                    text.set(i + 1, null);
                    break;
                }
            }
            //Delete the nulls from the list in case break worked
            for (int i=0;i<text.size();i+=1) {
                text.remove(null);
            }
        }
        while (text.contains("*")) {
            //In case text contains / we want to loop through it, calculate the first couple of numbers and calculating, then one more time in case text has /
            for (int i = 0; i < text.size(); i += 1) {
                if (text.get(i).toString().equals("*")) {
                    String newvalue = "";
                    double number_before = Double.parseDouble(text.get(i - 1));
                    double number_after = Double.parseDouble(text.get(i + 1));
                    newvalue = Double.toString(number_before * number_after);
                    int first_index = i - 1;
                    text.set(first_index, newvalue);
                    text.set(i, null);
                    text.set(i + 1, null);
                    break;
                }
            }
            //Delete the nulls from the list in case break worked
            for (int i=0;i<text.size();i+=1) {
                text.remove(null);
            }
        }
        while (text.contains("-")) {
            //In case text contains / we want to loop through it, calculate the first couple of numbers and calculating, then one more time in case text has /
            for (int i = 0; i < text.size(); i += 1) {
                if (text.get(i).toString().equals("-")) {
                    String newvalue = "";
                    if (!text.isEmpty() && text.get(0).equals("-")) {
                        double number_itself = Double.parseDouble(text.get(i+1));
                        number_itself = 0 - number_itself;
                        text.set((i), String.valueOf(number_itself));
                        text.set(i+1,null);
                        break;
                    }
                    double number_before = Double.parseDouble(text.get(i - 1));
                    double number_after = Double.parseDouble(text.get(i + 1));
                    newvalue = Double.toString(number_before - number_after);
                    int first_index = i - 1;
                    text.set(first_index, newvalue);
                    text.set(i, null);
                    text.set(i + 1, null);
                    break;
                }
            }
            //Delete the nulls from the list in case break worked
            for (int i=0;i<text.size();i+=1) {
                text.remove(null);
            }
        }
        while (text.contains("+")) {
            //In case text contains / we want to loop through it, calculate the first couple of numbers and calculating, then one more time in case text has /
            for (int i = 0; i < text.size(); i += 1) {
                if (text.get(i).toString().equals("+")) {
                    String newvalue = "";
                    double number_before = Double.parseDouble(text.get(i - 1));
                    double number_after = Double.parseDouble(text.get(i + 1));
                    newvalue = Double.toString(number_before + number_after);
                    int first_index = i - 1;
                    text.set(first_index, newvalue);
                    text.set(i, null);
                    text.set(i + 1, null);
                    break;
                }
            }
            //Delete the nulls from the list in case break worked
            for (int i=0;i<text.size();i+=1) {
                text.remove(null);
            }
        }
        //System.out.println(text);
        return text;
    }

}
