package BloodBankManagementApp.utility;

import java.sql.Array;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateValidation {
    static int MAX_VALID_YR = 9999;
    static int MIN_VALID_YR = 1800;
    public DateValidation(){}

    // Returns true if
    // given year is valid.
    static boolean isLeap(int year)
    {
        // Return true if year is
        // a multiple of 4 and not
        // multiple of 100.
        // OR year is multiple of 400.
        return (((year % 4 == 0) &&
                (year % 100 != 0)) ||
                (year % 400 == 0));
    }

    // Returns true if given
    // year is valid or not.
    static boolean isValidDate(int d,
                               int m,
                               int y)
    {
        // If year, month and day
        // are not in given range
        if (y > MAX_VALID_YR ||
                y < MIN_VALID_YR)
            return false;
        if (m < 1 || m > 12)
            return false;
        if (d < 1 || d > 31)
            return false;

        // Handle February month
        // with leap year
        if (m == 2)
        {
            if (isLeap(y))
                return (d <= 29);
            else
                return (d <= 28);
        }

        // Months of April, June,
        // Sept and Nov must have
        // number of days less than
        // or equal to 30.
        if (m == 4 || m == 6 ||
                m == 9 || m == 11)
            return (d <= 30);

        return true;
    }
    //static int isValidAge(Date date) {
    /*static boolean isValidAge(Date date) {
        LocalDate currentDate = LocalDate.now();

       // date = new Date();
        LocalDate local = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int age = Period.between( local, currentDate ).getYears();
        //return age;

        boolean isEligibleAge = false;
        if(age > 17 && age < 66){
            isEligibleAge = true;
        }
        return isEligibleAge;
    }*/
    static long eligibleAge(Date dob)throws Exception{
        long diff = 0;
        LocalDate now = LocalDate.now();
        /*Date then = new Date(2018, Calendar.NOVEMBER, 20);

        LocalDate converted = LocalDate.of(then.getYear(), then.getMonth(), then.getDate());*/
        try {
            Date then = new Date(String.valueOf(dob));
            //String date = new String(dob.toString());
            String date = new String(then.toString());
            String[] arr = (date.split("/"));
            int y = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            int d = Integer.parseInt(arr[2]);
            LocalDate converted = LocalDate.of(y, m, d);


             diff = Math.abs(ChronoUnit.YEARS.between(now, converted));

            return diff;
        }catch (Exception e){
            System.out.println("Error:  " + e);
        }
        //System.out.println("Result: " + diff);
       // return diff;

        return diff;
    }

    // Driver code
    public static void main(String args[])throws Exception
    {
        if (isValidDate(10, 12, 2028))
            System.out.println("Yes");
        else
            System.out.println("No");

        if (isValidDate(30, 11, 2000))
            System.out.println("Yes");
        else
            System.out.println("No");

        //Date date = new Date(2018,11,20);
        Date date = new Date("2018/11/20");

        /*if (isValidAge(date))
            System.out.println("Yes");
        else
            System.out.println("No");

        Date date2 = new Date(2000,11,20);
        if (isValidAge(date2))
            System.out.println("Yes");
        else
            System.out.println("No");*/
        //System.out.println(isValidAge(date));
        System.out.println(eligibleAge(date));
    }


//https://www.geeksforgeeks.org/program-check-date-valid-not/
// This code is contributed
// by Nikita Tiwari.
}
