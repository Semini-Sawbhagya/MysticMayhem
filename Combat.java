package mysticmayhem;
import java.util.*;
import java.util.stream.*;

import static java.lang.Math.max;

public class Combat {
    private Player player1;
    private Player player2;

    public Combat(Player player1,Player player2){
        this.player1=player1;
        this.player2=player2;
    }


    public void startBattle(){
        Player attacker = player1;
        Player defender = player2;
        ArrayList<Character>InitialAttackerArmy=new ArrayList<>(player1.getArmy());
        ArrayList<Character>InitialDefenderArmy=new ArrayList<>(player2.getArmy());
        ArrayList<Character>attackerArmy=attacker.getArmy();
        ArrayList<Character>defenderArmy=defender.getArmy();
        String defenderHomeGround=player2.gethomeGround();
        applyHomeGroundAdvantage(attacker.getArmy(),defenderHomeGround);
        applyHomeGroundAdvantage(defender.getArmy(),defenderHomeGround);
        System.out.println("Battle started between "+attacker.getName()+"and"+defender.getName()+".");
        System.out.println(attacker.getName()+"attacks first due to declaring war.");
        ArrayList<Character>attackerArmySorted=sortArmyBySpeed(attackerArmy);
        ArrayList<Character>defenderArmySorted=sortArmyBySpeed(defenderArmy);
        for (int turn=0;turn<20;turn++){
            System.out.println("\nTurn"+turn);

            handleAttacks(attackerArmySorted,defenderArmySorted,attacker,defender);
            shiftFirstElementToEnd(attackerArmySorted);
            Player temp =attacker;
            attacker=defender;
            defender=temp;
            ArrayList<Character>temp_army=attackerArmySorted;
            attackerArmySorted=defenderArmySorted;
            defenderArmySorted=temp_army;
            if (isBattleOver(attackerArmySorted, defenderArmySorted)){
                break;
            };


        }
        printBattleOutcome(attackerArmySorted,defenderArmySorted);
        restoreCharacterStats(player1,InitialAttackerArmy);
        restoreCharacterStats(player2,InitialDefenderArmy);


    }
    public void swapAttackerDefender(Player attacker,Player defender,ArrayList<Character>attackerArmy,ArrayList<Character>defenderArmy){

        attackerArmy=attacker.getArmy();
        defenderArmy=defender.getArmy();


    }

