import functions.*;
import functions.basic.*;

import static functions.Functions.*; // sum, mult, power, scale, shift, composition

import java.io.*;

public class Main {
    public static void main(String[] args) {

        // Создаем тестовые наборы точек для проверки функциональности:

        // Два идентичных набора точек
        FunctionPoint[] ptsA = {
                new FunctionPoint(0.0, 1.2),
                new FunctionPoint(1.0, 3.8),
                new FunctionPoint(2.0, 15.2)
        };
        FunctionPoint[] ptsB = {
                new FunctionPoint(0.0, 1.2),
                new FunctionPoint(1.0, 3.8),
                new FunctionPoint(2.0, 15.2)
        };

        // Набор с небольшим отличием в y
        FunctionPoint[] ptsC = {
                new FunctionPoint(0.0, 1.2),
                new FunctionPoint(1.0, 3.801), // Небольшое изменение в y
                new FunctionPoint(2.0, 15.2)
        };

        // Укороченный набор (только 2 точки вместо 3)
        FunctionPoint[] ptsShort = {
                new FunctionPoint(0.0, 1.2),
                new FunctionPoint(2.0, 15.2)
        };

        // Создаем табулированные функции на основе массивов
        ArrayTabulatedFunction arr1 = new ArrayTabulatedFunction(ptsA);
        ArrayTabulatedFunction arr2 = new ArrayTabulatedFunction(ptsB);
        ArrayTabulatedFunction arr3 = new ArrayTabulatedFunction(ptsC);
        ArrayTabulatedFunction arrShort = new ArrayTabulatedFunction(ptsShort);

        // Создаем табулированные функции на основе связных списков
        LinkedListTabulatedFunction list1 = new LinkedListTabulatedFunction(ptsA);
        LinkedListTabulatedFunction list2 = new LinkedListTabulatedFunction(ptsB);
        LinkedListTabulatedFunction list3 = new LinkedListTabulatedFunction(ptsC);
        LinkedListTabulatedFunction listShort = new LinkedListTabulatedFunction(ptsShort);

        // 1) Тестируем метод toString() - как функции представляются в виде строки
        System.out.println("\n-- toString() --");
        System.out.println("Array реализация: " + arr1);
        System.out.println("List  реализация: " + list1);

        // 2) Тестируем метод equals() - сравнение функций на равенство
        System.out.println("\n-- equals(): основные тестовые случаи --");

        // Сравнение одинаковых функций в пределах одного типа
        System.out.println("arr1 == arr2 (один класс, одинаковые данные): " + arr1.equals(arr2));
        System.out.println("list1 == list2 (один класс, одинаковые данные): " + list1.equals(list2));

        // Сравнение между разными реализациями с одинаковыми данными
        System.out.println("arr1 == list1 (разные классы, одинаковые данные): " + arr1.equals(list1));
        System.out.println("list1 == arr1 (разные классы, одинаковые данные): " + list1.equals(arr1));

        // Сравнение с немного отличающимися данными
        System.out.println("arr1 == arr3 (один класс, разные данные): " + arr1.equals(arr3));
        System.out.println("list1 == list3 (один класс, разные данные): " + list1.equals(list3));

        // Сравнение разных реализаций с разными данными
        System.out.println("arr1 == list3 (разные классы, разные данные): " + arr1.equals(list3));
        System.out.println("list1 == arr3 (разные классы, разные данные): " + list1.equals(arr3));

        // Сравнение с функциями разной длины
        System.out.println("arr1 == arrShort (разная длина): " + arr1.equals(arrShort));
        System.out.println("list1 == listShort (разная длина): " + list1.equals(listShort));

        // Граничные случаи сравнения
        System.out.println("arr1 == null: " + arr1.equals(null));
        System.out.println("arr1 == \"не функция\": " + arr1.equals("не функция"));

        // 3) Проверяем математические свойства метода equals()
        System.out.println("\n-- equals(): проверка математических свойств --");

        // Рефлексивность: объект должен быть равен самому себе
        System.out.println("Рефлексивность: arr1 == arr1: " + arr1.equals(arr1));
        System.out.println("Рефлексивность: list1 == list1: " + list1.equals(list1));

        // Симметричность: если A равно B, то B должно быть равно A
        boolean symmetry1 = arr1.equals(list1);
        boolean symmetry2 = list1.equals(arr1);
        System.out.println("Симметричность: arr1==list1 и list1==arr1: " + (symmetry1 && symmetry2));

        // Транзитивность: если A равно B и B равно C, то A должно быть равно C
        ArrayTabulatedFunction arr2copy = new ArrayTabulatedFunction(ptsB);
        boolean transitive = arr1.equals(arr2) && arr2.equals(arr2copy) && arr1.equals(arr2copy);
        System.out.println("Транзитивность: arr1==arr2 и arr2==arr2copy --> arr1==arr2copy: " + transitive);

        // Многократный вызов должен давать одинаковый результат
        boolean consistent1 = arr1.equals(arr2);
        boolean consistent2 = arr1.equals(arr2);
        System.out.println("Консистентность: повторный вызов дает тот же результат: " + (consistent1 == consistent2));

        // 4) Тестируем метод hashCode() - вычисление хеш-кода функций
        System.out.println("\n-- hashCode() --");
        int hArr1 = arr1.hashCode();
        int hArr2 = arr2.hashCode();
        int hList1 = list1.hashCode();
        int hList2 = list2.hashCode();

        System.out.println("хеш(arr1)  = " + hArr1);
        System.out.println("хеш(arr2)  = " + hArr2);
        System.out.println("хеш(list1) = " + hList1);
        System.out.println("хеш(list2) = " + hList2);

        // Проверяем, что равные объекты имеют равные хеш-коды
        System.out.println("равные массивы -  равные хеши: " + (arr1.equals(arr2) && hArr1 == hArr2));
        System.out.println("равные списки  - равные хеши: " + (list1.equals(list2) && hList1 == hList2));

        // 5) Проверяем, как изменяется хеш-код при модификации данных
        System.out.println("\n hashCode после изменения точки ");

        // Для массива
        int hashBeforeArr = arr2.hashCode();
        arr2.setPointY(1, arr2.getPointY(1) + 0.001); // Меняем координату y второй точки
        int hashAfterArr = arr2.hashCode();
        System.out.println("Массив: хеш до = " + hashBeforeArr + ", после = " + hashAfterArr +
                ", изменился = " + (hashBeforeArr != hashAfterArr));

        // Для списка
        int hashBeforeList = list2.hashCode();
        list2.setPointY(1, list2.getPointY(1) + 0.001);
        int hashAfterList = list2.hashCode();
        System.out.println("Список: хеш до = " + hashBeforeList + ", после = " + hashAfterList +
                ", изменился = " + (hashBeforeList != hashAfterList));

        // 6) Тестируем метод clone() - создание глубоких копий
        System.out.println("\n-- clone(): проверка глубокого копирования --");

        // Создаем клоны
        ArrayTabulatedFunction arrClone = arr1.clone();
        LinkedListTabulatedFunction listClone = list1.clone();

        System.out.println("arr1.equals(arrClone): " + arr1.equals(arrClone));
        System.out.println("list1.equals(listClone): " + list1.equals(listClone));

        // Сохраняем исходные значения y для проверки
        double originalYArrClone = arrClone.getPointY(0);
        double originalYListClone = listClone.getPointY(0);

        // Модифицируем оригинальные объекты
        arr1.setPointY(0, 999.0);
        list1.setPointY(0, 888.0);

        // Проверяем, что клоны не изменились
        System.out.println("Клон массива не изменился после модификации оригинала: " +
                (Double.compare(arrClone.getPointY(0), originalYArrClone) == 0));
        System.out.println("Клон списка не изменился после модификации оригинала: " +
                (Double.compare(listClone.getPointY(0), originalYListClone) == 0));

        // 7) Тестируем класс FunctionPoint отдельно
        System.out.println("\n-- FunctionPoint: отдельное тестирование --");
        FunctionPoint point = new FunctionPoint(1.1, -7.5);
        FunctionPoint pointClone = point.clone();
        FunctionPoint similarPoint = new FunctionPoint(1.1, -7.499); // Почти такая же точка

        System.out.println("point.toString(): " + point);
        System.out.println("point.equals(pointClone): " + point.equals(pointClone));
        System.out.println("хеш(point) == хеш(pointClone): " + (point.hashCode() == pointClone.hashCode()));
        System.out.println("point.equals(similarPoint) (должно быть false): " + point.equals(similarPoint));
    }
}