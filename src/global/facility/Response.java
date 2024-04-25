package global.facility;

import java.io.Serializable;

public class Response  implements Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private String massage;
    private Object object;
    public Response (String massage){
        this.massage = massage;
    }

    public String getMessage(){
        return massage;
    }
}
