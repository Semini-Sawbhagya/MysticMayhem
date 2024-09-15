package mysticmayhem;


import java.util.ArrayList;
import java.util.Scanner;


public class Main{
    public static Player[] arr;

    static {
        try{
            arr = new Player[]{new Player("whitewolf","GeraltofRivia",32,215,"Marshland"),new Player("Xavior","FunnyCat",40,200,"Desert"),new Player("Ginny","BlackPanther",20,50,"Arcane")};
        } catch (Player.UsernameTakenException e) {
            throw new RuntimeException(e);
        }
        try {
            arr[0].getArmy().add(new Character("Ranger"));
            arr[0].getArmy().add(new Character("Squire"));
            arr[0].getArmy().add(new Character("Warlock"));
            arr[0].getArmy().add(new Character("Medic"));
            arr[0].getArmy().add(new Character("Dragon"));
            arr[1].getArmy().add(new Character("Sunfire"));
            arr[1].getArmy().add(new Character("Cavalier"));
            arr[1].getArmy().add(new Character("Warlock"));
            arr[1].getArmy().add(new Character("Medic"));
            arr[1].getArmy().add(new Character("Dragon"));
            arr[2].getArmy().add(new Character("Ranger"));
            arr[2].getArmy().add(new Character("Squire"));
            arr[2].getArmy().add(new Character("Illusionist"));
            arr[2].getArmy().add(new Character("Soother"));
            arr[2].getArmy().add(new Character("Basilisk"));
        } catch (Character.UnknownCharacterException e) {
            throw new RuntimeException(e);
        }
        try {
            arr[0].getArmy().get(0).equip(new Equipment("Chainmail"));
            arr[0].getArmy().get(3).equip(new Equipment("Amulet"));
            arr[1].getArmy().get(4).equip(new Equipment("Chainmail"));
            arr[2].getArmy().get(1).equip(new Equipment("Amulet"));

        } catch (Equipment.UnknownEquipmentException e) {
            throw new RuntimeException(e);
        }


    }

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
            // Handle any exceptions.
        }
    }
    public  static Player[] extend_Array(Player arr[]){
        Player []temp=new Player[arr.length+1];
        for(int j=0;j<arr.length;j++){
            temp[j]=arr[j];
        }
        return temp;
    }
    public static  boolean isExists(int idd,Player []arr){

        int count=0;
        for(int i=0;i<arr.length;i++){
            if(idd==arr[i].getUserId()){
                count++;
            }
        }
        if(count>0) return true;
        else{
            return false;
        }
    }
    public static int getIndex(int idd,Player[]arr) {
        int n=0;
        for (int i = 0; i < arr.length; i++) {
            if(idd==arr[i].getUserId()){
                n=i;
                break;
            }
        }
        return n;
    }
    public static void Create_User_Profile() {
        Scanner input = new Scanner(System.in);
        clearConsole();
        System.out.println("        -------------------------------------         ");
        System.out.println("      |             Create User Profile          |          ");
        System.out.println("        -------------------------------------         ");
        System.out.println("");

        do {
            arr = extend_Array(arr);
            int l = arr.length - 1;

            try {
                System.out.print("Enter player Name  :");
                String n = input.next();
                System.out.println("");

                System.out.print("Enter player UserName  :");
                String u = input.next();

                System.out.println("Choose a home ground");
                System.out.println("[1]-Hillcrest\n[2]-Marshland\n[3]-Desert\n[4]-Arcane");
                System.out.print("Enter an option to continue >");
                int num = input.nextInt();
                input.nextLine();
                String h = "";
                switch (num) {
                    case (1):
                        h = "Hillcrest";
                        break;
                    case (2):
                        h = "Marshland";
                        break;
                    case (3):
                        h = "Desert";
                        break;
                    case (4):
                        h = "Arcane";
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a valid home ground.");
                        continue;
                }

                arr[l] = new Player(u, n, 1, 500, h);

            } catch (Player.UsernameTakenException e) {
                System.out.println("The Player ID already exists");
                continue;
            }

            int id = arr[l].getUserId();
            System.out.println("   \t =>   " + id + " is your id. Please keep this in mind to play.");
            System.out.print("Player has been added successfully. Do you want to add a new player (y/n):");
            String ss = input.next();
            if (!ss.equals("y")) {
                break;
            }

        } while (true);
    }

    public static Player getPlayerObject(int idd, Player[] arr){
        int n=getIndex(idd,arr);
        return arr[n];
    }

    public static void Update_Player_Details() {
        Scanner input = new Scanner(System.in);
        do {
            clearConsole();
            System.out.println("                -------------------------------------------------------");
            System.out.println("                                 UPDATE PLAYER DETAILS                ");
            System.out.println("                -------------------------------------------------------\n");

            System.out.print("Enter Player UserID  :");
            int idd = input.nextInt();
            int k = getIndex(idd, Main.arr);

            if (!isExists(idd, Main.arr)) {
                System.out.println("Invalid UserID. Do you want to search again?(y/n)");
                continue;
            } else {
                System.out.println("Player user name  :" + Main.arr[k].getUserName() + '\n');
                System.out.println("Player name  :" + Main.arr[k].getName() + '\n');
            }

            // Consume the newline character left in the buffer
            input.nextLine();

            System.out.print("Enter the new player name :");
            String bb = input.nextLine();
            Main.arr[k].setName(bb);

            System.out.println("\nPlayer details have been updated successfully.");
            System.out.print("Do you want to update another player's details? (y/n): ");
            String sss = input.next();

            if (sss.equals("y")) {
                continue;
            } else {
                break;
            }

        } while (true);
    }

    public static void deletePlayer() {
        Scanner input = new Scanner(System.in);
        do {
            clearConsole();
            System.out.println("               --------------------------------------------------------");
            System.out.println("               |                    DELETE PLAYER                     | ");
            System.out.println("               --------------------------------------------------------\n");

            System.out.print("Enter Player ID  :");
            int idd = input.nextInt();
            int k = getIndex(idd, Main.arr);

            if (!isExists(idd, Main.arr)) {
                System.out.println("Invalid Player ID. Do you want to search again? (y/n)");
                continue;
            } else {
                Player[] temp = new Player[Main.arr.length - 1];
                int count = 0;

                for (int i = 0; i < Main.arr.length; i++) {
                    if (i == k) {
                        continue;
                    }
                    temp[count] = Main.arr[i];
                    count++;
                }

                Main.arr = temp;
                System.out.println("Player has been deleted successfully.\nDo you want to delete another player? (y/n)");
                String sss = input.next();
                if (sss.equals("y")) {
                    continue;
                } else {
                    break;
                }
            }

        } while (true);
    }


    public static void BuyCharacters() {
        Scanner input = new Scanner(System.in);

        do {
            clearConsole();
            System.out.println("                --------------------------------------------------------------------------------");
            System.out.println("                                             BUY CHARACTERS FIRST TIME                          ");
            System.out.println("                ------------------------------------------------------------------------------\n");

            try {
                System.out.println("Enter player id :");
                System.out.println("If you forget the id, it has already been given above. Check it.........");
                int id = input.nextInt();

                // Check if the user id exists
                if (!isExists(id, Main.arr)) {
                    System.out.println("Invalid UserID. Do you want to search again? (y/n)");
                    break;
                }

                Player p = getPlayerObject(id, Main.arr);
                p.setgoldcoins(500);

                String ch1, ch2, ch3, ch4, ch5;

                System.out.println("----Choose the characters----");

                // Handle Archer input
                System.out.println("Enter Archer name :");
                System.out.println("\t\t# Shooter\n\t\t# Ranger\n\t\t# Sunfire\n\t\t# Zing");
                ch1 = input.next();
                Character c1 = new Character(ch1);
                p.addCharacter(c1);

                // Handle Knight input
                System.out.println("Enter Knight name :");
                System.out.println("\t\t# Squire\n\t\t# Cavalier\n\t\t# Templar\n\t\t# Zoro");
                ch2 = input.next();
                Character c2 = new Character(ch2);
                p.addCharacter(c2);

                // Handle Mage input
                System.out.println("Enter Mage name :");
                System.out.println("\t\t# Warlock\n\t\t# Illusionist\n\t\t# Enchanter\n\t\t# Conjurer");
                ch3 = input.next();
                Character c3 = new Character(ch3);
                p.addCharacter(c3);

                // Handle Healer input
                System.out.println("Enter Healer name :");
                System.out.println("\t\t# Soother\n\t\t# Medic\n\t\t# Alchemist\n\t\t# Saint");
                ch4 = input.next();
                Character c4 = new Character(ch4);
                p.addCharacter(c4);

                // Handle Mythical Creature input
                System.out.println("Enter Mythical Creature name :");
                System.out.println("\t\t# Dragon\n\t\t# Basilisk\n\t\t# Hydra\n\t\t# Phoenix");
                ch5 = input.next();
                Character c5 = new Character(ch5);
                p.addCharacter(c5);

                System.out.println("");
                System.out.print("Do you want to buy characters for another player? (y/n)");
                String ss = input.next();
                if (!ss.equals("y")) {
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }


        } while (true);
    }
    public static void sellCharacters() throws Player.CharacterTypeLimitException, Character.UnknownCharacterException {
        Scanner input = new Scanner(System.in);

        do {
            clearConsole();
            System.out.println("                -----------------------------------------------------------------");
            System.out.println("                                            SELL CHARACTERS                      ");
            System.out.println("                ---------------------------------------------------------------\n");

            System.out.println("Enter player id :");
            int id = input.nextInt();

            if (!isExists(id, Main.arr)) {
                System.out.println("Invalid UserID. Do you want to search again? (y/n)");
                break;
            } else {
                Player p = getPlayerObject(id, Main.arr);
                ArrayList<Character> army = p.getArmy();

                if (army.isEmpty()) {
                    System.out.println("Sorry! You have no characters to sell.");
                    break;
                }

                System.out.println("You have:");
                for (int i = 0; i < army.size(); i++) {
                    System.out.println(army.get(i).getName());
                }

                System.out.println("Enter the character name that you want to sell:");
                String ch1 = input.next();

                boolean found = false;
                for (int i = 0; i < p.getArmy().size(); i++) {
                    if (p.getArmy().get(i).getName().equals(ch1)) {
                        p.sellCharacter(p.getArmy().get(i));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Character not found in your army. Please enter a valid character name.");
                    continue;
                }
            }

            System.out.println("Sold the character successfully.\nDo you want to sell another character? (y/n)");
            String sss = input.next();
            if (sss.equals("y")) {
                continue;
            } else {
                break;
            }

        } while (true);
    }

    public static void BuyEquipments() throws Equipment.UnknownEquipmentException {
        Scanner input = new Scanner(System.in);
        do {
            clearConsole();
            System.out.println("                -----------------------------------------------------------------");
            System.out.println("                                             BUY EQUIPMENTS                      ");
            System.out.println("                ---------------------------------------------------------------\n");
            System.out.println("Enter player id :");
            int id = input.nextInt();
            Player p = getPlayerObject(id, Main.arr);
            System.out.println("Enter the character name that you want to buy equipments:");
            String ch1 = input.next();
            System.out.println("");
            System.out.println("Choose an option......");
            System.out.println("[1]-Buy an armour only\n[2]- Buy an Artefacts only.\n[3]-Buy both Armour and Artefacts");
            System.out.print("Enter an option to continue >");
            int num = input.nextInt();
            input.nextLine();
            switch (num) {
                case (1):
                    System.out.println("Enter the armour name  :");
                    System.out.println("\t\t#Chainmail.\n\t\t#Regalia. \n\t\t#Fleece. ");
                    String n1 = input.next();
                    Equipment c1 = new Equipment(n1);
                    for (int i = 0; i < p.getArmy().size(); i++) {
                        if (p.getArmy().get(i).getName().equals(ch1)) {

                            p.buyEquipment(p.getArmy().get(i),c1 );

                        }
                    }
                    break;
                case (2):
                    System.out.println("Enter the artefacts name  :");
                    System.out.println("\t\tExcalibur.\n\t\tAmulet\n\t\tFleece");
                    String n2 = input.next();
                    Equipment c2 = new Equipment(n2);
                    for (int i = 0; i < p.getArmy().size(); i++) {
                        if (p.getArmy().get(i).getName() == ch1) {
                            p.buyEquipment(p.getArmy().get(i),c2 );
                        }
                    }
                    break;
                case (3):
                    System.out.println("Enter the armour name  :");
                    System.out.println("\t\tChainmail.+\n\t\tRegalia. +\n\t\tFleece. ");
                    String n3 = input.next();
                    Equipment c3 = new Equipment(n3);
                    for (int i = 0; i < p.getArmy().size(); i++) {
                        if (p.getArmy().get(i).getName() == ch1) {

                            p.buyEquipment(p.getArmy().get(i),c3 );
                        }
                    }
                    System.out.println("Enter the artefacts name  :");
                    System.out.println("\t\tExcalibur.\n\t\tAmulet\n\t\tFleece");
                    String n4 = input.next();
                    Equipment c4 = new Equipment(n4);
                    for (int i = 0; i < p.getArmy().size(); i++) {
                        if (p.getArmy().get(i).getName() == ch1) {
                            p.buyEquipment(p.getArmy().get(i),c4 );
                        }
                    }

                    break;
            }
            break;
        }while(true);
        }
        public static void Battle() throws Player.CharacterTypeLimitException, Character.UnknownCharacterException {
            Scanner input = new Scanner(System.in);
            do {
                clearConsole();
                System.out.println("                --------------------------------------------------------------------");
                System.out.println("                                           WELCOME TO BATTLE                        ");
                System.out.println("                ------------------------------------------------------------------\n");
                System.out.println("Enter the user ID  :");
                int id=input.nextInt();
                if (!isExists(id, Main.arr)) {
                    System.out.println("Invalid UserID. Do you want to search again?(y/n)");
                    continue;
                }else {
                    Player player1 = getPlayerObject(id, Main.arr);
                    if(player1.getArmy().size()<5){
                        System.out.println("Your Army Is Incomplete. Cannot continue the battle!  ");
                        break;
                    }else {
                        System.out.println("Select an opponent    :");
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].getUserId() == id) {
                                continue;
                            }
                            System.out.println("Player name  :" + arr[i].getName() + "\t\t\t" + "XP Level  :" + arr[i].getXP());
                        }

                        System.out.println("Enter the name of the player  :");
                        String name = input.next();
                        int count=0;
                        for (int i = 0; i < arr.length; i++) {
                            if((arr[i].getName().equals(name))){
                                count++;
                            }
                        }
                        if(count==0){
                            System.out.println("Invalid player name enter again....");
                            continue;
                        }
                        Player player2=null;
                        for (int i = 0; i < arr.length; i++) {
                            if (name.equals(arr[i].getName())) {
                                player2 = arr[i];
                            }
                        }

                        Combat c1 = new Combat(player1, player2);
                        c1.startBattle();
                        System.out.println("Press Enter to continue...");
                        input.nextLine(); // Consume the newline character
                        input.nextLine(); // Wait for user input
                    }
                }
                break;
            }while(true);

        }
    public static void BuynewCharacters() throws Character.UnknownCharacterException, Player.CharacterTypeLimitException {
        Scanner input = new Scanner(System.in);

        do {
            clearConsole();
            System.out.println("                ----------------------------------------------------------------------------------");
            System.out.println("                                             BUY CHARACTERS AFTER SELLING                         ");
            System.out.println("                ---------------------------------------------------------------------------------\n");

            try {
                System.out.println("Enter player id:");
                System.out.println("If you forget the id, it has already been given above. Check it.........");
                int id = input.nextInt();

                if (!isExists(id, Main.arr)) {
                    System.out.println("Invalid UserID. Do you want to search again? (y/n)");
                    break;
                }

                Player p = getPlayerObject(id, Main.arr);
                int g = p.getgoldcoins();
                boolean a = false;
                boolean k = false;
                boolean m = false;
                boolean h = false;
                boolean mc = false;

                ArrayList<Character> temp = p.getArmy();
                for (int i = 0; i < temp.size(); i++) {
                    if ((temp.get(i).getType().equals("Archer"))) {
                        a = true;
                    }
                    if ((temp.get(i).getType().equals("Knight"))) {
                        k = true;
                    }
                    if ((temp.get(i).getType().equals("Mage"))) {
                        m = true;
                    }
                    if ((temp.get(i).getType().equals("Healer"))) {
                        h = true;
                    }
                    if ((temp.get(i).getType().equals("MythicalCreature"))) {
                        mc = true;
                    }
                }

                String ch1, ch2, ch3, ch4, ch5;

                System.out.println("----Choose the characters----");

                if (!a) {
                    System.out.print("Do you want to buy an Archer? (y/n): ");
                    if (input.next().equalsIgnoreCase("y")) {
                        System.out.println("Enter Archer name :");
                        System.out.println("\t\t# Shooter\n\t\t# Ranger\n\t\t# Sunfire\n\t\t# Zing");
                        ch1 = input.next();
                        Character c1 = new Character(ch1);
                        p.addCharacter(c1);
                    }else {
                        System.out.println("You can't continue the battle without this character. So you have to buy this character.....");
                    }
                }

                if (!k) {
                    System.out.print("Do you want to buy a Knight? (y/n): ");
                    if (input.next().equalsIgnoreCase("y")) {
                        System.out.println("Enter Knight name :");
                        System.out.println("\t\t# Squire\n\t\t# Cavalier\n\t\t# Templar\n\t\t# Zoro");
                        ch2 = input.next();
                        Character c2 = new Character(ch2);
                        p.addCharacter(c2);
                    }else {
                        System.out.println("You can't continue the battle without this character. So you have to buy this character.....");
                    }
                }

                if (!m) {
                    System.out.print("Do you want to buy a Mage? (y/n): ");
                    if (input.next().equalsIgnoreCase("y")) {
                        System.out.println("Enter Mage name :");
                        System.out.println("\t\t# Warlock\n\t\t# Illusionist\n\t\t# Enchanter\n\t\t# Conjurer");
                        ch3 = input.next();
                        Character c3 = new Character(ch3);
                        p.addCharacter(c3);
                    }else {
                        System.out.println("You can't continue the battle without this character. So you have to buy this character.....");
                    }
                }

                if (!h) {
                    System.out.print("Do you want to buy a Healer? (y/n): ");
                    if (input.next().equalsIgnoreCase("y")) {
                        System.out.println("Enter Healer name :");
                        System.out.println("\t\t# Soother\n\t\t# Medic\n\t\t# Alchemist\n\t\t# Saint");
                        ch4 = input.next();
                        Character c4 = new Character(ch4);
                        p.addCharacter(c4);
                    }else {
                        System.out.println("You can't continue the battle without this character. So you have to buy this character.....");

                    }
                }

                if (!mc) {
                    System.out.print("Do you want to buy a Mythical Creature? (y/n): ");
                    if (input.next().equalsIgnoreCase("y")) {
                        System.out.println("Enter Mythical Creature name :");
                        System.out.println("\t\t# Dragon\n\t\t# Basilisk\n\t\t# Hydra\n\t\t# Phoenix");
                        ch5 = input.next();
                        Character c5 = new Character(ch5);
                        p.addCharacter(c5);
                    }else {
                        System.out.println("You can't continue the battle without this character. So you have to buy this character.....");
                    }
                }

            } catch (Character.UnknownCharacterException | Player.CharacterTypeLimitException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (true);
    }

    public static void main(String args[]) throws Player.CharacterTypeLimitException, Character.UnknownCharacterException, Equipment.UnknownEquipmentException, Player.UsernameTakenException {
        Scanner input = new Scanner(System.in);

        do {
            clearConsole();
            System.out.println("              -------------------------------------------------------------------");
            System.out.println("              |                 Hello   Welcome   MysticMayhem                  |");
            System.out.println("              -------------------------------------------------------------------");
            System.out.println("");
            System.out.println("[1] Create user profiles                      \t\t [2] Update user name");
            System.out.println("[3] Delete Player Profile                     \t\t [4] Buy Characters first time");
            System.out.println("[5] Sell characters                           \t\t [6] Buy Equipments");
            System.out.println("[7] Battle                                    \t\t [8] Buy Characters after selling");
            System.out.println("");
            System.out.print("Enter an option to continue >");

            try {
                int num = input.nextInt();
                input.nextLine();

                switch (num) {
                    case 1:
                        Create_User_Profile();
                        break;
                    case 2:
                        Update_Player_Details();
                        break;
                    case 3:
                        deletePlayer();
                        break;
                    case 4:
                        BuyCharacters();
                        break;
                    case 5:
                        sellCharacters();
                        break;
                    case 6:
                        BuyEquipments();
                        break;
                    case 7:
                        Battle();
                        break;
                    case 8:
                        BuynewCharacters();
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a number between 1 and 8.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }

        } while (true);
    }

}
