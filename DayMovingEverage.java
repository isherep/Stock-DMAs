import java.io.*;
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
        private Day(String date, double price) {
            this.date = date;
            this.price = price;
            this.average = price;
        }
    }

        public ArrayList<String> getBulishDays(File file) {
            ArrayList<String> bulishDays = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                try {
                    String line;
                    double fiftyDayDMA = 0.00;
                    double fiftyDaySum = 0.00;
                    double nineDaySum = 0.00;
                    int numDays = 0;
                    double nineDayDMA = 0.00;
                    double sum = 0.00;
                    Queue<Day> queue9 = new LinkedList<>();
                    Queue<Day> queue50 = new LinkedList<>();
                    while ((line = reader.readLine()) != null) {
                        String date = line.substring(0, 10).trim();
                        String price = line.substring(11, 16).trim();
                        Double priceD = new Double(price);
                        Day day = new Day(date, priceD);
                        // add days to the queues of 9-Day DMA's and 50 day DMA's
                        queue9.add(day);
                        queue50.add(day);
                        //  increment totals of each prices
                        fiftyDaySum += day.price;
                        nineDaySum += day.price;
                        // increment amount of days passed per each lie
                        numDays++;
                        // calculate everage after 50 days gathered in the queue
                        while (queue50.size() == 50) {
                            // calculate everage
                            fiftyDayDMA = calculateDMAAndMoveFirstDay( fiftyDaySum, 50, queue50);
                            fiftyDaySum = removeOldDayFromTheQueue(queue50, fiftyDaySum);

                        }
                        // start calculating 9-day DMA only after 9 days are in the queue(9th day)
                        while (queue9.size() == 9 && numDays > 8) {
                            // calculate everage after 9 days gathered in the queue
                            //nineDayDMA = nineDaySum / 9;
                            //Day firstDay9 = queue9.poll();
                            //nineDaySum -= firstDay9.price;
                            nineDayDMA = calculateDMAAndMoveFirstDay(nineDaySum, 9, queue9);
                            nineDaySum = removeOldDayFromTheQueue(queue9, nineDaySum);
                            if (nineDayDMA > fiftyDayDMA && fiftyDayDMA > 0.00) {
                                bulishDays.add(day.date);
                            }
                        }
                    }
                } finally {
                    reader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bulishDays;
        }

        // calculated DMA and removed the first enqued day out the que
        public double calculateDMAAndMoveFirstDay(double sum, int days, Queue<Day> queue){
            return sum/days;
        }

        public double removeOldDayFromTheQueue(Queue<Day> queue, double sum){
            Day firstDay = queue.poll();
            sum -= firstDay.price;
            return sum;
        }

    public static void main(String[] main) throws java.io.FileNotFoundException {
        File file = new File(("/Users/student/Dropbox/input.txt"));

        DayMovingEverage myEverage= new DayMovingEverage();

         ArrayList<String> dates =  myEverage.getBulishDays(file);

        for(String date: dates) {
            System.out.println(date);
        }
    }

}
