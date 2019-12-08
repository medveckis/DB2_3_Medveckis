package com.medveckis;


import com.medveckis.util.DatabaseUtils;
import com.medveckis.util.DefaultClientUtils;

import java.util.Scanner;

public class Main {

    private static final String IO_ERROR_MESSAGE = "Kļūda: Nevar nolasīt klienta datus no konsoles.";

    public static void main(String[] args) {
        DefaultClientUtils.shoWelcome();
        try(Scanner sc = new Scanner(System.in)) {
            int choice = Integer.MAX_VALUE;
            while (choice != 0) {
                DefaultClientUtils.showAgenda();
                DefaultClientUtils.showInputMessage();
                String inputStr = sc.nextLine();
                choice = Integer.parseInt(inputStr);
                DefaultClientUtils.showNewLine();
                switch (choice) {
                    case 0: {
                        DefaultClientUtils.showGoodbye();
                        break;
                    }
                    case 1: {
                        if (DatabaseUtils.insertIntoSkolniekiTable(DefaultClientUtils.getSkolnieksFromInput(sc))) {
                            DefaultClientUtils.showNewLine();
                            System.out.println("Jauns skolnieks bija izveidots.");
                        } else {
                            System.out.println("Jauns skolnieks nebija izveidots dēļ kļūdas.");
                        }
                        break;
                    }
                    case 2: {
                        DatabaseUtils.getAllRowsFromSKOLNIEKI().forEach(DefaultClientUtils::showSkolnieks);
                        break;
                    }
                    default: DefaultClientUtils.showUnknownCommand();
                }
            }
        } catch (Exception e) {
            System.err.println(IO_ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
