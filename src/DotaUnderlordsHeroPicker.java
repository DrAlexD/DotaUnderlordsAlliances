import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class DotaUnderlordsHeroPicker {
    private ArrayList<Pair<Pair<ArrayList<Pair<HeroClass, Integer>>, ArrayList<Hero>>, Integer>> strategies = new ArrayList<>();

    public static void main(String[] args) {
        DotaUnderlordsHeroPicker dotaUnderlordsHeroPicker = new DotaUnderlordsHeroPicker();

        ArrayList<Hero> heroes = new ArrayList<>(Arrays.asList(Hero.values()));
        ArrayList<Hero> pickerList = new ArrayList<>();
        ArrayList<HeroClass> classes = new ArrayList<>();
        ArrayList<Integer> numberOfClass = new ArrayList<>();

        for (int i = 0; i < heroes.size() - 9; i++) {
            System.out.println((i + 1) + "/" + (heroes.size() - 9));
            dotaUnderlordsHeroPicker.calculate(heroes, pickerList, classes, numberOfClass, i);
        }

        dotaUnderlordsHeroPicker.sortingStrategies();
        dotaUnderlordsHeroPicker.printing();
    }

    private void calculate(final ArrayList<Hero> heroes, final ArrayList<Hero> pickerList, final ArrayList<HeroClass> classes, final ArrayList<Integer> numberOfClass, int index) {
        ArrayList<Hero> heroesCopy = new ArrayList<>(heroes);
        ArrayList<Hero> pickerListCopy = new ArrayList<>(pickerList);
        ArrayList<HeroClass> classesCopy = new ArrayList<>(classes);
        ArrayList<Integer> numberOfClassCopy = new ArrayList<>(numberOfClass);

        Hero addedHero = heroesCopy.remove(index);
        pickerListCopy.add(addedHero);

        getClasses(addedHero, classesCopy, numberOfClassCopy);

        if (pickerList.size() < 9) {
            for (int i = index; i < heroesCopy.size() + pickerListCopy.size() - 9; i++) {
                calculate(heroesCopy, pickerListCopy, classesCopy, numberOfClassCopy, i);
            }
        } else {
            ArrayList<Pair<HeroClass, Integer>> pickedStrategies = new ArrayList<>();
            int strategySum = getStrategies(classesCopy, numberOfClassCopy, pickedStrategies);

            sortAndAddMainStrategy(pickerListCopy, pickedStrategies, strategySum);
        }
    }

    private void getClasses(Hero addedHero, ArrayList<HeroClass> classesCopy, ArrayList<Integer> numberOfClassCopy) {
        HeroClass firstClass = addedHero.getFirstClass();
        int indFirst = classesCopy.indexOf(firstClass);
        if (indFirst != -1)
            numberOfClassCopy.set(indFirst, numberOfClassCopy.get(indFirst) + 1);
        else {
            classesCopy.add(firstClass);
            numberOfClassCopy.add(1);
        }

        HeroClass secondClass = addedHero.getSecondClass();
        int indSecond = classesCopy.indexOf(secondClass);
        if (indSecond != -1)
            numberOfClassCopy.set(indSecond, numberOfClassCopy.get(indSecond) + 1);
        else {
            classesCopy.add(secondClass);
            numberOfClassCopy.add(1);
        }

        HeroClass thirdClass = addedHero.getThirdClass();
        if (thirdClass != null) {
            int indThird = classesCopy.indexOf(thirdClass);
            if (indThird != -1)
                numberOfClassCopy.set(indThird, numberOfClassCopy.get(indThird) + 1);
            else {
                classesCopy.add(thirdClass);
                numberOfClassCopy.add(1);
            }
        }
    }

    private int getStrategies(ArrayList<HeroClass> classes, ArrayList<Integer> numberOfClass, ArrayList<Pair<HeroClass, Integer>> pickedStrategies) {
        int strategySum = 0;
        int i = 0;
        for (HeroClass currentClass : classes) {
            int numberOfCurrentClass = numberOfClass.get(i);

            if (numberOfCurrentClass >= currentClass.getNumberOfHeroesForLevelOne()) {
                pickedStrategies.add(new Pair<>(currentClass, numberOfCurrentClass));
                strategySum += numberOfCurrentClass;
            }
            i++;
        }

        return strategySum;
    }

    private void sortAndAddMainStrategy(ArrayList<Hero> pickerListCopy, ArrayList<Pair<HeroClass, Integer>> pickedStrategies, int strategySum) {
        if (pickedStrategies.size() > 1 && strategySum > 20) {
            pickedStrategies.sort((Pair<HeroClass, Integer> o1, Pair<HeroClass, Integer> o2) -> o2.getValue() - o1.getValue());
            strategies.add(new Pair<>(new Pair<>(pickedStrategies, pickerListCopy), strategySum));
        }
    }

    private void sortingStrategies() {
        strategies.sort((Pair<Pair<ArrayList<Pair<HeroClass, Integer>>, ArrayList<Hero>>, Integer> o1,
                         Pair<Pair<ArrayList<Pair<HeroClass, Integer>>, ArrayList<Hero>>, Integer> o2) ->
                o2.getValue() - o1.getValue());
    }

    private void printing() {
        for (Pair<Pair<ArrayList<Pair<HeroClass, Integer>>, ArrayList<Hero>>, Integer> strategy : strategies) {
            System.out.print(strategy.getValue() + ") ");
            for (Pair<HeroClass, Integer> pickedStrategies : strategy.getKey().getKey()) {
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
