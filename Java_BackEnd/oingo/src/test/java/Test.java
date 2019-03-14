import main.util.TimeConvert;

import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws Exception{
        // TODO 重复条件，周几还是每月几号
        Date today = TimeConvert.convertToDate("2018-12-14 12:12");
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(week_index);
    }


}
