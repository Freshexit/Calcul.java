import java.util.Scanner;

class RomanNumeral {
    private static final int[] ARA = {100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROM = { "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

    public static String toRoman(int num) throws Exception {
        if (num < 1 || num > 100) {
            throw new Exception("Результат не может быть меньше I");
        }
        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < ARA.length; i++) {
            while (num >= ARA[i]) {
                num -= ARA[i];
                roman.append(ROM[i]);
            }
        }
        return roman.toString();
    }
    public static int fromRoman(String roman) {
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            for (int j = 0; j < ROM.length; j++) {
                if(roman.startsWith(ROM[j], i)) {
                    result += ARA[j];
                    i += ROM[j].length() - 1;
                    break;
                }
            }
        }
        return result;
    }

    public static int fromArabic(String arab) throws Exception {
        try {
            int result = Integer.parseInt(arab);
            if (result < 1 || result > 10) {
                throw new Exception("Числа должны быть от 1 до 10");
            }
            return result;
        }
        catch (Exception e) {
            throw new Exception("Ошибка при переводе в арабское число");
        }
    }
}


class Main {
    public static void main(String[] args) {
        Scanner vvod = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение через пробел с числами от 1 до 10 или от I до X: ");
        String input = vvod.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        }
        catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        String[] oper = input.split("\\s");
        if (oper.length < 3) {
            throw new Exception("Строка не является математической операцией");
        }
        else if (oper.length > 3) {
            throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        String num1 = oper[0];
        String operator = oper[1];
        String num2 = oper[2];

        boolean roman = num1.matches("[IVX]+");
        if (roman != num2.matches("[IVX]+")) {
            throw new Exception("Используются одновременно разные системы счисления");
        }

        int a, b;
        if (roman) {
            a = RomanNumeral.fromRoman(num1);
            b = RomanNumeral.fromRoman(num2);
        }
        else {
            a = RomanNumeral.fromArabic(num1);
            b = RomanNumeral.fromArabic(num2);
        }

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new Exception("Вводимые числа должны быть от 1 до 10");
        }

        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                if (roman && result < 1) {
                    throw new Exception("В Римской системе нет отрицательных чисел");
                }
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new Exception("Введите оператор (+,-,*,/) между числами");
        }

        if (roman) {
            return RomanNumeral.toRoman(result);
        }
        else {
            return Integer.toString(result);
        }
    }
}