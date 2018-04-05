package callcenter.controller;

import callcenter.model.Call;
import callcenter.service.Dispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CallsController {

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public void test() {
    }

    @PostMapping("/v1/calls")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCall(@RequestBody Call call) {
        Dispatcher.getInstance().getIncomingCalls().add(call);
    }
}
