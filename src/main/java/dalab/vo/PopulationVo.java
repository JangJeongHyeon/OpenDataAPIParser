package dalab.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jang Jeong Hyeon on 2017-08-09.
 */

@Getter
@Setter
@Data
public class PopulationVo {
    private int year;
    private int gender;
    private int admCode;
    private String admName;
    private float avgAge;
    private int ageType;
    private int population;
}
