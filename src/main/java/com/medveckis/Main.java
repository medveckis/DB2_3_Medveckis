package com.medveckis;


import com.medveckis.util.DatabaseUtils;
import com.medveckis.util.ClientUtils;

import java.util.Scanner;

public class Main {

    private static final String IO_ERROR_MESSAGE = "Kļūda: Nevar nolasīt klienta datus no konsoles.";

    public static void main(String[] args) {
        ClientUtils.shoWelcome();
        try(Scanner sc = new Scanner(System.in)) {
            int choice = Integer.MAX_VALUE;
            while (choice != 0) {
                ClientUtils.showAgenda();
                ClientUtils.showInputMessage();
                String inputStr = sc.nextLine();
                choice = Integer.parseInt(inputStr);
                ClientUtils.showNewLine();
                switch (choice) {
                    case 0: {
                        ClientUtils.showGoodbye();
                        break;
                    }
                    case 1: {
                        if (DatabaseUtils.insertIntoSkolniekiTable(ClientUtils.getSkolnieksFromInput(sc))) {
                            ClientUtils.showNewLine();
                            System.out.println("Jauns skolnieks bija izveidots.");
                        } else {
                            System.out.println("Jauns skolnieks nebija izveidots dēļ kļūdas.");
                        }
                        break;
                    }
                    case 2: {
                        DatabaseUtils.getAllRowsFromSKOLNIEKI().forEach(ClientUtils::showSkolnieks);
                        break;
                    }
                    case 3: {
                        if (DatabaseUtils.deleteSkolnieks(ClientUtils.getIdFromInput(sc))) {
                            ClientUtils.showNewLine();
                            System.out.println("Skolnieks ir nodzests");
                        } else {
                            System.out.println("Skolnieks nebija nodzests");
                        }
                        break;
                    }
                    default: ClientUtils.showUnknownCommand();
                }
            }
        } catch (Exception e) {
            System.err.println(IO_ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
