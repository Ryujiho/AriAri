package hongik.enactus.myapplication.common;

import java.util.List;

public class DrugInfo {

    private static List<String> list;

    public static List<String> getList() {
        return list;
    }

    public static void setList(List<String> l) {
        list = l;
    }

}
