package Tests;

import ChangedClasses.CellEntry;
import ChangedClasses.CellFuntions;
import ChangedClasses.Ex2Sheet;
import Interfaces.Cell;

import java.util.Set;

public class GeneralTest {
    public static void main(String[] args) {
        System.out.println(CellFuntions.IsNumber("123"));
            Ex2Sheet sheet = new Ex2Sheet();
            String formula = "A0";

            Set<String> dependencies = sheet.parseDependencies(sheet.get(formula).getData());
            System.out.println(dependencies);

        }

}