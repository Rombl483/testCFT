package karakalchev.application;

import karakalchev.libraries.MergeSortFiles;

import java.io.*;
import java.util.Arrays;

public class MergeSortFilesApp {
    private static String[] arrayFiles;
    private static int sortType = MergeSortFiles.ASC;
    private static String filesDataType;

    private static void getAppArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-a")) {
                sortType = MergeSortFiles.ASC;
            } else if (args[i].equals("-d")) {
                sortType = MergeSortFiles.DESC;
            } else {
                if (args[i].equals("-s")) {
                    filesDataType = MergeSortFiles.STRING;
                } else if (args[i].equals("-i")) {
                    filesDataType = MergeSortFiles.INTEGER;
                }
                arrayFiles = Arrays.copyOfRange(args, i + 1, args.length);
                break;
            }
        }

        System.out.println("Параметры сортирвоки данных в файлах:");
        System.out.printf("Тип сортировки по %s%n", sortType == MergeSortFiles.ASC ? "возростанию" : "убыванию");
        System.out.printf("Тип данных в файлах %s%n", filesDataType.equals(MergeSortFiles.STRING) ? "строки" : "целые числа");
        System.out.println("Массив файлов:");
        System.out.println(Arrays.toString(arrayFiles));
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        try {
            getAppArgs(args);
            System.out.println("Запуск сортировки содержимого файлов методом слияния.");
            MergeSortFiles mergeSortFiles = new MergeSortFiles(arrayFiles, sortType, filesDataType);
            mergeSortFiles.merge();
            System.out.println("Сортировка содержимого файлов методом слияния завершена.");
        } catch (IllegalArgumentException e) {
            System.out.println("Не заданы параметры запуска сортировки данных в файлах");
        }
    }
}
