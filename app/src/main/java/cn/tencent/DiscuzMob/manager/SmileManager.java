package cn.tencent.DiscuzMob.manager;

import java.util.ArrayList;
import java.util.HashMap;

import cn.tencent.DiscuzMob.model.Smiley;
import cn.tencent.DiscuzMob.R;


/**
 * Created by kurt on 15-6-8.
 */
public class SmileManager {

    public static String[] SmileType = {"default", "coolmonkey", "grapeman"};

    public static String[] defaultCode = {"#[01_01]",
            "#[01_02]", "#[01_03]", "#[01_04]", "#[01_05]", "#[01_06]", "#[01_07]",
            "#[01_08]", "#[01_09]", "#[01_10]", "#[01_11]", "#[01_12]", "#[01_13]",
            "#[01_14]", "#[01_15]", "#[01_16]", "#[01_17]", "#[01_18]", "#[01_19]",
            "#[01_20]", "#[01_21]", "#[01_22]", "#[01_23]", "#[01_24]"};
//    public static String[] defaultCode = {"{:1_1:}", "{:1_2:}", "{:1_3:}", "{:1_4:}", "{:1_5:}", "{:1_6:}", "{:1_7:}", "{:1_8:}", "{:1_9:}", "{:1_10:}", "{:1_11:}"
//            , "{:1_12:}", "{:1_13:}", "{:1_14:}", "{:1_15:}", "{:1_16:}", "{:1_17:}", "{:1_18:}", "{:1_19:}", "{:1_20:}", "{:1_21:}", "{:1_22:}", "{:1_23:}", "{:1_24:}"};

    public static int[] defaultRes = {
            R.drawable.default01,
            R.drawable.default02,
            R.drawable.default03,
            R.drawable.default04,
            R.drawable.default05,
            R.drawable.default17,
            R.drawable.default07,
            R.drawable.default08,
            R.drawable.default09,
            R.drawable.default10,
            R.drawable.default11,
            R.drawable.default12,
            R.drawable.default13,
            R.drawable.default14,
            R.drawable.default15,
            R.drawable.default06,
            R.drawable.default16,
            R.drawable.default18,
            R.drawable.default19,
            R.drawable.default20,
            R.drawable.default21,
            R.drawable.default22,
            R.drawable.default23,
            R.drawable.default24};

    public static String[] coolmonkeyCode = {"#[02_01]", "#[02_02]", "#[02_03]", "#[02_04]", "#[02_05]",
            "#[02_06]", "#[02_07]", "#[02_08]", "#[02_09]",
            "#[02_10]", "#[02_11]", "#[02_12]", "#[02_13]",
            "#[02_14]", "#[02_15]", "#[02_16]"};
//    public static String[] coolmonkeyCode = {"{:2_25:}", "{:2_26:}", "{:2_27:}", "{:2_28:}", "{:2_29:}", "{:2_30:}", "{:2_31:}", "{:2_32:}", "{:2_33:}", "{:2_34:}", "{:2_35:}"
//            , "{:2_36:}", "{:2_37:}", "{:2_38:}", "{:2_39:}", "{:2_40:}"};

    public static int[] coolmonkeyRes = {
            R.drawable.coolmonkey01,
            R.drawable.coolmonkey02,
            R.drawable.coolmonkey03,
            R.drawable.coolmonkey04,
            R.drawable.coolmonkey05,
            R.drawable.coolmonkey06,
            R.drawable.coolmonkey07,
            R.drawable.coolmonkey08,
            R.drawable.coolmonkey09,
            R.drawable.coolmonkey10,
            R.drawable.coolmonkey11,
            R.drawable.coolmonkey12,
            R.drawable.coolmonkey13,
            R.drawable.coolmonkey14,
            R.drawable.coolmonkey15,
            R.drawable.coolmonkey16};


