//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt;

import com.wentuotuo.wtt.io.FileTool;
import com.wentuotuo.wtt.lang.StringX;
import java.io.File;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitWTTServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public InitWTTServlet() {
    }

    public void init() throws ServletException {
        super.init();
        System.out.println("**********************************InitWTTServlet Start*********************************");
        WTT.setRunMode(1);
        String var1 = this.getInitParameter("ConfigFile");
        System.out.println("ConfigFile = [" + var1 + "]");
        String var2 = this.getInitParameter("AppHome");
        if(StringX.isSpace(var2) || var2.equals(".") || var2.equals("/WEB-INF") || var2.equals("/WEB-INF/")) {
            var2 = this.getServletContext().getRealPath("/WEB-INF");
            var2 = var2.replace(System.getProperty("file.separator").charAt(0), '/');
        }

        WTT.setProperty("APP_HOME", var2);
        File var3 = FileTool.findFile(var1);
        if(var3 != null) {
            WTT.init(var3);
        } else {
            InputStream var4 = this.getServletContext().getResourceAsStream(var1);
            WTT.init(var4);
        }

        if(WTT.isInitOk()) {
            WTT.getLog().info("**********************************InitWTTServlet Success*********************************");
        } else {
            WTT.getLog().error("**********************************InitWTTServlet Failed***********************************");
        }

    }
}
