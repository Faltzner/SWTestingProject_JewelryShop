package collections;

public class Employee {
    private String id;
    private String name;
    private String numPhone;
    private String password;
    private int numOfWork;
    private String necklace;
    private String earrings;
    private String ring;
    private String bracelet;

    public Employee(String id, String name, String numPhone, String password, int numOfWork) {
        this.id = id;
        this.name = name;
        this.numPhone = numPhone;
        this.password = password;
        this.numOfWork = numOfWork;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumPhone() {
        return numPhone;
    }

    public String getPassword() {
        return password;
    }

    public int getNumOfWork() {
        return numOfWork;
    }

    public String getNecklaceSkill() {
        return necklace;
    }

    public String getEarringsSkill() {
        return earrings;
    }

    public String getBraceletSkill() {
        return bracelet;
    }

    public String getRingSkill() {
        return ring;
    }

    void setNecklace(String necklace) {
        this.necklace = necklace;
    }

    public void setEarrings(String earrings) {
        this.earrings = earrings;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public void setBracelet(String bracelet) {
        this.bracelet = bracelet;
    }

}