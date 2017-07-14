//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql.proc;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.log.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogResultDisplay implements ResultDisplay, Runnable {
    private PipedWriter writer = null;
    private BufferedReader reader = null;
    private PrintWriterResultDisplay prd = null;
    private Log logger = WTT.getLog();
    private Thread shower = null;
    private int logLevel = 1;

    public LogResultDisplay() {
        PipedReader var1 = new PipedReader();
        this.writer = new PipedWriter();

        try {
            var1.connect(this.writer);
        } catch (IOException var3) {
            this.logger.debug(var3);
        }

        this.reader = new BufferedReader(var1);
        this.prd = new PrintWriterResultDisplay(new PrintWriter(this.writer));
    }

    public void showResult(String var1, ResultSet var2) {
        if(this.shower == null) {
            this.shower = new Thread(this);
            this.shower.start();
        }

        this.prd.showResult(var1, var2);
    }

    public void showResult(int var1, String var2, long var3) {
        if(this.shower == null) {
            this.shower = new Thread(this);
            this.shower.start();
        }

        this.prd.showResult(var1, var2, var3);
    }

    public void showConnection(Connection var1) {
        if(this.shower == null) {
            this.shower = new Thread(this);
            this.shower.start();
        }

        this.prd.showConnection(var1);
    }

    public void showException(SQLException var1) {
        if(this.shower == null) {
            this.shower = new Thread(this);
            this.shower.start();
        }

        this.prd.showException(var1);
    }

    public void showHelpMessage(String[] var1) {
        if(this.shower == null) {
            this.shower = new Thread(this);
            this.shower.start();
        }

        this.prd.showHelpMessage(var1);
    }

    public void showMessage(String var1) {
        if(this.shower == null) {
            this.shower = new Thread(this);
            this.shower.start();
        }

        this.prd.showMessage(var1);
    }

    public void run() {
        while(true) {
            String var1 = null;

            try {
                var1 = this.reader.readLine();
            } catch (IOException var5) {
                this.logger.debug(var5);
            }

            if(var1 == null) {
                try {
                    this.reader.close();
                } catch (IOException var4) {
                    this.logger.debug(var4);
                }

                try {
                    this.writer.close();
                } catch (IOException var3) {
                    this.logger.debug(var3);
                }

                return;
            }

            switch(this.logLevel) {
                case 1:
                    this.logger.trace(var1);
                    break;
                case 2:
                    this.logger.debug(var1);
                    break;
                case 3:
                    this.logger.info(var1);
                    break;
                case 4:
                    this.logger.warn(var1);
                    break;
                case 5:
                    this.logger.error(var1);
                    break;
                case 6:
                    this.logger.fatal(var1);
            }
        }
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(int var1) {
        this.logLevel = var1;
    }
}
