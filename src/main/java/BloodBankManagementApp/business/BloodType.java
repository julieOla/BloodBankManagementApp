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


    private Type bloodType;

    public enum Type {
        A_POS, A_NEG, B_POS, B_NEG, O_POS, O_NEG, AB_POS, AB_NEG
    }
}

