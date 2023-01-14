package mr.mowitnow.mower.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mr.mowitnow.mower.model.enums.Direction;

@Data
@AllArgsConstructor
@Builder
public class Mower {
    private int x;
    private int y;
    private Direction direction;
}