    public static String[] grapemanCode = {
            "#[03_01]", "#[03_02]", "#[03_03]", "#[03_04]", "#[03_05]",
            "#[03_06]", "#[03_07]", "#[03_08]", "#[03_09]", "#[03_10]",
            "#[03_11]", "#[03_12]", "#[03_13]", "#[03_14]", "#[03_15]",
            "#[03_16]", "#[03_17]", "#[03_18]", "#[03_19]", "#[03_20]",
            "#[03_21]", "#[03_22]", "#[03_23]", "#[03_24]"
    };
//    public static String[] grapemanCode = {"{:3_383:}", "{:3_384:}", "{:3_385:}", "{:3_386:}", "{:3_387:}", "{:3_388:}", "{:3_389:}", "{:3_390:}", "{:3_391:}", "{:3_392:}", "{:3_393:}"
//            , "{:3_394:}", "{:3_395:}", "{:3_396:}", "{:3_397:}", "{:3_398:}", "{:3_399:}", "{:3_400:}", "{:3_401:}", "{:3_402:}", "{:3_403:}", "{:3_404:}", "{:3_405:}", "{:3_406:}"};


    public static int[] grapemanRes = {
            R.drawable.grapeman18,
            R.drawable.grapeman09,
            R.drawable.grapeman24,
            R.drawable.grapeman06,
            R.drawable.grapeman03,
            R.drawable.grapeman01,
            R.drawable.grapeman08,
            R.drawable.grapeman17,
            R.drawable.grapeman22,
            R.drawable.grapeman21,
            R.drawable.grapeman12,
            R.drawable.grapeman23,
            R.drawable.grapeman10,
            R.drawable.grapeman04,
            R.drawable.grapeman14,
            R.drawable.grapeman20,
            R.drawable.grapeman07,
            R.drawable.grapeman13,
            R.drawable.grapeman15,
            R.drawable.grapeman02,
            R.drawable.grapeman16,
            R.drawable.grapeman05,
            R.drawable.grapeman19,
            R.drawable.grapeman11,
    };

