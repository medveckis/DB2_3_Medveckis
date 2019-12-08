package com.medveckis.util;


import com.medveckis.model.Adrese;
import com.medveckis.model.SkolasBiedrs;
import com.medveckis.model.Skolnieks;

import java.util.Scanner;

public class ClientUtils {

    private static final String AUTHOR_INFO = "Autors: Maksims Medveckis 171RDB030 1.grupa 3.kurss";
    private static final String PROGRAM_DESCRIPTION = "Šī programma palīdzes jums strādāt ar Oracle datubāzi.";
    private static final String SAY_GOODBYE = "Visu labu!";
    private static final String COMMANDS_DESCRIPTION = "Apakšā jūs varat redzēt komandas, kuras ir pieejamas:";
    private static final String EXIT_MESSAGE = "Lai izietu no programmas, uzspiediet 0.";
    private static final String INSERT_INTO_SKOLNIEKI_COMMAND = "Lai ievietotu jaunus ierakstus tabulā ‘SKOLNIEKI’, uzspiediet 1.";
    private static final String SELECT_ALL_FROM_SKOLNIEKI_COMMAND = "Lai redzētu visus ierakstus tabulā ‘SKOLNIEKI’, uzspiediet 2.";
    private static final String NO_SUCH_COMMAND = "Tādu komandu nav!";
    private static final String INPUT = "Lūdzu, ievadiet komandas numuru: ";
    private static final String DELIMITER = ", ";
    private static final String STOP = ".";


    public static void shoWelcome() {
        System.out.println(AUTHOR_INFO);
        System.out.println(PROGRAM_DESCRIPTION);
        System.out.println();
    }

    public static void showAgenda() {
        System.out.println(COMMANDS_DESCRIPTION);
        System.out.println(EXIT_MESSAGE);
        System.out.println(INSERT_INTO_SKOLNIEKI_COMMAND);
        System.out.println(SELECT_ALL_FROM_SKOLNIEKI_COMMAND);
        System.out.println();
    }

    public static void showGoodbye() {
        System.out.println(SAY_GOODBYE);
        System.out.println();
    }

    public static void showUnknownCommand() {
        System.out.println(NO_SUCH_COMMAND);
        System.out.println();
    }

    public static void showInputMessage() {
        System.out.print(INPUT);
    }

    public static void showNewLine() {
        System.out.println();
    }

    public static void showSkolnieks(Skolnieks skolnieks) {
        System.out.println("Informācija par skolnieku: ");
        System.out.print("Vards: " + skolnieks.getSkolnieksInfo().getVards() + DELIMITER);
        System.out.print("Uzvards: " + skolnieks.getSkolnieksInfo().getUzvards() + DELIMITER);
        System.out.print("Telefona Numurs: " + skolnieks.getSkolnieksInfo().getTelefonaNumurs() + DELIMITER);
        System.out.print("Iela: " + skolnieks.getSkolnieksInfo().getDzivesVieta().getIela() + DELIMITER);
        System.out.print("Dzivokļa numurs: " + skolnieks.getSkolnieksInfo().getDzivesVieta().getDzivoklaNumurs() + DELIMITER);
        System.out.print("Pasta Indeks: " + skolnieks.getSkolnieksInfo().getDzivesVieta().getPastaIndeks() + DELIMITER);
        System.out.print("Klase: " + skolnieks.getKlaseId() + STOP);
        System.out.println();
        System.out.println();
    }

    public static Skolnieks getSkolnieksFromInput(Scanner sc) {
        Skolnieks skolnieks = new Skolnieks();
        SkolasBiedrs skolasBiedrs = new SkolasBiedrs();
        Adrese adrese = new Adrese();

        System.out.println("Ievadiet nepieciešāmo informāciju par skolnieku:");
        System.out.print("Id: ");
        skolnieks.setId(Integer.parseInt(sc.nextLine()));
        System.out.println();
        skolasBiedrs.setId(skolnieks.getId());
        System.out.print("Vards: ");
        skolasBiedrs.setVards(sc.nextLine());
        System.out.println();
        System.out.print("Uzvards: ");
        skolasBiedrs.setUzvards(sc.nextLine());
        System.out.println();
        skolasBiedrs.setePast(skolasBiedrs.getVards() + "." + skolasBiedrs.getUzvards() + "@inbox.lv");
        System.out.print("Telefona numurs: ");
        skolasBiedrs.setTelefonaNumurs(sc.nextLine());
        System.out.println();
        System.out.print("Adrese Id: ");
        adrese.setId(Integer.parseInt(sc.nextLine()));
        System.out.println();
        System.out.println("Dzivokļa numurs: ");
        adrese.setDzivoklaNumurs(Integer.parseInt(sc.nextLine()));
        System.out.println();
        System.out.print("Iela: ");
        adrese.setIela(sc.nextLine());
        System.out.println();
        System.out.print("Pilseta: ");
        adrese.setPilseta(sc.nextLine());
        System.out.println();
        System.out.print("Valsts: ");
        adrese.setValst(sc.nextLine());
        System.out.println();
        System.out.print("Pasta indeks: ");
        adrese.setPastaIndeks(sc.nextLine());
        System.out.println();
        skolasBiedrs.setDzivesVieta(adrese);
        skolnieks.setSkolnieksInfo(skolasBiedrs);
        System.out.print("Klase: ");
        skolnieks.setKlaseId(Integer.parseInt(sc.nextLine()));

        return skolnieks;
    }

    public static int getIdFromInput(Scanner sc) {
        System.out.println("Skolnieks id: ");
        return Integer.parseInt(sc.nextLine());
    }
}
