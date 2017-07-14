//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt;

public interface WTTService {
    String SID_DBCONNECTION = "DBCONNECTION";
    String SID_METADATA = "METADATA";
    String SID_LOG = "LOG";
    String SID_JBO = "JBO";

    String getServiceId();

    String getServiceProvider();

    String getServiceDescribe();

    String getServiceVersion();

    void init() throws WTTException;

    void shutdown();
}