    public ArrayList<Character> sortArmyBySpeed(ArrayList<Character> army) {
        return army.stream()
                .sorted(Comparator.comparingDouble(Character::getSpeed).reversed()
                        .thenComparing((Character c) -> {
                            switch (c.getType()) {
                                case "Healer":
                                    return 4;
                                case "Mage":
                                    return 3;
                                case "MythicalCreature":
                                    return 2;
                                case "Knight":
                                    return 1;
                                case "Archer":
                                    return 0;
                                default:
                                    return Integer.MAX_VALUE; // Default case if unknown type
                            }
                        }))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public void restoreCharacterStats (Player player,ArrayList<Character> initialArmy){

        player.getArmy().clear();
        for (Character initialCharacter : initialArmy) {
            try {
                Character restoredCharacter = new Character(initialCharacter.getName());
                ArrayList<Equipment>characterEquipments=new ArrayList<>();
                for(Equipment equipment:initialCharacter.getEquipment()){
                    Equipment restoredEquipment=new Equipment(equipment.getName());
                    characterEquipments.add(restoredEquipment);
                }
                restoredCharacter.getEquipment().addAll(characterEquipments);
                player.getArmy().add(restoredCharacter);

            } catch (Character.UnknownCharacterException e) {
                // Handle exception if character name is not found
                System.out.println("Unknown character name: " + initialCharacter.getName());
            } catch (Equipment.UnknownEquipmentException e) {

                e.printStackTrace();
            }
        }


    }
    public void handleAttacks(ArrayList<Character> attackerArmy, ArrayList<Character> defenderArmy,
                              Player attacker, Player defender) {

        Character attackerCharacter=attackerArmy.get(0);
        Character targetCharacter=getTargetCharacter(attackerCharacter.getType(), defenderArmy, attackerArmy);
        simulateAttack(attackerCharacter,targetCharacter);
        printAttackInfo(attackerCharacter,targetCharacter);
        if (targetCharacter.getHealth()<=0){
            defenderArmy.remove(targetCharacter);
            System.out.println(targetCharacter.getName() + " has been defeated!");
        }
        if (defenderArmy.isEmpty()) {
            System.out.println(defender.getName() + "'s army has been defeated!");
        }

    }

    public Character getTargetCharacter(String attackerType, ArrayList<Character> defenderArmy, ArrayList<Character> attackerArmy) {
        switch (attackerType) {
            case "Archer":
            case "Knight":
            case "MythicalCreature":
            case "Mage":
                // Target character with lowest defense
                return defenderArmy.stream()
                        .sorted(Comparator.comparingDouble(Character::getDefense)
                                .thenComparing((Character c) -> {
                                    switch (c.getType()) {
                                        case "Mage":
                                            return 4;
                                        case "Knight":
                                            return 3;
                                        case "Archer":
                                            return 2;
                                        case "MythicalCreature":
                                            return 1;
                                        case "Healer":
                                            return 0;
                                        default:
                                            return Integer.MAX_VALUE; // Default case if unknown type
                                    }
                                }))
                        .findFirst()
                        .orElse(null);
            case "Healer":
                // Target character in own army with lowest health
                return attackerArmy.stream()
                        .sorted(Comparator.comparingDouble(Character::getHealth))
                        .findFirst()
                        .orElse(null);
            default:
                return null;
        }
    }
    public void applyHomeGroundAdvantage(ArrayList<Character>army,String homeGround ){
        for(Character character:army){
            String battleGround = homeGround;
            switch (battleGround) {
                case "Hillcrest":
                    if (character.gethomeGround().equals("Hillcrest")) {
                        character.setAttack((character.getAttack() + 1)*1.2);
                        character.setDefense(character.getDefense() + 1);
                    } else if (character.gethomeGround().equals("Marshland") || character.gethomeGround().equals("Desert")) {
                        character.setSpeed(character.getSpeed() - 1);
                    }
                    break;
                case "Marshland":
                    if (character.gethomeGround().equals("Marshland")) {
                        character.setDefense(character.getDefense() + 2);
                    } else if (character.gethomeGround().equals("Desert")) {
                        character.setAttack(character.getAttack() - 1);

                    } else if (character.gethomeGround().equals("Arcane")) {
                        character.setSpeed(character.getSpeed() - 1);
                    }
                    break;
                case "Desert":
                    if (character.gethomeGround().equals("Marshland")) {
                        character.setHealth(character.getHealth() - 1);
                    } else if (character.gethomeGround().equals("Desert")) {
                        character.setAttack(character.getAttack() + 1);
                    }
                    break;
                case "Arcane":
                    if (character.gethomeGround().equals("Arcane")) {
                        character.setAttack(character.getAttack() + 2);

                    } else if (character.gethomeGround().equals("Hillcrest") || character.gethomeGround().equals("Marshland")) {
                        character.setSpeed(character.getSpeed() - 1);
                        character.setDefense(character.getDefense() - 1);
                    }
                    break;

                default:
                    break;
            }
        }



    }





    public void simulateAttack(Character attackerCharacter,Character defenderCharacter ){
        double damage;
        if(attackerCharacter.getType()=="Healer"){
            defenderCharacter.setHealth(defenderCharacter.getHealth()+0.1*attackerCharacter.getAttack());
        }
        else{
            damage=0.5*attackerCharacter.getAttack() - 0.1*defenderCharacter.getDefense();
            defenderCharacter.takeDamage(damage);
        }
        if (attackerCharacter.gethomeGround()=="Arcane"){
            attackerCharacter.setHealth(attackerCharacter.getHealth()*1.1);
        }
    }
    private void printAttackInfo(Character attacker, Character defender) {
        System.out.println(attacker.getName() + " attacks " + defender.getName() + "!");

        if (attacker.getType().equals("Healer")) {
            System.out.println(attacker.getName() + " heals " + defender.getName() +
                    " for " + (0.1 * attacker.getAttack()) + " health.");
        } else {
            System.out.println(defender.getName() + " takes " +
                    (0.5 * attacker.getAttack() - 0.1 * defender.getDefense()) +
                    " damage.");
        }
    }
    private void printBattleOutcome(ArrayList<Character>attackerArmy,ArrayList<Character>defenderArmy) {
        System.out.println("Battle Outcome:");

        // Print outcome for each player
        printPlayerOutcome(player1);
        printPlayerOutcome(player2);
        if (defenderArmy.isEmpty()){
            System.out.println(player1.getName() + "'s army has been defeated!");
            double prize_player2=player1.getgoldcoins()*0.1;
            int prize_money_player2=(int) prize_player2;

            player2.setgoldcoins(player2.getgoldcoins()+prize_money_player2);
            player1.setgoldcoins(player1.getgoldcoins()-prize_money_player2);
            player2.setXP(player2.getXP()+1);
        }else if (attackerArmy.isEmpty()){
            System.out.println(player2.getName() + "'s army has been defeated!");
            double prize_player1=player2.getgoldcoins()*0.1;
            int prize_money_player1=(int) prize_player1;

            player1.setgoldcoins(player1.getgoldcoins()+prize_money_player1);
            player2.setgoldcoins(player2.getgoldcoins()-prize_money_player1);
            player1.setXP(player1.getXP()+1);
        }
        else{
            System.out.println("It's a draw.");
        }
        printResults(player1);
        printResults(player2);
    }

    private void printPlayerOutcome(Player player) {
        System.out.println(player.getName() + "'s Final Army:");

        // Print status of each character in the player's army
        printCharacterStatus(player);
        System.out.println("Player's final status: ");

    }

    private void printCharacterStatus(Player player) {
        System.out.println("Status of Characters for " + player.getName() + ":");
        for (Character character : player.getArmy()) {
            System.out.println("Character: " + character.getName());
            System.out.print("   Health: " );
            System.out.format("%.2f",(max(0,character.getHealth())));
            System.out.println("   Attack: " + character.getAttack());
            System.out.println("   Defense: " + character.getDefense());
            System.out.println("   Speed: " + character.getSpeed());
            System.out.println("------------------------");
        }
    }
    public void shiftFirstElementToEnd(ArrayList<Character> army) {
        if (!army.isEmpty()) {
            // Save the first element
            Character firstCharacter = army.get(0);

            // Remove the first element
            army.remove(0);

            // Add the first element to the end
            army.add(firstCharacter);
        }
    }
    public boolean isBattleOver(ArrayList<Character> attackerArmy,ArrayList<Character>defenderArmy){
        return attackerArmy.isEmpty()|| defenderArmy.isEmpty();
    }
    public void printResults(Player player){
        System.out.println("Player status " + player.getName() + ":");
        System.out.println("Goldcoins " + player.getgoldcoins() + ":");
        System.out.println("XP " + player.getXP() + ":");

    }



}
