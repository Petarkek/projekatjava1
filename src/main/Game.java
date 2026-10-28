package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Game {
    private Player1 player;
    private Enemy1[] enemies;
    private ArrayList<String> eventLog;

    public Game(Player1 player) {
        if (player == null) {
            throw new IllegalArgumentException(
                    "Igrac ne smije biti null.");
        }

        this.player = player;
        this.enemies = new Enemy1[0];
        this.eventLog = new ArrayList<>();
    }

    public Player1 getPlayer() {
        return player;
    }

    public void setPlayer(Player1 player) {
        if (player == null) {
            throw new IllegalArgumentException(
                    "Igrac ne smije biti null.");
        }

        this.player = player;
    }

    public Enemy1[] getEnemies() {
        return Arrays.copyOf(enemies, enemies.length);
    }

    public ArrayList<String> getEventLog() {
        return new ArrayList<>(eventLog);
    }

    public void addEnemy(Enemy1 enemy) {
        if (enemy == null) {
            throw new IllegalArgumentException(
                    "Neprijatelj ne smije biti null.");
        }

        Enemy1[] newEnemies =
                Arrays.copyOf(enemies, enemies.length + 1);

        newEnemies[newEnemies.length - 1] = enemy;
        enemies = newEnemies;

        eventLog.add("ADD: " + enemy);
    }

    public boolean checkCollision(Player1 p, Enemy1 e) {
        if (p == null || e == null) {
            return false;
        }

        return p.getX() < e.getX() + e.getWidth()
                && p.getX() + p.getWidth() > e.getX()
                && p.getY() < e.getY() + e.getHeight()
                && p.getY() + p.getHeight() > e.getY();
    }

    public void decreaseHealth(Player1 p, Enemy1 e) {
        if (p == null || e == null) {
            throw new IllegalArgumentException(
                    "Igrac i neprijatelj ne smiju biti null.");
        }

        int oldHealth = p.getHealth();
        int newHealth = oldHealth - e.getDamage();

        if (newHealth < 0) {
            newHealth = 0;
        }

        p.setHealth(newHealth);

        eventLog.add("HIT: " + p.getName()
                + " by " + e.getType()
                + " for " + e.getDamage()
                + " -> HP " + oldHealth
                + " -> " + newHealth);
    }

    public ArrayList<Enemy1> findByType(String query) {
        ArrayList<Enemy1> matches = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return matches;
        }

        String cleanQuery = query.trim().toLowerCase(Locale.ROOT);

        for (Enemy1 enemy : enemies) {
            if (enemy.getType().toLowerCase(Locale.ROOT)
                    .contains(cleanQuery)) {
                matches.add(enemy);
            }
        }

        return matches;
    }

    public ArrayList<Enemy1> collidingWithPlayer() {
        ArrayList<Enemy1> colliding = new ArrayList<>();

        for (Enemy1 enemy : enemies) {
            if (checkCollision(player, enemy)) {
                colliding.add(enemy);
            }
        }

        return colliding;
    }

    public void resolveCollisions() {
        for (Enemy1 enemy : enemies) {
            if (checkCollision(player, enemy)) {
                decreaseHealth(player, enemy);
            }
        }
    }
}
