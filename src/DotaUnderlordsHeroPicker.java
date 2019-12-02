import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class DotaUnderlordsHeroPicker {
    static final int MAX_HEROES_ON_BOARD = 10;
    private static final int MIN_STRATEGY_SUM = 24;
    private static final int NUMBER_OF_THREADS = 8;
    private ArrayList<Pair<Pair<Pair<ArrayList<HeroClass>, int[]>, ArrayList<Hero>>, Pair<Integer, Integer>>> strategies = new ArrayList<>();
    private int maxClassesCounter = 0; //22
    private int maxStrategiesCounter = 0; //11

    public static void main(String[] args) {
        DotaUnderlordsHeroPicker dotaUnderlordsHeroPicker = new DotaUnderlordsHeroPicker();

        ArrayList<ArrayList<Integer>> heroIndexesForThreads = new ArrayList<>();
        ArrayList<Long> combForThreads = new ArrayList<>();
        dotaUnderlordsHeroPicker.getHeroIndexesForThreads(heroIndexesForThreads, combForThreads);

        double startTime = System.nanoTime();

        dotaUnderlordsHeroPicker.startThreads(heroIndexesForThreads, dotaUnderlordsHeroPicker, combForThreads);

        dotaUnderlordsHeroPicker.sortingStrategies();

        double estimatedTime = (double) (System.nanoTime() - startTime) / 100000000.0;
        System.out.println("Время выполнения - " + (double) Math.round(estimatedTime) / 10.0);

        dotaUnderlordsHeroPicker.printing();
    }

    private void getHeroIndexesForThreads(ArrayList<ArrayList<Integer>> heroIndexesForThreads, ArrayList<Long> combForThreads) {
        int numberOfHeroes = Hero.values().length;
        long allCombinations = numberOfCombinations(MAX_HEROES_ON_BOARD, numberOfHeroes);
        long avgCombForOneThread = allCombinations / NUMBER_OF_THREADS;

        long[] combForEveryHero = new long[numberOfHeroes - MAX_HEROES_ON_BOARD + 1];
        for (int i = 1; MAX_HEROES_ON_BOARD - 1 <= numberOfHeroes - i; i++) {
            combForEveryHero[i - 1] = numberOfCombinations(MAX_HEROES_ON_BOARD - 1, numberOfHeroes - i);
        }

        boolean[] flagForEveryHero = new boolean[combForEveryHero.length];
        for (int i = 0; i < combForEveryHero.length; i++) {
            if (!flagForEveryHero[i]) {
                ArrayList<Integer> heroIndexesForCurrentThread = new ArrayList<>();
                if (combForEveryHero[i] >= avgCombForOneThread) {
                    flagForEveryHero[i] = true;
                    heroIndexesForCurrentThread.add(i);
                    heroIndexesForThreads.add(heroIndexesForCurrentThread);
                    combForThreads.add(combForEveryHero[i]);
                } else {
                    flagForEveryHero[i] = true;
                    heroIndexesForCurrentThread.add(i);
                    long combForThread = combForEveryHero[i];
                    combForThread = getHeroIndexesForCurrentThread(heroIndexesForCurrentThread, combForEveryHero, flagForEveryHero, avgCombForOneThread, i, combForThread);
                    heroIndexesForThreads.add(heroIndexesForCurrentThread);
                    combForThreads.add(combForThread);
                }
            }
        }
    }

    private long numberOfCombinations(int k, int n) {
        long a = 1;
        long b = 1;
        for (int i = 1; i <= k; i++) {
            a *= n - i + 1;
            b *= i;
        }
        return a / b;
    }

    private long getHeroIndexesForCurrentThread(ArrayList<Integer> heroIndexesForCurrentThread, long[] combForEveryHero,
                                                boolean[] flagForEveryHero, long avgCombForOneThread, int i, long combForThread) {
        for (int j = i + 1; j < combForEveryHero.length; j++) {
            if (!flagForEveryHero[j]) {
                if (combForEveryHero[i] + combForEveryHero[j] < avgCombForOneThread) {
                    if (j != i + 1) {
                        int k = j;
                        while (flagForEveryHero[k - 1] && (k > i + 1)) {
                            k--;
                        }
                        if (k > i + 1) {
                            flagForEveryHero[k - 1] = true;
                            heroIndexesForCurrentThread.add(k - 1);
                            combForThread += combForEveryHero[k - 1];
                            return combForThread;
                        } else {
                            flagForEveryHero[j] = true;
                            heroIndexesForCurrentThread.add(j);
                            combForThread += combForEveryHero[j];
                            getHeroIndexesForCurrentThread(heroIndexesForCurrentThread, combForEveryHero, flagForEveryHero,
                                    avgCombForOneThread - combForEveryHero[i], j, combForThread);
                        }
                    } else {
                        flagForEveryHero[j] = true;
                        heroIndexesForCurrentThread.add(j);
                        combForThread += combForEveryHero[j];
                        getHeroIndexesForCurrentThread(heroIndexesForCurrentThread, combForEveryHero, flagForEveryHero,
                                avgCombForOneThread - combForEveryHero[i], j, combForThread);
                    }

                }
            }
        }
        return combForThread;
    }

    private void startThreads(ArrayList<ArrayList<Integer>> heroIndexesForThreads, DotaUnderlordsHeroPicker dotaUnderlordsHeroPicker, ArrayList<Long> combForThreads) {
        int countOfNeededThreads = heroIndexesForThreads.size();

        if (countOfNeededThreads >= 9) {
            System.out.println("Ошибка в количестве потоков!");
        } else {
            CalculatingThread secondThread = null;
            CalculatingThread thirdThread = null;
            CalculatingThread fourthThread = null;
            CalculatingThread fifthThread = null;
            CalculatingThread sixthThread = null;
            CalculatingThread seventhThread = null;
            CalculatingThread eighthThread = null;

            if (countOfNeededThreads >= 2) {
                secondThread = new CalculatingThread("SecondThread", heroIndexesForThreads.get(1), combForThreads.get(1), dotaUnderlordsHeroPicker);
                secondThread.start();
            }
            if (countOfNeededThreads >= 3) {
                thirdThread = new CalculatingThread("ThirdThread", heroIndexesForThreads.get(2), combForThreads.get(2), dotaUnderlordsHeroPicker);
                thirdThread.start();
            }
            if (countOfNeededThreads >= 4) {
                fourthThread = new CalculatingThread("FourthThread", heroIndexesForThreads.get(3), combForThreads.get(3), dotaUnderlordsHeroPicker);
                fourthThread.start();
            }
            if (countOfNeededThreads >= 5) {
                fifthThread = new CalculatingThread("FifthThread", heroIndexesForThreads.get(4), combForThreads.get(4), dotaUnderlordsHeroPicker);
                fifthThread.start();
            }
            if (countOfNeededThreads >= 6) {
                sixthThread = new CalculatingThread("SixthThread", heroIndexesForThreads.get(5), combForThreads.get(5), dotaUnderlordsHeroPicker);
                sixthThread.start();
            }
            if (countOfNeededThreads >= 7) {
                seventhThread = new CalculatingThread("SeventhThread", heroIndexesForThreads.get(6), combForThreads.get(6), dotaUnderlordsHeroPicker);
                seventhThread.start();
            }
            if (countOfNeededThreads == 8) {
                eighthThread = new CalculatingThread("EighthThread", heroIndexesForThreads.get(7), combForThreads.get(7), dotaUnderlordsHeroPicker);
                eighthThread.start();
            }

            startMainThreadWithData(heroIndexesForThreads.get(0), combForThreads.get(0));

            try {
                if (countOfNeededThreads >= 2) {
                    secondThread.join();
                }
                if (countOfNeededThreads >= 3) {
                    thirdThread.join();
                }
                if (countOfNeededThreads >= 4) {
                    fourthThread.join();
                }
                if (countOfNeededThreads >= 5) {
                    fifthThread.join();
                }
                if (countOfNeededThreads >= 6) {
                    sixthThread.join();
                }
                if (countOfNeededThreads >= 7) {
                    seventhThread.join();
                }
                if (countOfNeededThreads == 8) {
                    eighthThread.join();
                }
            } catch (InterruptedException e) {
                System.out.println("Thread has been interrupted");
            }
        }
    }

    private void startMainThreadWithData(ArrayList<Integer> heroIndexesForCurrentThread, Long combForThread) {
        StringBuilder allIndexesString = new StringBuilder();
        for (int i = 0, size = heroIndexesForCurrentThread.size(); i < size; i++) {
            if (i < size - 1) {
                allIndexesString.append(heroIndexesForCurrentThread.get(i)).append(", ");
            } else {
                allIndexesString.append(heroIndexesForCurrentThread.get(i));
            }
        }

        Thread.currentThread().setName("MainThread");
        System.out.println("    " + Thread.currentThread().getName() + "(" + allIndexesString + ") started with " + combForThread + ".");

        ArrayList<Hero> heroes = new ArrayList<>(Arrays.asList(Hero.values()));
        ArrayList<Hero> pickerList = new ArrayList<>();
        ArrayList<HeroClass> classes = new ArrayList<>();
        ArrayList<HeroClass> pickedStrategies = new ArrayList<>();
        int[] classesCounter = new int[27]; //22
        int[] pickedStrategiesCounter = new int[27]; //11

        double fullTime = 0.0;
        for (Integer index : heroIndexesForCurrentThread) {
            long startTime = System.nanoTime();
            calculate(heroes, pickerList, classes, pickedStrategies,
                    classesCounter, pickedStrategiesCounter, index.intValue(), 0, 0);
            double partTime = (double) (System.nanoTime() - startTime) / 100000000.0;
            double estimatedTime = (double) Math.round(partTime) / 10.0;
            fullTime += partTime / 10.0;
            System.out.println(Thread.currentThread().getName() + "(" + allIndexesString + ") end " + index +
                    "/" + (heroes.size() - DotaUnderlordsHeroPicker.MAX_HEROES_ON_BOARD) + " in " + estimatedTime);
        }

        System.out.println("    " + Thread.currentThread().getName() + "(" + allIndexesString + ") finished in " +
                (double) Math.round(fullTime * 10.0) / 10.0);
    }

    void calculate(final ArrayList<Hero> heroes, final ArrayList<Hero> pickerList,
                   final ArrayList<HeroClass> classes,
                   final ArrayList<HeroClass> pickedStrategies,
                   final int[] classesCounter,
                   final int[] pickedStrategiesCounter, int index, int strategySum, int costSum) {

        ArrayList<Hero> heroesCopy = new ArrayList<>(heroes);
        ArrayList<Hero> pickerListCopy = new ArrayList<>(pickerList);
        ArrayList<HeroClass> classesCopy = new ArrayList<>(classes);
        ArrayList<HeroClass> pickedStrategiesCopy = new ArrayList<>(pickedStrategies);
        int[] classesCounterCopy = classesCounter.clone();
        int[] pickedStrategiesCounterCopy = pickedStrategiesCounter.clone();

        Hero addedHero = heroesCopy.remove(index);
        pickerListCopy.add(addedHero);
        costSum += addedHero.getCost();

        strategySum += getClasses(addedHero.getFirstClass(), classesCopy, pickedStrategiesCopy, classesCounterCopy, pickedStrategiesCounterCopy);
        strategySum += getClasses(addedHero.getSecondClass(), classesCopy, pickedStrategiesCopy, classesCounterCopy, pickedStrategiesCounterCopy);

        HeroClass alliance = addedHero.getThirdClass();
        if (alliance != null) {
            strategySum += getClasses(alliance, classesCopy, pickedStrategiesCopy, classesCounterCopy, pickedStrategiesCounterCopy);
        }

        if (pickerListCopy.size() < MAX_HEROES_ON_BOARD) {
            for (int i = index; i <= heroesCopy.size() + pickerListCopy.size() - MAX_HEROES_ON_BOARD; i++) {
                calculate(heroesCopy, pickerListCopy, classesCopy, pickedStrategiesCopy,
                        classesCounterCopy, pickedStrategiesCounterCopy, i, strategySum, costSum);
            }
        } else {
            /*
            synchronized (this) {
                if (maxClassesCounter < classesCounterCopy[0]) {
                    maxClassesCounter = classesCounterCopy[0];
                }
                if (maxStrategiesCounter < pickedStrategiesCounterCopy[0]) {
                    maxStrategiesCounter = pickedStrategiesCounterCopy[0];
                }
            }*/
            if (strategySum >= MIN_STRATEGY_SUM) {
                sortAndAddMainStrategy(pickerListCopy, pickedStrategiesCopy, pickedStrategiesCounterCopy, strategySum, costSum);
            }
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
                                        int[] pickedStrategiesCounter, int strategySum, int costSum) {
        int i = 0;
        for (HeroClass hc : pickedStrategies) {
            hc.setCurrentCount(pickedStrategiesCounter[i + 1]);
            i++;
        }
        pickedStrategies.sort((HeroClass o1, HeroClass o2) -> o2.getCurrentCount() - o1.getCurrentCount());
        Arrays.sort(pickedStrategiesCounter, 1, pickedStrategiesCounter[0] + 1);
        strategies.add(new Pair<>(new Pair<>(new Pair<>(pickedStrategies, pickedStrategiesCounter), pickerListCopy), new Pair<>(strategySum, costSum)));
    }

    private void sortingStrategies() {
        strategies.sort(((Comparator<Pair<Pair<Pair<ArrayList<HeroClass>, int[]>, ArrayList<Hero>>, Pair<Integer, Integer>>>) (o1, o2) ->
                o2.getValue().getKey() - o1.getValue().getKey()).thenComparing((o1, o2) -> o2.getValue().getValue() - o1.getValue().getValue()));
    }

    private void printing() {
        if (maxClassesCounter != 0 && maxStrategiesCounter != 0) {
            System.out.println("Макс количество классов: " + maxClassesCounter);
            System.out.println("Макс количество стратегий: " + maxStrategiesCounter);
        }
        for (Pair<Pair<Pair<ArrayList<HeroClass>, int[]>, ArrayList<Hero>>, Pair<Integer, Integer>> strategy : strategies) {
            System.out.print(strategy.getValue().getKey() + ", " + strategy.getValue().getValue() + ") ");
            Pair<ArrayList<HeroClass>, int[]> a = strategy.getKey().getKey();
            for (int i = 0, lt = a.getKey().size(); i < lt; i++) {
                System.out.print(a.getKey().get(i).toString() + "(" + a.getValue()[lt - i] + ") ");
            }
            System.out.print(": ");
            for (Hero pickedHeroes : strategy.getKey().getValue()) {
                System.out.print(pickedHeroes.toString() + " ");
            }
            System.out.println();
        }
    }
}
