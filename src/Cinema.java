import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int command; // store the command to execute
        int totalRowsNumber; // number of total rows
        int seatsInEachRow; // number of seats in each row
        String[][] seats; // the room

        System.out.println("Enter the number of rows:");
        totalRowsNumber = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatsInEachRow = scanner.nextInt();

        seats = createRoom(totalRowsNumber, seatsInEachRow);

        do {
            command = printMenu(scanner);
            switch (command) {
                case 1:
                    drawRoom(seats);
                    break;
                case 2:
                    buyTicket(seats);
                    break;
                case 3:
                    printStatistics(seats);
                    break;
                case 0:
                    break;
            }
        } while (command != 0);
    }

    public static int printMenu(Scanner scanner) {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        return scanner.nextInt();
    }

    public static long calculateTotalIncome(String[][] seats) {
        int totalRowsNumber = seats.length;
        int seatsInEachRow = seats[0].length;

        if (totalRowsNumber * seatsInEachRow <= 60) {
            return (long) totalRowsNumber * seatsInEachRow * 10;
        } else {
            // In a larger room, the tickets are 10 dollars for the front half of the rows and 8 dollars for the back half.
            // Please note that the number of rows can be odd, for example, 9 rows.
            // In this case, the first half is the first 4 rows, and the second half is the other 5 rows.

            int firstHalf;
            int secondHalf;

            // calculate first half of the room
            firstHalf = totalRowsNumber / 2;
            secondHalf = firstHalf + (totalRowsNumber % 2);

            return (long) ((firstHalf * seatsInEachRow * 10) + (secondHalf * seatsInEachRow * 8));
        }
    }

    public static String[][] createRoom(int totalRowsNumber, int seatsInEachRow) {

        String[][] seats = new String[totalRowsNumber][seatsInEachRow];

        // Fill the array with S character
        for (String[] row : seats)
            Arrays.fill(row, "S");

        return seats;
    }

    public static void buyTicket(String[][] seats) {
        Scanner scanner = new Scanner(System.in);

        int rowNumber; // number of row for the seat
        int seatNumber; // seat in the row
        int ticketPrice; // contains the price of the ticket

        do {
            System.out.format("%nEnter a row number:%n");
            rowNumber = scanner.nextInt();

            System.out.println("Enter a seat number in that row:");
            seatNumber = scanner.nextInt();

            // check if row and seat number are out of bounds.
            if (rowNumber < 0 || rowNumber > seats.length || seatNumber < 0 || seatNumber > seats[0].length) {
                System.out.println("Wrong input!");
                continue;
            }

            // check if the selected ticket has been already purchased
            if (seats[rowNumber - 1][seatNumber - 1].equals("B")) {
                System.out.println("That ticket has already been purchased!");
            } else {
                break;
            }
        } while (true);

        // assign B to seat into array of Seats
        seats[rowNumber - 1][seatNumber - 1] = "B";

        // In a larger room, the tickets are 10 dollars for the front half of the rows and 8 dollars for the back half.
        // Please note that the number of rows can be odd, for example, 9 rows.
        // In this case, the first half is the first 4 rows, and the second half is the other 5 rows.

        // calculate price for the seat just bought
        // if seats are <= 60
        if (seats.length * seats[0].length <= 60) {
            // price is 10$
            ticketPrice = 10;
        } else if (rowNumber <= seats.length / 2) {
            // in first half price is 10$
            ticketPrice = 10;
        } else {
            // if second half price is 8%
            ticketPrice = 8;
        }

        System.out.printf("%nTicket price: $%d%n", ticketPrice);
    }

    public static void printStatistics(String[][] seats) {
        // gather data
        int purchasedTickets;
        long currentIncome;
        long totalIncome;
        float percentage;
        int totalRowsNumber = seats.length;
        int seatsInEachRow = seats[0].length;

        purchasedTickets = calculatePurchasedTickets(seats);
        totalIncome = calculateTotalIncome(seats);
        currentIncome = calculateCurrentIncome(seats);
        percentage = (float) (purchasedTickets / (float) (totalRowsNumber * seatsInEachRow)) * 100;

        System.out.format("%nNumber of purchased tickets: %d%n", purchasedTickets);
        System.out.format("Percentage: %.2f%%%n", percentage);
        System.out.format("Current income: $%d%n", currentIncome);
        System.out.format("Total income: $%d%n", totalIncome);
    }

    public static long calculateCurrentIncome(String[][] seats) {
        //int seatsCounter = 0;
        long currentIncome = 0;
        int firstHalf = 0;
        int secondHalf = 0;
        int totalSeats = 0;

        int totalRowsNumber = seats.length;
        int seatsInEachRow = seats[0].length;
        totalSeats = totalRowsNumber * seatsInEachRow;

        // calculate first half of the room
        firstHalf = totalRowsNumber / 2;
        secondHalf = firstHalf + (totalRowsNumber % 2);

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                // if seats is booked and total seats <= 60
                if (seats[i][j].equals("B") && totalSeats <= 60) {
                    // price is 10$
                    currentIncome = currentIncome + 10;
                } else if (seats[i][j].equals("B")) { // seats are > 60
                    // you need to subtract 1 from first half because index starts from zero!!
                    if (i <= firstHalf - 1) {
                        // in first half of the room, price is 10$
                        currentIncome = currentIncome + 10;
                    } else {
                        // in second half of the room, price is 8$
                        currentIncome = currentIncome + 8;
                    }
                }
            }
        }
        return currentIncome;
    }

    public static int calculatePurchasedTickets(String[][] seats) {
        int seatsCounter = 0;

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                // if seats is booked, then increment seatsCounter
                if (seats[i][j].equals("B")) {
                    seatsCounter++;
                }
            }
        }
        return seatsCounter;
    }

    public static void drawRoom(String[][] seats) {
        // print room
        System.out.println("\nCinema:");
        // print header for columns
        System.out.print("  ");
        //for (int i = 0; i < seatsInEachRow; i++) {
        for (int i = 0; i < seats[0].length; i++) {
            System.out.print(i + 1);
            if (i != seats[0].length - 1) {
                System.out.print(" ");
            } else {
                System.out.println("");
            }
        }

        // print rows
        for (int i = 0; i < seats.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j]);
                if (j != seats[i].length - 1) {
                    System.out.print(" ");
                } else {
                    System.out.println("");
                }
            }

        }
    }
}