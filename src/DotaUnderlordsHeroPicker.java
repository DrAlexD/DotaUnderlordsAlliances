import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class DotaUnderlordsHeroPicker {
    private ArrayList<Pair<Pair<Pair<ArrayList<HeroClass>, int[]>, ArrayList<Hero>>, Integer>> strategies = new ArrayList<>();

    public static void main(String[] args) {
        DotaUnderlordsHeroPicker dotaUnderlordsHeroPicker = new DotaUnderlordsHeroPicker();

        ArrayList<Hero> heroes = new ArrayList<>(Arrays.asList(Hero.values()));
        ArrayList<Hero> pickerList = new ArrayList<>();
        ArrayList<HeroClass> classes = new ArrayList<>();
        ArrayList<HeroClass> pickedStrategies = new ArrayList<>();
        int[] classesCounter = new int[27];
        int[] pickedStrategiesCounter = new int[27];

        for (int i = 0; i < heroes.size() - 9; i++) {
            System.out.println((i + 1) + "/" + (heroes.size() - 9));
            dotaUnderlordsHeroPicker.calculate(heroes, pickerList, classes, pickedStrategies, classesCounter, pickedStrategiesCounter, i, 0);
        }

        dotaUnderlordsHeroPicker.sortingStrategies();
        dotaUnderlordsHeroPicker.printing();
    }

    private void calculate(final ArrayList<Hero> heroes, final ArrayList<Hero> pickerList,
                           final ArrayList<HeroClass> classes,
                           final ArrayList<HeroClass> pickedStrategies,
                           final int[] classesCounter,
                           final int[] pickedStrategiesCounter, int index, int strategySum) {

        ArrayList<Hero> heroesCopy = new ArrayList<>(heroes);
        ArrayList<Hero> pickerListCopy = new ArrayList<>(pickerList);
        ArrayList<HeroClass> classesCopy = new ArrayList<>(classes);
        ArrayList<HeroClass> pickedStrategiesCopy = new ArrayList<>(pickedStrategies);
        int[] classesCounterCopy = classesCounter.clone();
        int[] pickedStrategiesCounterCopy = pickedStrategiesCounter.clone();

        Hero addedHero = heroesCopy.remove(index);
        pickerListCopy.add(addedHero);

        HeroClass alliance = addedHero.getFirstClass();
        strategySum += getClasses(alliance, classesCopy, pickedStrategiesCopy, classesCounterCopy, pickedStrategiesCounterCopy);
        alliance = addedHero.getSecondClass();
        strategySum += getClasses(alliance, classesCopy, pickedStrategiesCopy, classesCounterCopy, pickedStrategiesCounterCopy);
        alliance = addedHero.getThirdClass();
        if (alliance != null) {
            strategySum += getClasses(alliance, classesCopy, pickedStrategiesCopy, classesCounterCopy, pickedStrategiesCounterCopy);
        }

        if (pickerList.size() < 9) {
            for (int i = index; i < heroesCopy.size() + pickerListCopy.size() - 9; i++) {
                calculate(heroesCopy, pickerListCopy, classesCopy, pickedStrategiesCopy, classesCounterCopy, pickedStrategiesCounterCopy, i, strategySum);
            }
        } else {
            sortAndAddMainStrategy(pickerListCopy, pickedStrategiesCopy, pickedStrategiesCounterCopy, strategySum);
        }
    }

    private int getClasses(HeroClass alliance, ArrayList<HeroClass> classesCopy, ArrayList<HeroClass> pickedStrategiesCopy,
                           int[] classesCounter, int[] pickedStrategiesCounter) {
        int strategySum = 0;
        int ind = classesCopy.indexOf(alliance);
        int minHeroesForAlliance = alliance.getNumberOfHeroesForLevelOne();

        if (ind != -1) {
            int numberOfCurrentClass = classesCounter[ind + 1] + 1;
            classesCounter[ind + 1] = numberOfCurrentClass;
            if (numberOfCurrentClass > minHeroesForAlliance) {
                strategySum += 1;
                pickedStrategiesCounter[pickedStrategiesCopy.indexOf(alliance) + 1] = numberOfCurrentClass;
            } else if (numberOfCurrentClass == minHeroesForAlliance) {
                strategySum += numberOfCurrentClass;
                pickedStrategiesCopy.add(alliance);
                pickedStrategiesCounter[0]++;
                pickedStrategiesCounter[pickedStrategiesCounter[0]] = numberOfCurrentClass;
            }
        } else {
            classesCopy.add(alliance);
            classesCounter[0]++;
            classesCounter[classesCounter[0]] = 1;
            if (minHeroesForAlliance == 1) {
                strategySum += 1;
                pickedStrategiesCopy.add(alliance);
                pickedStrategiesCounter[0]++;
                pickedStrategiesCounter[pickedStrategiesCounter[0]] = 1;
            }
        }

        return strategySum;
    }

    private void sortAndAddMainStrategy(ArrayList<Hero> pickerListCopy, ArrayList<HeroClass> pickedStrategies,
                                        int[] pickedStrategiesCounter, int strategySum) {
        if (pickedStrategies.size() > 1 && strategySum > 20) {
            int i = 0;
            for (HeroClass hc : pickedStrategies) {
                hc.setCurrentCount(pickedStrategiesCounter[i + 1]);
                i++;
            }
            pickedStrategies.sort((HeroClass o1, HeroClass o2) -> o2.getCurrentCount() - o1.getCurrentCount());
            Arrays.sort(pickedStrategiesCounter, 1, pickedStrategiesCounter[0] + 1);
            strategies.add(new Pair<>(new Pair<>(new Pair<>(pickedStrategies, pickedStrategiesCounter), pickerListCopy), strategySum));
        }
    }

    private void sortingStrategies() {
        strategies.sort((Pair<Pair<Pair<ArrayList<HeroClass>, int[]>, ArrayList<Hero>>, Integer> o1,
                         Pair<Pair<Pair<ArrayList<HeroClass>, int[]>, ArrayList<Hero>>, Integer> o2) ->
                o2.getValue() - o1.getValue());
    }

    private void printing() {
        for (Pair<Pair<Pair<ArrayList<HeroClass>, int[]>, ArrayList<Hero>>, Integer> strategy : strategies) {
            System.out.print(strategy.getValue() + ") ");
            Pair<ArrayList<HeroClass>, int[]> a = strategy.getKey().getKey();
            for (int i = 0, lt = a.getKey().size(); i < lt; i++) {
                System.out.print(a.getKey().get(i).toString() + "(" + a.getValue()[lt - i] + ") ");
            }
            System.out.print(":");
            for (Hero pickedHeroes : strategy.getKey().getValue()) {
                System.out.print(pickedHeroes.toString() + ", ");
            }
            System.out.println();
        }
    }
}
