package main;

import java.util.Locale;

public class Player1 {
    private String name;
    private int x;
    private int y;
    private int width;
    private int height;
    private int health;

    public Player1(String name, int x, int y, int width, int height, int health) {
        setName(name);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setHealth(health);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ime igraca ne smije biti prazno.");
        }

        String cleanName = name.trim().replaceAll("\\s+", " ");
        String[] words = cleanName.split(" ");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            String firstLetter =
                    word.substring(0, 1).toUpperCase(Locale.ROOT);

            String restOfWord =
                    word.substring(1).toLowerCase(Locale.ROOT);

            if (formattedName.length() > 0) {
                formattedName.append(" ");
            }

            formattedName.append(firstLetter).append(restOfWord);
        }

        this.name = formattedName.toString();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException(
                    "Health mora biti izmedju 0 i 100.");
        }

        this.health = health;
    }

    @Override
    public String toString() {
        return "Player[" + name + "] @ (" + x + "," + y + ") "
                + width + "x" + height + " HP=" + health;
    }
}