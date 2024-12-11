import java.util.*;

class TrainCoach {
    private final int[][] coach; // 2D array to represent seat layout in the coach

    public TrainCoach() {
        // Initialize the coach with 11 rows of 7 seats and 1 row of 3 seats
        coach = new int[12][];
        for (int i = 0; i < 11; i++) {
            coach[i] = new int[7]; // Rows 1 to 11 have 7 seats each
        }
        coach[11] = new int[3]; // Row 12 has 3 seats
    }

    public void displaySeats() {
        // Display the current seat status
        System.out.println("Current Seat Availability:");
        for (int i = 0; i < coach.length; i++) {
            System.out.print("Row " + (i + 1) + ": ");
            for (int seat : coach[i]) {
                // Use 'O' to indicate available seats and 'X' for booked seats
                System.out.print((seat == 0 ? "O" : "X") + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void findAndBookSeats(int numSeats) {
        if (numSeats <= 0 || numSeats > 7) {
            // Validate the input for the number of seats to book
            System.out.println("Invalid number of seats. You can reserve 1 to 7 seats at a time.");
            return;
        }

        // Try to book seats in a single row if possible
        for (int i = 0; i < coach.length; i++) {
            int available = 0;
            // Count available seats in the current row
            for (int seat : coach[i]) {
                if (seat == 0)
                    available++;
            }

            if (available >= numSeats) {
                // Book the required number of seats in this row
                List<String> reserved = new ArrayList<>();
                for (int j = 0; j < coach[i].length && numSeats > 0; j++) {
                    if (coach[i][j] == 0) {
                        coach[i][j] = 1; // Mark seat as booked
                        reserved.add("Row " + (i + 1) + " Seat " + (j + 1));
                        numSeats--;
                    }
                }
                System.out.println("Seats reserved: " + reserved);
                displaySeats(); // Show updated seat status
                return;
            }
        }

        // If no single row can accommodate, book seats across multiple rows
        List<String> reserved = new ArrayList<>();
        for (int i = 0; i < coach.length && numSeats > 0; i++) {
            for (int j = 0; j < coach[i].length && numSeats > 0; j++) {
                if (coach[i][j] == 0) {
                    coach[i][j] = 1; // Mark seat as booked
                    reserved.add("Row " + (i + 1) + " Seat " + (j + 1));
                    numSeats--;
                }
            }
        }

        if (!reserved.isEmpty()) {
            // Display the reserved seats if successful
            System.out.println("Seats reserved: " + reserved);
        } else {
            // Notify user if there are not enough available seats
            System.out.println("Not enough seats available to fulfill the request.");
        }
        displaySeats(); // Show updated seat status
    }

    public static void main(String[] args) {
        TrainCoach coach = new TrainCoach(); // Create a new train coach instance
        coach.displaySeats(); // Display initial seat availability

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the number of seats to reserve (or -1 to exit): ");
            int seatsToReserve;
            try {
                seatsToReserve = scanner.nextInt(); // Read user input
                if (seatsToReserve == -1) {
                    // Exit the reservation system
                    System.out.println("Exiting reservation system. Goodbye!");
                    break;
                }
                coach.findAndBookSeats(seatsToReserve); // Attempt to book seats
            } catch (InputMismatchException e) {
                // Handle invalid input
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        scanner.close(); // Close the scanner resource
    }
}
