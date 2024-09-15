package mysticmayhem;
import java.util.*;
public class Character {
    private static final HashMap<String,characterData> characters=new HashMap<>();
    static{
        //name,type,price,attack,defence,health,speed,homeground
        addCharacterData("Shooter","Archer",80,11,4,6,9,"Hillcrest");
        addCharacterData("Ranger","Archer",115,14,5,8,10,"Hillcrest");
        addCharacterData("Sunfire","Archer",160,15,5,7,14,"Desert");
        addCharacterData("Zing","Archer",200,16,9,11,14,"Desert");
        addCharacterData("Saggitarius","Archer",230,18,7,12,17,"Arcane");
        addCharacterData("Squire","Knight",85,8,9,7,8,"Marshland");
        addCharacterData("Cavalier","Knight",110,10,12,7,10,"Hillcrest");
        addCharacterData("Templar","Knight",155,14,16,12,12,"Desert");
        addCharacterData("Zoro","Knight",180,17,16,13,14,"Hillcrest");
        addCharacterData("Swiftblade","Knight",250,18,20,17,13,"Marshland");
        addCharacterData("Warlock","Mage",100,12,7,10,12,"Marshland");
        addCharacterData("Illusionist","Mage",120,13,8,12,14,"Arcane");
        addCharacterData("Enchanter","Mage",160,16,10,13,16,"Hillcrest");
        addCharacterData("Conjurer","Mage",195,18,15,14,12,"Hillcrest");
        addCharacterData("Eldritch","Mage",270,19,17,18,14,"Arcane");
        addCharacterData("Soother","Healer",95,10,8,9,6,"Desert");
        addCharacterData("Medic","Healer",125,12,9,10,7,"Hillcrest");
        addCharacterData("Alchemist","Healer",150,13,13,13,13,"Marshland");
        addCharacterData("Saint","Healer",200,16,14,17,9,"Arcane");
        addCharacterData("Lightbringer","Healer",260,17,15,19,12,"Desert");
        addCharacterData("Dragon","MythicalCreature",120,12,14,15,8,"Desert");
        addCharacterData("Basilisk","MythicalCreature",165,15,11,10,12,"Marshland");
        addCharacterData("Hydra","MythicalCreature",205,12,16,15,11,"Marshland");
        addCharacterData("Pheonix","MythicalCreature",275,17,13,17,19,"Desert");
        addCharacterData("Pegasus","MythicalCreature",340,14,18,20,20,"Arcane");

    }
    private String name;
    private String type;
    private int price;
    private double attack;
    private double defence;
    private double health;
    private double speed;
    private String homeGround;
    private ArrayList<Equipment> equipment;

    public Character(String characterName) throws UnknownCharacterException{
        if(!characters.containsKey(characterName)){
            throw new UnknownCharacterException("Chracter "+characterName+" not found!");
        }
        characterData data = characters.get(characterName);
        this.name = data.getName();
        this.type = data.getType();
        this.price = data.getPrice();
        this.health = data.getHealth();
        this.attack = data.getAttack();
        this.defence = data.getDefence();
        this.speed = data.getSpeed();
        this.homeGround=data.gethomeGround();
        this.equipment = new ArrayList<>();
    }
    private static void addCharacterData(String name, String type,int price,double attack,double defence,double health, double speed,String homeGround){
        characters.put(name,new characterData(name,type,price,attack,defence,health,speed,homeGround));
    }
    public static class UnknownCharacterException extends Exception {
        public UnknownCharacterException(String message) {
            super(message);
        }
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = Math.max(health, 0);
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defence;
    }

    public void setDefense(double defence) {
        this.defence = defence;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public int getPrice() {
        return characters.get(name).getPrice();
    }

    public String gethomeGround(){
        return homeGround;
    }

    public void equip(Equipment equipment) {
        this.equipment.add(equipment);
        this.attack += equipment.getAttackBonus();
        this.defence += equipment.getDefenceBonus();
    }

    public void takeDamage(double damage) {
        this.health -= Math.max(damage , 0);
    }
    private static class characterData {
        private String name;
        private String type;
        private double health;
        private double attack;
        private double defence;
        private double speed;
        private int price;
        private String homeGround;

        public characterData(String name, String type, int price, double attack, double defence, double health, double speed,String homeGround) {
            this.name = name;
            this.type = type;
            this.health = health;
            this.attack = attack;
            this.defence = defence;
            this.speed = speed;
            this.price = price;
            this.homeGround=homeGround;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public double getHealth() {
            return health;
        }

        public double getAttack() {
            return attack;
        }

        public double getDefence() {
            return defence;
        }

        public double getSpeed() {
            return speed;
        }

        public int getPrice() {
            return price;
        }
        public String gethomeGround(){
            return homeGround;
        }
    }


}
