package cn.tencent.DiscuzMob.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.tencent.DiscuzMob.base.RedNetApp;

/**
 * Created by AiWei on 2016/5/12.
 */
public class DumpUtils {

    public static void dump(final String filename, final String... lines) {
        if (lines != null && lines.length > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(RedNetApp.getInstance().getCacheDir("dump"), filename + ".txt");
                    FileWriter fw = null;
                    BufferedWriter bw = null;
                    try {
                        fw = new FileWriter(file);
                        bw = new BufferedWriter(fw);
                        for (String line : lines) {
                            bw.write(line);
                            bw.newLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (bw != null) {
                                bw.close();
                            }
                            if (fw != null) {
                                fw.close();
                            }
                        } catch (IOException e2) {
                        }
                    }
                }
            }).start();
        }
    }

}
