import java.io.*;
import java.util.*;

class Contact {
    String name, phone, email;

    Contact(String n, String p, String e) {
        name = n;
        phone = p;
        email = e;
    }

    public String toString() {
        return name + "," + phone + "," + email;
    }

    public static Contact fromString(String str) {
        String[] parts = str.split(",");
        if (parts.length == 3) {
            return new Contact(parts[0], parts[1], parts[2]);
        }
        return null;
    }
}

public class ContactManager {
    static final String FILE_NAME = "contacts.txt";
    static ArrayList<Contact> contacts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadContacts();
        while (true) {
            System.out.println("\n1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addContact();
                case 2 -> viewContacts();
                case 3 -> editContact();
                case 4 -> deleteContact();
                case 5 -> { saveContacts(); System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addContact() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        contacts.add(new Contact(name, phone, email));
        saveContacts();
        System.out.println("Contact added.");
    }

    static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts.");
            return;
        }
        int i = 1;
        for (Contact c : contacts) {
            System.out.println(i++ + ". " + c.name + " | " + c.phone + " | " + c.email);
        }
    }

    static void editContact() {
        viewContacts();
        if (contacts.isEmpty()) return;
        System.out.print("Enter number to edit: ");
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 1 || index > contacts.size()) {
            System.out.println("Invalid index.");
            return;
        }
        Contact c = contacts.get(index - 1);
        System.out.print("New name (" + c.name + "): ");
        String name = sc.nextLine();
        System.out.print("New phone (" + c.phone + "): ");
        String phone = sc.nextLine();
        System.out.print("New email (" + c.email + "): ");
        String email = sc.nextLine();

        if (!name.isEmpty()) c.name = name;
        if (!phone.isEmpty()) c.phone = phone;
        if (!email.isEmpty()) c.email = email;

        saveContacts();
        System.out.println("Contact updated.");
    }

    static void deleteContact() {
        viewContacts();
        if (contacts.isEmpty()) return;
        System.out.print("Enter number to delete: ");
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 1 || index > contacts.size()) {
            System.out.println("Invalid index.");
            return;
        }
        contacts.remove(index - 1);
        saveContacts();
        System.out.println("Contact deleted.");
    }

    static void loadContacts() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Contact c = Contact.fromString(line);
                if (c != null) contacts.add(c);
            }
        } catch (IOException e) {
            // Ignore if file not found initially
        }
    }

    static void saveContacts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact c : contacts) {
                bw.write(c.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts.");
        }
    }
}
