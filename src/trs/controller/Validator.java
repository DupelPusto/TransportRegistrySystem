package trs.controller;

public class Validator {

    private static final int VIN_LENGTH = 10;
    private static final int ENG_CODE_LENGTH = 8;
    private static final int PH_NUMBER_LENGTH = 12;


    // VIN-code format XXX12345XX
    public static boolean isVinCodeValid(String vinCode){

        if (vinCode.length() != VIN_LENGTH) return false;

        for (int i = 0; i < VIN_LENGTH; i++) {
            if (i < 3 || i > 7){
                if (!Character.isLetter(vinCode.charAt(i))) return false;
            }
            if (i > 2 && i < 8){
                if (!Character.isDigit(vinCode.charAt(i))) return false;
            }
        }
        return true;
    }

    // Engine-code format XXX1234X
    public static boolean isEngineCodeValid(String engineCode){

        if (engineCode.length() != ENG_CODE_LENGTH) return false;

        for (int i = 0; i < ENG_CODE_LENGTH; i++) {
            if (i < 3 || i == ENG_CODE_LENGTH - 1){
                if (!Character.isLetter(engineCode.charAt(i))) return false;
            }
            if (i > 2 && i < ENG_CODE_LENGTH - 1){
                if (!Character.isDigit(engineCode.charAt(i))) return false;
            }
        }
        return true;
    }

    // Phone-number format 380XXXXXXXXX
    public static boolean isPhoneNumberValid(String number){

        if (number.length() != PH_NUMBER_LENGTH) return false;

        if (!number.startsWith("380")) return false;

        for (int i = 0; i < PH_NUMBER_LENGTH; i++) {
            if (!Character.isDigit(number.charAt(i))) return false;
        }
        return true;
    }

}
