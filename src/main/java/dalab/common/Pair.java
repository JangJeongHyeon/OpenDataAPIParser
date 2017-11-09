package dalab.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jang Jeong Hyeon on 2017-09-14.
 */

@Getter
@Setter
@AllArgsConstructor
public class Pair<T,E> {
    private T first;
    private E second;
}
