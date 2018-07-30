package server.Log;

import java.io.*;

public class Log {
    private static final String filename="database/log.txt";
    public static void addLog(String s){
        try {
            FileWriter fw = new FileWriter(filename, true);
            fw.write(s);
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
