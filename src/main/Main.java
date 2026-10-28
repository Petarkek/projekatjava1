package main;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Player1 player = new Player1(
                "  pEtAr   keKovic  ",
                10, 5, 32, 32, 85
        );

        Game game = new Game(player);

        // Prvi neprijatelj se dodaje rucno.
        Enemy1 skeleton = new Enemy1(
                "Skeleton", 60, 5, 16, 16, 10
        );

        game.addEnemy(skeleton);

        // Drugi neprijatelj se pravi iz stringa.
        String enemyData = "Goblin; 12,5;16×16;20";

        Enemy1 goblin = parseEnemy(enemyData);

        game.addEnemy(goblin);

        System.out.println("Svi neprijatelji:");

        for (Enemy1 enemy : game.getEnemies()) {
            System.out.println(enemy);
        }

        System.out.println("\nNeprijatelji ciji tip sadrzi 'gob':");

        ArrayList<Enemy1> found = game.findByType("gob");

        if (found.isEmpty()) {
            System.out.println("Nema pronadjenih neprijatelja.");
        } else {
            for (Enemy1 enemy : found) {
                System.out.println(enemy);
            }
        }

        System.out.println("\nNeprijatelji koji se sudaraju sa igracem:");

        ArrayList<Enemy1> colliding = game.collidingWithPlayer();

        if (colliding.isEmpty()) {
            System.out.println("Nema sudara.");
        } else {
            for (Enemy1 enemy : colliding) {
                System.out.println(enemy);
            }
        }

        System.out.println("\nIgrac prije sudara: " + game.getPlayer());

        game.resolveCollisions();

        System.out.println("Igrac poslije sudara: " + game.getPlayer());

        System.out.println("\nCijeli eventLog:");

        for (String event : game.getEventLog()) {
            System.out.println(event);
        }
    }

    private static Enemy1 parseEnemy(String line) {
        if (line == null) {
            throw new IllegalArgumentException(
                    "Tekst neprijatelja ne smije biti null.");
        }

        String[] parts = line.split(";");

        if (parts.length != 4) {
            throw new IllegalArgumentException(
                    "Pogresan format. Ocekivano: Tip; x,y; sirinaxvisina; damage");
        }

        String[] position = parts[1].trim().split(",");
        String[] dimensions = parts[2].trim().split("[xX×]");

        if (position.length != 2 || dimensions.length != 2) {
            throw new IllegalArgumentException(
                    "Pogresan format pozicije ili dimenzija neprijatelja.");
        }

        try {
            String type = parts[0].trim();

            int x = Integer.parseInt(position[0].trim());
            int y = Integer.parseInt(position[1].trim());

            int width = Integer.parseInt(dimensions[0].trim());
            int height = Integer.parseInt(dimensions[1].trim());

            int damage = Integer.parseInt(parts[3].trim());

            return new Enemy1(
                    type, x, y, width, height, damage
            );

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Pozicija, dimenzije i damage moraju biti cijeli brojevi.",
                    e
            );
        }
    }
}