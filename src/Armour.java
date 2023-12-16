import java.io.Serializable;

public class Armour implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String name;
  private int durability;
  private int defenseRating;

  public Armour(String name, int durability, int defenseRating) {
    this.name = name;
    this.durability = durability;
    this.defenseRating = defenseRating;
  }

  public void reduceDurability(int reduction) {
    durability -= reduction;
    if (durability <= 0) {
      System.out.println("Your " + name + " has been rendered useless. It cannot be repaired and must be sold for scraps.");
      defenseRating = 0;
    }
  }

  public void repairArmour(int amount) {
    durability += amount;
    if (durability > 100) {
      durability = 100;
    }
  }
  
  public int getDefenseRating() {
    return (int) (defenseRating * (durability / 100.0)) ;
  }
  
  @Override
  public String toString() {
    return name + ", Defense Rating: " + defenseRating + "\n";
  }
}