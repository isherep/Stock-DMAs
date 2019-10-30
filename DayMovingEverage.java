import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
Programming challenge description:
A classic stock trading pattern happens when a 9-Day Moving Average (9-DMA) crosses the 50-Day Moving Average (50-DMA).
 This can be indicative of a bullish or a bearish setup, depending on the direction.
When the 9-DMA crosses above the 50-DMA from below, it is Bullish. When the 9-DMA cross below the 50-DMA from above, it is Bearish.
Write a program that reads in a series of dates and prices, calculates the 9-DMA and 50-DMA,
then returns the dates of any bullish signals that occurred.
NOTE: The Moving Average cannot be calculated for a given day if there is not enough historical data to cover the period in question.

For example, a series of prices that begin on January 1 cannot have a 9-DMA calculated before January 9 since 9 days of
historical prices do not exist until January 9.
Input:
A series of Date|Price pairs in non-localized format. Dates will follow ISO 8601 format YYYY-MM-DD. Prices will be a two-decimal value with no currency indications.
For example:
2016-01-01|22.05
2016-01-02|22.45
2016-01-03|23.57
Output:
A date in ISO 8601 format where a Golden Cross occurred. If no Golden Cross happened, return the string NULL
For example:
2016-01-03

 */

public class DayMovingEverage {

    private static class Day {
        String date;
        double price;
        double average;

        // initialize the average to the current days price
        public Day(String date, double price) {
            this.date = date;
            this.price = price;
            this.average = price;

        }
    }

    public static void main(String[] main) throws java.io.FileNotFoundException {
        try {
            // InputStreamReader in = new InputStreamReader( new FileInputStream("Machintosh%20HD/Users/student/Documents/input.rtf"));
            String line;
            // InputStream in = new FileInputStream(new File("Users/student/Documents/input.rtf"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/student/Dropbox/input.txt")));
            StringBuilder out = new StringBuilder();
            double fiftyDayDMA = 0.00;
            double fiftyDaySum = 0.00;
            double nineDaySum = 0.00;
            int numDays = 0;
            double nineDayDMA = 0.00;
            double sum = 0.00;
            Queue<Day> queue9 = new LinkedList<>();
            Queue<Day> queue50 = new LinkedList<>();
            ArrayList<Day> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String date = line.substring(0, 10).trim();
                String price = line.substring(11, 16).trim();
                Double priceD = new Double(price);
                Day day = new Day(date, priceD);
                //System.out.println(day.date);
                queue9.add(day);
                queue50.add(day);
                fiftyDaySum += day.price;
                nineDaySum += day.price;
                numDays++;
                // if the 9 day average cannot be calculated before numDays hit 9 - store this everage somewhere
                sum += day.price;

                day.average = sum / numDays;
                //System.out.println("Day Everage " + day.average);
                // when reached 50 days - remove first day from the que and add new to the end
                // recalculate the everage
                while (queue50.size() == 50) {
                    // calculate everage
                   // System.out.println("50Queue size " + queue50.size());
                    //System.out.print("50Day DMA  " + fiftyDayDMA);
                    fiftyDayDMA = fiftyDaySum / 50;
                    Day firstDay50 = queue50.poll();
                    fiftyDaySum -= firstDay50.price;
                    // compare current 9days with this
                    /*
                    System.out.println("50 Day DMA " + fiftyDayDMA);
                    System.out.println("The date" + day.date);
                    System.out.println(" Last removed day " + firstDay50.price);
                    */


                }

                //if(numDays > 9) {

                    while (queue9.size() == 9 && numDays > 8) {
                        // calculate everage
                        //System.out.println("9Queue size " + queue9.size());
                        nineDayDMA = nineDaySum / 9;
                        Day firstDay9 = queue9.poll();
                        // System.out.println("day.average " + day.average);
                        nineDaySum -= firstDay9.price;
                        Day lastEnquedDay = ((LinkedList<Day>) queue9).get(queue9.size() - 1);
                        if (nineDayDMA > fiftyDayDMA && fiftyDayDMA > 0.00) {
                            System.out.println("**** " + day.date + "******");
                        }

                        System.out.println("The day that 9DMA is higher - " + day.date);
                        System.out.print("50Day DMA  " + fiftyDayDMA);
                        System.out.println("Last enqued day " + lastEnquedDay.date + ", " + lastEnquedDay.average);

                        // }
                        //  System.out.println("9 Day DMA " + nineDayDMA);
                        // System.out.println(" Last removed day " + firstDay9.price);
                        //((LinkedList<Day>) queue50).addLast(day);
                    }
                }


                // for every day, check if the 50 day everage, check if the Day's 9 day everage is larger

            //}
            //Prints the string content read from input stream
            //reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
