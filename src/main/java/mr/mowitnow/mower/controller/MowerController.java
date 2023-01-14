package mr.mowitnow.mower.controller;

import mr.mowitnow.mower.model.Input;
import mr.mowitnow.mower.model.Mower;
import mr.mowitnow.mower.service.MowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class MowerController {

    @Autowired
    MowerService mowerService;

    @PostMapping
    public ResponseEntity<List<Mower>> computeMowersFinalDestination(@RequestBody Input input) {
        return ResponseEntity.status(HttpStatus.OK).body(mowerService.computeMowersFinalDestination(input));
    }
}
