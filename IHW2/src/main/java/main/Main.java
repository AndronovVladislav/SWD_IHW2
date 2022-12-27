package main;

import utils.Errors;
import utils.Utilities;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WorkController workController = new WorkController(Utilities.scanner.nextLine());

        if (workController.checkBeforeStart()) {
            workController.workLoop();
        } else {
            Errors.badStartDirectory();
        }
    }
}