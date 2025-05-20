package untildawn.practice.Model.Enum;

import com.badlogic.gdx.Input;

public enum ControlKeys {
    GO_UP(Input.Keys.W),
    GO_DOWN(Input.Keys.S),
    GO_LEFT(Input.Keys.A),
    GO_RIGHT(Input.Keys.D),
    ;

    private int keyCode;
    ControlKeys(int keyCode) {
        this.keyCode = keyCode;
    }
    public int getKeyCode() {
        return keyCode;
    }
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
