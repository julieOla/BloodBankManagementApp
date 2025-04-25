package BloodBankManagementApp.business;

import lombok.Builder;

import java.util.Arrays;

public enum BloodTypes {
    A_POS("A+"), A_NEG("A-"), B_POS("B+"), B_NEG("B-"),
    O_POS("O+"), O_NEG("O-"), AB_POS("AB+"), AB_NEG("AB-");

    private String bloodType;
    BloodTypes(String s) {
        this.bloodType = s;
    }
    public String getBloodType(){
        return this.bloodType;
    }
    public static BloodTypes fromString(String value) {
        for (BloodTypes type : values()) {
            if (type.bloodType.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown blood type: " + value);
    }
    public static  void main(String[] args){

        System.out.println(Arrays.toString(BloodTypes.values()));
        //System.out.println(BloodTypes);
        for (BloodTypes bloodtype: BloodTypes.values()) {
            System.out.println(bloodtype.getBloodType());

        }
    }
}
