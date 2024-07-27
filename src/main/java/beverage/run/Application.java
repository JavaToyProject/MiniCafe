package beverage.run;

import beverage.aggregate.Beverage;
import beverage.aggregate.BeverageCategory;
import beverage.service.BeverageService;

import java.util.Scanner;

import static beverage.run.BeverageController.displayMainMenu;
import static beverage.run.BeverageController.handleMainMenu;

public class Application {
    // 임시

    public static void main(String[] args) {
        while (true) {
            int input = displayMainMenu();
            if (handleMainMenu(input)) return;
        }
    }
}