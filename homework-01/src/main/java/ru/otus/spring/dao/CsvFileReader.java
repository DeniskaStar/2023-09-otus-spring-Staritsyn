package ru.otus.spring.dao;

import java.util.List;

public interface CsvFileReader {

    /**
     * Чтение cvs-файла из папки с ресурсами
     *
     * @param fileName  имя cvs-файла
     * @param separator разделитель
     * @return массив значений для каждой строки файла
     */
    List<List<String>> readFile(String fileName, char separator);
}
