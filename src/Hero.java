public enum Hero {
    Акс(HeroClass.Крепкий, HeroClass.Громила),
    Антимаг(HeroClass.Неуловимый, HeroClass.Убийца),
    Туск(HeroClass.Дикарь, HeroClass.Воин),
    Веник(HeroClass.Дикарь, HeroClass.Чернокнижник),
    ШэдоуДемон(HeroClass.Бессердечный, HeroClass.Демон),
    Дровка(HeroClass.Бессердечный, HeroClass.Охотник),
    Бладсикер(HeroClass.ОднойКрови, HeroClass.Меткий),
    Никс(HeroClass.Насекомое, HeroClass.Убийца),
    Тини(HeroClass.Первородный, HeroClass.Воин),
    Бэтрайдер(HeroClass.Тролль, HeroClass.Рыцарь),
    ОгрМаг(HeroClass.ОднойКрови, HeroClass.Маг),
    БаунтиХантер(HeroClass.Задира, HeroClass.Убийца),
    Разор(HeroClass.Первородный, HeroClass.Маг),
    Энча(HeroClass.Дикарь, HeroClass.Друид, HeroClass.Целитель),
    Варлок(HeroClass.ОднойКрови, HeroClass.Чернокнижник, HeroClass.Целитель),
    Шаман(HeroClass.Тролль, HeroClass.Шаман),

    Дазл(HeroClass.Тролль, HeroClass.Целитель),
    Тимбер(HeroClass.Задира, HeroClass.Изобретатель),
    ВитчДоктор(HeroClass.Тролль, HeroClass.Чернокнижник),
    Квопа(HeroClass.Демон, HeroClass.Убийца),
    Цмка(HeroClass.Человек, HeroClass.Маг),
    Фурион(HeroClass.Друид, HeroClass.Шаман),
    Луна(HeroClass.Неуловимый, HeroClass.Рыцарь),
    Врка(HeroClass.Неуловимый, HeroClass.Охотник),
    Магнус(HeroClass.Шаман, HeroClass.Громила),
    Пудж(HeroClass.Бессердечный, HeroClass.Воин),
    Пак(HeroClass.Неуловимый, HeroClass.Дракон, HeroClass.Маг),
    Бистмастер(HeroClass.Крепкий, HeroClass.Охотник),
    Чаос(HeroClass.Демон, HeroClass.Рыцарь),
    Слардар(HeroClass.Чешуйчатый, HeroClass.Воин),
    Вивер(HeroClass.Насекомое, HeroClass.Неуловимый),

    Абадон(HeroClass.Бессердечный, HeroClass.Рыцарь),
    Вайпер(HeroClass.Чешуйчатый, HeroClass.Дракон),
    /*Джагер(HeroClass.Крепкий, HeroClass.Воин),
    Ио(HeroClass.Первородный, HeroClass.Друид),
    Сф(HeroClass.Демон, HeroClass.Чернокнижник),
    Легионка(HeroClass.Человек, HeroClass.Чемпион),
    Люкан(HeroClass.Человек, HeroClass.Дикарь, HeroClass.Охотник),
    Лина(HeroClass.Человек, HeroClass.Маг),
    Тинкер(HeroClass.Задира, HeroClass.Изобретатель),
    Морф(HeroClass.Первородный, HeroClass.Маг),
    Найкс(HeroClass.Бессердечный, HeroClass.Громила),
    Омник(HeroClass.Человек, HeroClass.Рыцарь, HeroClass.Целитель),
    Сларк(HeroClass.Чешуйчатый, HeroClass.Убийца),
    Снайпер(HeroClass.Задира, HeroClass.Меткий, HeroClass.Охотник),
    Терроблейд(HeroClass.Демон, HeroClass.Охотник),
    Фантомка(HeroClass.Неуловимый, HeroClass.Убийца),
    Клокверк(HeroClass.Задира, HeroClass.Изобретатель),
    Трент(HeroClass.Друид, HeroClass.Громила),

    Алхимик(HeroClass.Задира, HeroClass.Чернокнижник),
    Дум(HeroClass.Демон, HeroClass.Громила),
    Бристлбек(HeroClass.Крепкий, HeroClass.Дикарь),
    СандКинг(HeroClass.Дикарь, HeroClass.Насекомое),
    Кунка(HeroClass.Человек, HeroClass.Воин),
    Бруда(HeroClass.Насекомое, HeroClass.Чернокнижник),
    Мирана(HeroClass.Неуловимый, HeroClass.Охотник),
    Тайд(HeroClass.Чешуйчатый, HeroClass.Воин),
    Некрофос(HeroClass.Бессердечный, HeroClass.Целитель),
    ЛонДруид(HeroClass.Дикарь, HeroClass.Друид),
    АркВарден(HeroClass.Первородный, HeroClass.Шаман),
    ДрагонКнайт(HeroClass.Человек, HeroClass.Дракон, HeroClass.Рыцарь),
    Темпларка(HeroClass.Неуловимый, HeroClass.Убийца),
    Котл(HeroClass.Человек, HeroClass.Маг),

    Войд(HeroClass.Первородный, HeroClass.Убийца),
    Гиро(HeroClass.Меткий, HeroClass.Изобретатель),
    Дизраптор(HeroClass.Крепкий, HeroClass.Чернокнижник),
    Лич(HeroClass.Бессердечный, HeroClass.Маг),
    Медуза(HeroClass.Чешуйчатый, HeroClass.Охотник),
    Минер(HeroClass.Задира, HeroClass.Изобретатель),
    Свен(HeroClass.Человек, HeroClass.Чешуйчатый, HeroClass.Рыцарь),
    Тролль(HeroClass.Тролль, HeroClass.Воин),
    Энигма(HeroClass.Первородный, HeroClass.Шаман)*/;

    private final HeroClass firstClass;
    private final HeroClass secondClass;
    private final HeroClass thirdClass;

    Hero(HeroClass firstClass, HeroClass secondClass) {
        this.firstClass = firstClass;
        this.secondClass = secondClass;
        this.thirdClass = null;
    }

    Hero(HeroClass firstClass, HeroClass secondClass, HeroClass thirdClass) {
        this.firstClass = firstClass;
        this.secondClass = secondClass;
        this.thirdClass = thirdClass;
    }

    public HeroClass getFirstClass() {
        return firstClass;
    }

    public HeroClass getSecondClass() {
        return secondClass;
    }

    public HeroClass getThirdClass() {
        return thirdClass;
    }
}
