// 1. Создаем enum или класс, хранящий UI-цвета для стратегий
import core.BasicMovementStrategy;
import core.MovementStrategy;
import core.SupportedOnlyByBoxStrategy;
import core.TimeLimitedMovementStrategy;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class StrategyUIMapper {
    private static final Map<Class<?>, Color> strategyColorMap = new HashMap<>();

    static {
        strategyColorMap.put(BasicMovementStrategy.class, Color.ORANGE);
        strategyColorMap.put(TimeLimitedMovementStrategy.class, Color.RED);
        strategyColorMap.put(SupportedOnlyByBoxStrategy.class, Color.RED);
        // добавляй сюда другие стратегии и их цвета
    }

    public static Color getColorForStrategy(MovementStrategy strategy) {
        return strategyColorMap.getOrDefault(strategy.getClass(), Color.GRAY);
    }
}