import java.util.*;

class Card {
    String suit;
    String rank;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return rank.equals(card.rank) && suit.equals(card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}

public class CardCollectionSystem {
    private static Map<String, Set<Card>> cardCollection = new HashMap<>();

    public static void addCard(String rank, String suit) {
        cardCollection.putIfAbsent(suit, new HashSet<>());
        Card newCard = new Card(rank, suit);
        if (cardCollection.get(suit).contains(newCard)) {
            System.out.println("Error: Card \"" + newCard + "\" already exists.");
        } else {
            cardCollection.get(suit).add(newCard);
            System.out.println("Card added: " + newCard);
        }
    }

    public static void findCardsBySuit(String suit) {
        if (cardCollection.containsKey(suit) && !cardCollection.get(suit).isEmpty()) {
            for (Card card : cardCollection.get(suit)) {
                System.out.println(card);
            }
        } else {
            System.out.println("No cards found for " + suit + ".");
        }
    }

    public static void displayAllCards() {
        if (cardCollection.isEmpty() || cardCollection.values().stream().allMatch(Set::isEmpty)) {
            System.out.println("No cards found.");
        } else {
            for (Set<Card> cards : cardCollection.values()) {
                for (Card card : cards) {
                    System.out.println(card);
                }
            }
        }
    }

    public static void removeCard(String rank, String suit) {
        if (cardCollection.containsKey(suit)) {
            Card cardToRemove = new Card(rank, suit);
            if (cardCollection.get(suit).remove(cardToRemove)) {
                System.out.println("Card removed: " + cardToRemove);
                if (cardCollection.get(suit).isEmpty()) {
                    cardCollection.remove(suit);
                }
            } else {
                System.out.println("Error: Card not found.");
            }
        } else {
            System.out.println("Error: Suit not found.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nCard Collection System");
            System.out.println("1. Add Card");
            System.out.println("2. Find Cards by Suit");
            System.out.println("3. Display All Cards");
            System.out.println("4. Remove Card");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Card Rank: ");
                    String rank = scanner.nextLine();
                    System.out.print("Enter Card Suit: ");
                    String suit = scanner.nextLine();
                    addCard(rank, suit);
                    break;
                case 2:
                    System.out.print("Enter Suit to Search: ");
                    String searchSuit = scanner.nextLine();
                    findCardsBySuit(searchSuit);
                    break;
                case 3:
                    displayAllCards();
                    break;
                case 4:
                    System.out.print("Enter Card Rank to Remove: ");
                    String removeRank = scanner.nextLine();
                    System.out.print("Enter Card Suit to Remove: ");
                    String removeSuit = scanner.nextLine();
                    removeCard(removeRank, removeSuit);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
v
