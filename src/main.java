import java.util.Scanner;
import java.util.Stack;

public class main {
    public static void main(String[] args) {
        String str = "10+x*((90-10)/y*50+10-x+40*(50+10))+30";
        String[] str1;
        str1 = myParser(str);
        Stack<Float> stack1 = new Stack<>();
        Stack<Character> stack2 = new Stack<>();
        char ch;

        if(str.matches("[\\\\(+|[a-zA-Z0-9]*[\\+\\-\\/\\*]?[a-zA-Z0-9]+|\\\\)+]*") && checkBrackets(str)) {
            for (int i = 0; str1[i] != null; i++) {
                if (str1[i].charAt(0) >= '0' && str1[i].charAt(0) <= '9')
                    stack1.push(Float.parseFloat(str1[i]));
                else {
                    if (stack2.empty())
                        stack2.push(str1[i].charAt(0));
                    else {
                        switch (str1[i].charAt(0)) {
                            case '-', '+' -> {
                                ch = stack2.peek();
                                while ((ch == '+' || ch == '-' || ch == '/' || ch == '*') && (!stack1.empty() && !stack2.empty())) {
                                    operate(stack1, ch);
                                    stack2.pop();
                                    if (!stack2.empty())
                                        ch = stack2.peek();
                                }
                                stack2.push(str1[i].charAt(0));
                            }
                            case '*', '/' -> {
                                ch = stack2.peek();
                                while ((ch == '/' || ch == '*') && (!stack1.empty() && !stack2.empty())) {
                                    operate(stack1, ch);
                                    stack2.pop();
                                    if (!stack2.empty())
                                        ch = stack2.peek();
                                }
                                stack2.push(str1[i].charAt(0));
                            }
                            case '(' -> stack2.push(str1[i].charAt(0));
                            case ')' -> {
                                ch = stack2.peek();
                                while (ch != '(' && (!stack1.empty() && !stack2.empty())) {
                                    operate(stack1, ch);
                                    stack2.pop();
                                    if (!stack2.empty())
                                        ch = stack2.peek();
                                }
                                stack2.pop();
                            }
                        }

                    }
                }

                System.out.print(stack1);
                System.out.println(stack2);
            }

            while (!stack2.empty() && !stack1.empty() && stack1.size() > 1) {
                if (stack2.size() > 1) {
                    operate(stack1, stack2.peek());
                    stack2.pop();
                } else {
                    operate(stack1, stack2.pop());
                }
            }
            System.out.println(stack2);
            System.out.println(stack1);
        }
        else
            System.out.println("Not match!");

    }

    public static void operate(Stack<Float> stack, char op) {
        if (!stack.empty()) {
            float a = stack.pop(), b = stack.pop();
            switch (op) {
                case '+' -> stack.push(a + b);
                case '-' -> stack.push(b - a);
                case '*' -> stack.push(a * b);
                case '/' -> stack.push(b / a);
            }
        }

    }

    public static String[] myParser(String str){
        String[] result = new String[str.length()];
        boolean first = true, nul = true;
        Pair[] pair = new Pair[str.length()];
        Scanner scanner = new Scanner(System.in);
        String tmp;


        int j = -1, k = 0;
        for (int i = 0; i < str.length(); i++){
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9')
                if(first) {
                    j++;
                    result[j] = str.charAt(i) + "";
                    first = false;
                }
                else
                    result[j] += str.charAt(i) + "";
            else
                if(str.charAt(i) != '+' && str.charAt(i) != '-' && str.charAt(i) != '*' && str.charAt(i) != '/' && str.charAt(i) != ' ' && str.charAt(i) != '(' && str.charAt(i) != ')'){
                    tmp = find(pair, str.charAt(i));
                    if(nul || tmp.equals("a")){
                        try{
                            System.out.print("Input " + str.charAt(i) + ' ');
                            pair[k] = new Pair(str.charAt(i), scanner.nextFloat());
                            j++;
                            result[j] = pair[k].second + "";
                            k++;
                            nul = false;
                            System.out.println();
                        }catch(Exception e){
                            System.out.println(e);
                        }
                    }
                    else {
                        if(str.charAt(i) != ' '){
                            j++;
                            result[j] = tmp;
                        }
                    }
                }
                else {
                    if(str.charAt(i) != ' ') {
                        j++;
                        result[j] = str.charAt(i) + "";
                        first = true;
                    }
                }
        }

        return result;
    }

    public static String find(Pair[] pair, char name){
        boolean founded = false;
        int i = 0;
        while(pair[i] != null & !founded){
            if(pair[i].first == name)
                founded = true;
            i++;
        }

        if(founded)
            return String.valueOf(pair[i - 1].second);
        else
            return 'a' + "";
    }

    public static boolean checkBrackets(String str){
        char ch;
        int countL = 0, countR = 0;
        for (int i = 0; i < str.length();i++){
            ch = str.charAt(i);
            switch (ch) {
                case '(' -> countL++;
                case ')' -> countR++;
            }
        }
        return countL - countR == 0;
    }
}