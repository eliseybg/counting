package com.counting.breaktime;

public class LineCounter {
    public static int SolveLine(String a) {
        // переменная а хранит строку условия, а после и ответ
        //если в строке есть пробел, то его уберут
        while ((a.indexOf(" ") != -1) || a.indexOf("=") != -1) {
            int d = 0;
            if (a.indexOf(" ") != -1)
                d = a.indexOf(" ");
            if (a.indexOf("=") != -1)
                d = a.indexOf("=");
            StringBuffer sb = new StringBuffer(a);
            sb.delete(d, d + 1);
            a = sb.toString();
        }
        // переменная а хранит строку условия, а после и ответ
        /*
         * умножение и деление действие выполняется столько раз, сколько всего операций,
         * либо останавливается, если нет знаков * или /
         */
        for (int i = 0; i < a.length() - 1; i++) {
            /*
             * умножение если умножение ближе к началу строки, то оно выполнится вначале
             */
            if ((a.indexOf("*", 0) < a.indexOf("/", 0) || a.indexOf("/", 0) == -1) && a.indexOf("*", 0) != -1) {
                // переменная к показывает индекс знака умножения
                int k = a.indexOf("*", 0);

                // нахождение первого числа

                // num1str строковая переменнная, которой будет присвоено первое число
                String num1str = "";
                // переменная n показывает начало первого числа и задает первое число
                int n = 0;
                for (n = k - 1; n >= 0; n--) {
                    // переменная  lines получает символ из строки, который стоит перед предыдущим
                    // символом от числа
                    String lines = Character.toString(a.charAt(n));

                    if (lines.matches("[0-9]*") || lines.matches("[.]")) {
                        num1str = lines + num1str;
                    } else
                        break;
                }
                // переменная safestr1 вернет строку до n-го индекса строки
                String safestr1 = "";
                for (int q = 0; q <= n; q++)
                    safestr1 += Character.toString(a.charAt(q));
                // переменная num1 хранит первое число
                double num1 = Double.parseDouble(num1str);

                // нахождение второго числа

                // num2str строковая переменнная, которой будет присвоено второе число
                String num2str = "";
                // переменная е показывает начало второго числа и задает второе число
                int e = 0;
                for (e = k + 1; e < a.length(); e++) {
                    // переменная  lines получает символ из строки, который стоит после предыдущего
                    // символа от числа
                    String lines = Character.toString(a.charAt(e));
                    if (lines.matches("[0-9]*") || lines.matches("[.]")) {
                        num2str = num2str + lines;
                    } else
                        break;
                }
                // переменная num2 хранит второе число
                double num2 = Double.parseDouble(num2str);
                // переменная res хранит произведение чисел
                double res = num1 * num2;
                // переменная safestr2 вернет строку от е и до конца
                String safestr2 = "";
                for (int q = e; q < a.length(); q++)
                    safestr2 += Character.toString(a.charAt(q));
                // вернет строку с посчитанным произведением
                a = safestr1 + res + safestr2;

            } else {
                // деление
                if (a.indexOf("/", 0) > 0) {
                    // переменная к показывает индекс знака умножения
                    int k = a.indexOf("/", 0);

                    // нахождение первого числа

                    // num1str строковая переменнная, которой будет присвоено первое число
                    String num1str = "";
                    // переменная n показывает начало первого числа и задает первое число
                    int n = 0;
                    for (n = k - 1; n >= 0; n--) {
                        // переменная  lines получает символ из строки, который стоит перед предыдущим
                        // символом от числа
                        String lines = Character.toString(a.charAt(n));
                        if (lines.matches("[0-9]*") || lines.matches("[.]")) {
                            num1str = lines + num1str;
                        } else
                            break;
                    }
                    // переменная safestr1 вернет строку до n-го индекса строки
                    String safestr1 = "";
                    for (int q = 0; q <= n; q++)
                        safestr1 += Character.toString(a.charAt(q));
                    // переменная num1 хранит первое число
                    double num1 = Double.parseDouble(num1str);

                    // нахождение второго числа

                    // num2str строковая переменнная, которой будет присвоено второе число
                    String num2str = "";
                    // переменная е показывает начало второго числа и задает второе число
                    int e = 0;
                    for (e = k + 1; e < a.length(); e++) {
                        // переменная  lines получает символ из строки, который стоит после предыдущего
                        // символа от числа
                        String lines = Character.toString(a.charAt(e));
                        if (lines.matches("[0-9]*") || lines.matches("[.]")) {
                            num2str = num2str + lines;
                        } else
                            break;
                    }
                    // переменная num2 хранит второе число
                    double num2 = Double.parseDouble(num2str);
                    // переменная res хранит деление чисел
                    double res = (double) num1 / num2;
                    // переменная safestr2 вернет строку от е и до конца
                    String safestr2 = "";
                    for (int q = e; q < a.length(); q++)
                        safestr2 += Character.toString(a.charAt(q));
                    // вернет строку с посчитанным делением
                    a = safestr1 + res + safestr2;
                }

            }
            if (a.indexOf("/", 0) == -1 && a.indexOf("*", 0) == -1)
                break;
        }

        /*
         * сумма и разность действие выполняется столько раз, сколько всего операций,
         * либо останавливается, если нет знаков + или -
         */
        for (int i = 0; i < a.length() - 1; i++) {
            // если строка начинается с символа +, то знак + убирается
            if (a.indexOf("+", 0) == 0)
                a = a.substring(1);
            /*
             * сумма если сумма ближе к началу строки, то она выполнится вначале
             */
            // если строка начинается с символа -, то строка считается с индекса 1
            int startline = 0;
            if (a.indexOf("-", 0) == 0)
                startline = 1;
            if ((a.indexOf("+", startline) < a.indexOf("-", startline) || a.indexOf("-", startline) == -1)
                    && a.indexOf("+", startline) != -1) {
                if (a.indexOf("+", startline) > 0) {
                    // переменная к показывает индекс знака умножения
                    int k = a.indexOf("+", startline);

                    // нахождение первого числа

                    // num1str строковая переменнная, которой будет присвоено первое число
                    String num1str = "";
                    // переменная n показывает начало первого числа и задает первое число
                    int n = 0;
                    for (n = k - 1; n >= 0; n--) {
                        // переменная  lines получает символ из строки, который стоит перед предыдущим
                        // символом от числа
                        String lines = Character.toString(a.charAt(n));

                        if (lines.matches("[0-9]*") || lines.matches("[.]") || lines.matches("[-]")) {
                            num1str = lines + num1str;
                        } else
                            break;
                        if (lines.matches("[-]"))
                            break;
                    }
                    // переменная safestr1 вернет строку до n-го индекса строки
                    String safestr1 = "";
                    for (int q = 0; q <= n; q++)
                        safestr1 += Character.toString(a.charAt(q));
                    // переменная num1 хранит первое число
                    double num1 = Double.parseDouble(num1str);

                    // нахождение второго числа

                    // num2str строковая переменнная, которой будет присвоено второе число
                    String num2str = "";
                    // переменная е показывает начало второго числа и задает второе число
                    int e = 0;
                    for (e = k + 1; e < a.length(); e++) {
                        // переменная  lines получает символ из строки, который стоит после предыдущего
                        // символа от числа
                        String lines = Character.toString(a.charAt(e));
                        if (lines.matches("[0-9]*") || lines.matches("[.]")) {
                            num2str = num2str + lines;
                        } else
                            break;
                    }
                    // переменная num2 хранит второе число
                    double num2 = Double.parseDouble(num2str);
                    // переменная res хранит сумму чисел
                    double res = num1 + num2;
                    String safestr2 = "";
                    for (int q = e; q < a.length(); q++)
                        // переменная safestr2 вернет строку от е и до конца
                        safestr2 += Character.toString(a.charAt(q));
                    String strres = "";
                    // если сумма не равна 0, то она будет записана в строку
                    if (res != 0)
                        strres = "" + res;
                    a = safestr1 + strres + safestr2;
                    if (startline == 1)
                        a = a.substring(1);
                }
            } else {
                // разность
                if (a.indexOf("-", startline) > 0) {
                    // переменная к показывает индекс знака умножения
                    int k = a.indexOf("-", startline);

                    // нахождение первого числа

                    // num1str строковая переменнная, которой будет присвоено первое число
                    String num1str = "";
                    // переменная n показывает начало первого числа и задает первое число
                    int n = 0;
                    for (n = k - 1; n >= 0; n--) {
                        String lines = Character.toString(a.charAt(n));
                        if (lines.matches("[0-9]*") || lines.matches("[.]") || lines.matches("[-]")) {
                            num1str = lines + num1str;
                        } else
                            break;
                        if (lines.matches("[-]"))
                            break;
                    }
                    String safestr1 = "";
                    for (int q = 0; q <= n; q++)
                        // переменная safestr1 вернет строку до n-го индекса строки
                        safestr1 += Character.toString(a.charAt(q));
                    // переменная num1 хранит первое число
                    double num1 = Double.parseDouble(num1str);

                    // нахождение второго числа

                    // num2str строковая переменнная, которой будет присвоено второе число
                    String num2str = "";
                    // переменная е показывает начало второго числа и задает второе число
                    int e = 0;
                    for (e = k + 1; e < a.length(); e++) {
                        // переменная  lines получает символ из строки, который стоит после предыдущего
                        // символа от числа
                        String lines = Character.toString(a.charAt(e));
                        if (lines.matches("[0-9]*") || lines.matches("[.]")) {
                            num2str = num2str + lines;
                        } else
                            break;
                    }
                    // переменная num2 хранит второе число
                    double num2 = Double.parseDouble(num2str);
                    // переменная res хранит разность чисел
                    double res = (double) num1 - num2;
                    String safestr2 = "";
                    for (int q = e; q < a.length(); q++)
                        // переменная safestr2 вернет строку от е и до конца

                        safestr2 += Character.toString(a.charAt(q));
                    String strres = "";
                    // если разность не равна 0, то она будет записана в строку
                    if (res != 0)
                        strres = "" + res;
                    a = safestr1 + strres + safestr2;
                    if (startline == 1)
                        a = a.substring(1);
                }
            }
            // остановка цикла, если нет символов + и -
            if (a.indexOf("-", startline) == -1 && a.indexOf("+", startline) == -1)
                break;
        }
        int result = 0;
        // если длина строки не равна 0, то она будет переведена в число, иначе получит
        // значение 0
        if (a.length() != 0)
            result = (int) Double.parseDouble(a);
        result = Math.round(result);
        return result;
    }
}
