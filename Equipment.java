package mysticmayhem;

import java.util.HashMap;

public class Equipment {
    private String name;
    private String type;
    private int price;
    private double attackBonus;
    private double defenceBonus;
    private double speedBonus;
    private double healthBonus;
    private static final HashMap<String,EquipmentData> equipmentData=new HashMap<>();
    static{
        //name,type,price,attackBonus,defenceBonus,healthBonus,speedBonus
        addEquipmentData("Chainmail","Armour",70,0,1,0,-1);
        addEquipmentData("Regalia","Armour",105,0,1,0,0);
        addEquipmentData("Fleece","Armour",150,0,2,1,-1);
        addEquipmentData("Excalibur","Artefact",150,2,0,0,0);
        addEquipmentData("Amulet","Artefact",200,1,-1,1,1);
        addEquipmentData("Crystal","Artefact",210,2,1,-1,-1);
    }

    public Equipment(String equipmentName) throws UnknownEquipmentException {
        if (!equipmentData.containsKey(equipmentName)) {
            throw new UnknownEquipmentException("Equipment '" + equipmentName + "' not found!");
        }
        EquipmentData data = equipmentData.get(equipmentName);
        this.name = data.getName();
        this.type = data.getType();
        this.price = data.getPrice();
        this.attackBonus = data.getAttackBonus();
        this.defenceBonus = data.getDefenceBonus();
        this.healthBonus = data.getHealthBonus();
        this.speedBonus = data.getSpeedBonus();
    }
    private static void addEquipmentData(String name, String type, int price, double attackBonus, double defenceBonus, double healthBonus, double speedBonus) {
        equipmentData.put(name, new EquipmentData(name, type, price, attackBonus, defenceBonus, healthBonus, speedBonus));
    }
    public static class UnknownEquipmentException extends Exception {
        public UnknownEquipmentException(String message) {
            super(message);
        }
    }

    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public int getPrice(){
        return price;
    }
    public double getAttackBonus(){
        return attackBonus;
    }
    public double getDefenceBonus(){
        return defenceBonus;
    }
    public double getHealthBonus(){
        return healthBonus;
    }
    public double getSpeedBonus(){
        return speedBonus;
    }
    private static class EquipmentData{
        private String name;
        private String type;
        private int price;
        private double attackBonus;
        private double defenceBonus;
        private double healthBonus;
        private double speedBonus;

        public EquipmentData(String name,String type, int price,double attackBonus,double defenceBonus,double healthBonus,double speedBonus){
            this.name=name;
            this.type=type;
            this.price=price;
            this.attackBonus=attackBonus;
            this.defenceBonus=defenceBonus;
            this.healthBonus=healthBonus;
            this.speedBonus=speedBonus;
        }
        public String getName(){
            return name;
        }
        public String getType(){
            return type;
        }
        public int getPrice(){
            return price;
        }
        public double getAttackBonus(){
            return attackBonus;
        }
        public double getDefenceBonus(){
            return defenceBonus;
        }
        public double getHealthBonus(){
            return healthBonus;
        }
        public double getSpeedBonus(){
            return speedBonus;
        }
    }
}
