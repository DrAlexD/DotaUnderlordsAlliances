import javafx.util.Pair;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;

public class DotaUnderlordsHeroPicker {
    private ArrayList<Pair<Pair<ArrayList<SimpleEntry<HeroClass, Integer>>, ArrayList<Hero>>, Integer>> strategies = new ArrayList<>();

    public static void main(String[] args) {
        DotaUnderlordsHeroPicker dotaUnderlordsHeroPicker = new DotaUnderlordsHeroPicker();

        ArrayList<Hero> heroes = new ArrayList<>(Arrays.asList(Hero.values()));
        ArrayList<Hero> pickerList = new ArrayList<>();
        ArrayList<SimpleEntry<HeroClass, Integer>> classes = new ArrayList<>();
        ArrayList<SimpleEntry<HeroClass, Integer>> pickedStrategies = new ArrayList<>();

        for (int i = 0; i < heroes.size() - 9; i++) {
            System.out.println((i + 1) + "/" + (heroes.size() - 9));
            dotaUnderlordsHeroPicker.calculate(heroes, pickerList, classes, pickedStrategies, i, 0);
        }

        dotaUnderlordsHeroPicker.sortingStrategies();
        dotaUnderlordsHeroPicker.printing();
    }

    private void calculate(final ArrayList<Hero> heroes, final ArrayList<Hero> pickerList, final ArrayList<SimpleEntry<HeroClass, Integer>> classes,
                           final ArrayList<SimpleEntry<HeroClass, Integer>> pickedStrategies, int index, int strategySum) {

        ArrayList<Hero> heroesCopy = new ArrayList<>(heroes);
        ArrayList<Hero> pickerListCopy = new ArrayList<>(pickerList);
        ArrayList<SimpleEntry<HeroClass, Integer>> classesCopy = new ArrayList<>();
        for (SimpleEntry<HeroClass, Integer> cl : classes) {
            classesCopy.add(new SimpleEntry<>(cl.getKey(), cl.getValue()));
        }
        ArrayList<SimpleEntry<HeroClass, Integer>> pickedStrategiesCopy = new ArrayList<>();
        for (SimpleEntry<HeroClass, Integer> ps : pickedStrategies) {
            pickedStrategiesCopy.add(new SimpleEntry<>(ps.getKey(), ps.getValue()));
        }

        Hero addedHero = heroesCopy.remove(index);
        pickerListCopy.add(addedHero);

        HeroClass alliance = addedHero.getFirstClass();
        strategySum += getClasses(alliance, classesCopy, pickedStrategiesCopy);
        alliance = addedHero.getSecondClass();
        strategySum += getClasses(alliance, classesCopy, pickedStrategiesCopy);
        alliance = addedHero.getThirdClass();
        if (alliance != null) {
            strategySum += getClasses(alliance, classesCopy, pickedStrategiesCopy);
        }

        if (pickerList.size() < 9) {
            for (int i = index; i < heroesCopy.size() + pickerListCopy.size() - 9; i++) {
                calculate(heroesCopy, pickerListCopy, classesCopy, pickedStrategiesCopy, i, strategySum);
            }
        } else {
            sortAndAddMainStrategy(pickerListCopy, pickedStrategiesCopy, strategySum);
        }
    }

    private int getClasses(HeroClass alliance, ArrayList<SimpleEntry<HeroClass, Integer>> classesCopy, ArrayList<SimpleEntry<HeroClass, Integer>> pickedStrategiesCopy) {
        int strategySum = 0;
        int ind = -1;
        int i = 0;
        for (SimpleEntry<HeroClass, Integer> a : classesCopy) {
            if (a.getKey() == alliance) {
                ind = i;
                break;
            }
            i++;
        }
        int minHeroesForAlliance = alliance.getNumberOfHeroesForLevelOne();

        if (ind != -1) {
            SimpleEntry<HeroClass, Integer> a = classesCopy.get(ind);
            int numberOfCurrentClass = a.getValue() + 1;
            a.setValue(numberOfCurrentClass);
            if (numberOfCurrentClass > minHeroesForAlliance) {
                strategySum += 1;
                for (SimpleEntry<HeroClass, Integer> se : pickedStrategiesCopy) {
                    if (se.getKey() == alliance) {
                        se.setValue(numberOfCurrentClass);
                        break;
                    }
                }
            } else if (numberOfCurrentClass == minHeroesForAlliance) {
                strategySum += numberOfCurrentClass;
                pickedStrategiesCopy.add(a);
            }
        } else {
            SimpleEntry<HeroClass, Integer> a = new SimpleEntry<>(alliance, 1);
            classesCopy.add(a);
            if (minHeroesForAlliance == 1) {
                strategySum += 1;
                pickedStrategiesCopy.add(a);
            }
        }

        return strategySum;
    }

    private void sortAndAddMainStrategy(ArrayList<Hero> pickerListCopy, ArrayList<SimpleEntry<HeroClass, Integer>> pickedStrategies, int strategySum) {
        if (pickedStrategies.size() > 1 && strategySum > 20) {
            pickedStrategies.sort((SimpleEntry<HeroClass, Integer> o1, SimpleEntry<HeroClass, Integer> o2) -> o2.getValue() - o1.getValue());
            strategies.add(new Pair<>(new Pair<>(pickedStrategies, pickerListCopy), strategySum));
        }
    }

    private void sortingStrategies() {
        strategies.sort((Pair<Pair<ArrayList<SimpleEntry<HeroClass, Integer>>, ArrayList<Hero>>, Integer> o1,
                         Pair<Pair<ArrayList<SimpleEntry<HeroClass, Integer>>, ArrayList<Hero>>, Integer> o2) ->
                o2.getValue() - o1.getValue());
    }

    private void printing() {
        for (Pair<Pair<ArrayList<SimpleEntry<HeroClass, Integer>>, ArrayList<Hero>>, Integer> strategy : strategies) {
            System.out.print(strategy.getValue() + ") ");
            for (SimpleEntry<HeroClass, Integer> pickedStrategies : strategy.getKey().getKey()) {
                System.out.print(pickedStrategies.getKey().toString() + "(" + pickedStrategies.getValue() + ") ");
            }
            System.out.print(":");
            for (Hero pickedHeroes : strategy.getKey().getValue()) {
                System.out.print(pickedHeroes.toString() + ", ");
            }
            System.out.println();
        }
    }
}
