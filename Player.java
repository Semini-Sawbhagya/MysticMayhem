package mysticmayhem;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    private int userID;
    private String username;
    private String name;
    private int XP;
    private int gc;
    private String homeGround;
    private ArrayList<Character> army;
    private boolean combatMode;
    
    private static ArrayList<String> usernames = new ArrayList<>();


    public Player(String username,String name,int XP,int gc,String homeGround) throws UsernameTakenException{
        if (usernames.contains(username)){
            throw new UsernameTakenException("UserName Already Taken!");
        }
        this.name=name;
        this.username=username;
        this.XP=XP;
        this.gc=gc;
        this.homeGround=homeGround;
        this.army= new ArrayList<>();
        this.combatMode=false;
        this.userID= ThreadLocalRandom.current().nextInt(1, 100000);
        ;
        usernames.add(username);

    }
    public static class UsernameTakenException extends Exception { // Nested class definition
        public UsernameTakenException(String message) {
            super(message);
        }
    }
    public static class InsufficientArmyException extends Exception {
        public InsufficientArmyException(String message) {
            super(message);
        }
    }
    public static class CharacterTypeLimitException extends Exception{
        public CharacterTypeLimitException(String message){
            super(message);
        }
    }
    public int getUserId(){
        return userID;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getUserName(){
        return username;
    }
    public int getXP(){
        return XP;
    }
    public void setXP(int XP){
        this.XP=XP;
    }
    public int getgoldcoins(){
        return gc;
    }
    public void setgoldcoins(int gc){
        this.gc=gc;
    }
    public String gethomeGround(){
        return homeGround;
    }
    public ArrayList<Character> getArmy(){
        return army;
    }
    public void addCharacter(Character character) throws CharacterTypeLimitException, Character.UnknownCharacterException {
        if (army.size() < 5) {
            boolean hasSameType=false;
            for (Character existingCharacter:army){
                if (existingCharacter.getType()==character.getType()){
                    hasSameType=true;
                    break;
                }
            }
            if (!hasSameType){
                if(gc>=character.getPrice()){
                    army.add(character);
                    gc-= character.getPrice();
                }
                else{
                    System.out.println("Goldcoins are not enough!   Please Buy Characters Again!");
                    army.clear();
                    Main.BuyCharacters();
                }
            }else{
                throw new CharacterTypeLimitException("Your army can only have one character of each type! ");
            }
        }else{
            System.out.println("You have a full army!");
        }


    }
    public boolean combatModeActivated() throws InsufficientArmyException {
        if (army.size() != 5) {
            throw new InsufficientArmyException("Army must consist of exactly 5 characters!");
        }

        if (!combatMode) {
            combatMode = true;
            return true; // Combat mode activated
        } else {
            System.out.println("You are already in combat mode!");
            return false; // Combat mode not activated (already active)
        }
    }
    public boolean buyEquipment(Character character, Equipment equipment) {

        if (gc >= equipment.getPrice()) {
            for (Equipment existingEquipment : character.getEquipment()) {
                if (existingEquipment.getType().equals(equipment.getType())) {
                    // Remove existing equipment if found
                    character.getEquipment().remove(existingEquipment);
                    break;
                }
            }
            character.equip(equipment);
            gc -= equipment.getPrice();
            return true;
        } else {
            System.out.println("Insufficient funds to buy equipment!");
            return false;
        }
    }


    public void sellCharacter(Character character) {
        army.remove(character);
        gc += (int) (character.getPrice() * 0.9);
    }

    public boolean isCombatMode() {
        return combatMode;
    }


}
