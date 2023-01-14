package mr.mowitnow.mower.service;

import mr.mowitnow.mower.model.Dimension;
import mr.mowitnow.mower.model.Input;
import mr.mowitnow.mower.model.Mower;
import mr.mowitnow.mower.model.enums.Direction;
import mr.mowitnow.mower.model.enums.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
public class MowerService {


    public List<Mower> computeMowersFinalDestination(Input input) {
        if(!isValidInput(input)) {
            throw new IllegalArgumentException(String.format("Invalid Input %s", input));
        }
        List<Mower> mowers = new ArrayList<>();
        for (int i = 0; i < input.getMowers().size(); i++) {
            mowers.add(
                    computeMowerFinalDestination(input.getDimension(), input.getMowers().get(i), input.getOrdersSequence().get(i)
                    ));
        }
        return mowers;
    }

    private boolean isValidInput(Input input) {
        if(input.getMowers().size() != input.getOrdersSequence().size())
            return false;
        for(Mower mower : input.getMowers()) {
            if(!isValidPosition(input.getDimension(), mower)) return false;
        }
        return true;
    }

    private Mower computeMowerFinalDestination(
            Dimension dimension,
            Mower currentMower,
            String orderSequence) {
        for (int i = 0; i < orderSequence.length(); i++) {
            currentMower = moveMower(dimension, currentMower, String.valueOf(orderSequence.charAt(i)));
        }

        return currentMower;
    }

    private Mower moveMower(Dimension dimension, Mower currentMower, String order) {
        return computeNextMove(currentMower, order, dimension);
    }

    private Mower computeNextMove(Mower currentMower, String order, Dimension dimension) {
        if(!order.equals(Order.A.name())) {
            currentMower = rotate(currentMower, order);
            return currentMower;
        }
        return moveToNextCell(currentMower, dimension);
    }

    private Mower rotate(Mower currentMower, String order) {

        Direction currentDirection = currentMower.getDirection();

        if(Objects.equals(order, Order.G.name())) {
            if (Objects.equals(currentDirection.name(), Direction.E.name())) currentMower.setDirection(Direction.N);
            if (Objects.equals(currentDirection.name(), Direction.N.name())) currentMower.setDirection(Direction.W);
            if (Objects.equals(currentDirection.name(), Direction.W.name())) currentMower.setDirection(Direction.S);
            if (Objects.equals(currentDirection.name(), Direction.S.name())) currentMower.setDirection(Direction.E);
        }

        if(Objects.equals(order, Order.D.name())) {
            if (Objects.equals(currentDirection.name(), Direction.E.name())) currentMower.setDirection(Direction.S);
            if (Objects.equals(currentDirection.name(), Direction.S.name())) currentMower.setDirection(Direction.W);
            if (Objects.equals(currentDirection.name(), Direction.W.name())) currentMower.setDirection(Direction.N);
            if (Objects.equals(currentDirection.name(), Direction.N.name())) currentMower.setDirection(Direction.E);
        }

        return currentMower;
    }

    private Mower moveToNextCell(Mower currentMower, Dimension dimension) {

        Mower nextMower = Mower.builder()
                .x(currentMower.getX())
                .y(currentMower.getY())
                .direction(currentMower.getDirection())
                .build();

        if(Objects.equals(nextMower.getDirection().name(), Direction.E.name()))
            nextMower.setX(nextMower.getX() + 1);
        if(Objects.equals(nextMower.getDirection().name(), Direction.N.name()))
            nextMower.setY(nextMower.getY() + 1);
        if(Objects.equals(nextMower.getDirection().name(), Direction.W.name()))
            nextMower.setX(nextMower.getX() - 1);
        if(Objects.equals(nextMower.getDirection().name(), Direction.S.name()))
            nextMower.setY(nextMower.getY() - 1);

        if(!isValidPosition(dimension, nextMower)) return currentMower;

        return nextMower;
    }

    private boolean isValidPosition(Dimension dimension, Mower currentMower) {
        return (currentMower.getX() >= 0 && currentMower.getX() <= dimension.getWidth())
                && (currentMower.getY() >= 0 && currentMower.getY() <= dimension.getHeight());
    }
}
