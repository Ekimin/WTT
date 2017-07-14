import com.wentuotuo.wtt.WTT;

/**
 * Created by Ekimin on 2017/7/13.
 */
public class Test {
    public static void main(String[] args){
        WTT.init("etc/wtt.xml");
        WTT.getLog().info(WTT.getProperty("APP_HOME"));
    }
}
