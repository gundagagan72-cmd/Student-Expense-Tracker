import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Expense> expenses = new ArrayList<>();

        // Load old expenses from file
        loadExpenses(expenses);

        while (true) {

            System.out.println("\n====================================");
            System.out.println("       STUDENT EXPENSE TRACKER");
            System.out.println("====================================");

            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Expenses");
            System.out.println("4. Search Expense");
            System.out.println("5. Delete Expense");
            System.out.println("6. Category Wise Total");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            // ---------------- ADD EXPENSE ----------------
            if (choice == 1) {

                System.out.print("Enter expense name: ");
                String name = sc.nextLine();

                System.out.print("Enter amount: ");
                double amount = sc.nextDouble();
                sc.nextLine();

                System.out.print("Enter category: ");
                String category = sc.nextLine();

                Expense expense = new Expense(name, amount, category);

                expenses.add(expense);

                saveExpenses(expenses);

                System.out.println("Expense added successfully!");
            }

            // ---------------- VIEW EXPENSES ----------------
            else if (choice == 2) {

                if (expenses.isEmpty()) {

                    System.out.println("No expenses found.");

                } else {

                    System.out.println("\n----------- ALL EXPENSES -----------");

                    for (int i = 0; i < expenses.size(); i++) {

                        Expense expense = expenses.get(i);

                        System.out.println("\nExpense No : " + (i + 1));
                        System.out.println("Name       : " + expense.name);
                        System.out.println("Amount     : ₹" + expense.amount);
                        System.out.println("Category   : " + expense.category);
                    }

                    System.out.println("\n------------------------------------");
                }
            }

            // ---------------- TOTAL EXPENSE ----------------
            else if (choice == 3) {

                double total = 0;

                for (Expense expense : expenses) {

                    total = total + expense.amount;
                }

                System.out.println("Total Expenses: ₹" + total);
            }

            // ---------------- SEARCH EXPENSE ----------------
            else if (choice == 4) {

                System.out.print("Enter expense name to search: ");
                String searchName = sc.nextLine();

                boolean found = false;

                for (Expense expense : expenses) {

                    if (expense.name.equalsIgnoreCase(searchName)) {

                        System.out.println("\nExpense Found!");
                        System.out.println("Name     : " + expense.name);
                        System.out.println("Amount   : ₹" + expense.amount);
                        System.out.println("Category : " + expense.category);

                        found = true;
                    }
                }

                if (!found) {

                    System.out.println("Expense not found.");
                }
            }

            // ---------------- DELETE EXPENSE ----------------
            else if (choice == 5) {

                if (expenses.isEmpty()) {

                    System.out.println("No expenses to delete.");

                } else {

                    System.out.print("Enter expense number to delete: ");
                    int number = sc.nextInt();
                    sc.nextLine();

                    if (number >= 1 && number <= expenses.size()) {

                        Expense removed = expenses.remove(number - 1);

                        saveExpenses(expenses);

                        System.out.println(
                            removed.name + " deleted successfully!"
                        );

                    } else {

                        System.out.println("Invalid expense number.");
                    }
                }
            }

            // ---------------- CATEGORY TOTAL ----------------
            else if (choice == 6) {

                System.out.print("Enter category: ");
                String category = sc.nextLine();

                double categoryTotal = 0;

                boolean found = false;

                for (Expense expense : expenses) {

                    if (expense.category.equalsIgnoreCase(category)) {

                        categoryTotal =
                            categoryTotal + expense.amount;

                        found = true;
                    }
                }

                if (found) {

                    System.out.println(
                        category + " Total: ₹" + categoryTotal
                    );

                } else {

                    System.out.println(
                        "No expenses found in this category."
                    );
                }
            }

            // ---------------- EXIT ----------------
            else if (choice == 7) {

                System.out.println(
                    "Thank you for using Student Expense Tracker!"
                );

                break;
            }

            else {

                System.out.println(
                    "Invalid choice. Please enter 1-7."
                );
            }
        }

        sc.close();
    }


    // =====================================================
    // SAVE EXPENSES TO FILE
    // =====================================================

    public static void saveExpenses(ArrayList<Expense> expenses) {

        try {

            FileWriter writer = new FileWriter(FILE_NAME);

            for (Expense expense : expenses) {

                writer.write(expense.toString());
                writer.write("\n");
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error while saving expenses.");
        }
    }


    // =====================================================
    // LOAD EXPENSES FROM FILE
    // =====================================================

    public static void loadExpenses(ArrayList<Expense> expenses) {

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            return;
        }

        try {

            BufferedReader reader =
                new BufferedReader(new FileReader(FILE_NAME));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length == 3) {

                    String name = data[0];

                    double amount =
                        Double.parseDouble(data[1]);

                    String category = data[2];

                    Expense expense =
                        new Expense(name, amount, category);

                    expenses.add(expense);
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println("Error while loading expenses.");

        } catch (NumberFormatException e) {

            System.out.println("Invalid data in expense file.");
        }
    }
}