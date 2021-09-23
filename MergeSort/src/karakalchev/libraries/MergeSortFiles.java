package karakalchev.libraries;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class MergeSortFiles implements MergeSort {
    public static final int DESC = -1;
    public static final int ASC = 1;

    public static final String STRING = "String";
    public static final String INTEGER = "Integer";

    private final String[] filesName;
    private final int sortType;
    private final String filesDataType;

    public MergeSortFiles(String[] filesName, int sortType, String filesDataType) {
        this.filesName = filesName;
        this.sortType = sortType;
        this.filesDataType = filesDataType;
    }

    private Integer getSortKey(HashMap<Integer, String> readersLine) {
        Integer sortKey = -1;
        for (Integer key : readersLine.keySet()) {
            if (sortKey < 0) {
                sortKey = key;
            } else {
                String sortValue = readersLine.get(sortKey);
                String currentValue = readersLine.get(key);

                if (sortValue != null && currentValue != null) {
                    boolean compareData;
                    if (filesDataType.equals(INTEGER)) {
                        compareData = compare(Integer.parseInt(sortValue), Integer.parseInt(currentValue));
                    } else {
                        compareData = compare(sortValue, currentValue);
                    }

                    if (compareData) {
                        sortKey = key;
                    }
                }
            }
        }

        return sortKey;
    }

    @Override
    public void merge() throws IOException {
        String finalFile = filesName[0];
        String[] files = Arrays.copyOfRange(filesName, 1, filesName.length);

        HashMap<Integer, String> readersLine = new HashMap<>();
        BufferedReader[] readers = new BufferedReader[files.length];
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(finalFile));

            for (int i = 0; i < files.length; i++) {
                readers[i] = new BufferedReader(new FileReader(files[i]));
                readersLine.put(i, readers[i].readLine());
            }

            while (!readersLine.isEmpty()) {
                int sortKey = getSortKey(readersLine);
                if (sortKey > -1) {
                    if (readersLine.get(sortKey) != null) {
                        writer.write(readersLine.get(sortKey));
                        writer.newLine();
                        writer.flush();
                    }
                    String line = readers[sortKey].readLine();
                    if (line == null) {
                        readersLine.remove(sortKey);
                    } else {
                        readersLine.put(sortKey, line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }

            for (BufferedReader reader : readers) {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }

    @Override
    public boolean isValid() {
        if (filesName == null) {
            throw new IllegalArgumentException("Не задан массив файлов.");
        }

        if (filesName.length == 0) {
            throw new IllegalArgumentException("Не задан массив файлов.");
        }

        if (filesName.length == 1) {
            throw new IllegalArgumentException("Не задан массив файлов для сортировки.");
        }

        return true;
    }

    private <T extends Comparable<T>> boolean compare(T a, T b) {
        return (sortType * a.compareTo(b)) >= 0;
    }
}
