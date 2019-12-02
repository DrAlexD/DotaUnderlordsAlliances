import java.util.ArrayList;
import java.util.Arrays;

public class CalculatingThread extends Thread {
    private ArrayList<Integer> heroIndexesForCurrentThread;
    private DotaUnderlordsHeroPicker dotaUnderlordsHeroPicker;
    private Long combForThread;

    CalculatingThread(String name, ArrayList<Integer> heroIndexesForCurrentThread, Long combForThread, DotaUnderlordsHeroPicker dotaUnderlordsHeroPicker) {
        super(name);
        this.heroIndexesForCurrentThread = heroIndexesForCurrentThread;
        this.dotaUnderlordsHeroPicker = dotaUnderlordsHeroPicker;
        this.combForThread = combForThread;
    }

    @Override
    public void run() {
        StringBuilder allIndexesString = new StringBuilder();
        for (int i = 0, size = heroIndexesForCurrentThread.size(); i < size; i++) {
            if (i < size - 1) {
                allIndexesString.append(heroIndexesForCurrentThread.get(i)).append(", ");
            } else {
                allIndexesString.append(heroIndexesForCurrentThread.get(i));
            }
        }

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
            dotaUnderlordsHeroPicker.calculate(heroes, pickerList, classes, pickedStrategies,
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
}