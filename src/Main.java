import java.util.Scanner;
import java.util.TreeMap;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Опциональная подсказка для пользователя
        //System.out.println("Введите троку формата '[число a][операция][число b]', где:");
        //System.out.println("[число] - арабское число от 1 до 10 включительно ИЛИ римское число от I до X включительно");
        //System.out.println("[число a] И [число b] должны быть записаны в одном формате");
        //System.out.println("[операция] - '+'(сложение), '-'(вычитание), '*'(умножение),ИЛИ '/'(деление)");
        String input = scanner.nextLine();
        try {
            String output = calc(input);
            System.out.println(output);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        //В ТЗ не регламентировано использование пробелов, но в примерах input они присутствуют
        //Метод не чувствителен к наличию пробелов
        String expression = input.replaceAll(" ","");
        String[] operands = expression.split("[+\\-*/]");
        int a;
        int b;
        String operation = expression;
        int result;
        boolean result_conversion;
        String output = "";

        if (operands.length != 2)
            throw new Exception("INCORRECT OPERANDS OR/AND OPERATIONS AMOUNT");
        for (String operand : operands) operation = operation.replaceFirst(operand, "");
        if (operation.length() != 1)
            throw new Exception("INCORRECT OPERATIONS AMOUNT");
        if (ArabicNumber.isShortArabic(operands[0]) && ArabicNumber.isShortArabic(operands[1])) {
            a = ArabicNumber.convertToInt(operands[0]);
            b = ArabicNumber.convertToInt(operands[1]);
            result_conversion = false;
        }
        else if (RomanNumber.isShortRoman(operands[0]) && RomanNumber.isShortRoman(operands[1])) {
            a = RomanNumber.convertToInt(operands[0]);
            b = RomanNumber.convertToInt(operands[1]);
            result_conversion = true;
        }
        else
            throw new Exception("INCORRECT OPERANDS TYPE");

        result = calculation(a, b, operation);
        if (!result_conversion)
            output = ArabicNumber.convertToArabic(result);
        else if (result > 0)
            output = RomanNumber.convertToRoman(result);
        else
            throw new Exception("INCORRECT RESULT (ROMANIAN NUMBERS)");

        return output;
    }

    public static int calculation(int a, int b, String operation) {
        if (operation.equals("+")) return a + b;
        if (operation.equals("-")) return a - b;
        if (operation.equals("*")) return a * b;
        if (operation.equals("/")) return a / b; //По ТЗ b != 0
        return 0;
    }
}

class RomanNumber {
    //Ввиду ограниченного диапазона допустимых значений было принято решение использовать
    //массив допустимых римских чисел вместо реализации конвертера римских чисел в арабские
    //и последующей проверки диапазона
    static String[] roman_short_numbers = new String[]{"I", "II", "III", "IV", "V",
    "VI", "VII", "VIII", "IX", "X"};
    public static boolean isShortRoman(String str) {
        for (int i = 0; i < roman_short_numbers.length; i++)
            if (str.equals(roman_short_numbers[i]))
                return true;
        return false;
    }
    public static int convertToInt(String str) {
        for (int i = 0; i < roman_short_numbers.length; i++)
            if (str.equals(roman_short_numbers[i]))
                return i + 1;
        return -1;
    }
    public static String convertToRoman(int x) {
        TreeMap<Integer, String> number = new TreeMap<>();
        number.put(1000, "M");
        number.put(900, "CM");
        number.put(500, "D");
        number.put(400, "CD");
        number.put(100, "C");
        number.put(90, "XC");
        number.put(50, "L");
        number.put(40, "XL");
        number.put(10, "X");
        number.put(9, "IX");
        number.put(5, "V");
        number.put(4, "IV");
        number.put(1, "I");

        String roman = "";
        int arabic;
        do {
            arabic = number.floorKey(x);
            roman += number.get(arabic);
            x -= arabic;
        } while (x != 0);

        return roman;
    }
}

class ArabicNumber {
    //Реализация аналогична римским числам ради единообразия кода
    static String[] arabic_short_numbers = new String[]{"1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10"};
    public static boolean isShortArabic(String str) {
        for (int i = 0; i < arabic_short_numbers.length; i++)
            if (str.equals(arabic_short_numbers[i]))
                return true;
        return false;
    }
    //Методы ради единообразия кода
    public static int convertToInt(String str) {
        return Integer.parseInt(str);
    }
    public static String convertToArabic(int x) {
        return Integer.toString(x);
    }
}