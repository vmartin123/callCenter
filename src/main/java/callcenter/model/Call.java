package callcenter.model;

import lombok.Data;

@Data
public class Call {
    private int id;
    private int duration;

    public Call() {
    }

    public Call(int id, int duration) {
        this.id = id;
        this.duration = duration;
    }
}
