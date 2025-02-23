package ink.reactor.api.player;

// https://minecraft.wiki/w/Experience#Leveling_up
public class ExperienceCalculator {
    public static int getRequiredExperienceToLevelUp(int level) {
        if (level <= 15) return 2 * level + 7;
        else if (level <= 31) return 5 * level - 38;
        else return 9 * level - 158;
    }

    public static int getRecollectedExperience(int level) {
        if (level <= 15) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if (level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360);
        } else {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
        }
    }

    public static int getCurrentLevelFromExperience(float experience) {
        if (experience <= 352) {
            return (int) (Math.sqrt(experience + 9) - 3);
        } else if (experience <= 1507) {
            return (int) (8.1 + Math.sqrt(0.4 * (experience - 195.975)));
        } else {
            return (int) (18.0556 + Math.sqrt(0.2222 * (experience - 752.986)));
        }
    }

    public static float getExperienceBarPercentage(float experience) {
        if (experience < 0) return 0;

        final int currentLevel = getCurrentLevelFromExperience(experience);
        final int experienceAtCurrentLevel = getRecollectedExperience(currentLevel);
        final int requiredExperience = getRequiredExperienceToLevelUp(currentLevel);

        final float experienceInCurrentLevel = experience - experienceAtCurrentLevel;
        return Math.min(1, Math.max(0, experienceInCurrentLevel / requiredExperience));
    }
}
