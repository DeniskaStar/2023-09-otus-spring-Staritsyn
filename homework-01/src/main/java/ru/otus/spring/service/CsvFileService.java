package ru.otus.spring.service;

import java.util.List;

public interface CsvFileService {

    /**
     * Чтение cvs-файла из папки с ресурсами
     *
     * @param fileName  имя csv-файла
     * @param separator разделитель
     * @return массив значений для каждой строки файла
     */
    List<List<String>> readFile(String fileName, char separator);
}
