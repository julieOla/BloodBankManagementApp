package BloodBankManagementApp.business;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BloodType {

    private int bloodTypeID;
    private String bloodType;
    public enum BloodTypes {
        A_POS("A+"), A_NEG("A-"), B_POS("B+"), B_NEG("B-"),
        O_POS("O+"), O_NEG("O-"), AB_POS("AB+"), AB_NEG("AB-");

        private String bloodType;

        BloodTypes(String s) {
            this.bloodType = s;
        }

        public String getBloodType() {
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
    }

        //==========================================================================
    /*private int bloodTypeID;


    //private BloodType bloodType;
    private Type bloodType;
    public enum Type {
        A_POS, A_NEG, B_POS, B_NEG, O_POS, O_NEG, AB_POS, AB_NEG;
    }*/

        //==========================================================================
    /*public enum Type {
        A_POS("A+"), A_NEG("A-"), B_POS("B+"), B_NEG("B-"),
        O_POS("O+"), O_NEG("O-"), AB_POS("AB+"), AB_NEG("AB-");

        private final String label;
        Type(String label) {
            this.label = label;
        }

        public static Type fromString(String value) {
            for (Type type : values()) {
                if (type.label.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown blood type: " + value);
        }

        public int getBloodTypeID() {

            return getBloodTypeID();
        }
    }*/

}

