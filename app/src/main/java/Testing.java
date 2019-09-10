import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AiWei on 2016/12/27.
 */
public class Testing {

    public static void main(String[] args) {
        String regix = "DISCUZUPLOAD\\|(\\d+)\\|(\\d+)\\|(\\d+)\\|(\\d+)";
        Matcher m = Pattern.compile(regix).matcher("DISCUZUPLOAD|3|0|1|1048576");
        while (m.find()) {
            System.out.print(m.group(1));
        }
    }

}
