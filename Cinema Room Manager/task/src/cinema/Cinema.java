package cinema;
import java.util.Scanner;

public class Cinema {
    private static char[][] cinemaSeats;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the cinema
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();
        initializeCinema(rows, seatsPerRow);

        while (true) {
            // Updated menu display
            System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printSeating(rows, seatsPerRow);
                    break;
                case 2:
                    buyTicket(scanner, rows, seatsPerRow);
                    break;
                case 3:
                    statistics(rows, seatsPerRow);
                    break;
                case 0:
                    return; // Exit the program
                default:
                    System.out.println("Unknown command! Please try again.");
            }
        }
    }

    private static void initializeCinema(int rows, int seatsPerRow) {
        cinemaSeats = new char[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                cinemaSeats[i][j] = 'S'; // S for available seat
            }
        }
    }

    private static void printSeating(int rows, int seatsPerRow) {
        System.out.println("\nCinema:");
        System.out.print("  ");
        for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
            System.out.print(seatNum + " ");
        }
        System.out.println();

        for (int rowNum = 1; rowNum <= rows; rowNum++) {
            System.out.print(rowNum + " ");
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                System.out.print(cinemaSeats[rowNum - 1][seatNum - 1] + " ");
            }
            System.out.println();
        }
    }

    private static void buyTicket(Scanner scanner, int rows, int seatsPerRow) {
        while (true) {
            System.out.println("\nEnter a row number:");
            int selectedRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int selectedSeat = scanner.nextInt();

            if (selectedRow <= 0 || selectedRow > rows || selectedSeat <= 0 || selectedSeat > seatsPerRow) {
                System.out.println("Wrong input!");
            } else if (cinemaSeats[selectedRow - 1][selectedSeat - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                cinemaSeats[selectedRow - 1][selectedSeat - 1] = 'B';
                int ticketPrice = calculateTicketPrice(rows, seatsPerRow, selectedRow);
                System.out.println("Ticket price: $" + ticketPrice);
                printSeating(rows, seatsPerRow);  // Display seating arrangement after booking
                break;
            }
        }
    }

    private static int calculateTicketPrice(int rows, int seatsPerRow, int selectedRow) {
        int totalSeats = rows * seatsPerRow;
        if (totalSeats <= 60 || selectedRow <= rows / 2) {
            return 10; // Front half or small cinema
        } else {
            return 8; // Back half of a larger cinema
        }
    }

    private static void statistics(int rows, int seatsPerRow) {
        int purchasedTickets = 0;
        int currentIncome = 0;
        int totalIncome = 0;
        int totalSeats = rows * seatsPerRow;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seatsPerRow; j++) {
                totalIncome += calculateTicketPrice(rows, seatsPerRow, i);
            }
        }

        for (int i = 0; i < cinemaSeats.length; i++) {
            for (int j = 0; j < cinemaSeats[i].length; j++) {
                if (cinemaSeats[i][j] == 'B') {
                    purchasedTickets++;
                    currentIncome += calculateTicketPrice(rows, seatsPerRow, i + 1);
                }
            }
        }

        double occupancyRate = (double) purchasedTickets / totalSeats * 100;

        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%%%n", occupancyRate);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }
}
