package forum.logic;

public class InputLengthHandler {
    public final static int TITLE_SIZE = 60;
    public final static int DESCRIPTION_SIZE = 100;
    public final static int MESSAGE_SIZE = 500;
    public final static int EMAIL_SIZE = 321;
    public final static int PASSWORD_SIZE = 64;
    public final static int USERNAME_SIZE = 60;



    public String checkLengthAndReturnValue(String input, int size){
        if (input.length() <= size){
            return input;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            String[] letters = input.split("");
            for (int i = 0; i < size; i++) {
                stringBuilder.append(letters[i]);
            }
            return stringBuilder.toString();
        }
    }
}
