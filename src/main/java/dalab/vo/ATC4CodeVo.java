package dalab.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jang Jeong Hyeon on 2017-09-14.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ATC4CodeVo {
    private double presentCode;
    private String name;
    private int companyCode;
    private String companyName;
    private String atcCode;
    private String atcCodeName;
    private String ingredient;
}