    public static final String[] CODE_DECODE = new String[]{
            "#[01_01]", "#[01_02]", "#[01_03]", "#[01_04]", "#[01_05]",
            "#[01_06]", "#[01_07]", "#[01_08]", "#[01_09]", "#[01_10]",
            "#[01_11]", "#[01_12]", "#[01_13]", "#[01_14]", "#[01_15]",
            "#[01_16]", "#[01_17]", "#[01_18]", "#[01_19]", "#[01_20]",
            "#[01_21]", "#[01_22]", "#[01_23]", "#[01_24]",
            "#[02_01]", "#[02_02]", "#[02_03]", "#[02_04]", "#[02_05]",
            "#[02_06]", "#[02_07]", "#[02_08]", "#[02_09]",
            "#[02_10]", "#[02_11]", "#[02_12]", "#[02_13]",
            "#[02_14]", "#[02_15]", "#[02_16]",
            "#[03_01]", "#[03_02]", "#[03_03]", "#[03_04]", "#[03_05]",
            "#[03_06]", "#[03_07]", "#[03_08]", "#[03_09]", "#[03_10]",
            "#[03_11]", "#[03_12]", "#[03_13]", "#[03_14]", "#[03_15]",
            "#[03_16]", "#[03_17]", "#[03_18]", "#[03_19]", "#[03_20]",
            "#[03_21]", "#[03_22]", "#[03_23]", "#[03_24]"
    };
    public static final int[] EMOJ = new int[defaultRes.length + coolmonkeyRes.length + grapemanRes.length];
    public static final HashMap<String, Integer> MAPPING = new HashMap<>();
    public static final String[] CODE_DECODE1 = new String[]{
            "{:1_1:}", "{:1_2:}", "{:1_3:}", "{:1_4:}", "{:1_5:}",
            "{:1_6:}", "{:1_7:}", "{:1_8:}", "{:1_9:}", "{:1_10:}",
            "{:1_11:}", "{:1_12:}", "{:1_13:}", "{:1_14:}", "{:1_15:}",
            "{:1_16:}", "{:1_17:}", "{:1_18:}", "{:1_19:}", "{:1_20:}",
            "{:1_21:}", "{:1_22:}", "{:1_23:}", "{:1_24:}",
            "{:2_25:}", "{:2_26:}", "{:2_27:}", "{:2_28:}", "{:2_29:}",
            "{:2_30:}", "{:2_31:}", "{:2_32:}", "{:2_33:}", "{:2_34:}",
            "{:2_35:}", "{:2_36:}", "{:2_37:}", "{:2_38:}", "{:2_39:}",
            "{:2_40:}",
            "{:3_41:}", "{:3_42:}", "{:3_43:}", "{:3_44:}", "{:3_45:}",
            "{:3_46:}", "{:3_47:}", "{:3_48:}", "{:3_49:}", "{:3_50:}",
            "{:3_51:}", "{:3_52:}", "{:3_53:}", "{:3_54:}", "{:3_55:}",
            "{:3_56:}", "{:3_57:}", "{:3_58:}", "{:3_59:}", "{:3_60:}",
            "{:3_61:}", "{:3_62:}", "{:3_63:}", "{:3_64:}"
    };
    public static final String[] CODE_DECODE2 = new String[]{
            "http://bbs.cnpenjing.com/static/image/smiley/default/smile.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/sad.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/biggrin.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/cry.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/huffy.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/shocked.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/tongue.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/shy.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/titter.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/sweat.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/mad.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/lol.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/loveliness.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/funk.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/curse.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/dizzy.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/shutup.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/sleepy.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/hug.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/victory.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/time.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/kiss.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/handshake.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/default/call.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/01.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/02.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/03.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/04.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/05.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/06.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/07.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/08.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/09.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/10.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/11.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/12.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/13.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/14.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/15.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/coolmonkey/16.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/01.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/02.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/03.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/04.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/05.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/06.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/07.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/08.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/09.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/10.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/11.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/12.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/13.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/14.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/15.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/16.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/17.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/18.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/19.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/20.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/21.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/22.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/23.gif",
            "http://bbs.cnpenjing.com/static/image/smiley/grapeman/24.gif",
    };

    static {
        System.arraycopy(defaultRes, 0, EMOJ, 0, defaultRes.length);
        System.arraycopy(coolmonkeyRes, 0, EMOJ, defaultRes.length, coolmonkeyRes.length);
        System.arraycopy(grapemanRes, 0, EMOJ, defaultRes.length + coolmonkeyRes.length, grapemanRes.length);
        for (int i = 0; i < CODE_DECODE.length; i++) {
            MAPPING.put(CODE_DECODE[i], EMOJ[i]);
        }
    }

    public ArrayList<ArrayList<Smiley>> initSmileData() {
        ArrayList<ArrayList<Smiley>> result = new ArrayList<>();
        ArrayList<Smiley> defaultData = new ArrayList<>();
        for (int i = 0; i < defaultCode.length; i++) {
            Smiley smiley = new Smiley();
            smiley.setCode(defaultCode[i]);
            smiley.setResCode(defaultRes[i]);
            defaultData.add(smiley);
        }
        ArrayList<Smiley> coolmonkeyData = new ArrayList<>();
        for (int i = 0; i < coolmonkeyCode.length; i++) {
            Smiley smiley = new Smiley();
            smiley.setCode(coolmonkeyCode[i]);
            smiley.setResCode(coolmonkeyRes[i]);
            coolmonkeyData.add(smiley);
        }

        ArrayList<Smiley> grapemanyData = new ArrayList<>();
        for (int i = 0; i < grapemanCode.length; i++) {
            Smiley smiley = new Smiley();
            smiley.setCode(grapemanCode[i]);
            smiley.setResCode(grapemanRes[i]);
            grapemanyData.add(smiley);
        }
        result.add(defaultData);
        result.add(coolmonkeyData);
        result.add(grapemanyData);
        return result;
    }
}
