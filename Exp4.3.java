import java.util.*;

class TicketBookingSystem {
    private final boolean[] seats;

    public TicketBookingSystem(int numSeats) {
        seats = new boolean[numSeats];
    }

    public synchronized boolean bookSeat(int seatNumber, String userName) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            System.out.println("Invalid seat number!");
            return false;
        }
        if (seats[seatNumber - 1]) {
            System.out.println(userName + ": Seat " + seatNumber + " is already booked!");
            return false;
        }
        seats[seatNumber - 1] = true;
        System.out.println(userName + " booked seat " + seatNumber);
        return true;
    }
}

class User extends Thread {
    private final TicketBookingSystem system;
    private final int seatNumber;
    private final String userName;

    public User(TicketBookingSystem system, int seatNumber, String userName, int priority) {
        this.system = system;
        this.seatNumber = seatNumber;
        this.userName = userName;
        setPriority(priority);
    }

    @Override
    public void run() {
        system.bookSeat(seatNumber, userName);
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        int numSeats = 5;
        TicketBookingSystem system = new TicketBookingSystem(numSeats);

        List<User> users = new ArrayList<>();
        users.add(new User(system, 1, "Anish (VIP)", Thread.MAX_PRIORITY));
        users.add(new User(system, 2, "Bobby (Regular)", Thread.NORM_PRIORITY));
        users.add(new User(system, 3, "Charlie (VIP)", Thread.MAX_PRIORITY));
        users.add(new User(system, 4, "Bobby (Regular)", Thread.MIN_PRIORITY));
        users.add(new User(system, 4, "Anish (VIP)", Thread.MAX_PRIORITY));
        users.add(new User(system, 1, "Bobby (Regular)", Thread.NORM_PRIORITY));
        users.add(new User(system, 6, "David (Regular)", Thread.NORM_PRIORITY));
        users.add(new User(system, 0, "Eve (Regular)", Thread.NORM_PRIORITY));

        for (User user : users) {
            user.start();
        }

        for (User user : users) {
            try {
                user.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
