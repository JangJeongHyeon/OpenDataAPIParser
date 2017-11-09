package dalab.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * Created by Jang Jeong Hyeon on 2017-09-14.
 */

@Getter
@Setter
@EqualsAndHashCode
public class ATC4StepAPIVo {
    private String atcCode;
    private String atcCodeName;
    private String date;
    private int insuranceType;
    private int moneyOfUseAmount;
    private int recuClcd;
    private int sgguCd;
    private String sgguCdNm;
    private String sidoCdNm;
    private float totalUseQuantity;
}
