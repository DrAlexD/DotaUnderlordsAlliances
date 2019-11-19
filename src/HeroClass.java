public enum HeroClass {
    Бессердечный(2, 4, 6),
    Воин(3, 6),
    Громила(2, 4),
    Демон(1),
    Дикарь(2, 4, 6),
    Дракон(2),
    Друид(2, 4),
    Задира(2, 4, 6),
    Изобретатель(2, 4),
    Крепкий(2, 4),
    Маг(3, 6),
    Меткий(2),
    Насекомое(3),
    Неуловимый(3, 6),
    ОднойКрови(2),
    Охотник(3, 6),
    Первородный(2, 4, 6),
    Рыцарь(2, 4, 6),
    Тролль(2, 4),
    Убийца(3, 6),
    Целитель(3),
    Человек(2, 4, 6),
    Чемпион(1),
    Чернокнижник(2, 4, 6),
    Чешуйчатый(2, 4),
    Шаман(2, 4);

    private final int numberOfHeroesForLevelOne;
    private final int numberOfHeroesForLevelTwo;
    private final int numberOfHeroesForLevelThree;

    HeroClass(int numberOfHeroesForLevelOne, int numberOfHeroesForLevelTwo, int numberOfHeroesForLevelThree) {
        this.numberOfHeroesForLevelOne = numberOfHeroesForLevelOne;
        this.numberOfHeroesForLevelTwo = numberOfHeroesForLevelTwo;
        this.numberOfHeroesForLevelThree = numberOfHeroesForLevelThree;
    }

    HeroClass(int numberOfHeroesForLevelOne, int numberOfHeroesForLevelTwo) {
        this.numberOfHeroesForLevelOne = numberOfHeroesForLevelOne;
        this.numberOfHeroesForLevelTwo = numberOfHeroesForLevelTwo;
        this.numberOfHeroesForLevelThree = -1;
    }

    HeroClass(int numberOfHeroesForLevelOne) {
        this.numberOfHeroesForLevelOne = numberOfHeroesForLevelOne;
        this.numberOfHeroesForLevelTwo = -1;
        this.numberOfHeroesForLevelThree = -1;
    }

    public int getNumberOfHeroesForLevelOne() {
        return numberOfHeroesForLevelOne;
    }

    public int getNumberOfHeroesForLevelTwo() {
        return numberOfHeroesForLevelTwo;
    }

    public int getNumberOfHeroesForLevelThree() {
        return numberOfHeroesForLevelThree;
    }
}
