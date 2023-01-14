package mr.mowitnow.mower.model;

import lombok.Data;

import java.util.List;

@Data
public class Input {
    private Dimension dimension;
    private List<Mower> mowers;
    private List<String> ordersSequence;
}
