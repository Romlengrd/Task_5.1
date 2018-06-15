package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    static File[] child;
    //static File[] roots;
    static File backIndex;
    static String absPath;
    static String fileName;

    public static void showFilesAndDirectories (File f) {

        if (!f.isDirectory ()) {
            System.out.println (f.getName());
            fileName = f.getName();
            absPath = f.getAbsolutePath();
            readFile();
        }

        if (f.isDirectory ()) {
            try {
                System.out.println(f.getCanonicalFile());
                child = f.listFiles();
                if (f.listFiles()!=null) {

                    for (int i = 0; i < child.length; i++) {
                        System.out.println(i + " " + child[i]);
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void readFile () {
        if (fileName.contains(".txt")) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(absPath)), Charset.forName("UTF-8"));
                System.out.println(content);
                System.out.println();
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Можно открыать только txt файлы!");
        }
    }
    public static void writeInFile (String content) {

        if (absPath!=null) {
            try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(absPath), "utf-8")) {
                out.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.out.println("Вы в каталоге, выберите файл");
    }
    public static void addWriteInFile (String contentNew) {
        if (fileName.contains(".txt")) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(absPath)), Charset.forName("UTF-8"));
                //System.out.println(content);
                System.out.println();

                if (absPath != null) {
                    try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(absPath), "utf-8")) {
                        out.write(content + contentNew);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else System.out.println("Вы в каталоге, выберите файл");


            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("сюда записать не получится txt файлы!");
        }
    }


    public static void createFile (String name) {
        File file2 = new File(child[0].getParent(),name + ".txt");
        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deleteFile (int num) {
        File fd = child[num];
        fd.delete();
            try {
                goBack();
                goDirectory(num);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println();
            }
    }

    public static void goDirectory (int num) {

            if (child[num].canRead()) {
                if (child[num].getParent()!=null) {
                backIndex = new File(child[num].getParent());
                }
                showFilesAndDirectories(child[num]);

            } else {
                System.out.println("Каталог не доступен");
            }

    }
    public static void goBack () {
        showFilesAndDirectories(backIndex);
    }

    public static void main(String[] args) {

        System.out.println("Выберите директорию по номеру");
        child = File.listRoots();
        for (int i = 0; i < child.length; i++) {
            System.out.println("Root[" + i + "]:" + child[i]);
        }

        goDirectory(3);
        goDirectory(18);
        goDirectory(2); //работает
        //goBack(); //работает
        //deleteFile(2);  //работает
        //createFile("Новый текстовый документ"); // работает
        writeInFile("lalalala"); //Работает
        addWriteInFile("nenenene"); //checked

    }
}
